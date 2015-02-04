<%@ page contentType="text/html" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<link href="../static/bootstrap.min.css" rel="stylesheet">
<link href="../static/dashboard.css" rel="stylesheet">
<link href="../static/index.css" rel="stylesheet">

<script src="../static/jquery-1.11.2.js"></script>
<script src="../static/bootstrap.min.js"></script>

</head>

<body style="padding-top: 0px;">
	<div id="logobar">
		<div
			style="width: 120px; height: 80px; float: left; display: block; overflow: hidden">
			<img src="../static/Logo.jpg" />
		</div>
		<div
			style="height: 80px; float: left; display: block; font-family: '' Cambria '">
			<span
				style="display: block; margin-top: 45px; font-size: 22px; font-weight: bold; color: #0B60A3; font-style: italic;">

				Big Data Cloud Management Plateform</span>
		</div>

	</div>
	<nav class="navbar navbar-inverse navbar-fixed-top"
		style="position: relative; margin: 0px; clear: both;">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/action/Main.jsp">Home</a>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/action/appList">App Management</a></li>
					<li><a href="/action/jobList">Job Management</a></li>
					<li><a href="/action/getAgents">Admin</a></li>
				</ul>
			</div>
			<div id="navbar" class="navbar-collapse collapse"></div>
		</div>
	</nav>

	<div class="container-fluid" style="position: relative; height: 700px;">
		<div class="row">