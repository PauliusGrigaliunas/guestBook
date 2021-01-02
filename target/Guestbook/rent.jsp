<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.*,accommodation.Accommodation" %>
<%@page import="java.util.*,guest.Guest"%>
<html>
    <head>
        <title>Rent</title>
    </head>
    <body>
        <h2>Rents</h2>
        <form method="GET" action="RentServlet">
            <table>
                <tr>
                    <td>Guest id: </td>
                    <td><input type="number" name="id"/></td>
                    <td>Name:</td>
                    <td><input type="text" name="name"/></td>
                    <td>Surname:</td>
                    <td><input type="text" name="surname"/></td>
                </tr>
                <tr>
                    <td>Accommodation description:</td>
                    <td><input type="text" name="description"/></td>
                    <td>Space:</td>
                    <td><input type="number" name="space"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Search"/></td>
                </tr>
            </table>
        </form>
        <hr><h3>Guests</h3><ol> <%
            List<Guest> guests = (List<Guest>)request.getAttribute("guests");
            for (Guest guest : guests) { %>
            <li> <%= guest %> </li> <%
                } %>
        </ol><hr>
        <hr><h3>Accommodations</h3>
        <ol> <%
            List<Accommodation> accommodations = (List<Accommodation>)request.getAttribute("accommodations");
            for (Accommodation accommodation : accommodations) { %>
            <li> <%= accommodation %> </li> <%
                } %>
        </ol><hr>
        <h3>Rent</h3>
        <form method="POST" action="RentServlet">
            <table>
                <tr>
                    <td>Guest id: </td>
                    <td><input type="number" name="id"/></td>
                </tr>
                <tr>
                    <td>Accommodation nr:</td>
                    <td><input type="number" name="nr"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Assign"/></td>
                </tr>
            </table>
        </form>
        <hr>
        <a href="index.jsp">Back</a>
    </body>
</html>
