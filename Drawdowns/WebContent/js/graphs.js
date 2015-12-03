function drawScatterPlot(json_object, year, month) {
	// this function draws the scatter plot.
	var dayMin = year + "-" + month + "-01";
	console.log(dayMin);
	month++;
	// year++;
	var dayMax = year + "-" + month + "-01";
	console.log(dayMax);
	var chart1 = c3.generate({
		bindto : '#scatter_plot',
		size : {
			height : 500,
		},
		data : {
			xs : {
				High : 'High_x',
				HighMedium : 'HighMedium_x',
				Medium : 'Medium_x',
				MediumLow : 'MediumLow_x',
				Low : 'Low_x',
			},
			json : json_object,
			mimeType : 'json',
			type : 'scatter',
			colors : {
				High : '#CC0000',
				HighMedium : '#FF0000',
				Medium : '#FF9999',
				MediumLow : '#3399FF',
				Low : '#0A1F33',
			},
		},
		axis : {
			x : {
				type : 'timeseries',
				label : 'Time',
				min : dayMin,
				max : dayMax,
				tick : {
					format : '%Y-%m-%d',
					rotate : 90,
					fit : false
				}
			},
			y : {
				label : 'Permno',
			}
		},
		grid : {
			x : {
			// lines:index_dates,
			},
		},
		subchart : {
			show : true
		},
	});
}
function drawSummaryGraph(json_ary, bindvalue) {
	var chart2 = c3.generate({
		bindto : bindvalue,
		data : {
			xs : {
				Total : 'year',
			},
			json : json_ary,
			mimeType : 'json',
			type : 'bar',
		},
		size : {
			height : 220
		},
		axis : {
			x : {
				label : 'year',
				tick : {
					culling : {
						max : 1,
					},
					rotate : 90,
					multiline : false,
				},
			},
			y : {
				padding : 0,
			// max : 2300,
			},
		},
	});
}
function drawIndex(json_ary) {
	// console.log(indexDate);
	var chart3 = c3.generate({
		bindto : '#barIndex',
		data : {
			xs : {
				value : 'date',
			},
			colors : {
				value : '#000000',
			},
			json : json_ary,
			mimeType : 'json',
			type : 'bar',
		},
		size : {
			height : 220
		},
		bar : {
			width : {
				ratio : 0.2
			}
		},
		axis : {
			x : {
				type : 'timeseries',
				label : 'Time',
				tick : {
					// values : indexDate,
					format : '%Y-%m-%d',
					rotate : 90,
					fit : false
				},
			},

			y : {
				max : 0.1,
				min : -0.3,
				label : 'Drawdown value',
				tick : {
					values : [ 0.10, 0, -0.10, -0.20, -0.30 ]
				},

			// max : 2300,
			},
		},
		grid : {
			x : {
				show : true,
			},
			y : {
				lines : [ {
					value : 0,
					text : 'Drawdown value 0'
				} ]
			}
		},
	});
}
function drawLossMcGraph(jsd) {
	var chart4 = c3.generate({
		bindto : '#multihistogram',
		padding : {
			top : 0,
			right : 60,
			bottom : 0,
			left : 50,
		},
		data : {
			url : 'bootstrap/data/aa.json',
			mimeType : 'json',
			type : 'bar',
			xs : {
				'emp_value' : 'emp_date',
				'index_value' : 'index_date',
				'cumulative_value' : 'cumulative_date',
			},
			axes : {
				emp_value : 'y',
				index_value : 'y2',
				cumulative_value : 'y'
			}
		},
		bar : {
			width : {
				ratio : 0.08
			// this makes bar width 50% of length between ticks
			},
		},
		zoom : {
			enabled : true
		},
		size : {
			height : 220
		},
		axis : {
			x : {
				type : 'timeseries',
				label : 'Time',
				tick : {
					format : '%Y-%m-%d',
					rotate : 90,
					fit : false
				}
			},
			y : {
				tick : {
					format : function(d) {
						return d / 1000000;
					},
				},
				label : 'Loss Market Capitalization - millions'
			},
			y2 : {
				tick : {
					format : function(d) {
						return d + "%";
					}
				},
				show : true,
				label : 'Index'
			},
		},

	});
}
function drawLossMcGraph(json_ary) {
	var chart5356 = c3.generate({
		bindto : '#lossbar',
		padding : {
			top : 0,
			right : 60,
			bottom : 0,
			left : 50,
		},
		data : {
			url : json_ary,
			mimeType : 'json',
			type : 'bar',
			xs : {
				'emp_value' : 'emp_date',
				'index_value' : 'index_date',
				'cumulative_value' : 'cumulative_date',
				//'ReturnValue' : 'dates',
			},
			axes : {
				emp_value : 'y',
				index_value : 'y2',
				cumulative_value : 'y'
			}
		},
		bar : {
			width : {
				ratio : 0.08
			// this makes bar width 50% of length between ticks
			},
		},
		zoom : {
			enabled : true
		},
		size : {
			height : 220
		},
		axis : {
			x : {
				type : 'timeseries',
				label : 'Time',
				tick : {
					format : '%Y-%m-%d',
					rotate : 90,
					fit : false
				}
			},
			y : {
				tick : {
					format : function(d) {
						return d / 1000000;
					},
				},
				label : 'Loss Market Capitalization - millions'
			},
			y2 : {
				tick : {
					format : function(d) {
						return d + "%";
					}
				},
				show : true,
				label : 'Index'
			},
		},

	});
}
