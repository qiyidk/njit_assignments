package njit.cs631.group2.generateData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import njit.cs631.group2.core.Document.DocumentType;
import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;
import njit.cs631.group2.core.internal.Cache;
import njit.cs631.group2.core.internal.DataBaseConnection;
import njit.cs631.group2.core.internal.ExtractDataFromCSV;
import njit.cs631.group2.core.internal.SystemPara;

/**
 * <p>
 * DataBaseInitiate
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class DataBaseInitiate {
    
    private Connection connection = null;
    private DataBaseConnection db = null;
    
    public void init(){
        System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server is starting...");
        try{
            if (SystemPara.reset){
                // reset data
                System.out.println(new Date(System.currentTimeMillis()).toString() + ": reset database...");
                init2();
            }
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server has started...");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    
    
    
    public void init2(){
        
        try{
            db = new DataBaseConnection();
            connection = db.getConnection();
            resetDataBase();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    private void resetDataBase(){
        String drop = "Drop DATABASE " + SystemPara.database;
        String isExist = "SHOW DATABASES";
        String createDataBase = "Create DataBase " + SystemPara.database;
        String selectDataBase = "USE " + SystemPara.database;
        Statement stat = null;
        ResultSet rs = null;
        SystemPara.redirect = true;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery(isExist);
            boolean exist = false;
            while (rs.next()) if (rs.getString("Database").equalsIgnoreCase(SystemPara.database)) exist = true;
            // if exist, drop table first
            if (exist) stat.executeUpdate(drop);
            // create new database
            stat.executeUpdate(createDataBase);
            // select database
            stat.execute(selectDataBase);
           
            // create table
            executeSQLFromFile("citylib.txt");
            
            // initiate common data
            executeSQLFromFile("citylib2.txt");
            
            // initiate documents and relating information
            List<String[]> documents = ExtractDataFromCSV.extractData("Documents.csv");
            insertDocuments(documents);
            SystemPara.redirect = true;
            writeIntoFile("sql.txt", Cache.getCache().getSql());
            
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
    
    private void executeSQLFromFile(String FileName){
        InputStreamReader input = null;
        BufferedReader in = null;
        Statement stat =null;
        try{
            input = new InputStreamReader(DataBaseInitiate.class.getClassLoader().getResourceAsStream(FileName));  
            in = new BufferedReader(input);
            String line = null;
            String sql = "";
            stat = connection.createStatement();
            while((line = in.readLine()) != null){
                sql = sql + "\n" + line;
                if (line.endsWith(";")){
                    stat.executeUpdate(sql);
                    sql = "";
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (stat != null) stat.close();
                if (in != null) in.close();
                if (input != null) input.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void insertDocuments(List<String[]> documents){
        Statement stat =null;
        HashMap<String, Integer> PUBLISHER = new HashMap<String, Integer>();
        HashMap<String, Integer> AUTHOR = new HashMap<String, Integer>();
        
        SQLTemplate createDocument = new SQLTemplate(SQLKeyword.INSERT, "DOCUMENT");
        SQLTemplate createAuthor = new SQLTemplate(SQLKeyword.INSERT, "Author");
        SQLTemplate createPublisher = new SQLTemplate(SQLKeyword.INSERT, "Publisher");
        SQLTemplate createWrites = new SQLTemplate(SQLKeyword.INSERT, "WRITES");
        SQLTemplate createCopy = new SQLTemplate(SQLKeyword.INSERT, "COPY");
        SQLTemplate createCHIEF_EDITOR = new SQLTemplate(SQLKeyword.INSERT, "CHIEF_EDITOR");
        SQLTemplate createINV_EDITOR = new SQLTemplate(SQLKeyword.INSERT, "INV_EDITOR");
        SQLTemplate createBOOK = new SQLTemplate(SQLKeyword.INSERT, "BOOK");
        SQLTemplate createJOURNAL_VOLUME = new SQLTemplate(SQLKeyword.INSERT, "JOURNAL_VOLUME");
        SQLTemplate createJOURNAL_ISSUE = new SQLTemplate(SQLKeyword.INSERT, "JOURNAL_ISSUE");
        SQLTemplate createPROCEEDINGS = new SQLTemplate(SQLKeyword.INSERT, "PROCEEDINGS");
        SQLTemplate createBORROWS = new SQLTemplate(SQLKeyword.INSERT, "BORROWS");
        
        try{
            stat = connection.createStatement();
            connection.setAutoCommit(false);
            
            String selectDataBase = "USE " + SystemPara.database;
            String ignoreFK = "set foreign_key_checks = 0";
            String recoverFK = "set foreign_key_checks = 1";
            stat.execute(selectDataBase);
            stat.execute(ignoreFK);
            
            int borNumber = 10000;
            
            for(int i = 1; i < documents.size(); i++){
                String[] document = documents.get(i);
                String isbn = document[0];
                String title = document[1];
                String author = document[2];
                Integer authorId = AUTHOR.get(author);
                String publishTime = document[3] + "-" + "1" + "-" + "1";
                String publisher = document[4];
                Integer publisherID = PUBLISHER.get(publisher);
                String type = null;
                String address = null;
                String location1 = "001A03";
                String location2 = "002A08";
                String location3 = "005B09";
                switch(i % 6){
                case 0 :type = "BOOK"; address = 100 + i +  "Bergen Ave"; break;
                case 1 :type = "JOURNAL_VOLUME"; address = 110 + i + " Passiac Ave"; break;
                case 2 :type = "PROCEEDINGS"; address = 120 + i + " Golden Street"; break;
                case 3 :type = "BOOK"; address = 200 + i + " Harrison Ave"; break;
                case 4 :type = "JOURNAL_VOLUME"; address = 210 + i + " Kerney Ave"; break;
                case 5 :type = "PROCEEDINGS"; address = 220 + i + " Dragon Ave"; break;
                }
                if (publisherID == null){
                    publisherID = PUBLISHER.size() + 10000;
                    PUBLISHER.put(publisher, publisherID);
                    String[] PublisherValues = new String[]{String.valueOf(publisherID),publisher, address};
                    createPublisher.setValues(PublisherValues);
                    stat.executeUpdate(createPublisher.getSQL());
                }
                if (authorId == null){
                    authorId = AUTHOR.size() + 10000;
                    AUTHOR.put(author, authorId);
                    String[] authorValues = new String[]{String.valueOf(authorId), author};
                    createAuthor.setValues(authorValues);
                    stat.executeUpdate(createAuthor.getSQL());
                }
                
                String[] DocumentValues = new String[]{String.valueOf(i), title, publishTime, String.valueOf(publisherID)};
                List<String[]> Copyvalues = new ArrayList<String[]>();
                Copyvalues.add(new String[]{String.valueOf(i), "1", "1", location1});
                Copyvalues.add(new String[]{String.valueOf(i), "2", "1", location2});
                Copyvalues.add(new String[]{String.valueOf(i), "3", "1", location3});
                Copyvalues.add(new String[]{String.valueOf(i), "1", "2", location1});
                Copyvalues.add(new String[]{String.valueOf(i), "2", "2", location2});
                Copyvalues.add(new String[]{String.valueOf(i), "3", "2", location3});
                Copyvalues.add(new String[]{String.valueOf(i), "1", "3", location1});
                Copyvalues.add(new String[]{String.valueOf(i), "2", "3", location2});
                Copyvalues.add(new String[]{String.valueOf(i), "3", "3", location3});
                Copyvalues.add(new String[]{String.valueOf(i), "1", "4", location1});
                Copyvalues.add(new String[]{String.valueOf(i), "2", "4", location2});
                Copyvalues.add(new String[]{String.valueOf(i), "3", "4", location3});
                Copyvalues.add(new String[]{String.valueOf(i), "1", "5", location1});
                Copyvalues.add(new String[]{String.valueOf(i), "2", "5", location2});
                Copyvalues.add(new String[]{String.valueOf(i), "3", "5", location3});
                
                String date = "2015"
                        + "-" + (i / 10 + 1 > 12 ? 12 : i / 10 + 1) 
                        + "-" + (i / 4 + 1 > 28 ? 28 : i / 4 + 1);
                
                String date2 = "2016"
                        + "-" + (i / 10 + 2 > 12 ? 12 : i / 10 + 2) 
                        + "-" + (i / 4 + 2 > 28 ? 28 : i / 4 + 2);
                
                String date3 = "2016"
                        + "-" + (i / 10 + 3 > 12 ? 12 : i / 10 + 3) 
                        + "-" + (i / 4 + 3 > 28 ? 28 : i / 4 + 3);
                
                if (type.equals(DocumentType.BOOK.getName())){
                    String[] BOOK_Values = new String[]{String.valueOf(i), isbn};
                    createBOOK.setValues(BOOK_Values);
                    stat.executeUpdate(createBOOK.getSQL());
                    
                    String[] WriteValues = new String[]{String.valueOf(authorId), String.valueOf(i)};
                    createWrites.setValues(WriteValues);
                    stat.executeUpdate(createWrites.getSQL());
                }
                else if (type.equals(DocumentType.JOURNAL_VOLUME.getName())){
                    String[] JOURNAL_VOLUME_Values = new String[]{String.valueOf(i), String.valueOf(10000 + i), String.valueOf(authorId)};
                    createJOURNAL_VOLUME.setValues(JOURNAL_VOLUME_Values);
                    stat.executeUpdate(createJOURNAL_VOLUME.getSQL());
                    
                    String[] CHIEF_EDITOR_Values = new String[]{String.valueOf(authorId), author};
                    createCHIEF_EDITOR.setValues(CHIEF_EDITOR_Values);
                    stat.executeUpdate(createCHIEF_EDITOR.getSQL());
                    
                    List<String[]> issueValues = new ArrayList<String[]>();
                    issueValues.add(new String[]{String.valueOf(i), "1", "Artificial Intelligence"});
                    issueValues.add(new String[]{String.valueOf(i), "2", "Datebase Performance"});
                    issueValues.add(new String[]{String.valueOf(i), "3", "Data Mining"});
                    issueValues.add(new String[]{String.valueOf(i), "4", "Cloud Computing"});
                    createJOURNAL_ISSUE.setValues(issueValues);
                    stat.executeUpdate(createJOURNAL_ISSUE.getSQL());
                    
                    List<String[]> editorValues = new ArrayList<String[]>();
                    editorValues.add(new String[]{String.valueOf(i), "1", "John Smith"});
                    editorValues.add(new String[]{String.valueOf(i), "2", "Sherry Hou"});
                    editorValues.add(new String[]{String.valueOf(i), "3", "Wenbin Ma"});
                    editorValues.add(new String[]{String.valueOf(i), "4", "Yi Qi"});
                    createINV_EDITOR.setValues(editorValues);
                    stat.executeUpdate(createINV_EDITOR.getSQL());
                    
                }
                else if (type.equals(DocumentType.PROCEEDINGS.getName())){
                    String[] PROCEEDING_Values = new String[]{String.valueOf(i), date, address, author};
                    createPROCEEDINGS.setValues(PROCEEDING_Values);
                    stat.executeUpdate(createPROCEEDINGS.getSQL());
                }
                
                List<String[]> BORROW_Values = new ArrayList<String[]>();
                String user1 = String.valueOf((i + 1) % 20);
                String user2 = String.valueOf((i + 2) % 20);
                String user3 = String.valueOf((i + 3) % 20);
                String user4 = String.valueOf((i + 4) % 20);
                String user5 = String.valueOf((i + 5) % 20);
                String user6 = String.valueOf((i + 6) % 20);
                String lib = String.valueOf((i % 5 + 1));
                if (i % 3 == 0){
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user1, String.valueOf(i), lib, String.valueOf(i % 3 + 1), date, date2});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user2, String.valueOf(i), lib, String.valueOf((i + 1)% 3 + 1), date, date2});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user3, String.valueOf(i), lib, String.valueOf((i + 2)% 3 + 1), date, date2});
                }
                if (i % 5 == 0){
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user1, String.valueOf(i), lib, String.valueOf(i % 3 + 1), date2, date3});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user3, String.valueOf(i), lib, String.valueOf((i + 1)% 3 + 1), date2, date3});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user5, String.valueOf(i), lib, String.valueOf((i + 2)% 3 + 1), date2, date3});
                }
                else if (i % 11 == 0){
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user2, String.valueOf(i), lib, String.valueOf(i % 3 + 1), date2, date3});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user4, String.valueOf(i), lib, String.valueOf((i + 1)% 3 + 1), date2, date3});
                    BORROW_Values.add(new String[]{String.valueOf(borNumber++), user6, String.valueOf(i), lib, String.valueOf((i + 2)% 3 + 1), date2, date3});
                }
                BORROW_Values.add(new String[]{String.valueOf(borNumber++), "4", String.valueOf(i), lib, "1", date, date2});
                createBORROWS.setValues(BORROW_Values);
                stat.executeUpdate(createBORROWS.getSQL());
               
                createDocument.setValues(DocumentValues);
                createCopy.setValues(Copyvalues);
                
                stat.executeUpdate(createDocument.getSQL());
                stat.executeUpdate(createCopy.getSQL());
                
            }
            stat.execute(recoverFK);
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (stat != null) stat.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void writeIntoFile(String fileName, List<String> data) {
        File file = new File(fileName);
        if (file.exists()) file.delete();
        BufferedWriter out = null;
        FileWriter fout = null;
        try{
            file.createNewFile();        
            fout = new FileWriter(file);        
            out = new BufferedWriter(fout);
            for (int i = 0 ; i < data.size() - 1; i++){
                out.write(data.get(i));
                out.newLine();
            }
            out.write(data.get(data.size() - 1));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (out != null) out.close();
                if (fout != null) fout.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }        
    } 
    
    
    public static void main(String[] args){
        new DataBaseInitiate().init();
    }
}
