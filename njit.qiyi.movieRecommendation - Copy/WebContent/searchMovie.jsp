<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import="njit.qiyi.movieRecommendation.service.*, njit.qiyi.movieRecommendation.core.*, java.util.*" 
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html>
<style type = "text/css">

body {
  background-image: url("images/background2.jpg");
  background-position: 50% 50%;
  background-size:cover; 
}</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Movie</title>
</head>
<body>
<h1 style="line-height:200%">Search movie</h1>
<form action="searchMovie.jsp" target="_top" method="post">
<input type="text" name="movie" size = "30" value="Please enter movie title..." onFocus="if(value=='Please enter movie title...'){value=''}" onblur="if(value=='') {value='Please enter movie title...'}" >
<input type="submit" value="Search"></form>
<form action="selectMovies.jsp" target="_top" method="post">
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
// get parameter
String movie= request.getParameter("movie");
// get first ten movies by fuzzy match
List<Movie> list = new GetMovieList().getCloselyMatchMovieList(movie, 10);
for (Movie m : list){
%>
<tr>
<td>
<input type="radio" name = "radio" value= <%=m.getID()%> style="text-align:center; vertical-align: middle;">
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
<%}%>
</table>
<input type="hidden" name = "type" value="Add">
<input type="submit" value="Add">
</form>
</body>
</html>