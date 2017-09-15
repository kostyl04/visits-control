let monthsParseArray=["январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"];
		let formmodel;
		let monthSelect=$("#month");
		let yearSelect=$("#year");
		let departmentSelect=$("#dep");
		let personSelect=$("#person");
let timePredict=$("#time");
		window.onload = function() {
			console.log(yearSelect.value);
			getformmodel();
			$("#form").submit(function(){
				if(personSelect.val().length==0){
					$("#person option").prop("selected", "selected");}
				timePredict.html(getPredictTime());
			});
			var clearbtn=document.getElementById("clear");
			clearbtn.onclick=function(){
				var personSelect=document.getElementById("person");
				var depSelect=document.getElementById("dep");
				for(let opt of personSelect.options){
					if(opt.selected)opt.selected=false;
				}
				for(let opt of depSelect.options){
					if(opt.selected)opt.selected=false;
				}
				$("#dep").select2().change();
				$("#person").select2().change();
			};
			

		}
		function getformmodel() {
			$("#form :input").prop("disabled", true);
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : path + "api/getstartform",
				dataType : 'json',
				timeout : 100000,
				success : function(data) {
					
					formmodel=data;
					fillForm();

				},
				error : function(e) {
					console.log("ERROR: ", e);
					fillForm(e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}
		function getPredictTime(){
			let count = $('#person option:selected').length;
			count=count==0?$('#person option').length:count;
			console.log(count);
			let time=count*168;
			return "Ожидаемое время выполнения "+parseTime(time);
		};
		function parseTime(time){
			var minutes = Math.floor(time / 60000);
			  var seconds = ((time % 60000) / 1000).toFixed(0);
			  return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
		};
		function fillForm(){
			console.dir(formmodel);
			let changeMonths=function(){
				monthSelect.empty();
				for (let month of formmodel.map[yearSelect.val()]) {
					console.log(month)
					monthSelect.append(new Option(monthsParseArray[month-1], month));
				}
			}
			let changePersons=function(){
				let persons=[];
				personSelect.empty();
				if(departmentSelect.val().length!=0){
				
				
				
				for (let dep of departmentSelect.val()) {
					for(let person of formmodel.selectPersonsList){
						let year=yearSelect.val();
						let month=monthSelect.val();
						let isNotDismiss=false;
						if(person.dismissDate==null||person.dismissDate[0]>year)
							isNotDismiss=true;
						else if(person.dismissDate[0]==year&&person.dismissDate[1]>=month)
							isNotDismiss=true;
						if(person.personDepartmentName==dep&&isNotDismiss)
							persons.push(person);
					}
						
				}
				for(let person of persons){
					personSelect.append(new Option(person.fullName,JSON.stringify(person)));
				}}else{
					for(let person of formmodel.selectPersonsList){
						let year=yearSelect.val();
						let month=monthSelect.val();
						let isNotDismiss=false;
						if(person.dismissDate==null||person.dismissDate[0]>year)
							isNotDismiss=true;
						else if(person.dismissDate[0]==year&&person.dismissDate[1]>=month)
							isNotDismiss=true;
						if(isNotDismiss)
							persons.push(person);
					}
					for(let person of persons){
						personSelect.append(new Option(person.fullName,JSON.stringify(person)));
					}
				}
				console.dir(personSelect);
			}
			for (let year in formmodel.map) {
				yearSelect.append(new Option(year, year));
			}
			for(let person of formmodel.selectPersonsList){
				 personSelect.append(new Option(person.fullName,JSON.stringify(person)));
			}
			for(let dep of formmodel.selectDepartmentsList){
				 departmentSelect.append(new Option(dep.departmentName,dep.departmentName));
			}
			
			changeMonths();
			yearSelect.change(function(){
				changeMonths();
				changePersons();
			});
			monthSelect.change(function(){
				changePersons();
			});
			departmentSelect.change(function(){
				changePersons();
			});
			$("#form :input").prop("disabled", false);
			departmentSelect.select2({
				placeholder: 'Все'
			});
			personSelect.select2({
				placeholder: 'Все'
			});
			
		}