<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<form class="form-search" id=appSearch name="appSearch"
		action="/action/appSearch.action" method="get">
		<div class="input-append">
			<input type="text" name="appName" class="span6 search-query"
				placeholder="kmeans">
			<button type="submit" class="btn">Search</button>
		</div>
	</form>
</div>
<%@ include file="end.jsp"%>