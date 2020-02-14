<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Trispesa</title>

<!-- Bootstrap  -->
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- css -->
<link rel="stylesheet" href="../css/jquery-ui.css">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/footer.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>

	<!-- Navigation NON TOCCARE!!! -->
	<nav id="nav"
		class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="nav-item py-0 navbar-brand" href="../administration">
				Tri<span class="span-title">Spesa</span> Administration
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="btn" id="home-button"
						href="../administration">Home</a></li>
					<li class="nav-item"><a class="btn" id="supermarket-button"
						href="supermarket">Gestione supermercati</a></li>
					<li class="nav-item"><a class="btn" id="product-button"
						href="">Gestione prodotti</a></li>
					<li class="nav-item"><a class="btn" id="stats-button" href="#">Statistiche</a></li>
					<li class="nav-item"><a class="btn" id="maps-button" href="#">Mappe</a></li>
					<li class="nav-item py-0"><a href="../user/home"
						id="logout-button" class="btn">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row mx-auto d-flex justify-content-center">
			<button id="add-chart" style="margin: 10px;" type="button"
				class="btn add-item">+ Aggiungi un altro grafico</button>
		</div>
		<div id="charts-container" class="row mx-auto d-flex justify-content-center">
<!-- 			<div id="chart-1" class="col-lg-6 col-md-6 mb-4"> -->
<!-- 				<form> -->
<!-- 					<div class="form-group"> -->
<!-- 						<label for="type">Tipo:</label> <select id="type-select" -->
<!-- 							onchange="typeSelected()" name="type" class="form-control"> -->
<!-- 							<option value="null">---</option> -->
<!-- 							<option value="supermarket">Supermercati</option> -->
<!-- 							<option value="category">Categorie</option> -->
<!-- 							<option value="product">Prodotti</option> -->
<!-- 						</select> -->
<!-- 					</div> -->
<!-- 					<div id="all-supermarkets" class="form-group" -->
<!-- 						style="display: none;"> -->
<!-- 						<input id="all-supermarkets-check" name="allSupermarkets" -->
<!-- 							type="checkbox" onclick="allSupermarketsSelected()" /> Tutti i -->
<!-- 						supermercati <input id="supermarket-string" -->
<!-- 							placeholder="Digita e scegli..." class="form-control" -->
<!-- 							name="supermarket" type="text" /> <input id="supermarket-id" -->
<!-- 							type="hidden" name="supermarket" /> -->
<!-- 					</div> -->
<!-- 					<div id="all-categories" class="form-group" style="display: none;"> -->
<!-- 						<input id="all-categories-check" name="allCategories" -->
<!-- 							type="checkbox" onclick="allCategoriesSelected()" /> Tutte le -->
<!-- 						categorie <input id="category-string" -->
<!-- 							placeholder="Digita e scegli..." class="form-control" -->
<!-- 							name="category" type="text" /><input id="category-id" -->
<!-- 							type="hidden" name="category" /> -->
<!-- 					</div> -->
<!-- 					<div id="all-products" class="form-group" style="display: none;"> -->
<!-- 						<input id="all-products-check" name="allProducts" type="checkbox" -->
<!-- 							onclick="allProductsSelected()" /> Tutti i prodotti <input -->
<!-- 							id="product-string" placeholder="Digita e scegli..." -->
<!-- 							class="form-control" name="product" type="text" /><input -->
<!-- 							id="product-id" type="hidden" name="product" /> -->
<!-- 					</div> -->
<!-- 					<div id="data-type" class="form-group" style="display: none;"> -->
<!-- 						<label for="type">Tipo dei dati:</label> <select -->
<!-- 							id="data-type-select" name="dataType" class="form-control"> -->
<!-- 							<option value="cash" selected>Guadagni in &euro;</option> -->
<!-- 							<option value="units">Unità vendute</option> -->
<!-- 						</select> -->
<!-- 						<button style="margin: 10px;" id="generate-graph" type="button" -->
<!-- 							class="btn add-item">Genera grafico</button> -->
<!-- 					</div> -->
<!-- 				</form> -->
<!-- 				<div id="chart-container-1" style="height: 370px; width: 100%;"></div> -->
<!-- 			</div> -->
		</div>
	</div>

	<footer class="footer-distributed">
		<div class="footer-left">
			<h3>
				Tri<span class="span-title">Spesa</span>
			</h3>
			<p class="footer-company-name">Trispesa © 2020</p>
		</div>
		<div class="footer-center">
			<div>
				<i class="fa fa-map-marker"></i>
				<p>
					<span>Via Pietro Bucci</span>Rende, Cosenza
				</p>
			</div>
			<div>
				<i class="fa fa-phone"></i>
				<p>348-3218976</p>
			</div>
			<div>
				<i class="fa fa-envelope"></i>
				<p>
					<a href="mailto:trispesaStaff@gmail.com">trispesastaff@gmail.com</a>
				</p>
			</div>
		</div>
		<div class="footer-right">
			<p class="footer-company-about">
				<span>Informazioni sito:</span> Questo progetto è stato sviluppato
				da un gruppo di studenti dell'Università della Calabria,
				dipartimento di Matematica e Informatica, per l'esame di Ingegneria
				del Software.
			</p>
			<div class="footer-icons">
				<a href="https://www.mat.unical.it/demacs"><img
					src="../images/logo_unical.png" width="24" height="20"></img></a> <a
					href="https://github.com/Goffredson/Trispesa"><i
					class="fa fa-github"></i></a>
			</div>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="../vendor/jquery/jquery.min.js"></script>
	<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="../js/statistics.js"></script>
</body>
</html>