function drawScatterPlot(json_object,index_dates,year,month){
	//this function draws the scatter plot.
	var dayMin = year+"-"+month+"-01";
	console.log(dayMin);
	month++;
	var dayMax = year+"-"+month+"-01";
	console.log(dayMax);
	var chart1 = c3.generate({
		bindto: '#scatter_plot',
		size: {
        height: 500,
		},
		data: {
			xs: {
				High: 'High_x',
				HighMedium: 'HighMedium_x',
				Medium: 'Medium_x',
				MediumLow: 'MediumLow_x',
				Low: 'Low_x', 
			},
			json:json_object,
			mimeType: 'json',
			type: 'scatter',
			colors: {
				High: '#CC0000',
				HighMedium: '#FF0000',
				Medium: '#FF9999',
				MediumLow: '#3399FF',
				Low: '#0A1F33',
			},
		},
		axis: {
			x: {
				type: 'timeseries',
				label: 'Time',
				min : dayMin,
				max : dayMax,
				tick: {
					format: '%Y-%m-%d',
					rotate:90,
					fit: false
				}
			},
			y: {
				label: 'Permno',
			}
		},
		grid: {
			x: {
				//lines:index_dates,
			},
		},
		subchart: {
			show: true
		},
    
	});
}
function drawSummaryGraph(json_ary,bindvalue){
	var chart2=c3.generate({
	    bindto:bindvalue,
	    data:{
	        xs: {
	            Total: 'year',
	        },
	        json:json_ary,
	        mimeType: 'json',
	        type : 'bar',
	    },
	    size: {
	            height: 220
	    },
	    axis:{
	    	x:{
	    		label: 'year',
	    		tick: {
	    		      culling: {
	    		    	  max : 1,
	    		      },
	    		      rotate: 90,
	                  multiline: false,
	    		},
	    	},
	        y:{
	            padding : 0,
	            //max : 2300,
	        },
	    },
	});
}

function drawIndex(json_ary){
	//console.log(indexDate);
	var chart3=c3.generate({
	    bindto:'#barIndex',
	    data:{
	        xs: {
	            value: 'date',
	        },
	        colors:{
	        	value : '#000000',
	        },
	        json:json_ary,
	        mimeType: 'json',
	        type : 'bar',
	    },
	    size: {
	            height: 220
	    },
	    bar: {
            width: {
                ratio: 0.2 
            }
        },
	    axis:{
	    	x: {
				type: 'timeseries',
				label: 'Time',
				tick: {
					//values : indexDate,
					format: '%Y-%m-%d',
					rotate:90,
					fit: false
				},
			},
	    	
	        y:{
	        	max:0.1,
	            min:-0.3,
	            label: 'Drawdown value',
                tick: {
	              values: [0.10, 0.05, 0, ,-0.05, -0.10, -0.15, -0.20, -0.25, -0.30]
	            }
	            //max : 2300,
	        },
	    },
	    grid: {
            y: {
                lines: [
                    {value: 0, text: 'Drawdown value 0'}
                ]
            }
        },
	});
}