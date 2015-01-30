<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Add New Job</h1>
<div class='span9 main'>
	<div style="width: 400px; height: auto">
		<form class='form-horizontal' id=jobStore name="jobStore"
			action="/action/jobStore.action" method="get">

			<div class="form-group">
				<label class="control-label">Name</label> <input type="text"
					name="name" id="name" placeholder="daily" class="form-control" />
			</div>

			<div class="form-group">
				<label class="control-label">Defination</label> <input type="text"
					name="defination" id="defination" placeholder="nweight,wordcount"
					class="form-control" />
			</div>

			<div class="form-group">
				<label class="control-label">Cycle</label> <input type="text"
					name="cycle" id="cycle" placeholder="0 0 2" class="form-control" />
			</div>

			<div class="form-group" style="text-align: right">
				<button type="submit" class="btn btn-default">Submit</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="end.jsp"%>