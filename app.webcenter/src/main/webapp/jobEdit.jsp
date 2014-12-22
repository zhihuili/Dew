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
    <s:form action="jobModify" >
    	<s:textfield name="job.jobId" label="JobId" />
        <s:textfield name="job.name" label="Name" />
        <s:textfield name="job.defination" label="Defination" />
        <s:textfield name="job.cycle" label="Cycle" />
        <s:textfield name="job.userId" label="UserId" />
        <s:submit />
    </s:form>

</body>
</html>