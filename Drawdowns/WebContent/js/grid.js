var urldata,width,height,id,square;

var  colorCode =[],pattern =[],blue =[],red =[],green =[];

var calData, grid, row, col,text;

var blue_max;
var blue_min;
var red_max;
var red_min;
var green_max;
var green_min;
	
function setColorCode(BLN,BSN,BM,BSP,BLP,RLN,RSN,RM,RSP,RLP,GLN,GSN,GM,GSP,GLP){
	
	for(index = 0; index < blue.length; index++){	
		if(blue[index]<BLN && green[index]<GLN){
			colorCode[index] = '#ff0000'; // pushdown red
			pattern[index]="Pushdown";
		}else if(blue[index]<BLN && green[index]>GSN && green[index]<GSP && red[index]>RSN && red[index]<BSP){
			colorCode[index] = '#D2691E'; //loss no impact
			pattern[index]="Loss-No-Impact";
		}else if(blue[index]>BSN && blue[index]<BM && green[index]>GSN && green[index]<GSP && red[index]>RSN && red[index]<BSP){
			colorCode[index] = '#228B22'; //no impact green
			pattern[index]="No-Impact";
		}else if(blue[index]>BSN && blue[index]<BM){
			colorCode[index] = '#0000ff'; //LLHG blue
			pattern[index]="LLHG";
		}else if(blue[index]<BLN && green[index]>GM){
			colorCode[index] = '#87CEFA'; //non align light blue
			pattern[index]="Non-Align";
		}else{
			colorCode[index] = '#fff';  //
			pattern[index]="None";
		}
	}

	d3.select("svg").remove();
	calendarWeekHour('#chart', window.innerWidth*0.8, window.innerHeight*0.6, false);
}

function setData(data){
	var i = 0;
	urldata=data;
	$.each(urldata.person,function(){
		blue[i] = this['blue'];
		red[i] = this['red'];
		green[i] = this['green'];
		i++;
    });
	 blue_max=Math.abs(Math.max.apply(Math,blue));
	 blue_min=Math.abs(Math.min.apply(Math,blue));
	 red_max=Math.abs(Math.max.apply(Math,red));
	 red_min=Math.abs(Math.min.apply(Math,red));
	 green_max=Math.abs(Math.max.apply(Math,green));
	 green_min=Math.abs(Math.min.apply(Math,green));
	 
	 for(index = 0; index < blue.length; index++){
		 if(blue[index]<0){
				blue[index]=blue[index]*100/blue_min;
			}else{
				blue[index]=blue[index]*100/blue_max;
			}
			if(red[index]<0){
				red[index]=red[index]*100/red_min;
			}else{
				red[index]=red[index]*100/red_max;
			}
			if(green[index]<0){
				green[index]=green[index]*100/green_min;
			}else{
				green[index]=green[index]*100/green_max;
			}
	 } 
}

function calendarWeekHour(Gid, Gwidth, Gheight, Gsquare)
{	
	width=Gwidth;
	height=Gheight;
	id=Gid;
	square=Gsquare;
    calData = randomData(width, height, square);
    grid = d3.select(id).append("svg").attr("width", width).attr("height", height).attr("class", "chart");

    row = grid.selectAll(".row").data(calData).enter().append("svg:g").attr("class", "row");

    col = row.selectAll(".cell")
                 .data(function (d) { return d; })
                 .enter().append("svg:rect")
                 .attr("class", "cell")
                 .attr("x", function(d) { return d.x; })
                 .attr("y", function(d) { return d.y; })
				 .attr("value", function(d) { return d.value; })
                 .attr("width", function(d) { return d.width; })
                 .attr("height", function(d) { return d.height; })
                 .on('click', function() {
                    console.log(d3.select(this).attr("value"));
                 })
                 .style('fill', ("color", function(d) { return d.color; }))
                 .style("stroke", '#000');

	text = row.selectAll(".label")
        		 .data(function(d) {return d;})
     		     .enter().append("svg:text")
       			 .attr("x", function(d) { return d.x + d.width/2 })
        		 .attr("y", function(d) { return d.y + d.height/2 })
       			 .attr("text-anchor","middle")
				 .style("font-size","12px")
        		 .attr("dy",".35em")
        		 .text(function(d) { return d.value });
}


function randomData(gridWidth, gridHeight, square)
{
    var data = new Array();
    var gridItemWidth = gridWidth / 13;
    var gridItemHeight = (square) ? gridItemWidth : gridHeight / 12;
    var startX = gridItemWidth ;
    var startY = gridItemHeight ;
    var stepX = gridItemWidth;
    var stepY = gridItemHeight;
    var xpos = startX;
    var ypos = startY;
    var newValue = 0;
    var count = 0;

	var ccc = 0;
	var monthId = 0;
	var month = ["","January","February","March","April","May","June","July","Augest","September","October","November","December"];
	var year = 2004;
	var parseDate = d3.time.format("%d-%b-%y").parse;
				
    for (var index_a = 0; index_a < 12; index_a++)
    {
        data.push(new Array());
        for (var index_b = 0; index_b < 13; index_b++)
        {	
			if(index_a==0){
				data[index_a].push({ 
                                time: index_b, 
                                value: month[monthId],
                                width: gridItemWidth,
                                height: gridItemHeight,
                                x: xpos,
                                y: ypos,
                                count: count,
								color : '#A9A9A9',
                            });
							monthId++;
			}else if(index_b==0){
				data[index_a].push({ 
                                time: index_b, 
                                value: year,
                                width: gridItemWidth,
                                height: gridItemHeight,
                                x: xpos,
                                y: ypos,
                                count: count,
								color : '#A9A9A9',
                            });
							year++;
			}else{
				data[index_a].push({ 
                                time: index_b, 
                                value: pattern[ccc],
                                width: gridItemWidth,
                                height: gridItemHeight,
                                x: xpos,
                                y: ypos,
                                count: count,
								color : colorCode[ccc],
                            });
							ccc++;
			}
            
            xpos += stepX;
            count += 1;

        }
		
        xpos = startX;
        ypos += stepY;
    }	
    return data;
}