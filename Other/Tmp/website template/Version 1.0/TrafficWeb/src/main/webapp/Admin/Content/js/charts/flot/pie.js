/*
	#5bb75b - green
	#da4f49 - red
	#0074cc - blue
	#faa732 - gold
*/
$(function () {
		var data = [];
		var series = Math.floor(Math.random()*10)+1;
		var series = 4;
		for( var i = 0; i<series; i++)
		{
			data[i] = { label: "Series"+(i+1), data: Math.floor(Math.random()*100)+1 }
		}
	
	$.plot($("#chart-pie"), data, 
	{
			colors: ["#5bb75b", "#0074cc", "#da4f49", "#faa732"],
			series: {
				pie: { 
					show: true,
					label: {
						show: false,
						formatter: function(label, series){
							return '<div style="font-size:11px;text-align:center;padding:4px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';
						},
						threshold: 0.1
					}
				}
			},
			legend: {
				show: true,
				noColumns: 1, // number of colums in legend table
				labelFormatter: null, // fn: string -> string
				labelBoxBorderColor: "#888", // border color for the little label boxes
				container: null, // container (as jQuery object) to put legend in, null means default on top of graph
				position: "ne", // position of default legend container within plot
				margin: [5, 10], // distance from grid edge to default legend container within plot
				backgroundOpacity: 0 // set to 0 to avoid background
			},
			grid: {
				hoverable: false,
				clickable: false
			},
	});
	
	});