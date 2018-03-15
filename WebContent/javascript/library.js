
function showWindow(event, action) {
    hidelements();
    document.getElementById(action).style.display = "block";
    event.currentTarget.className += " active";
}

function hidelements(){
	 if( document.getElementById("Search")){
		    document.getElementById("Search").style.display = "none";

	}if( document.getElementById("searchtable")){
		    document.getElementById("searchtable").style.display = "none";

	}if( document.getElementById("addBorrowerResult")){
		    document.getElementById("addBorrowerResult").style.display = "none";

	}if( document.getElementById("Add")){
		    document.getElementById("Add").style.display = "none";

	}if( document.getElementById("searchtablecheckin")){
		    document.getElementById("searchtablecheckin").style.display = "none";

	}if( document.getElementById("Check-in")){
		    document.getElementById("Check-in").style.display = "none";

	}if( document.getElementById("showfinetable")){
	    document.getElementById("showfinetable").style.display = "none";
	    
	}if( document.getElementById("RefreshFines")){
	    document.getElementById("RefreshFines").style.display = "none";

	}if( document.getElementById("finesearchtable")){
	    document.getElementById("finesearchtable").style.display = "none";
	    
	}if( document.getElementById("PayFine")){
	    document.getElementById("PayFine").style.display = "none";

	}
	 
}


function validatesearch(){
	if(document.getElementById('searchfield').value == "" || document.getElementById('searchfield').value == null){
		alert("Please enter a search criteria!");
		return false;
	}
	return true;
}

function bookcheckout(isbn){
	var cardid = prompt("Please enter your Card Id for checkout ?</br>", "ID");
	if(cardid){
		var xmlhttp;
	    if (window.XMLHttpRequest) {// Doesnt work in IE6 and older
	        xmlhttp = new XMLHttpRequest();
	    } 
	    xmlhttp.onreadystatechange = function() {
	    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	    		alert(xmlhttp.responseText);
	    		location.reload();
	        }
	    }
	    xmlhttp.open('GET', 'http://localhost:8080/Library_Management_System/BookCheckOut?isbn='+isbn+'&cardid='+cardid, true);
	    xmlhttp.send(null);
	}

}

function validateadd(){
	if(document.getElementById('fn').value == "" || document.getElementById('fn').value == null){
		alert("Please enter First Name");
		return false;
	}
	if(document.getElementById('ln').value == "" || document.getElementById('ln').value == null){
		alert("Please enter Last Name");
		return false;
	}
	if(document.getElementById('ssn').value == "" || document.getElementById('ssn').value == null){
		alert("Please enter SSN");
		return false;
	}
	var ssnregex = new RegExp("^\\d{3}\\-\\d{2}\\-\\d{4}$");
	if( !ssnregex.test(document.getElementById('ssn').value) ){
		alert("Please enter valid 9 digit SSN. Example: 999-99-9999");
		return false;
	}
	if(document.getElementById('address').value == "" || document.getElementById('address').value == null){
		alert("Please enter Address");
		return false;
	}
	if(document.getElementById('city').value == "" || document.getElementById('city').value == null){
		alert("Please enter City");
		return false;
	}
	if(document.getElementById('state').value == "" || document.getElementById('state').value == null){
		alert("Please enter State");
		return false;
	}
	if(document.getElementById('phone').value == "" || document.getElementById('phone').value == null){
		alert("Please enter Phone number");
		return false;
	}
	var phoneregex = new RegExp("^\\d{10}$");
	if( !phoneregex.test(document.getElementById('phone').value) ){
		alert("Please enter valid 10 digit phone number. Example: 9999999999");
		return false;
	}
	
	return true;
	
}


function validatecheckin(){
	if(document.getElementById('searchfieldcheckin').value == "" || document.getElementById('searchfieldcheckin').value == null){
		alert("Please enter a search criteria");
		return false;
	}
	return true;
}

function checkinbook(isbnandid){
	var xmlhttp;
	var param=isbnandid.split('+');
    if (window.XMLHttpRequest) {// doesnt run in IE6 and older
        xmlhttp = new XMLHttpRequest();
    } 
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    			alert(xmlhttp.responseText);
    			location.reload();
        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/Library_Management_System/BookCheckIn?isbn='+param[0]+'&id='+param[1], true);
    xmlhttp.send(null);

}

function validatepayfine(){
	if(document.getElementById('payfinesearchfield').value == "" || document.getElementById('payfinesearchfield').value == null){
		alert("Please enter a search criteria");
		return false;
	}
	return true;
}

function payfine(cardid){
	alert(cardid);
	var xmlhttp;
    if (window.XMLHttpRequest) {// doesnt run in IE6 and older
        xmlhttp = new XMLHttpRequest();
    } 
    
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	    		alert(xmlhttp.responseText);
	    		location.reload();
        }
    }
    xmlhttp.open('GET', 'http://localhost:8080/Library_Management_System/PayFine?cardid='+cardid, true);
    xmlhttp.send(null);
	
	
}




