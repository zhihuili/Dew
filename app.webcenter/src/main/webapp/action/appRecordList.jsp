<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
                <tr>
                    <th>RecordID</th>
                    <th>AppName</th>
                    <th>JobRecord</th>
                    <th>EnginAppID</th>
                    <th>StartTime</th>
                    <th>EndTime</th>
                    <th>Result</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="appRecords">
                    <tr>
                        <td><s:property value="recordID" /></td>
                        <td><s:property value="appName" /></td>
                        <td><s:property value="jobRecordID" /></td>
                        <td><s:property value="enginAppID" /></td>
                        <td><s:property value="startTime" /></td>
                        <td><s:property value="endTime" /></td>
                        <td><s:property value="result" /></td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
	</div>
<%@ include file="end.jsp"%>