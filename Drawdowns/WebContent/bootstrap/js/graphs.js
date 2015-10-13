function myFunction(info){
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
        json:info,
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
            show: true
        },
    },
     subchart: {
        show: true
    },
    
});
}
function myfunction2(info){
	var chart = c3.generate({
		bindto: '#plot1',
	    data: {
	        json: {
	            data1: [30, 20, 50, 40, 60, 50],
	            data2: [200, 130, 90, 240, 130, 220],
	            data3: [300, 200, 160, 400, 250, 250]
	        }
	    }
	});
	 var x = [
	            {name: 'www.site1.com', upload: 800, download: 500, total: 400},
	            {name: 'www.site2.com', upload: 600, download: 600, total: 400},
	            {name: 'www.site3.com', upload: 400, download: 800, total: 500},
	            {name: 'www.site4.com', upload: 400, download: 700, total: 500},
	        ];

	setTimeout(function () {
	    chart.load({
	        json: x,
	        keys: {
	            value: ['upload', 'download'],
	        }
	    });
	}, 2000);
}