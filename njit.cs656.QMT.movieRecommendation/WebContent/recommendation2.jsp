<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="njit.cs656.QMT.movieRecommendation.service.*, njit.cs656.QMT.movieRecommendation.core.*, java.util.*" 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html>
<style type = "text/css">
body {
  background-image: url("images/background2.jpg");
  background-position: 50% 50%;
  background-size:cover; 
}</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie Recommendation</title>
</head>
<body>
<h1 style="line-height:200%">Movie Recommendation</h1>
<form action="index.htm" method="post">
<table>
<%
// get parameter
String user= request.getParameter("user");
User u = null;
try{
u = new GetUser().getUser(Integer.parseInt(user));
}catch(Exception e){
    // do nothing
}
if (u != null){
%>
<tr>
<td><b>Movie Title</b></td> 
<td><b>IssueTime</b></td>
<td><b>Genres</b></td> 
<td><b>AverageRating</b></td> 
</tr>
<%
// get recommendatin
List<Movie> recommendatin = new RecommendMovie().getRecommendMovies(u, 5);
for (Movie m : recommendatin){
%>
<tr>
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
</table>
<input type="submit" value="Go Back">
</form>
</body>
</html>