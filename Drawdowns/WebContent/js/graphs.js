function drawScatterPlot(json_object){
	//this function draws the scatter plot.
	//input parameter is a json_object.
	var chart = c3.generate({
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
				show: true,
			},
		},
		subchart: {
			show: true
		},
    
	});
}
function drawSummaryGraph(json_ary){
	var chart4=c3.generate({
	    bindto:'#histogram',
	    data:{
	        xs: {
	            count: 'year',
	        },
	        url:json_ary,
	        mimeType: 'json',
	        type : 'bar',
	    },
	    size: {
	            height: 220
	    },
	    axis:{
	    	x:{
	    		label: 'Year',
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