function  chart1( json,jsonTime) {
	$(function () {
	    $('#container1').highcharts({
	        chart: {
	            type: 'area'
	        },
	        title: {
	            text: 'CPU Usage'
	        },
	        xAxis: {
	            categories: jsonTime,
	            labels: {
	            	enabled: false
	            }
	        },
	        yAxis: {
	        	 title: {
		                text: 'Usage (%)'
		            },
	            min: 0,
	            max:100
	        },
	        plotOptions: {
	            area: {
	                marker: {
	                    enabled: false,
	                    symbol: 'circle',
	                    radius: 10
	                }
	            }
	        },
	        series: json
	    });
	});   				
}
function  chart2( json,jsonTime) {
	$(function () {
	    $('#container2').highcharts({
	        chart: {
	            type: 'area'
	        },
	        title: {
	            text: 'Memory Usage'
	        },
	        xAxis: {
	            categories: jsonTime,
	            labels: {
	            	enabled: false
	            }
	        },
	        yAxis: {
	        	 title: {
		                text: 'Usage (%)'
		            },
	            min: 0
	        },
	        plotOptions: {
	            area: {
	                marker: {
	                    enabled: false,
	                    symbol: 'circle',
	                    radius: 10
	                }
	            }
	        },
	        series: json
	    });
	});   				
}
function  chart3 ( json,jsonTime) {
	    $('#container3').highcharts({
	        title: {
	            text: 'Network Utility',
	            x: -20 //center
	        },
	        xAxis: {
	            categories: jsonTime,
	            labels: {
	            	enabled: false
	            }
	        },
	        yAxis: {
	            min:0,
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        plotOptions: {
	            series: {
	                marker: {
	                    radius: 0
	                }
	            }
	        },
	        tooltip: {
	            valueSuffix: '%'
	        },
	        series:json
	    });
	}
function  chart4( json,jsonTime) {
    $('#container4').highcharts({
        title: {
            text: 'Disk Utility',
            x: -20 //center
        },
        xAxis: {
            categories: jsonTime,
            labels: {
            	enabled: false
            }
        },
        yAxis: {
            min:0,
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        plotOptions: {
            series: {
                marker: {
                    radius: 0
                }
            }
        },
        tooltip: {
            valueSuffix: '%'
        },
        series:json
    });
}