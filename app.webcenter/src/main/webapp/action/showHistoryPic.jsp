<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<s:iterator value="piclink" id="column">
			<s:property value="column"/>
			<br>
				<img src='/servlet/image?fileName=<s:property value="column"/>' width="800" height="300"/>
			<br>
		</s:iterator>
</body>
</html>