<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<style type="text/css">
        table {
            border: 1px solid black;
            border-collapse: collapse;
        }
        
        table thead tr th {
            border: 1px solid black;
            padding: 3px;
            background-color: #cccccc;
        }
        
        table tbody tr td {
            border: 1px solid black;
            padding: 3px;
        }
    </style>
</head>
<body>
user name : ${sessionScope.currentUser}


        <table cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Password</th>
                    <th>Type</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="users">
                    <tr>
                        <td><s:property value="id" /></td>
                        <td><s:property value="name" /></td>
                        <td><s:property value="password" /></td>
                        <td><s:property value="type" /></td>
                        <td>
                            <a href='<s:url action="userEdit"><s:param name="id" value="id" /></s:url>'>
                                Edit
                            </a>
                            &nbsp;
                            <a href='<s:url action="Remove"><s:param name="id" value="id" /></s:url>'>
                                Delete
                            </a>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
<a href="userStore.jsp">Add New User</a>
<br>
<a href="Main.jsp">back Home</a>

</body>
</html>