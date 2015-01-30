<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Application Record List</h1>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>AppName</th>
				<th>StartTime</th>
				<th>EndTime</th>
				<th>Result</th>
				<th>Operation</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="appRecords">
				<tr>
					<td><s:property value="appName" /></td>
					<td><s:property value="startTime" /></td>
					<td><s:property value="endTime" /></td>
					<td><s:property value="result" /></td>
					<td><a
						href='<s:url action="showAnalysisResult"><s:param name="enginID" value="enginAppID" /><s:param name="path" value="" /></s:url>'>
							Analysis </a> &nbsp; <a
						href='<s:url action="logQuery"><s:param name="appRecordId" value="enginAppID" /></s:url>'>
							LogQuery </a> &nbsp; <a
						href='<s:url action="showDiagnosisResult"><s:param name="enginID" value="enginAppID" /><s:param name="path" value="" /></s:url>'>
							Diagnosis </a>&nbsp; <a
						href='<s:url action="driverLog"><s:param name="enginAppID" value="enginAppID" /></s:url>'>
							DriverLog </a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<%@ include file="end.jsp"%>