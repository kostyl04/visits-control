let monthsParseArray=["январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"];
		let formmodel;
//		let monthSelect=$("#month");
//		let yearSelect=$("#year");
		let departmentSelect=$("#dep");
		let personSelect=$("#person");
		let  calendar = null;
		
let timePredict=$("#time");
		window.onload = function() {
			waitingDialog.show('�?дет загрузка формы, пожалуйста подождите');
			setTimeout(function(){
				getformmodel();
			},1000)
			
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
					console.dir(data.map);
					calendar = new Calendar(data.map);
					$(".arrow-left").click(function(){
						calendar.leftArrowOnClick();
					});
					$(".arrow-right").click(function(){
						calendar.rightArrowOnClick();
					});
					formmodel=data;
					fillForm();
					waitingDialog.hide();

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
			//	monthSelect.empty();
				console.dir(formmodel.map);
//				for (let month of formmodel.map[calendar.getSelectedYear()]) {
//					console.log(month)
//				//	monthSelect.append(new Option(monthsParseArray[month-1], month));
//				}
			}
			let changePersons=function(){
				let persons=[];
				personSelect.empty();
				if(departmentSelect.val().length!=0){
				
				
				
				for (let dep of departmentSelect.val()) {
					for(let person of formmodel.selectPersonsList){
//						let year=yearSelect.val();
//						let month=monthSelect.val();
						
						let year = 	calendar.getSelectedYear();
						let month = calendar.getSelectedMonthNumber();
						
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
//						let year=yearSelect.val();
//						let month=monthSelect.val();
						let year = calendar.getSelectedYear();
						let month = calendar.getSelectedMonthNumber();
						
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
			//	console.dir(personSelect);
			}
//			for (let year in formmodel.map) {
//				yearSelect.append(new Option(year, year));
//			}
			for(let person of formmodel.selectPersonsList){
				 personSelect.append(new Option(person.fullName,JSON.stringify(person)));
			}
			for(let dep of formmodel.selectDepartmentsList){
				 departmentSelect.append(new Option(dep.departmentName,dep.departmentName));
			}
			
			changeMonths();
			calendar.setOnchangeCallback(function(){
				changeMonths();
				changePersons();
			});
			
//			yearSelect.change(function(){
//				changeMonths();
//				changePersons();
//			});
//			monthSelect.change(function(){
//				changePersons();
//			});
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
		function Calendar(datesMap){
			this.datesMap=datesMap;
			this.yearIndex=0;
			this.monthIndex=0;
			// this.monthShortNames=['Янв','Фев','Мрт','Апр','Май','�?юн','�?юл','Авг','Сен','Окт','Нбр','Дек'];
			this.monthLabels=$(".month-label");
			this.yearLabel=$(".year-label");
			
			var selfc = this;
			this.getSelectedYear = function(){
				let year=Object.keys(this.datesMap)[selfc.yearIndex];
				return year;
			}
			this.getSelectedYearNumber = function(){
				return selfc.yearIndex;
			}
			this.getSelectedMonthNumber = function(){
				let year=Object.keys(selfc.datesMap)[selfc.yearIndex];
				return selfc.monthIndex;//year[selfc.monthIndex];
			}			
			this.setOnchangeCallback = function( arg ){
				selfc._onChangeCallback = arg;
			}
			
			this.parseDatesInCalendar=function(){
				let year=Object.keys(this.datesMap)[this.yearIndex];
				this.yearLabel.html(year);
				for(let mIndex in this.datesMap[year]){
					this.monthLabels[mIndex].style.display="inline-block";
					this.monthLabels[mIndex].className=this.monthLabels[mIndex].className.replace("active-label","");
				}
				this.monthLabels[this.monthIndex].className+=' active-label';
				
				this.onChange();
			}
			this.rightArrowOnClick=function(){
				
				let inRange=this.yearIndex<Object.keys(this.datesMap).length-1;
				if(inRange){
					this.yearIndex++;
					this.monthIndex=0;
					this.parseDatesInCalendar();
				}
				//onChange();
				
			}
			this.leftArrowOnClick=function(){
				let inRange=this.yearIndex>0;
				if(inRange){
					this.yearIndex--;
					this.monthIndex=0;
					this.parseDatesInCalendar();
				}
				//onChange();
			}
			this.onChange = function(){
				var input_year = $('#year');
				var input_month = $('#month');
				
				input_year.val(this.getSelectedYear());
				input_month.val(this.getSelectedMonthNumber());
				
				input_year.text = 	this.getSelectedYear();
				input_month.text = this.getSelectedMonthNumber();
				
				
				console.log("Selected: " + input_year.value  +" " + input_month.value );
				
				if(selfc._onChangeCallback){
					selfc._onChangeCallback();
				}
			}
			let self = this;
			this.monthLabels.click(function(e){
				// this.monthLabels[mIndex].className
				let index=jQuery.inArray( e.target, self.monthLabels );
				// self.monthLabels[index].className+="active-label";
				self.monthIndex=index;
				self.parseDatesInCalendar();
				//onChange();
				
				
			});
			this.parseDatesInCalendar();
		}
		
		
		
		
		
		
		