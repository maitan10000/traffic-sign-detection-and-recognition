$(function () {

	var data = new Array ();
    var config = new Array();
	
	data.push ([[1,25],[2,34],[3,19],[4,45],[5,56]]);
	data.push ([[1,13],[2,12],[3,25],[4,23],[5,31]]);
	data.push ([[1,31],[2,13],[3,46],[4,15],[5,14]]);
 
    for (var i=0, s=data.length; i<s; i++) {
    	
	     config.push({
	        data:data[i],
	        grid:{
            hoverable:true
        },
	        bars: {
	            show: true, 
	            barWidth: 0.2, 
	            order: 1,
	            lineWidth: 0.5, 
				fillColor: { colors: [ { opacity: 0.5 }, { opacity: 1 } ] }
	        }
	    });
	}
	    
    $.plot($("#chart-bar"), config, {
    	colors: ["#5bb75b", "#0074cc", "#da4f49"]
                
    });
                  
});