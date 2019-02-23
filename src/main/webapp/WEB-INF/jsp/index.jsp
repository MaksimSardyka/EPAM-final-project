<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>Auction</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<!-- Ajax -->
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script type="text/javascript">
	GET: $(document).ready(function() {
		// GET REQUEST
		$("#getLotSet").click(function(event) {
			event.preventDefault();
			ajaxGet();
		});

		// DO GET
		function ajaxGet() {
			$.ajax({
				url : "http://localhost:9090/lot",
				success : function(result) {
					console.log(result);
					$('pre').html(JSON.stringify(result, undefined, 2));
				}
			});
		}
	})
</script>
<script type="text/javascript">
	GET: $(document).ready(function() {
		// GET REQUEST
		$("#getLotById").click(function(event) {
			event.preventDefault();
			ajaxGetLotById();
		});

		// DO GET
		function ajaxGetLotById() {
			$.ajax({
				url : "http://localhost:9090/lot/"+$("#lotId").val(),
				success : function(result) {
					console.log(result);
					$('pre').html(JSON.stringify(result, undefined, 2));
				}
			});
		}
	})
</script>
<body class="d-flex flex-column">

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="/">Auction</a>
		<c:if test="${empty lotAll}">
			<a href="/lot/" class="btn btn-primary">Look at our lot list.</a>
		</c:if>
	</nav>

	<!-- Ajax -->
	<button id="getLotSet" type="button" class="btn btn-primary">Get
		lot set</button>
	<br />
	<input type="number" id="lotId" placeholder="Lot id" value="" />
	<button id="getLotById" type="button" class="btn btn-primary">Get
		lot by Id</button>
	<pre></pre>
	<!-- Ajax -->

	<div class="container text-center">
		<div class="row justify-content-center">
			<div class="col-md-7">
				<c:if test="${not empty about}">
			${about}<br />
				</c:if>
				<c:if test="${not empty terms}">
			${terms}<br />
				</c:if>
			</div>
		</div>
	</div>

	<footer class="fixed-bottom navbar-dark bg-dark">
		<div class="container text-center">
			<c:if test="${empty terms}">
				<a href="/terms/" class="btn btn-primary">Our terms and
					conditions.</a>
			</c:if>
			<c:if test="${empty about}">
				<a href="/about/" class="btn btn-primary">About us.</a>
				<br />
			</c:if>
		</div>
	</footer>
</body>
</html>