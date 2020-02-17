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

<!-- Maps -->
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
	integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
	crossorigin="" />
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
	integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
	crossorigin=""></script>

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
						href="">Gestione supermercati</a></li>
					<li class="nav-item"><a class="btn" id="product-button"
						href="product">Gestione prodotti</a></li>
					<li class="nav-item"><a class="btn" id="stats-button"
						href="statistics">Statistiche</a></li>
					<li class="nav-item py-0"><a href="../user/home"
						id="logout-button" class="btn">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="d-flex justify-content-between">
			<!-- barra di ricerca -->
			<div id="search-bar" class="input-group">
				<input id="query-string" onkeyup="querySuperMarketsString()"
					type="text" class="form-control" placeholder="Supermercato">
			</div>
			<!-- Aggiungi supermercato -->
			<div>
				<button id="addSuperMarket" onclick="prepareAddSupermarket()"
					class="btn add-item" role="button">+ Aggiungi supermercato</button>
			</div>
		</div>

		<table class="table table-hover table-responsive">
			<tr>
				<th>NOME</th>
				<th>NAZIONE</th>
				<th>CITTA'</th>
				<th>INDIRIZZO</th>
				<th>AFFILIATO</th>
				<th></th>
				<th></th>
			</tr>
			<tbody id="super-market-table-body">
				<c:set var="cont" value="1"></c:set>
				<c:forEach items="${superMarkets}" var="superMarket">
					<tr>
						<td>${superMarket.name}</td>
						<td>${superMarket.country}</td>
						<td>${superMarket.city}</td>
						<td>${superMarket.address}</td>
						<c:if test="${superMarket.affiliate == true}">
							<td id="supermarket-affiliate">SI</td>
						</c:if>
						<c:if test="${superMarket.affiliate == false}">
							<td id="supermarket-not-affiliate">NO</td>
						</c:if>
						<td width="10%"><button
								id="modify-supermarket-${superMarket.id}"
								onclick="prepareModSupermarket(${superMarket.id})"
								class="btn mod-item" role="button">Modifica
								supermercato</button></td>
						<td width="10%"><c:if test="${superMarket.affiliate == true}">
								<button data-toggle="modal" data-target="#delete-modal-${cont}"
									class="btn del-item" role="button">Rimuovi
									affiliazione</button>
							</c:if> <c:if test="${superMarket.affiliate == false}">
								<button onclick="affiliateSupermarket(${superMarket.id})"
									class="btn add-item" role="button">Aggiungi
									affiliazione</button>
							</c:if> <!-- The Modal -->
							<div class="modal" id="delete-modal-${cont}">
								<div class="modal-dialog">
									<div class="modal-content">
										<!-- Modal Header -->
										<div class="modal-header">
											<h4 class="modal-title">ATTENZIONE!</h4>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										<!-- Modal body -->
										<div class="modal-body">
											Rimuovendo l'affiliazione del supermercato verrano rimossi
											tutti i prodotti venduti dal supermercato.<br>Sei sicuro
											di voler continuare?
										</div>
										<!-- Modal footer -->
										<div class="modal-footer">
											<button
												onclick="deleteSupermarket(${superMarket.id}, ${cont})"
												type="button" class="btn del-item">Rimuovi
												affilizione</button>
											<button type="button" class="btn back-item"
												data-dismiss="modal">Annulla</button>
										</div>

									</div>
								</div>
							</div></td>
					</tr>
					<c:set var="cont" value="${cont + 1}"></c:set>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- MODALE -->
	<div class="modal" id="result-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="result-modal-title" class="modal-title"></h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<div id="result-modal-body" class="jumbotron">
						<p>
							<b>Tipo: </b><span id="result-modal-type"></span>
						</p>
						<p>
							<b>Oggetto: </b><span id="result-modal-object"></span>
						</p>
						<p>
							<b>Stato: </b><span id="result-modal-state"></span>
						</p>
					</div>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="close" data-dismiss="modal">Chiudi</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="manage-supermarket-modal">
		<div class="modal-dialog" style="max-width: 80%;">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="manage-supermarket-modal-title" class="modal-title"></h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<!-- maps -->
					<!-- Box esterno -->
					<div id="main-box" class="container">
						<!--Box ricerca + risultati-->
						<div id="query-box" class="container">
							<div class="input-group">
								<input id="super-market-name" type="text" class="form-control"
									placeholder="Cerca il supermercato">
								<div class="input-group-append">
									<a id="super-market-button" href="#"
										onclick="querySuperMarkets()"><span
										class="input-group-text"><img
											src="../images/search.png" width="25px" /></span></a>
								</div>
							</div>
							<div id="query-result" class="overflow-auto">
								<!-- I risultati della query su Nominatim vengono inseriti qui -->
							</div>
						</div>
						<!--Box mappa-->
						<div id="map" class="container">
							<div id="mapid"></div>
						</div>
					</div>
					<!-- form -->
					<form id="manage-supermarket-form" class="needs-validation"
						novalidate autocomplete="on">
						<div class="form-group">
							<label for="name">Nome:</label>
							<div id="input">
								<input type="text" class="form-control" id="name"
									placeholder="Nome" name="name" required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="city">Nazione:</label>
							<div id="input">
								<input type="text" class="form-control" id="country"
									placeholder="Nazione" name="country" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="city">Città:</label>
							<div id="input">
								<input type="text" class="form-control" id="town"
									placeholder="Città " name="city" required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="address">Indirizzo:</label>
							<div id="input">
								<input type="text" class="form-control" id="address"
									placeholder="Indirizzo" name="address" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="address">Latitudine:</label>
							<div id="input">
								<input type="number" class="form-control" id="lat"
									placeholder="Latitudine" name="latitude" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="address">Longitudine:</label>
							<div id="input">
								<input type="number" class="form-control" id="lon"
									placeholder="Longitudine" name="longitude" required
									autocomplete="off">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button id="manage-supermarket-button" type="button"
						class="btn add-item"></button>
					<button type="button" class="btn back-item"
						onclick="clearMapForm()">Reset</button>
					<button type="button" class="btn back-item" data-dismiss="modal">Chiudi</button>
				</div>
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
					src="../images/logo_unical.png" width="24" height="20"></img></a> <a
					href="https://github.com/Goffredson/Trispesa"><i
					class="fa fa-github"></i></a>
			</div>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="../vendor/jquery/jquery.min.js"></script>
	<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="../js/manageSuperMarket.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>

</body>
</html>