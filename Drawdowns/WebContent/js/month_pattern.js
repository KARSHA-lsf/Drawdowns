

function draw_month_patterns(json_object,month) {
	var chart = c3.generate({
		bindto : '#M'+month,
		/*data: {
	        columns: [
	            ['data1', 30, 200, 100, 400, 150, 250]
	        ],*/
		data : {
			xs : {
				mcap : 'naics',
			},
			colors : {
				mcap : '#FF0040',
			},
			names:{
				mcap: 'EOM total loss market capitalization'},
				   	
			json : json_object,
			mimeType : 'json',
			type : 'bar',
		},
		axis : {
			y : {
				tick : {
					format : function(d) {
						return d / 1000000;
										
					},
				},
				label : 'EOM total loss market capitalization - millions $'
				
			}
		},
	        
	    bar: {
	        width: {
	            ratio: 0.25 // this makes bar width 50% of length between ticks
	        }
	        // or
	        //width: 100 // this makes bar width 100px
	    },
		tooltip: {
	        format: {
	           value: function (value, ratio, id) {
	        	
	        	   value = (value/1000000).toFixed(4)+"M$";
	        	   return value;
	        	
	        	}
	           }
	  		},
		grid : {
		
			y : {
				lines : [ {
					value : 0,
				} ]
			}
		}
	});
}



				
	
	