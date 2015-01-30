<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Application Search</h1>
<div class='span9 main'>
	<div style="width: 400px; height: auto">
		<form class="form-search" id=appSearch name="appSearch"
			action="/action/appSearch.action" method="get">
			<div class="form-group">
				<input type="text" name="appName" class="form-control"
					placeholder="kmeans">
				<button type="submit" class="btn btn-default">Search</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="end.jsp"%>