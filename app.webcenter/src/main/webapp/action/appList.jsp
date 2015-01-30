<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Application List</h1>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>Name</th>
				<th>path</th>
				<th>executable</th>
				<th>strategy</th>
				<th>type</th>
				<th>host</th>
				<th>Operation</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="apps">
				<tr>
					<td><s:property value="name" /></td>
					<td><s:property value="path" /></td>
					<td><s:property value="executable" /></td>
					<td><s:property value="strategy" /></td>
					<td><s:property value="type" /></td>
					<td><s:property value="host" /></td>
					<td><a
						href='<s:url action="appEdit"><s:param name="appId" value="appId" /></s:url>'>
							Edit </a> &nbsp; <a
						href='<s:url action="Remove"><s:param name="appId" value="appId" /></s:url>'>
							Delete </a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<%@ include file="end.jsp"%>