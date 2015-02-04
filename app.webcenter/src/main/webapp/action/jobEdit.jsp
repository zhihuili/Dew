<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<h1 id='DashBoardTitle' class="page-header">Modify Job</h1>
<div class='span9 main'>
	<div style="width: 400px; height: auto">
		<form class='form-horizontal' id=jobModify name="jobModify"
			action="/action/jobModify.action" method="get">
			<div class="form-group" style="display: none">
				<label for="name">JobID</label>
				<s:textfield name="job.jobId" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="name">Name</label>
				<s:textfield name="job.name" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Defination</label>
				<s:textfield name="job.defination" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="name">Cycle</label>
				<s:textfield name="job.cycle" cssClass="form-control" />
			</div>


			<div class="form-group" style="text-align: right">
				<button type="submit" value="login" class="btn btn-default">Submit</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="end.jsp"%>