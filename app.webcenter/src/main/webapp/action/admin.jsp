<%@ include file="head.jsp"%>
<div class='span4'></div>
<div class='span8'>
<h2>Dew Agents Status</h2>
</div>
<div class='span12 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>IP</th>
				<th>HostName</th>
				<th>URL</th>
				<th>Type</th>
				<th>Services</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="agents">
				<tr>
					<td><s:property value="ip" /></td>
					<td><s:property value="hostName" /></td>
					<td><s:property value="url" /></td>
					<td><s:property value="type" /></td>
					<td><s:property value="services" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<%@ include file="end.jsp"%>