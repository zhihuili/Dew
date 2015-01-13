<%@ include file="head.jsp"%>
<%@ include file="jobMenu.jsp"%>
<div class='span9 main'>
	<form class='form-horizontal' id=jobStore name="jobStore"
		action="/action/jobStore.action" method="get">
		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<input type="text" name="name" id="name" placeholder="daily">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="host">Defination</label>
			<div class="controls">
				<input type="text" name="defination" id="defination" placeholder="nweight,wordcount">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="path">Cycle</label>
			<div class="controls">
				<input type="text" name="cycle" id="cycle"
					placeholder="0 0 2">
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