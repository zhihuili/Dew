$(document).ready(function(){
	$(".menubar>li>a").each(function(){
		this.style.textDecoration="none";
	});


	$(".menubar>li>a").click(function(){
		$(".menubar>li>a").each(function(){
			this.style.color="#9d9d9d";
			this.style.textDecoration="none";
		});

		this.style.color="#fff";
		this.style.textDecoration="none";
	});

	
	//Load date
	getData();
	
});

//Update Chart
setInterval(getData, 5000 );

function getData(){
	$.ajax({
		url: "../action/getClusterData.action", 
		type:"post",
		dataType:"json",
		context: document.body, 
		success: function(result){
			console.log(eval(result));
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
	
}


