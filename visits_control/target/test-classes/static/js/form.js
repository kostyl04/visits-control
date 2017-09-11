let formmodel;
let monthSelect=$("#month");
let yearSelect=$("#year");
let departmentSelect=$("#dep");
let personSelect=$("#person");

window.onload = function() {
	console.log(yearSelect.value);
	getformmodel();
	$("#form").submit(function(){
		if(personSelect.val().length==0){
			$("#person option").prop("selected", "selected");}
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
function fillForm(){
	console.dir(formmodel);
	let changeMonths=function(){
		monthSelect.empty();
		for (let month of formmodel.map[yearSelect.val()]) {
			console.log(month)
			monthSelect.append(new Option(month, month));
		}
	}
	let changePersons=function(){
		if(departmentSelect.val().length!=0){
		personSelect.empty();
		let persons=[];
		
		for (let dep of departmentSelect.val()) {
			for(let person of formmodel.selectPersonsList){
				if(person.personDepartmentName==dep)
					persons.push(person);
			}
				
		}
		for(let person of persons){
			personSelect.append(new Option(person.fullName,JSON.stringify(person)));
		}}else{
			for(let person of formmodel.selectPersonsList){
				 personSelect.append(new Option(person.fullName,JSON.stringify(person)));
			}
		}
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