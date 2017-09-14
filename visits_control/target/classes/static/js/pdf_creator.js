/**
 * 
 */
function pdf_creator_init(){
	this.print_button = document.getElementById('print-button');
	this.print_button.onclick=print_button_onclick;
}
function print_button_onclick(){
	alert('print');
}