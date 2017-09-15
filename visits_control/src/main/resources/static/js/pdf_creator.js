/**
 * 
 */

function pdf_creator_init(){

	var jspdf_src = "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.js";
// var jspdf_autotable =
// "https://raw.githubusercontent.com/bpampuch/pdfmake/master/build/vfs_fonts.js";
	var vfs = "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js";
	
// load_script(jspdf_src, function(){
// var button = document.getElementById("print-button");
// button.onclick = print_button_onclick;
// });
	
	load_script(jspdf_src, function(){
		load_script(vfs, function(){
			var button = document.getElementById("print-button");
			button.onclick = function(e){
				test(e);
			}
		});
	});

	function load_script(_src, _success_callback){
		$.getScript(_src, function(data, textStatus, jqxhr) {
			console.log(data); // data returned
			console.log(textStatus); // success
			console.log(jqxhr.status); // 200
			console.log('Load was performed.');
			
			if(jqxhr.status == 200){
				
				console.log(_src + ' ***HAS BEEN LOAD**');
				if(_success_callback)_success_callback();
			}else{
				console.log('**CANNOT LOAD** ' + _src);
			}
			
			});
	}
	
}
var hexDigits = new Array
("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"); 

// Function to convert rgb color to hex format
function rgb2hex(rgb) {
rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

function hex(x) {
return isNaN(x) ? "00" : hexDigits[(x - x % 16) / 16] + hexDigits[x % 16];
}
function generateTest(tr){
	
	let tds=tr.getElementsByTagName("td");
	let arr=[];
	
	for(let td of tds){
		console.log("align"+getComputedStyle(td).textAlign);
		let colspan=td.colSpan;
		let text=td.innerHTML;
		
		arr.push({
			"text":text,
			"fontSize":10,
			"colSpan":colspan,
			"fillColor":(td.style.backgroundColor)?rgb2hex(td.style.backgroundColor):'',
			"alignment":getComputedStyle(td).textAlign
		});
		for(let i=0;i<colspan-1;i++){
			arr.push({});
		}
	}
	return arr;
}

function parseData(){
	 let trs = document.getElementById("table-results-body").getElementsByTagName("tr");
	let trArray=[];
	for(let tr of trs){
		if(tr.style.display!="none")
			trArray.push(generateTest(tr));
		
	

	}
			
	
	return  trArray;
}
function test(e){
	console.dir(parseData());
	print_button_onclick(parseData(),e.target.value);
	
}
function print_button_onclick(data,type){
	// var docDefinition = { content: 'русские символы' };
	
		let header1=[
           	[{"columns":
        		[{"text":document.getElementById("table-title").innerHTML,"fontSize":10,"margin":[0,0,0,0],"fillColor":"#f2f2f2"}
        				],"colSpan":11,"alignment":"center"},"","","","","","","","","",""],
        					[
        					{"text":"#","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
        					{"text":"ФИО","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
        					{"text":"Ставка","fillColor":"#b4c6e7","bold":true,  rowSpan:3,"fontSize":10 },
        					{"text":"План, ч.","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
        					
        					{"text":"Отработано","fillColor":"#b4c6e7","bold":true, colSpan:2,"fontSize":10 },
        					{},
        					
        					{"text":"Неявки на работу","fillColor":"#b4c6e7","bold":true, colSpan:5,"fontSize":10 },
        					{},
        					{},
        					{},
        					{},

        					],
        					[
        						{},// "#"
        						{},// "ФИО"
        						{},// Ставка
        						{},// Рабочий план,
									// часов
        						{"text":"ч.","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
        						{"text":"%","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
        						
        						{"text":"О","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
        						{"text":"Б","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
        						{"text":"К","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
        						
        						{"text":"ПР","fillColor":"#b4c6e7","bold":true, colSpan:2,"fontSize":10 },{},
        					],
        					[
        						{},{},{},{},{},{},{},{},{},
        						
        						{"text":"Ч.","fillColor":"#b4c6e7","bold":true,"fontSize":10 },
        						{"text":"%","fillColor":"#b4c6e7","bold":true,"fontSize":10 },
        					],
        					// /data
        			
        ];
		let header2=[
		            		[{"columns":
        		[{"text":document.getElementById("table-title").innerHTML,"fontSize":10,"margin":[0,0,0,0],"fillColor":"#f2f2f2"}
        				],"colSpan":12,"alignment":"center"},"","","","","","","","","","",""],
		            					[
		            					{"text":"#","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
		            					{"text":"ФИО","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
		            					{"text":"Ставка","fillColor":"#b4c6e7","bold":true,  rowSpan:3,"fontSize":10 },
		            					{"text":"Подразделения","fillColor":"#b4c6e7","bold":true,  rowSpan:3,"fontSize":10 },
		            					{"text":"План, ч.","fillColor":"#b4c6e7","bold":true, rowSpan:3,"fontSize":10 },
		            					
		            					{"text":"О","fillColor":"#b4c6e7","bold":true, colSpan:2,"fontSize":10 },
		            					{},
		            					
		            					{"text":"Неявки на работу","fillColor":"#b4c6e7","bold":true, colSpan:5,"fontSize":10 },
		            					{},
		            					{},
		            					{},
		            					{},

		            					],
		            					[
		            						{},// "#"
		            						{},// "ФИО"
		            						{},// Ставка
		            						{},
		            						{},// Рабочий план,
		    									// часов
		            						{"text":"ч.","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
		            						{"text":"%","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
		            						
		            						{"text":"О","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
		            						{"text":"Б","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
		            						{"text":"К","fillColor":"#b4c6e7","bold":true, rowSpan:2,"fontSize":10 },
		            						
		            						{"text":"Б/П","fillColor":"#b4c6e7","bold":true, colSpan:2,"fontSize":10 },{},
		            					],
		            					[
		            						{},{},{},{},{},{},{},{},{},{},
		            						
		            						{"text":"ч.","fillColor":"#b4c6e7","bold":true,"fontSize":10 },
		            						{"text":"%","fillColor":"#b4c6e7","bold":true,"fontSize":10 },
		            					],
		            					// /data
		            			
		            ];
		let header= type==1?header1:header2;
	var dd = {
			 header: function(currentPage, pageCount) {
				 return { text: currentPage.toString() + ' из ' + pageCount, alignment:'right' }; },
			content: [
				{
		            columns: [
		                {
		                    width: '90%',
		                    alignment: 'left',
		                    style: 't1',
		                    table: {
		                        headerRows: 4,
		                        dontBreakRows: true,
		                        body:header,
		                        width:"auto",
		                      
		                    },
		                    
		                },
		                {text: '', width: '*'},
		               
		            ]
		        }
			],  styles: {
		                t1: {
		                    fontSize: 10
		                }
	
			}
		}
	let body=dd.content[0].columns[0].table.body;
	for(let o of data)
		body.push(o);
	pdfMake.createPdf(dd).open();

	
}