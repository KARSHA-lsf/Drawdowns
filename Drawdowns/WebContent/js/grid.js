var urldata,width,height,id,square;

var  colorCode =[],pattern =[],blue =[],red =[],green =[];
var ogl_blue=[],ogl_red =[],ogl_green =[];

var calData, grid, row, col,text;

var pushdown=0,lossNoImpact=0,noImpact=0,LLHG=0,nonAlign=0,none=0;

var blue_max;
var blue_min;
var red_max;
var red_min;
var green_max;
var green_min;
	
function setColorCode(BLN,BSN,BM,BSP,BLP,RLN,RSN,RM,RSP,RLP,GLN,GSN,GM,GSP,GLP){
	
	pushdown=0,lossNoImpact=0,noImpact=0,LLHG=0,nonAlign=0,none=0;
	
	for(index = 0; index < blue.length; index++){	
		if(blue[index]<BLN && green[index]<GLN){
			colorCode[index] = '#ff0000'; // pushdown red
			pattern[index]="Pushdown";
			pushdown=pushdown+1;
		}else if(blue[index]<BLN && green[index]>GSN && green[index]<GSP && red[index]>RSN && red[index]<BSP){
			colorCode[index] = '#D2691E'; //loss no impact
			pattern[index]="Loss-No-Impact";
			lossNoImpact=lossNoImpact+1;
		}else if(blue[index]>BSN && blue[index]<BM && green[index]>GSN && green[index]<GSP && red[index]>RSN && red[index]<BSP){
			colorCode[index] = '#228B22'; //no impact green
			pattern[index]="No-Impact";
			noImpact=noImpact+1;
		}else if(blue[index]>BSN && blue[index]<BM){
			colorCode[index] = '#0000ff'; //LLHG blue
			pattern[index]="LLHG";
			LLHG=LLHG+1;
		}else if(blue[index]<BLN && green[index]>GM){
			colorCode[index] = '#87CEFA'; //non align light blue
			pattern[index]="Non-Align";
			nonAlign=nonAlign+1;
		}else{
			colorCode[index] = '#FF69B4';  //
			pattern[index]="None";
			none=none+1;
		}
		
	}
	console.log(pushdown,lossNoImpact,noImpact,LLHG,nonAlign,none);
	d3.select("svg").remove();
	calendarWeekHour('#chart', window.innerWidth, window.innerHeight*0.5, false);
}

function setData(data){
	var i = 0;
	urldata=data;
	$.each(urldata.person,function(){
		ogl_blue[i] = this['blue'];
		ogl_red[i] = this['red'];
		ogl_green[i] = this['green'];
		i++;
    });
	blue=ogl_blue.slice();
	red=ogl_red.slice();
	green=ogl_green.slice();
}

function setPercentages(scale){
	
	if(scale=="G"){
		 blue_max=Math.abs(Math.max.apply(Math,ogl_blue));
		 blue_min=Math.abs(Math.min.apply(Math,ogl_blue));
		 red_max=Math.abs(Math.max.apply(Math,ogl_red));
		 red_min=Math.abs(Math.min.apply(Math,ogl_red));
		 green_max=Math.abs(Math.max.apply(Math,ogl_green));
		 green_min=Math.abs(Math.min.apply(Math,ogl_green));
		 
		 for(index = 0; index < 132; index++){
			 if(ogl_blue[index]<0){
					blue[index]=ogl_blue[index]*100/blue_min;
				}else{
					blue[index]=ogl_blue[index]*100/blue_max;
				}
				if(ogl_red[index]<0){
					red[index]=ogl_red[index]*100/red_min;
				}else{
					red[index]=ogl_red[index]*100/red_max;
				}
				if(ogl_green[index]<0){
					green[index]=ogl_green[index]*100/green_min;
				}else{
					green[index]=ogl_green[index]*100/green_max;
				}
		 } 
	}else{	
		var blue_tmp=ogl_blue.slice();
		var red_tmp=ogl_red.slice();
		var green_tmp=ogl_green.slice();
		var b_min,b_max,r_min,r_max,g_min,g_max;
		for(index = 0; index < 132; index++){	
			if(index%12==0){
				 var b_tmp_ary=blue_tmp.splice(0,12);
				 var r_tmp_ary=red_tmp.splice(0,12);
				 var g_tmp_ary=green_tmp.splice(0,12);
				 
				b_min=Math.abs(Math.min.apply(Math,b_tmp_ary));
				b_max=Math.abs(Math.max.apply(Math,b_tmp_ary));
				r_min=Math.abs(Math.min.apply(Math,r_tmp_ary));
				r_max=Math.abs(Math.max.apply(Math,r_tmp_ary));
				g_min=Math.abs(Math.min.apply(Math,g_tmp_ary));
				g_max=Math.abs(Math.max.apply(Math,g_tmp_ary));
			}
			//console.log(green[index]+" min : "+g_min+" max :"+g_max);
			if(ogl_blue[index]<0){
				blue[index]=ogl_blue[index]*100/b_min;
			}else{
				blue[index]=ogl_blue[index]*100/b_max;
			}
			if(ogl_red[index]<0){
				red[index]=ogl_red[index]*100/r_min;
			}else{
				red[index]=ogl_red[index]*100/r_max;
			}
			if(ogl_green[index]<0){
				green[index]=ogl_green[index]*100/g_min;
			}else{
				green[index]=ogl_green[index]*100/g_max;
			}

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
				 .style("font-size",function(d) { return d.width/8 })
        		 .attr("dy",".35em")
        		 .text(function(d) { return d.value });
}


function randomData(gridWidth, gridHeight, square)
{
    var data = new Array();
    var gridItemWidth = gridWidth / 16;
    var gridItemHeight = (square) ? gridItemWidth : gridHeight / 13;
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

function pieChart(){
	var chart = c3.generate({
		bindto : '#pieChart',
	    data: {
	        columns: [
	            ['PushDown', pushdown],
	            ['Loss_No_Impact', lossNoImpact],
	            ['No_Impact', noImpact],
	            ['LLHG', LLHG],
	            ['Non_Align', nonAlign],
	            ['None', none],
	        ],
	        type : 'pie',
	        colors : {
	        	PushDown : '#ff0000',
	        	Loss_No_Impact : '#D2691E',
	        	No_Impact : '#228B22',
	        	LLHG : '#0000ff',
	        	Non_Align : '#87CEFA',
	        	None : '#FF69B4',
			},
	    },
	    pie: {
	        label: {
	            format: function (value, ratio, id) {
	                return d3.format('')(value);
	            }
	        }
	    }
	});
}
