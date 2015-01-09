<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<div class='span9 main'>
	<form class='form-horizontal' id=appStore name="appStore"
		action="/action/appStore.action" method="get">
		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<input type="text" name="name" id="name" placeholder="kmeans">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="host">Host</label>
			<div class="controls">
				<input type="text" name="host" id="host" placeholder="sr145">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="path">Path</label>
			<div class="controls">
				<input type="text" name="path" id="path"
					placeholder="/home/username/workload/kmeans">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="executable">Executable</label>
			<div class="controls">
				<input type="text" name="executable" id="executable"
					placeholder="./run.sh">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="strategy">Strategy</label>
			<div class="controls">
				<select name="strategy" id="strategy">
					<option value="reexecute">reExecute</option>
					<option value="alert">alert</option>
					<option value="nothing">nothing</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="type">Type</label>
			<div class="controls">
				<select name="type" id="type">
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