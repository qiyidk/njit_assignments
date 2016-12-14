<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="njit.qiyi.movieRecommendation.service.*, njit.qiyi.movieRecommendation.core.*, java.util.*" 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<style type = "text/css">
body {
  background-image: url("images/background2.jpg");
  background-position: 50% 50%;
  background-size:cover; 
}</style>
<head>
<title>Select Movies</title>
</head>
<body>
<div align="Left">
<h1 style="line-height:200%">Step 1: Search and add your favorite movies</h1>
<form action="searchMovie.jsp" target="_top" method="post">
<input type="text" name="movie" size = "30" value="Please enter movie title..." onFocus="if(value=='Please enter movie title...'){value=''}" onblur="if(value=='') {value='Please enter movie title...'}" >
<input type="submit" value="Search">
</form>
<form action="selectMovies.jsp" target="_top" method="post">
<%
// get parameter
String s = request.getParameter("radio");
String d = request.getParameter("delete");
String type = request.getParameter("type");
@SuppressWarnings("unchecked") 
Set<Integer> movies = (Set<Integer>) session.getAttribute("movieID");
if (("Add").equals(type) && (s != null)){
    Integer movieID = Integer.parseInt(s);
    if (movieID != null) {
        if (movies == null) {
            movies = new HashSet<Integer>();
            movies.add(movieID);
            session.setAttribute("movieID", movies);
        }
        else if (!movies.contains(movieID)) movies.add(movieID);
    } 
}
else if (("Remove".equals(type)) && (d != null)){
    Integer movieID= Integer.parseInt(d);
    movies.remove(movieID);
}    
%>


<%
if ((movies != null) && (movies.size() != 0)){
%>
<table>
<tr>
<td></td>
<td></td>
<td><b>Movie Title</b></td> 
<td><b>IssueTime</b></td>
<td><b>Genres</b></td> 
<td><b>AverageRating</b></td> 
</tr>
<%
    for (Integer id : movies){
        Movie m = new GetMovieInfo().getMovie(id);
%>
<tr>
<td>
<input type="radio" name = "delete" value= <%=m.getID()%> style="text-align:center; vertical-align: middle;">
</td>
<td></td>
<td><%=m.getTitle()%></td>
<td><%=m.getIssueTime()%></td>
<%
String genre = "";
    for (int i = 0; i < m.getGenres().size(); i++){
        genre = genre + m.getGenres().get(i);
        if (i != m.getGenres().size() - 1) genre = genre + "|";
    }
%>
<td><%=genre%></td>
<td><%=m.getAvgRating()%></td>
</tr>
<%}}%>
<tr></tr>
</table>
<input type="hidden" name = "type" value="Remove"/>
<input type="submit" value="Remove"/>
</form>
<h1 style="line-height:200%">Step 2: Get recommend movies</h1>
<form action="recommendation.jsp" target="_top" method="post">
<%
String movieStr = "";
if (movies != null){
StringBuilder movieString = new StringBuilder();
for (int i : movies){
    movieString.append(i).append(",");
}
if (movieString.length() != 0) movieStr = movieString.substring(0, movieString.length() - 1);
}
%>
<input type="hidden" name = "movies" value=<%=movieStr%>>
<input type="submit" value= "Get Recommendation">
</form>
<form action="index.htm" target="_top" method="post">
<input type="submit" value="Go Back">
</form>

</div>
</body>
</html>