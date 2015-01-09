<%@ include file="head.jsp"%>
<div class='span3'></div>
<div class='span9'>
	<form class='form-horizontal' id="login" name="login"
		action="/action/login" method="post">
		<div class="control-group">
			<label class="control-label" for="username">User</label>
			<div class="controls">
				<input type="text" name="username" id="username" placeholder="Username">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<input type="password" name="password" id="password" placeholder="Password">
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<label class="checkbox"> <input type="checkbox" disabled>
					Remember me
				</label>
				<button type="submit" value="login" class="btn">Sign in</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="end.jsp"%>