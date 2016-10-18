package njit.cs656.QMT.movieRecommendation.core.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;
import njit.cs656.QMT.movieRecommendation.core.service.SimilartityEntry;


/**
 * <p>
 * DataBaseConnection
 * </p>
 *
 * @author qiyi
 * @version 2015-11-13
 */
public class DataBaseConnection {
   
    private Connection connection = null;
    private int batchSize = 5000;
    public DataBaseConnection(){       
        String connectionURL = "jdbc:mysql://" + SystemPara.connectionURL;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            connection =  DriverManager.getConnection(connectionURL + "?rewriteBatchedStatements=true", SystemPara.userName, SystemPara.password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void resetAllData(){
        // unimplemented
        String drop = "Drop DATABASE qmt";
        String isExist = "SHOW DATABASES";
        String createDataBase = "Create DataBase qmt";
        String selectDataBase = "USE qmt";
        String createTable_movie = "Create TABLE movie(id int primary key, title VARCHAR(200), genres VARCHAR(200), issueTime int, avgRating decimal(2,1))";
        String createTable_user = "Create TABLE user(id int, recommendMovieid int, similarity double)";
        String createTable_ratings = "Create TABLE ratings(userId int, movieId int, rating decimal(2,1), primary key(userId, movieId))";
        String createTable_similarMovies = "Create TABLE similarMovies(movieId int, relatedMovieID int, similarity double, primary key(movieId, relatedMovieID))";
        Statement stat =null;
        ResultSet rs = null;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery(isExist);
            boolean exist = false;
            while (rs.next()) if (rs.getString("Database").equals("qmt")) exist = true;
            // if exist, drop table first
            if (exist) stat.executeUpdate(drop);
            // create new database
            stat.executeUpdate(createDataBase);
            // select database
            stat.execute(selectDataBase);
            // create table
            stat.executeUpdate(createTable_movie);
            stat.executeUpdate(createTable_user);
            stat.executeUpdate(createTable_ratings);
            stat.executeUpdate(createTable_similarMovies);
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            try{
                if (rs != null) rs.close();
                if (stat != null) stat.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void insertUsers(List<User> userList){
        Statement stat = null;
        PreparedStatement stat2 = null;
        PreparedStatement stat3 = null;
        try {
            String selectDataBase = "USE qmt";
            stat = connection.createStatement();
            stat.executeUpdate(selectDataBase);
            // insert user
            String insertUser = "Insert into user values(?,?,?)";
            String insertRatings = "Insert into ratings values(?,?,?)";
            int b = 0;
            stat2 = connection.prepareStatement(insertUser);
            stat2.clearBatch();
            // set user 
            for (User u : userList){
                UserImpl user = (UserImpl)u;
                if (user.getRecommendMovies().size() == 0){
                    b++;
                    stat2.setInt(1,u.getUserID());
                    stat2.setInt(2,-1); // -1 represent null
                    stat2.setDouble(3,-1); // -1 represent null
                    stat2.addBatch();
                    if (b == batchSize){
                        stat2.executeBatch();
                        connection.commit();
                        stat2.clearBatch();
                        b = 0;
                    } 
                }
                else
                for (SimilartityEntry entry : user.getRecommendMovies()){
                    b++;
                    stat2.setInt(1,u.getUserID());
                    stat2.setInt(2,entry.getKey());
                    stat2.setDouble(3,entry.getSimilarity());
                    stat2.addBatch();
                    if (b == batchSize){
                        stat2.executeBatch();
                        connection.commit();
                        stat2.clearBatch();
                        b = 0;
                    }
                }
            }
            if (b != 0){
                stat2.executeBatch();
                connection.commit();
                stat2.clearBatch();
                b = 0;
            }
            // set rating
            stat3 = connection.prepareStatement(insertRatings);
            stat3.clearBatch();
            for (User u : userList){
                Map<Integer, Double> ratings = ((UserImpl)(u)).getRatings();
                for (int i : ratings.keySet()){
                    b++;
                    stat3.setInt(1,u.getUserID());
                    stat3.setInt(2,i);
                    stat3.setDouble(3,ratings.get(i));
                    stat3.addBatch();
                    if (b == batchSize){
                        stat3.executeBatch();
                        connection.commit();
                        stat3.clearBatch();
                        b = 0;
                    }
                }
                if (b != 0){
                    stat3.executeBatch();
                    connection.commit();
                    stat3.clearBatch();
                    b = 0;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (stat != null) stat.close();
                if (stat2 != null) stat2.close();
                if (stat3 != null) stat3.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void insertMovies(List<Movie> Movies){
        Statement stat = null;
        PreparedStatement stat2 = null;
        PreparedStatement stat3 = null;
        try {
            String selectDataBase = "USE qmt";
            stat = connection.createStatement();
            stat.executeUpdate(selectDataBase);
            // insert movie
            String insertMovie = "Insert into movie values(?,?,?,?,?)";
            String insertMovieSimilarity = "Insert into similarMovies values(?,?,?)";
            
            int b = 0;
            stat2 = connection.prepareStatement(insertMovie);
            stat2.clearBatch();
            // set movie 
            for (Movie m : Movies){
                b++;
                stat2.setInt(1,m.getID());
                stat2.setString(2,m.getTitle());
                // get genres
                StringBuilder genres = new StringBuilder();
                for (int i = 0; i < m.getGenres().size(); i++){
                    String s = m.getGenres().get(i);
                    genres.append(s);
                    if (i != m.getGenres().size() - 1) genres.append("|");
                }
                stat2.setString(3,genres.toString());
                stat2.setInt(4,m.getIssueTime());
                stat2.setDouble(5,m.getAvgRating());
                stat2.addBatch();
                if (b == batchSize){
                    stat2.executeBatch();
                    connection.commit();
                    stat2.clearBatch();
                    b = 0;
                }
            }
            if (b != 0){
                stat2.executeBatch();
                connection.commit();
                stat2.clearBatch();
                b = 0;
            }
            // set movieSimilarity
            stat3 = connection.prepareStatement(insertMovieSimilarity);
            stat3.clearBatch();
            for (Movie m : Movies){
                List<SimilartityEntry> similarMovies = ((MovieImpl)(m)).getSimilarMovies();
                for (SimilartityEntry entry : similarMovies){
                    b++;
                    stat3.setInt(1,m.getID());
                    stat3.setInt(2,entry.getKey());
                    stat3.setDouble(3,entry.getSimilarity());
                    stat3.addBatch();
                    if (b == batchSize){
                        stat3.executeBatch();
                        connection.commit();
                        stat3.clearBatch();
                        b = 0;
                    }
                }
                if (b != 0){
                    stat3.executeBatch();
                    connection.commit();
                    stat3.clearBatch();
                    b = 0;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (stat != null) stat.close();
                if (stat2 != null) stat2.close();
                if (stat3 != null) stat3.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public List<User> getUserList(){
        Statement stat = null;
        Statement stat2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        List<User> list = new ArrayList<User>();
        try {
            connection.setAutoCommit(true);
            //select dataBase
            String selectDataBase = "USE qmt";
            stat = connection.createStatement();
            stat.executeUpdate(selectDataBase);
            // set user information
            String getUser = "select * from user order by id asc, similarity desc";
            rs = stat.executeQuery(getUser);
            int lastUser = -1; // the id of last scanned user 
            int id = -1;
            HashMap<Integer, UserImpl> map = new HashMap<Integer, UserImpl>();
            UserImpl user = null; 
            while (rs.next()){
                id = rs.getInt(1);
                if (id != lastUser){
                    if (user != null) {
                        list.add(user);
                        map.put(lastUser, user);
                    }
                    user = new UserImpl();
                    lastUser = id;
                    user.setId(id);
                }
                SimilartityEntryImpl entry = new SimilartityEntryImpl(rs.getInt(2), rs.getDouble(3));
                user.setRecommendMovie(entry);
            }
            // deal with the last user
            if (user != null) {
                list.add(user);
                map.put(lastUser, user);
            }
            // set user ratings
            String getRatings = "select * from ratings";
            stat2 = connection.createStatement();
            rs2 = stat2.executeQuery(getRatings);
            while (rs2.next()) {
                UserImpl u = map.get(rs2.getInt(1));
                u.setRating(rs2.getInt(2), rs2.getDouble(3));
            }
            connection.setAutoCommit(false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (rs != null) rs.close();
                if (rs2 != null) rs.close();
                if (stat != null) stat.close();
                if (stat2 != null) stat2.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<Movie> getMovieList(){
        Statement stat = null;
        Statement stat2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        List<Movie> list = new ArrayList<Movie>();
        try {
            connection.setAutoCommit(true);
            //select dataBase
            String selectDataBase = "USE qmt";
            stat = connection.createStatement();
            stat.executeUpdate(selectDataBase);
            // get movie information
            String getMovie = "select * from movie";
            rs = stat.executeQuery(getMovie);
            HashMap<Integer, MovieImpl> map = new HashMap<Integer, MovieImpl>();
            while (rs.next()){
                MovieImpl m = new MovieImpl(); 
                m.setId(rs.getInt(1));
                m.setTitle(rs.getString(2));
                for (String s : rs.getString(3).split("\\|")) m.setGenre(s);
                m.setIssueTime(rs.getInt(4));
                m.setAvgRating(rs.getDouble(5));
                list.add(m);
                map.put(rs.getInt(1), m);
            }
            // set similar movies
            String getSimilarMovies = "select * from similarMovies";
            stat2 = connection.createStatement();
            rs2 = stat2.executeQuery(getSimilarMovies);
            while (rs2.next()) {
                MovieImpl m = map.get(rs2.getInt(1));
                SimilartityEntryImpl entry = new SimilartityEntryImpl(rs2.getInt(2), rs2.getDouble(3));
                m.setSimilarMovie(entry);
            }
            connection.setAutoCommit(false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (rs != null) rs.close();
                if (rs2 != null) rs.close();
                if (stat != null) stat.close();
                if (stat2 != null) stat2.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }
    public void close(){
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
