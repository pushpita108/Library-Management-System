
<%@page import="java.util.ArrayList"%>
<%@page import="valueObj.SearchFields"%>
<%@page import="valueObj.FineFields"%>
<%@page import="valueObj.CheckInFields"%>
<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Library Management System</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../javascript/library.js"></script>
	<link rel="stylesheet" href="../css/library.css">
</head>
  
  
<body>
<div class="jumbotron">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h1>Library Management System</h1>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<p>Db Design first project : To search, borrow books and maintain account in a library.</p>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<p>Guided by: Prof. Chris Davis</p>
</div>

<div class="container">
	<div class="row">
	<div class="tab">
		<div class="col-lg-4">
			
			<p>
				<button type="button" class="btn btn-success btn-lg" onclick="showWindow(event, 'Search')">Search book</button>
			</p>
		</div>
		<div class="col-lg-4">
			
			<p>
				<button type="button" class="btn btn-success btn-lg" onclick="showWindow(event, 'Add')">Add Borrower</button>
			</p>
		</div>   
		<div class="col-lg-4">
			
			<p>
				<button type="button" class="btn btn-success btn-lg" onclick="showWindow(event, 'Check-in')">Check-in Book</button>
			</p>
		</div>  
	    <div class="col-lg-4">
			
			<p>
				<button type="button" class="btn btn-success btn-lg" onclick="showWindow(event, 'PayFine')">Pay Fine</button>
			</p>
		</div>
		<div class="col-lg-4">
			
			<p>
				<button type="button" class="btn btn-warning btn-lg" onclick="showWindow(event, 'RefreshFines')">Refresh Fines</button>
			</p>
		</div>
	</div>
	</div>
</div>

<!-- Search books in library button and display result table with checkout button for available books -->
<div class="row">
<!-- Search -->
	<div id="Search" class="tab-content" style="margin: 0 auto; width:30%">
		<form method="GET" action="http://localhost:8080/Library_Management_System/BookSearch" onsubmit="return validatesearch();">
  			<p><input type="text" id="searchtextbox" name="searchtextboxname" style="height: 40px; width: 400px; " /></p>
  			<p><button type="submit" class="btn btn-primary" >Search</button></p>
  		</form>
  	</div>
 
  	<div id="searchtable" style="margin: 0 auto; width:90%">
  		<br/><br/>
   		<% if((String)session.getAttribute("searchRes") =="gotResults") {
  		session.removeAttribute("searchRes");
  		%>
		  	           
		  	<table class="table table-striped">
		    <thead>
		      <tr>
		        <th>ISBN</th>
		        <th>Book Title</th>
		        <th>Author</th>
		        <th>Availability</th>
		      </tr>
		    </thead>
		    <tbody>
			    <%
				SearchFields searchRes = (SearchFields)session.getAttribute("searchvalues");
			    ArrayList<String> isbn = new ArrayList<String>();
				ArrayList<String> title = new ArrayList<String>();
				ArrayList<String> author = new ArrayList<String>();
				ArrayList<String> available = new ArrayList<String>();
				
				isbn=searchRes.getIsbn();
				title=searchRes.getTitle();
				author=searchRes.getName();
				available=searchRes.getAvailable();
				
				for(int index=0; index<isbn.size(); index++){ // looping on every isbn as it is the primary key 
			    %>
			      <tr>
			       <td><%= isbn.get( index ) %></td>
			       <td><%= title.get( index ) %></td>
			       <td><%= author.get( index ) %></td>
			       <td><%= available.get( index ) %></td>
			       
			       <% if(available.get(index).equals("YES")){ %>
			       <td> <input type="button" class="btn btn-primary btn-lg" name="checkout" id="<%= isbn.get(index)%>"  onclick="bookcheckout(this.id);" value="checkout"></td>
			       	
			       <%}else if(available.get(index).equals("NO")) {%>
			 	   <td> <input type="button" class="btn btn-disabled btn-lg" name="checkout" onclick="" value="Not available"></td>
			       	
			       <%}%>
			      </tr>
			    <%} %>  
			  </tbody>
			</table>
			<% }
   
    		else if((String)session.getAttribute("searchRes") =="emptyResultSet"){
 		session.removeAttribute("searchRes");
   		%>
   			<p style="margin: 0 auto; width:90%">No books found for this criteria</p>
   		<%} %>
	</div>
</div>
<!-- Search books in library button and display result table with checkout button for available books END -->

<!-- Add Borrower button and Form to enter details and Result Div -->
<div class="row">
	<div id="Add" class="form-group" style="margin: 0 auto;">
		<!-- add borrower -->
		<form method="GET" action="http://localhost:8080/Library_Management_System/AddBorrower" style="text-align: left; margin-left: 450px;" onsubmit="return validateadd();">
			<table>
				<tr>  
					<td><label for="fname">First Name : </label></td>
					<td><input type="text" class="form-control" id="fn" name="fname"></td>
				</tr>
				<tr>
					<td><label for="lname">Last Name : </label></td>
					<td><input type="text" class="form-control" id="ln" name="lname"></td>
				</tr>
				<tr>
					<td><label for="ssn">SSN : </label></td>
					<td><input type="text" class="form-control" id="ssn" name="ssn"></td>
				</tr>
				<tr>
					<td><label for="address">Address : </label></td>
					<td><input type="text" class="form-control" id="address" name="address">
				</td>
				</tr>
				<tr>
					<td><label for="city">City : </label></td>
					<td><input type="text" class="form-control" id="city" name="city">
				</td>
				</tr>
				<tr>
					<td><label for="state">State : </label></td>
					<td><input type="text" class="form-control" id="state" name="state">
				</td>
				</tr>  
				<tr>
					<td><label for="phone">Phone Number : </label></td>
					<td> <input type="text" class="form-control" id="phone" name="phone"></td>
				</tr>
				<tr>
					<td> <button type="submit" class="btn btn-primary" style="align:center">Submit</button></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="addBorrowerResult" style="text-align:center; align:center;">
		<%
		if((String)session.getAttribute("insertRes") =="added") {
			session.removeAttribute("insertRes");%>
			<h5>
			Borrower added with Card id : <%=(String)session.getAttribute("cardid") %>
			</h5>
		<% }else if((String)session.getAttribute("insertRes") =="notAdded"){ 
			session.removeAttribute("insertRes"); %>
			<h5>Borrower could not be added. Kindly re-check entered values.</h5>
		<% }else if((String)session.getAttribute("insertRes") =="alreadyExists"){ 
			session.removeAttribute("insertRes"); %>
			<h5>Borrower already exists. Card id : <%=(String)session.getAttribute("cardid") %> 
			</h5>
		<%}%>
	</div>
</div>	
<!-- Add Borrower button and Form to enter details and Result Div END-->	
	<br><br><br>

<!-- Check in a book Button and Display table -->	
<div class="row">
	<div id="Check-in" class="tab-content" style="margin: 0 auto; width:30%">
		<form method="GET" action="http://localhost:8080/Library_Management_System/BookCheckInSearch" onsubmit="return validatecheckin();">
			<p><input type="text" id="checkinsearchfield" name="checkinsearchfield" style="width: 400px; height: 40px;"/></p>
			<p><button type="submit" class="btn btn-primary" >Search book for Check In</button>  </p>
		</form>
	</div>
	
	 <div id="searchtablecheckin" style="margin: 0 auto; width:90%">
	   <% if((String)session.getAttribute("searchcheckin") == "found") {
	  		session.removeAttribute("searchcheckin");
	    %>
			              
			  <table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Book ISBN</th>
			        <th>First Name</th>
			        <th>Last Name</th>
			        <th>Borrower ID</th>
			      </tr>
			    </thead>
			    <tbody>
			    
			    <%
				CheckInFields search = (CheckInFields)session.getAttribute("searchcheckinval");
			    ArrayList<String> isbn = new ArrayList<String>();
				ArrayList<String> fname = new ArrayList<String>();
				ArrayList<String> lname = new ArrayList<String>();
				ArrayList<String> borrower = new ArrayList<String>();
			
				isbn=search.getIsbn();
				fname=search.getfname();
				lname=search.getlname();
				borrower=search.getBorrowerId();

				for(int i=0;i<isbn.size();i++){
			    %>
			      <tr>
			       <td><%= isbn.get(i)%></td>
			       <td><%= fname.get(i)%></td>
			       <td><%= lname.get(i)%></td>
			       <td><%= borrower.get(i)%></td>
			       <td> <button onclick="checkinbook(this.id)" class="btn btn-primary btn-lg" id="<%=isbn.get(i)%>+<%=borrower.get(i)%>">Check In Book</button>  </td>
			      </tr>
			    <%} %>  
			    </tbody>
			  </table>
	  <% }
	   else if((String)session.getAttribute("searchcheckin") =="empty"){
	 		session.removeAttribute("searchcheckin");
	   %>
	   <p style="margin: 0 auto; width:90%">No books to check in found for this criteria.</p>
	   <%} %>
	</div>
</div>
<!-- Check in a book Button and Display table END -->	

<!-- Refresh fines and Show the table of updated fines -->
<div class="row">
	<div id="RefreshFines" class="tab-content" style="margin: 0 auto; width:30%">
		<form method="GET" action="http://localhost:8080/Library_Management_System/RefreshFines">
			<h4>Are you sure to refresh all fines ?</h4>
			<p><button type="submit" class="btn btn-primary" >Refresh and Show Fines</button></p>
		</form>
	</div>

	<div id="showfinetable" style="margin: 0 auto; width:90%">
	   <% if((String)session.getAttribute("refreshRes") == "success") {
	  		session.removeAttribute("refreshRes");
	    %>
			              
			  <table class="table table-striped">
			    <thead>
			      <tr>
			        <th>Card ID</th>
			        <th>Total Fine</th>
			      </tr>
			    </thead>
			    <tbody>
			    
			    <%
			    FineFields search = (FineFields)session.getAttribute("refreshedFinesRes");
				ArrayList<Float> fines = new ArrayList<Float>();
				ArrayList<String> cardId = new ArrayList<String>();
			
				cardId=search.getCardId();
				fines=search.getFineAmt();

				for(int i=0;i<cardId.size();i++){
			    %>
			      <tr>
			       <td><%= cardId.get(i)%></td>
			       <td><%= fines.get(i)%></td>
			      </tr>
			    <%} %>  
			    </tbody>
			  </table>
	  <% } else if((String)session.getAttribute("refreshRes") =="failed"){
	 		session.removeAttribute("refreshRes");
	   %>
	   <p style="margin: 0 auto; width:90%">Could not refresh fines. Please try again</p>
	   <% } else if((String)session.getAttribute("refreshRes") =="empty"){
	 		session.removeAttribute("refreshRes");
	   %>
	   <p style="margin: 0 auto; width:90%">No fines to display</p>
	   <% } %>
	</div>
</div>
<!-- Refresh fines and Show the table of updated fines END -->

<!--  Pay Fine search bar and table fo results to pay from -->

<div class="row">
	<div id="PayFine" class="tab-content" style="margin: 0 auto; width:30%">
		<form method="GET" action="http://localhost:8080/Library_Management_System/ShowFine" onsubmit="return validatepayfine();">
			<p>Enter borrower card number : </p>
			<p><input type="text" id="payfinesearchfield" name="payfinesearchfield" style="width: 400px; height: 40px;"/></p>
			<p><button type="submit" class="btn btn-primary" >Search</button>  </p>
		</form>
	</div>
	
	<div id="finesearchtable" style="margin: 0 auto; width:90%">
  		<br/><br/>
   		<% if((String)session.getAttribute("showFine") =="success") {
  		session.removeAttribute("showFine");
  		%>
		  	           
		  	<table class="table table-striped">
		    <thead>
		      <tr>
		        <th>CARD ID</th>
		        <th>Total Fine</th>
		        <th></th>
		      </tr>
		    </thead>
		    <tbody>
			    <%
			    FineFields searchRes = (FineFields)session.getAttribute("showFineRes");
			    ArrayList<String> cardid = new ArrayList<String>();
				ArrayList<Float> fineamt = new ArrayList<Float>();
				ArrayList<String> datein = new ArrayList<String>();
				
				cardid=searchRes.getCardId();
				fineamt=searchRes.getFineAmt();
				datein=searchRes.getDateIn();
				
				for(int index=0; index<cardid.size(); index++){ // looping on every isbn as it is the primary key 
					System.out.println("card id from jsp : " + cardid.get(index));
			    %>
			      <tr>
			       <td><%= cardid.get( index ) %></td>
			       <td><%= fineamt.get( index ) %></td>
			       <td> <input type="button" class="btn btn-primary btn-lg" name="payfine" id="<%= cardid.get(index)%>"  onclick="payfine(this.id);" value="pay"></td>
			     
			      </tr>
			    <%} %>  
			  </tbody>
			</table>
			<% }
   
    		else if((String)session.getAttribute("showFine") =="failed"){
 		session.removeAttribute("showFine");
   		%>
   			<p style="margin: 0 auto; width:90%">No fines found for this ID. You may have defaulting books which are not returned yet.</p>
   		<%} %>
	</div>
</div>

<!--  Pay Fine search bar and table fo results to pay from END-->

</body>
</html>