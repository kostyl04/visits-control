/**
	   * FILTER CREATED BY ROMAN KOSTYL VERSION 1.0 PATTERN CHAIN OF RESPONSOBILITIES
	   */
	  var fioinput = document.getElementById("fio-input");
	  var rateinput = document.getElementById("rate-input");
	  var percentinput = document.getElementById("percent-input");


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

	  function filterChain() {
	      this.filters = [new fullNameFilter(), new rateSizeFilter(), new percentFilter()];
	      this.doChain = function() {
	          this.reset();
	          for (let tr of trs) {
	              for (let td of tr.getElementsByTagName("td")) {
	                  for (let filter of this.filters) {
	                      filter.filter(td);
	                  }
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
	          if (td.id == "ratecol" && Number(td.innerHTML) < filter) {
	              td.parentNode.style.display = "none";

	          } else if (this.nextFilter) this.nextFilter.filter(td);


	      }
	  };

	  function percentFilter() {

	      this.filter = function(td) {
	          var filter = Number(percentinput.value);
	          if (td.id == "percentcol" && Number(td.innerHTML) < filter) {
	              td.parentNode.style.display = "none";

	          } else if (this.nextFilter) this.nextFilter.filter(td);

	      }
	  };