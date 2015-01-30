<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome to Dew</title>
<link href="../static/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="../static/signin.css" rel="stylesheet">
<script src="../static/login.js"></script>
</head>
<body>

	<div class="container">
		<form class='form-signin' id="login" name="login"
			action="/action/login" method="post">
			<h2 class="form-signin-heading">Please sign in</h2>
			<label for="inputEmail" class="sr-only">Email address</label> <input
				type="text" id="username" name="username" class="form-control"
				placeholder="" required autofocus> <label
				for="inputPassword" class="sr-only">Password</label> <input
				type="password" id="password" name="password" class="form-control"
				placeholder="Password" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit"
				value="login">Sign in</button>
		</form>

	</div>
</body>
</html>