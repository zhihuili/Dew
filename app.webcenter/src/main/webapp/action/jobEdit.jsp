<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<div class='span9 main'>
	<form class='form-horizontal' id=jobModify name="jobModify"
		action="/action/jobModify.action" method="get">
		<div class="control-group" style="display: none">
			<label class="control-label" for="name">JobID</label>
			<div class="controls">
				<s:textfield name="job.jobId"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<s:textfield name="job.name"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Defination</label>
			<div class="controls">
				<s:textfield name="job.defination"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Cycle</label>
			<div class="controls">
				<s:textfield name="job.cycle"/>
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