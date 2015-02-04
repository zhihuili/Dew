<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Modify Application Info</h1>
<div class='span9 main'>
	<div style="width: 400px; height: auto">
		<form class='form-horizontal' id=appModify name="appModify"
			action="/action/appModify.action" method="get">
			<div class="control-group" style="display: none">
				<label class="control-label" for="name">AppID</label>
				<div class="controls">
					<s:textfield name="app.appId" />
				</div>
			</div>
			<div class="form-group">
				<label for="name">Name</label>
				<s:textfield name="app.name" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Host</label>
				<s:textfield name="app.host" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Path</label>
				<s:textfield name="app.path" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Executable</label>
				<s:textfield name="app.executable" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Strategy</label> <select name="app.strategy"
					id="strategy" class="form-control">
					<option value="reexecute">reExecute</option>
					<option value="alert">alert</option>
					<option value="nothing">nothing</option>
				</select>
			</div>

			<div class="form-group">
				<label for="type">Type</label> <select name="app.type" id="type"
					class="form-control">
					<option value="spark">spark</option>
					<option value="hadoop">hadoop</option>
					<option value="hive">hive</option>
				</select>
			</div>

			<div class="form-group" style="text-align: right">
				<button type="submit" class="btn btn-default">Submit</button>
			</div>

		</form>
	</div>
</div>
<%@ include file="end.jsp"%>