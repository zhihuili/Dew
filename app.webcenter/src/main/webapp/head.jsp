 <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<head>
<title>Dew Web Center</title>
<link href="/static/bootstrap-combined.min.css"
	rel="stylesheet">

<style type='text/css'>
html, body {
	background-color: #CCC;
	margin: 0;
	padding: 0;
	height: 100%;
}

#container {
	min-height: 100%;
	height: auto !important;
	height: 100%;
	position: relative;
}

#container.header {
	padding: 10px;
}

#container.page {
	padding-bottom: 60px;
}

#container.footer {
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 60px;
	clear: both;
}
</style>
</head>
<body>
	<div class='container'>
		<div class='header'></div>
		<div class='page'>
			<img src="/static/logo.jpg">
			<div class='navbar navbar-inverse'>
				<div class='navbar-inner nav-collapse' style="height: auto;">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="/action/appList">App Management</a></li>
						<li><a href="/action/jobList">Job Management</a></li>
						<li><a href="#">Log Management</a></li>
					</ul>
				</div>
			</div>
			<div>
			<p> Welcome ${sessionScope.currentUser}, enjoy it.</p>
			</div>
			<div id='content' class='row-fluid'>