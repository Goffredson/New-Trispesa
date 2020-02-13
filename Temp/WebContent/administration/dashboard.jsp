<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Trispesa</title>

<!-- Bootstrap  -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- css -->
<link href="css/main.css" rel="stylesheet">
<link href="css/footer.css" rel="stylesheet">
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
			<a class="nav-item py-0 navbar-brand" href=""> Tri<span
				class="span-title">Spesa</span> Administration
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="btn" id="home-button"
						href="">Home</a></li>
					<li class="nav-item"><a class="btn" id="supermarket-button"
						href="administration/supermarket">Gestione supermercati</a></li>
					<li class="nav-item"><a class="btn" id="product-button"
						href="administration/product">Gestione prodotti</a></li>
					<li class="nav-item"><a class="btn" id="stats-button" href="#">Statistiche</a></li>
					<li class="nav-item"><a class="btn" id="maps-button" href="#">Mappe</a></li>
					<li class="nav-item py-0"><a href="user/home"
						id="logout-button" class="btn">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="row mx-auto">
			<h2 class="mx-auto title-secondary">Benvenuto
				${sessionScope.administrator.username}</h2>
		</div>

		<div class="row mx-auto">
			<div class="col-lg-6 col-md-6 mb-4">
				<a href="administration/supermarket">
					<div class="card d-flex">
						<img class="card-img-top"
							src="images/administration/superMarket.png" height="250" alt="" />
						<div class="card-body mx-auto">
							<h4 class="card-title admin-card">Gestione supermercati</h4>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-6 col-md-6 mb-4">
				<a href="administration/product">
					<div class="card d-flex">
						<img class="card-img-top" src="images/administration/products.png"
							height="250" alt="" />
						<div class="card-body mx-auto">
							<h4 class="card-title admin-card">Gestione prodotti</h4>
						</div>
					</div>
				</a>
			</div>
		</div>

		<div class="row mx-auto">
			<div class="col-lg-6 col-md-6 mb-4">
				<a href="administration/statistics">
					<div class="card d-flex">
						<img class="card-img-top"
							src="images/administration/statistics.jpg" height="250" alt="" />
						<div class="card-body mx-auto">
							<h4 class="card-title admin-card">Statistiche</h4>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-6 col-md-6 mb-4">
				<a href="#">
					<div class="card d-flex">
						<img class="card-img-top" src="images/administration/maps.jpg"
							height="250" alt="" />
						<div class="card-body mx-auto">
							<h4 class="card-title admin-card">Mappe</h4>
						</div>
					</div>
				</a>
			</div>
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
					src="images/logo_unical.png" width="24" height="20"></img></a> <a
					href="https://github.com/Goffredson/Trispesa"><i
					class="fa fa-github"></i></a>
			</div>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>