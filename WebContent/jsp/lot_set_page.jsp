<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Bootstrap 4 Example</title>
<meta charset="utf-8">
<!-- To adapt for device's screen width: -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- B4 links: -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<!-- Custom JS -->
<script src="js/sidebar.js"></script>
<!-- Custom styles -->
<link rel="stylesheet" href="css/main/main.css">
<link rel="stylesheet" href="css/style.css">
</head>

<body>
        <%@include file="/jsp/fragment/header.jsp"%>
	<!-- Central part(Cards)-->
	<div class="container-fluid content wrapper">
		<!-- Sidebar -->
		<nav class="categories bg-secondary" class="text-light">
			<ul class="components">
				<!-- Элемент сайдбара без дочерних элементов -->
				<li><a href="#">Категория без подкатегорий</a></li>
				<!-- /Элемент сайдбара без дочерних элементов -->
				<!-- Элемент сайдбара без дочерних элементов -->
				<li><a href="#">Категория 1</a></li>
				<!-- /Элемент сайдбара без дочерних элементов -->
				<!-- Элемент сайдбара без дочерних элементов -->
				<li><a href="#">Категория 2</a></li>
				<!-- /Элемент сайдбара без дочерних элементов -->
				<!-- Эллемент сайдбара с дочерними элементами -->
				<li><a href="#homeSubmenu" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle">Антиквариат</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a href="#">Книги</a></li>
						<li><a href="#">Монеты</a></li>
						<li><a href="#">Картины</a></li>
					</ul></li>
				<!-- /Эллемент сайдбара с дочерними элементами -->
				<!-- Эллемент сайдбара с дочерними элементами -->
				<li><a href="#pageSubmenu" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle">Pages</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li><a href="#">Page 1</a></li>
						<li><a href="#">Page 2</a></li>
						<li><a href="#">Page 3</a></li>
					</ul></li>
				<!-- /Эллемент сайдбара с дочерними элементами -->
			</ul>
		</nav>
		<!-- /Sidebar -->
		<!-- Central content -->
		<div class="bg-light">
			<!-- Breadcrumb -->
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item"><a href="#">Library</a></li>
				<li class="breadcrumb-item active">Data</li>
			</ol>
			<!-- /Breadcrumb -->
			<div class="container-fluid">
				<!-- Cards -->
				<div class="row">
					<!-- Card -->
					<c:forEach items="${requestScope.lot_set}" var="lot">
						<div class="col-sm-6 col-md-4 col-xl-3">
							<div class="card mb-4 shadow-sm">
								<!-- mb-4 = отступ снизу  -->
								<img class="card-img-top" src="http://placehold.it/500x500"
									alt="Card image cap">
								<div class="card-body">
									<p class="card-text">${lot.name}</p>
									<form method="POST" action="controller">
										<input type="hidden" name="command" value="lot_page">
										<input type="hidden" name="lot_id" value="${lot.id}"> <input
											type="submit" class="btn btn-outline-primary btn-block"
											value="See auction">
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- /Card -->
				</div>
				<!-- /Cards -->
				<!-- Pagination -->
				<ul class="pagination">
					<li class="page-item disabled"><a class="page-link" href="#">&laquo;</a>
					</li>
					<li class="page-item active"><a class="page-link" href="#">1</a>
					</li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#">4</a></li>
					<li class="page-item"><a class="page-link" href="#">5</a></li>
					<li class="page-item"><a class="page-link" href="#">&raquo;</a>
					</li>
				</ul>
				<!-- /Pagination -->
			</div>
		</div>
	</div>
	<!-- /Central part(Cards)-->
	<!-- Footer -->
	<footer class="page-footer bg-dark">
		<!-- Copyright -->
		<div class="footer-copyright text-center py-3 text-light">Copyright
			EPAM© 2018</div>
		<!-- /Copyright -->
	</footer>
	<!-- /Footer -->
</body>
</html>