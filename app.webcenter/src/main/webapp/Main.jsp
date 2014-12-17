<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type='text/javascript' src='../js/jquery-1.9.1.js'></script>
</head>
<body>
login success!
<br>
user name : ${sessionScope.currentUser}

<table border="1">
<tr>
  <td><a href="userList">admin</a></td>
  <td><a href="appList">Application Management</a></td>
</tr>
</table>
</body>
</html>