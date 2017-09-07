<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resume Submit</title>

<!-- jquery for date picker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>

</head>
<body>
<!-- spring form tags  -->
<form:form  enctype="multipart/form-data" method="POST" action="candidatedatahandler">
 <!-- required -->
<form:input path="name" placeholder="Enter Fullname" /><br>  

<!-- jquery datepicker --> 
<form:input path="date" id="datepicker" placeholder="Enter Date of birth" /><br>

<!--label tag's for attribute takes id of input tag as value   -->
<input type="file" name="image"><br>


<!-- label tag around input doesn't require for attribute -->
<input type="file" name="resume"><br> 
<input type="submit"> 
</form:form>

</body>
</html>