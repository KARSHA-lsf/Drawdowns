function draw_month_patterns(json_object) {
	var chart = c3.generate({
		bindto : '#M1',
		/*data: {
	        columns: [
	            ['data1', 30, 200, 100, 400, 150, 250]
	        ],*/
		data : {
			xs : {
				mcap : 'naics',
			},
			colors : {
				value : '#000000',
			},
			json : json_object,
			mimeType : 'json',
			type : 'bar',
		},
	        
	    bar: {
	        width: {
	            ratio: 0.5 // this makes bar width 50% of length between ticks
	        }
	        // or
	        //width: 100 // this makes bar width 100px
	    }
	});
}