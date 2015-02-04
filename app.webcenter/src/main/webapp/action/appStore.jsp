<%@ include file="head.jsp"%>
<%@ include file="appMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Add New Application</h1>
<div class='span9 main'>
	<div style="width: 400px; height: auto">
		<form class='form-horizontal' id=appStore name="appStore"
			action="/action/appStore.action" method="get">
			<div class="form-group">
				<label class="control-label" for="name">Name</label> <input
					type="text" name="name" id="name" placeholder="kmeans"
					class="form-control" />
			</div>

			<div class="form-group">
				<label class="control-label" for="host">Host</label> <input
					type="text" name="host" id="host" placeholder="sr145"
					class="form-control" />
			</div>

			<div class="form-group">
				<label class="control-label" for="path">Path</label> <input
					type="text" name="path" id="path"
					placeholder="/home/username/workload/kmeans" class="form-control" />
			</div>

			<div class="form-group">
				<label class="control-label" for="executable">Executable</label> <input
					type="text" name="executable" id="executable"
					placeholder="./run.sh" class="form-control" />
			</div>

			<div class="form-group">
				<label for="strategy">Strategy</label> <select name="strategy"
					id="strategy" class="form-control">
					<option value="reexecute">reExecute</option>
					<option value="alert">alert</option>
					<option value="nothing">nothing</option>
				</select>
			</div>

			<div class="form-group">
				<label for="type">Type</label> <select name="type" id="type"
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