<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
                <tr>
                    <th>Name</th>
                    <th>Defination</th>
                    <th>Cycle</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="jobs">
                    <tr>
                        <td><s:property value="name" /></td>
                        <td><s:property value="defination" /></td>
                        <td><s:property value="cycle" /></td>
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
	</div>
<%@ include file="end.jsp"%>