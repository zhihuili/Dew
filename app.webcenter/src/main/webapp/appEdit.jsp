<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Application</title>
</head>
<body>

	<h2>
    </h2>
    <s:form action="appModify" >
    	<s:textfield name="app.app_id" label="App_id" />
        <s:textfield name="app.name" label="Name" />
        <s:textfield name="app.path" label="path" />
        <s:textfield name="app.executable" label="executable" />
        <s:select name="app.strategy" label="Strategy" list="{'reexecute','alert','nothing'}" value="app.strategy"/>
        <s:select name="app.type" label="Type" list="{'spark','hadoop','hive'}" value="app.type"/>
        <s:submit />
    </s:form>

</body>
</html>