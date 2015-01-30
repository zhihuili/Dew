<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Driver Log</h1>
<div class='span9 main'>
	<s:textarea name="driverLogContent" cols="140" rows="30"
		value="%{driverLogContent}" />
</div>
<%@ include file="end.jsp"%>