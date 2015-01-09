<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<form action="logQuery" method="post">
		<div>
			<input type="hidden" name="appRecordId"
				value="<c:out value="${appRecordId}" default="11" />"> </input> <input
				type="text" name="queryWords"
				placeholder="<c:out value="${queryWords}" default="Exception" />"></input>
			<button type="submit" class="btn">Search</button>
		</div>
		<style type='text/css'>
textarea {
	min-height: 100%;
}
</style>
		<div>
			<h4>Query Result:</h4>
			<textarea row="20" class="input-block-level"><c:out
					value="${logResult}" default="..." /></textarea>

		</div>
	</form>
</div>
<%@ include file="end.jsp"%>