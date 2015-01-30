<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Show DiagnosisResult</h1>
<div class='span9 main'>
	<table
		class="table table-striped table-bordered table-hover table-condensed">
		<tbody>
			<s:iterator value="result" id="column">
				<tr>
					<s:iterator value="column" id="item">
						<td><s:property value="item" /></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
<%@ include file="end.jsp"%>