<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>
</head>
<body>

	<h2>
    </h2>
    <s:form action="userStore" >
        <s:textfield name="name" label="Name" />
        <s:textfield name="password" label="Password" />
        <s:textfield name="type" label="Type" />
        <s:submit />
    </s:form>

</body>
</html>