<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="java.util.*,accommodation.Accommodation" %>
<html>
<head>
    <title>Accommodations</title>
</head>
<body>
<h2>Accommodations</h2>
<h3>Add Accommodation</h3>
<form method="POST" action="AccommodationServlet">
    Description: <input type="text" name="decription"/>
    Space: <input type="number" name="space"/>
    House: <input type="checkbox" name="isHouse"/>
    <input type="submit" value="Add"/>
</form>
<h3>Search</h3>
<form method="GET" action="AccommodationServlet">
    <table>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description"/></td>
        </tr>
        <tr>
            <td>Space:</td>
            <td><input type="number" name="space"/></td>
        </tr>
    </table>
    <input type="submit" value="Search"/>
</form>
<h3>Update</h3>
<form method="POST" action="AccommodationServletUpdate">
    <table>
        <tr>
            <td>Number:</td>
            <td><input type="number" name="nr"/></td>
        </tr>
        <tr>
            <td>Is ready?:</td>
            <td><input type="checkbox" name="ready"/></td>
        </tr>
        <tr>
            <td>Space:</td>
            <td><input type="number" name="space"/></td>
        </tr>
    </table>
    <input type="submit" value="Update"/>
</form>
<h3>Delete</h3>
<form method="POST" action="AccommodationServletDelete">
    <table>
        <tr>
            <td> Accommodation id:</td>
            <td><input type="number" name="nr"/></td>
        </tr>
    </table>
    <input type="submit" value="Delete"/>
</form>
<hr>
<ol><%
    List<Accommodation> Accommodations = (List<Accommodation>) request.getAttribute("accommodations");
    for (Accommodation accommodation : Accommodations) { %>
    <li><%= accommodation %>
    </li>
    <%
        } %>
</ol>
<hr>
<a href="index.jsp">Back</a>
</body>
</html>
