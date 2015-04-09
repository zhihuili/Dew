<%@ page contentType="text/html" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<link href="../static/bootstrap.min.css" rel="stylesheet">
<link href="../static/index.css" rel="stylesheet">

<script src="../static/jquery-1.11.2.js"></script>
<script src="../static/bootstrap.min.js"></script>
<script src="../static/highcharts.js"></script>
<script src="../static/exporting.js"></script>

<script src="../static/charts.js"></script>
<script src="../static/index.js"></script>
</head>

<body>
	<div id="framework">
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
			style="position: relative">
			<div class="container-fluid"
				style="width: 8%; margin-left: 0px; margin-right: 0px; float: left">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>

				</div>
				<div id="navbar" class="navbar-collapse collapse"></div>
			</div>
			<div class="container-fluid"
				style="width: 85%; margin-left: 0px; margin-right: 0px; text-align: right; float: left; line-height: 50px">
				<label style="color: white"> System Metrics @ <c:out
						value="${hostName}" />(<c:out value="${ip}" />)
				</label>
			</div>
		</nav>


		<div id="divCharts">
			<div class="chart-panel">
				<div id="container1" class="chartContainer"></div>
			</div>
			<div class="chart-panel chart-panel-35">
				<div id="container2" class="chartContainer"></div>
			</div>
			<div class="chart-panel">
				<div id="container3" class="chartContainer"></div>
			</div>
			<div class="chart-panel chart-panel-35">
				<div id="container4" class="chartContainer"></div>
			</div>
		</div>

		<br style="clear: both;" />


		<div id="divFooter"
			style="clear: both; margin-top: 15px; border-top: solid 1px #ccc; text-align: right; color: #ccc; padding: 5px;">
			Dew@intel</div>
	</div>
</body>
</html>
