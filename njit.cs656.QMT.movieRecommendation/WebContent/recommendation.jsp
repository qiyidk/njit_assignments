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
<form action="selectMovies.jsp" method="post">
<table>
<%
// get parameter
String movieStr= request.getParameter("movies");
if ((movieStr != null) && (movieStr.trim().length() != 0)){
%>
<tr>
<td><b>Movie Title</b></td> 
<td><b>IssueTime</b></td>
<td><b>Genres</b></td> 
<td><b>AverageRating</b></td> 
</tr>
<%
String[] ids = movieStr.split(",");
List<Movie> list = new ArrayList<Movie>();
for (String i : ids){
    Integer movieID= Integer.parseInt(i);
    Movie m = new GetMovieInfo().getMovie(movieID);
    list.add(m);
}
// get recommendatin
List<Movie> recommendatin = new RecommendMovie().getRecommendMovies(list);
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