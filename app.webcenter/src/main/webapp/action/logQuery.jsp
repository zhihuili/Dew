<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<form action="logQuery" method="post">
		<div>
			<input type="hidden" name="appRecordId"
				value="<c:out value="${appRecordId}" default="11" />"> </input>
		</div>

		<div class="form-group">
			<div class="col-sm-3">
				<input type="text" name="queryWords" class="form-control"
					placeholder="<c:out value="${queryWords}" default="Exception" />" />
			</div>
			<div class="col-sm-2">
				<button type="submit" class="btn btn-default">Search</button>
			</div>
		</div>

		<div class="form-group" style="clear: both;">
			<label style="margin: 12px; font-size: 20px;">Query Result</label>
			<textarea class="form-control" style="margin-left: 12px;" rows="20"><c:out
					value="${logResult}" default="..." /></textarea>
		</div>
	</form>
</div>
<%@ include file="end.jsp"%>