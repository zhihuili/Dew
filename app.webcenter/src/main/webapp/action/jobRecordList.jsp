<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
                <tr>
                    <th>JobName</th>
                    <th>StartTime</th>
                    <th>EndTime</th>
                    <th>Result</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="jobRecords">
                    <tr>
                        <td><s:property value="jobName" /></td>
                        <td><s:property value="startTime" /></td>
                        <td><s:property value="endTime" /></td>
                        <td><s:property value="result" /></td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
	</div>
<%@ include file="end.jsp"%>