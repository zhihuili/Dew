<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Application List</title>
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
                    <th>JobId</th>
                    <th>Name</th>
                    <th>Defination</th>
                    <th>Cycle</th>
                    <th>UserId</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="jobs">
                    <tr>
                        <td><s:property value="jobId" /></td>
                        <td><s:property value="name" /></td>
                        <td><s:property value="defination" /></td>
                        <td><s:property value="cycle" /></td>
                        <td><s:property value="userId" /></td>
                        <td>
                            <a href='<s:url action="jobEdit"><s:param name="jobId" value="jobId" /></s:url>'>
                                Edit
                            </a>
                            &nbsp;
                            <a href='<s:url action="Remove"><s:param name="jobId" value="jobId" /></s:url>'>
                                Delete
                            </a>
                            &nbsp;
                            <a href='<s:url action="JobRun"><s:param name="name" value="name" /></s:url>'>
                                Run
                            </a>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
<a href="jobStore.jsp">Add Job</a>
<br>
<a href="Main.jsp">back Home</a>

</body>
</html>