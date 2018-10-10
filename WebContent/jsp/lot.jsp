<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Lot page</title>
<!-- To adapt for device's screen width: -->
<%@include file="/jsp/fragment/head_info.jsp"%>
<!-- Custom styles -->
<link rel="stylesheet" href="css/container.css">
        <link rel="stylesheet" href="css/form.css">
</head>

<body>
    <%@include file="/jsp/fragment/header.jsp"%>
	<!-- Lot -->
	<div class="container">
		<div class="row pt-5 align-items-center">
			 	<%@include file="/jsp/fragment/lot_data.jsp"%>
		</div>
		 
	</div>
    <%@include file="/jsp/fragment/footer.jsp"%>
</body>
</html>