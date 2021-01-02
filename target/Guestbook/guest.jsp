<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,guest.Guest"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Guests</title>
    </head>

    <body>
        <h2>Guest book</h2>
        <h3>Add Guest</h3>
        <form method="POST" action="GuestServlet">
            Name: <input type="text" name="name" />
            Surname: <input type="text" name="surname" />
            <input type="submit" value="Add" />
        </form>
        <h3>Search</h3>
        <form method="GET" action="GuestServlet">
            <table>
                <tr>
                    <td> User id:</td>
                    <td><input type="number" name="id"/></td>
                </tr>
                <tr>
                    <td> Name:</td>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                    <td> Surname:</td>
                    <td><input type="text" name="surname"/></td>
                </tr>
            </table>
            <input type="submit" value="Search"/>
        </form>
        <h3>Update</h3>
        <form method="POST" action="GuestServletUpdate">
            <table>
                <tr>
                    <td> User id:</td>
                    <td><input type="number" name="id"/></td>
                </tr>
                <tr>
                    <td> Name:</td>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                    <td> Surname:</td>
                    <td><input type="text" name="surname"/></td>
                </tr>
            </table>
            <input type="submit" value="Update"/>
        </form>
        <h3>Delete</h3>
        <form method="POST" action="GuestServletDelete">
            <table>
                <tr>
                    <td> User id:</td>
                    <td><input type="number" name="id"/></td>
                </tr>
            </table>
            <input type="submit" value="Delete"/>
        </form>
        <hr><ol> <%
            List<Guest> guests = (List<Guest>)request.getAttribute("guests");
            for (Guest guest : guests) { %>
                <li> <%= guest %> </li> <%
            } %>
        </ol><hr>
        <a href="index.jsp">Back</a>
     </body>
 </html>