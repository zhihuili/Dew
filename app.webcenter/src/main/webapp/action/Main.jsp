<%@ include file="head.jsp"%>

<div class='span3'></div>
<div class='span9'>
<h2>Cluster System Performance Metrics</h2>
</div>
<div class="row">
	<div class="span6">
		<div id="container1" style="width:100%;height:200px"></div>
    </div>
    <div class="span6">.
		<div id="container2" style="width:100%;height:200px"></div>
    </div>
 </div>
 
 <div class="row">
 	<div class="span6">
 		<div id="container3" style="width:100%;;height:200px"></div>
    </div>
    <div class="span6">.
       <div id="container4" style="width:100%;height:200px"></div>
    </div>
</div>
<script type="text/javascript" src="../static/jquery-1.11.2.js"></script> 
<script type="text/javascript" src="../static/highcharts.js"></script>
<script type="text/javascript" src="../static/exporting.js"></script> 
 <script type="text/javascript" src="../static/highcharts-more.js"></script> 
<script type="text/javascript" src="../static/charts.js"></script> 
<script type="text/javascript" src="../static/jquery.json-2.2.js"></script> 
<script>
 function getData(){
		$.ajax({
			url: "/action/getClusterData.action", 
			type:"post",
			dataType:"json",
			context: document.body, 
			success: function(result){
				var dataCPU = eval('(' + result.jsonCPU + ')');
				var dataMEM = eval('(' + result.jsonMEM + ')');
				var dataNETWORK = eval('(' + result.jsonNETWORK + ')');
				var dataDISK = eval('(' + result.jsonDISK + ')');
				var dataTIME = eval('(' + result.jsonTIME + ')');
				chart1(dataCPU,dataTIME);
				chart2(dataMEM,dataTIME);
				chart3(dataNETWORK,dataTIME);
				chart4(dataDISK,dataTIME);
	        }})
		   setTimeout('getData()', 5000 )
	}
	getData();
 </script>
<%@ include file="end.jsp"%>