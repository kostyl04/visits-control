/**
 * FILTER CREATED BY ROMAN KOSTYL VERSION 1.0 PATTERN CHAIN OF RESPONSOBILITIES
 */
	  var fioinput = document.getElementById("fio-input");
	  var rateinput = document.getElementById("rate-input");
	  var percentinput = document.getElementById("percent-input");
	  var depinput = document.getElementById("dep-input");
	  
	  var dep_names_list = document.getElementById("departments_names");
	    function DepSet(){
	    	this.set = [];
	    }
	    DepSet.prototype.add = function(value){
	    	if (this.set.indexOf(value) == -1){ 
	    		  this.set.push(value);
	    	}
	    }  
	    DepSet.prototype.clear = function(){
	    	this.set = [];
	    }
	    DepSet.prototype.values = function(){
	    	return this.set;
	    }
	    DepSet.prototype.containce= function(_val){
	    	return this.set.indexOf(_value) == -1?false:true;
	    }
	    
	  var DepartmentNamesSet = new DepSet(); 

	  var fiocol = document.getElementsByClassName("fiocol");
	  var ratecol = document.getElementsByClassName("ratecol");
	  var percentcol = document.getElementsByClassName("percentcol");
	  var trs = document.getElementById("table-results-body").getElementsByTagName("tr");
	  var filterChain = new filterChain();
	  rateinput.oninput = function() {
	      rateinput.onkeyup();
	  }
	  percentinput.oninput = function() {
	      percentinput.onkeyup();
	  }
	  rateinput.onkeyup = function() {
	      filterChain.doChain();
	  };
	  percentinput.onkeyup = function() {
	      filterChain.doChain();
	  };
	  fioinput.onkeyup = function() {
	      filterChain.doChain();

	  };
	  depinput.onkeyup = function() {
	      filterChain.doChain();
	  };
	  document.querySelector('input[list="departments_names"]').addEventListener('input', function(){
		  filterChain.doChain();
	  });
	  document.getElementsByClassName("btn-clear-deps-input")[0].onclick=function(){
		  depinput.value='';
		 fioinput.value =''; 
		 rateinput.value=0;
		  percentinput.value=0;
		
		  filterChain.doChain();
	  }
// var saved_onload = window.onload;
	  window.onload = function(){
		  pdf_creator_init();
		  dep_names_list.innerHTML = '';
      	  var options = '';
      	  
		  for (let tr of trs) {
              for (let td of tr.getElementsByTagName("td")) {
            	  if( !(td.id == "ratecol" ) ) continue; 
              
            	  for(var sub of td.title.toUpperCase().trim().split('\n')){
            		  DepartmentNamesSet.add(sub);
            	  }
          	
            	  }
              }
		  for(var val of DepartmentNamesSet.values()){
      		  options += '<option value="'+val+'" />';
      	  }
      	  dep_names_list.innerHTML = options;
		  
	  }

	  function filterChain() {
	      this.startFilter = new fullNameFilter();
	      this.doChain = function() {
	          this.reset();
	          for (let tr of trs) {
	              for (let td of tr.getElementsByTagName("td")) {
	                	 this.startFilter.filter(td);    
	              }
	          }
	          this.removeUselessDeps();



	      };
	      this.reset = function() {
	          for (let tr of trs)
	              tr.style.display = "";
	      };
	      this.removeUselessDeps=function(){
	    	  let bool=false;
	      		let depnametrid;
	      	for(let i=0;i<trs.length;i++){
	      		if(trs[i].id=="depname-tr"){
	      			if(bool==true){
	      				trs[depnametrid].style.display="none";
	      				
	      			}
	      			depnametrid=i;
      				bool=true;
	      		}else if(trs[i].style.display==""){
	      			bool=false;
	      		}else if((i==trs.length-1)&&bool==true){
	      			trs[depnametrid].style.display="none";
	      		}
	      			
	      		
	      	}
	      		
	      };
	      
	      
	  }

	  function fullNameFilter() {

	      this.nextFilter = new rateSizeFilter();
	      this.filter = function(td) {
	          var filter = fioinput.value;

	          if (td.id == "fiocol" && td.innerHTML.toUpperCase().indexOf(filter.toUpperCase()) == -1) {
	              td.parentNode.style.display = "none";

	          } else if (this.nextFilter) this.nextFilter.filter(td);


	      }
	  };

	  function rateSizeFilter() {
	      this.nextFilter = new percentFilter();
	      this.filter = function(td) {
	          var filter = Number(rateinput.value);
	         
	          // if(Number(td.innerHTML)!=0)
	          if (td.id == "ratecol" && Number(td.innerHTML) != filter&&filter!=0) {
	              td.parentNode.style.display = "none";

	          } else if (this.nextFilter) this.nextFilter.filter(td);


	      }
	  };

	  function percentFilter() {
		  this.nextFilter = new depFilter();
	      this.filter = function(td) {
	          var filter = Number(percentinput.value);
	          let value=Number(td.innerHTML.split("%")[0]);
	          if (td.id == "percentcol" && value > filter&&filter) {
	              td.parentNode.style.display = "none";

	          } else if (this.nextFilter) this.nextFilter.filter(td);

	      }
	  };
	  function depFilter() {

	      this.filter = function(td) {
	          var filter = depinput.value;
	          if (td.id == "ratecol" && td.title.toUpperCase().indexOf(filter.toUpperCase()) == -1) {
	              td.parentNode.style.display = "none";

	          } else {  
	        	 
	          	  
	        	  if (this.nextFilter) this.nextFilter.filter(td);
	          }

	      }
	   

	    
// var set = new DepSet();
// set.add('отдел');
// set.add('отдел гидро');
// set.add('отдел гидродина');
// set.clear();
// set.add('3');
// for(var val of set.values()){
// console.log(val);
// }
//	      
	      
	      
	      
	  };