<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<form class='form-horizontal' id=appModify name="appModify"
		action="/action/appModify.action" method="get">
		<div class="control-group">
			<label class="control-label" for="name">AppID</label>
			<div class="controls">
				<s:textfield name="app.appId"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<s:textfield name="app.name"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Host</label>
			<div class="controls">
				<s:textfield name="app.host"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Path</label>
			<div class="controls">
				<s:textfield name="app.path"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Executable</label>
			<div class="controls">
				<s:textfield name="app.executable"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="strategy">Strategy</label>
			<div class="controls">
				<select name="app.strategy" id="strategy">
					<option value="reexecute">reExecute</option>
					<option value="alert">alert</option>
					<option value="nothing">nothing</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="type">Type</label>
			<div class="controls">
				<select name="app.type" id="type">
					<option value="spark">spark</option>
					<option value="hadoop">hadoop</option>
					<option value="hive">hive</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<button type="submit" value="login" class="btn">Submit</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="end.jsp"%>