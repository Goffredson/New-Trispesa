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
<!-- Inclusioni (bootstrap, JQuery)  -->
<script src="vendor/jquery/jquery.min.js"></script>


<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="vendor/owl.carousel.js"></script>
<!-- Script -->
<script src="js/cart.js"></script>
<script src="js/login.js"></script>

<link href="css/owl.carousel.css" rel="stylesheet" />
<link href="css/owl.theme.default.css" rel="stylesheet" />


<!-- CSS -->

<script>
	$(document).ready(function() {

		$('.owl-carousel').owlCarousel({
			loop : true,
			margin : 10,
			nav : true,
			autoplay : true,

			responsive : {
				0 : {
					items : 1
				},
				600 : {
					items : 3
				},
				1000 : {
					items : 5
				}
			}
		})

	});
</script>

<link href="css/main.css" rel="stylesheet">

</head>

<body>
	<!-- Navbar principale  -->
	<nav id="nav"
		class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="home">Trispesa</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto" id="ulNavBar">
					<li>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modalCart">Carrello</button>
					</li>
					<li>
						<!-- Div di login -->
						<div class="dropdown" id="loginDropdown">
							<a class="btn btn-secondary dropdown-toggle login-button" href=""
								role="button" id="loginButton" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Login</a>
							<div class="dropdown-menu login-dropdown">
								<form class="px-4 py-3">
									<div class="form-group">
											<label for="inputUsername">Nome utente</label> <input
											type="text" class="form-control" id="inputUsername"
											placeholder="Inserisci nome utente">
									</div>
									<div class="form-group">
										<label for="inputPassword">Password</label> <input
											type="password" class="form-control" id="inputPassword"
											placeholder="Password">
									</div>
									<input type="button" class="btn btn-primary color-scheme"
										value="Autenticati" onclick="ajaxLog('login', 500, true)">

								</form>
								<div class="dropdown-item" id="credenzialiErrate"
									style="color: red; display: none;">Username o password
									errati.</div>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="" data-toggle="modal"
									data-target="#modalLogin">Effettua registrazione</a> <a
									class="dropdown-item" href="">Password dimenticata?</a>
							</div>
						</div> <!-- Animazione slide per il form --> <script
							type="text/javascript">
							$('#loginDropdown').on(
									'show.bs.dropdown',
									function() {
										$(this).find('.dropdown-menu').first()
												.stop(true, true).slideDown();
									});

							$('#loginDropdown').on(
									'hide.bs.dropdown',
									function() {
										$(this).find('.dropdown-menu').first()
												.stop(true, true).slideUp();
									});
						</script>
					</li>
					<li class="nav-item login-dependent" id="ordini"><a
						class="nav-link" href="#">Ordini</a></li>
					<li class="nav-item login-dependent" id="profilo"><a
						class="nav-link" href="user?page=profile">Profilo</a></li>
					<li class="nav-item login-dependent" id="dieta"><a href="user/manageDiet"
						class="nav-link" href="#">Dieta</a></li>
					<li><input type="button" id="logoutButton"
						class="btn btn-primary login-dependent" value="Logout"
						onclick="ajaxLog('logout', 500, true)"></li>


					<li class="nav-item"><a class="nav-link" href="administration">Parte
							admin</a></li>
				</ul>
			</div>
		</div>
		<!-- Aggiorno la navbar se c'� un cliente in sessione -->
		<c:if test="${customer != null}">
			<script type="text/javascript">
				updateNavbarDOM('login', 0);
			</script>
		</c:if>
	</nav>
	<!-- Chiusura navbar principale -->

	<!-- Modale form registrazione -->
	<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Registrazione cliente</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true"></span>
					</button>
				</div>
				<div class="modal-body">
					<!-- Form di registrazione -->
					<form action="user/signup"
						class="text-center border border-light p-5" method="post"
						name="registrationForm">
						<div class="form-row mb-4">
							<div class="col">
								<input type="text" name="firstName" class="form-control"
									placeholder="Nome">
							</div>
							<div class="col">
								<input type="text" name="lastName" class="form-control"
									placeholder="Cognome">
							</div>
						</div>
						<input type="email" name="email" class="form-control mb-4"
							placeholder="E-mail"> <input type="text" name="username"
							class="form-control mb-4" placeholder="Username"> <input
							type="password" name="password" class="form-control"
							placeholder="Password"> <input type="password"
							name="passwordConfirmation" class="form-control"
							placeholder="Conferma password" aria-describedby="passwordHelp">
						<small id="passwordHelp" class="form-text text-muted mb-4">
							Almeno 8 caratteri e un numero</small> <input type="text"
							placeholder="Data di nascita" name="birthDate"
							onfocus="(this.type='date')" onblur="(this.type='text')"
							class="form-control">
						<div class="modal-footer">
							<input type="submit"
								class="btn color-scheme btn-info my-4 btn-block waves-effect waves-light"
								value="Registrati">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Chiusura modale registrazione -->

	<!-- Toast di notifica -->
	<div id="welcomeToast" class="toast notification-toast" role="alert"
		aria-live="assertive" aria-atomic="true" data-delay="5000">
		<div class="toast-header color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="toastMessage"></div>
	</div>
	<!-- Chiusura toast di notifica -->

	<!-- Navbar categorie -->
	<nav id="navCategories" class="navbar navbar-expand-lg">
		<div class="container">
			<div class="col-lg-12">
				<div class="scrollmenu color-scheme rounded">
					<c:forEach items="${listaMacroCategorie}" var="categoria">
						<a class="color-scheme"
							href="user/showProducts?categoria=${categoria.id}"
							class="list-group-item">${categoria.name}</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</nav>

	<!-- Corpo della pagina: carousel e ricerca -->
	<!-- 	<div class="container"> -->
	<!-- 		<div class="row"> -->
	<!-- Barra di ricerca -->
	<!-- 			<form id="searchProduct" method="post" action="user/showProducts"> -->
	<!-- 				<div class="input-group"> -->
	<!-- 					<input id="nomeProdotto" name="nomeProdotto" type="text" -->
	<!-- 						class="form-control" placeholder="Prodotto"> -->
	<!-- 					<script type="text/javascript"> -->
	<!-- // 						$("#searchProduct").submit(function(e) { -->

	<!-- // 							var nomeProdotto = $("#nomeProdotto").val(); -->
	<!-- // 							if (nomeProdotto == "") { -->
	<!-- // 								window.alert("Inserisci un prodotto"); -->
	<!-- // 								e.preventDefault(); -->
	<!-- // 							} -->
	<!-- // 						}); -->
	<!-- 					</script> -->
	<!-- 					<input class="btn btn-success" value="Cerca" type="submit" /> -->
	<!-- 					<div class="input-group-append"></div> -->
	<!-- 				</div> -->
	<!-- 			</form> -->
	<!-- 		</div> -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="input-group" id="boot-search-box">
					<input type="text" class="form-control"
						placeholder="Type a search term like: mobile phone" />
					<div class="input-group-btn">
						<div class="btn-group" role="group">
							<div class="dropdown dropdown-lg">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown" aria-expanded="false">
									<span class="caret"></span>
								</button>
								<div class="dropdown-menu dropdown-menu-right" role="menu">

									<div class="form-check">
										<input type="checkbox" class="form-check-input"
											id="exampleCheck1"> <label class="form-check-label"
											for="exampleCheck1">Di marca</label>
									</div>

								</div>


							</div>
						</div>
						<button type="button" class="btn btn-success ">
							<small> Cerca</small>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modalCart" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<!--Header-->
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">Il tuo carrello</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true"></span>
					</button>
				</div>
				<!--Body-->
				<div class="modal-body">
					<div class="count">
						<h3>
							<small>Tempo rimanente</small>
						</h3>
						<div id="timer"></div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th>N.</th>
								<th>Nome prodotto</th>
								<th>Prezzo</th>
								<th></th>

							</tr>
						</thead>
						<tbody id="listaProdottiCarrello">
							<c:set var="totalCartPrice" scope="request" value="${0}" />
							<c:forEach items="${customer.cart}" var="product">
								<c:set var="totalCartPrice" scope="request"
									value="${totalCartPrice + product.key.price*product.value}" />

								<tr id="product_${product.key.id}">
									<th scope="row" id="productQuantity">${product.value}</th>
									<td id="productName">${product.key.name}</td>
									<td id="productPrice">${product.key.price}</td>
									<td><a><i class="fas fa-times"></i></a></td>
									<td><button type="button"
											onclick="updateCart(${product.key.id}, '${product.key.name}', ${product.key.price}, '${product.key.superMarket.name}', 'remove');"
											class="btn btn-danger">Rimuovi</button></td>
								</tr>

							</c:forEach>
							<c:forEach items="${anonymousCart}" var="product">
								<c:set var="totalCartPrice" scope="request"
									value="${totalCartPrice + product.key.price*product.value}" />

								<tr id="product_${product.key.id}">
									<th scope="row" id="productQuantity">${product.value}</th>
									<td id="productName">${product.key.name}</td>
									<td id="productPrice">${product.key.price}</td>
									<td><a><i class="fas fa-times"></i></a></td>
									<td><button type="button"
											onclick="updateCart(${product.key.id}, '${product.key.name}', ${product.key.price}, '${product.key.superMarket.name}', 'remove');"
											class="btn btn-danger">Rimuovi</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
					<h2 id="totalCartPrice" class="hidden-xs text-center">${totalCartPrice}</h2>

				</div>
				<!--Footer-->
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-primary"
						data-dismiss="modal">Chiudi</button>
					<c:if test="${customer != null}">
						<a id="orderButton" href="manageOrder"><button
								class="btn btn-primary">Conferma ordine</button></a>
					</c:if>
					<c:if test="${customer == null}">
						<a id="orderAnchor" href=""><button id="orderButton"
								onclick="$('#modalCart').modal('hide'); $('.modal-backdrop').hide(); $('#loginToast').toast('show');"
								class="btn btn-primary">Conferma ordine</button></a>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<!-- 	<div class="carousel slide my-4  mx-auto" data-ride="carousel"> -->
	<!-- 		<ol class="carousel-indicators"> -->
	<!-- 			<li data-target="#carouselExampleIndicators" data-slide-to="0" -->
	<!-- 				class="active"></li> -->
	<!-- 			<li data-target="#carouselExampleIndicators" data-slide-to="1"></li> -->
	<!-- 			<li data-target="#carouselExampleIndicators" data-slide-to="2"></li> -->
	<!-- 		</ol> -->
	<!-- 		<div class="carousel-inner" role="listbox"> -->
	<!-- 			<div class="carousel-item active"> -->
	<!-- 				<img class="d-block img-fluid" src="http://placehold.it/900x350" -->
	<!-- 					alt="First slide"> -->
	<!-- 			</div> -->
	<!-- 			<div class="carousel-item">ul class="dropdown-submenu" role="menu"> -->
	<!-- 				<img class="d-block img-fluid" src="http://placehold.it/900x350" -->
	<!-- 					alt="Second slide"> -->
	<!-- 			</div> -->
	<!-- 			<div class="carousel-item"> -->
	<!-- 				<img class="d-block img-fluid" src="http://placehold.it/900x350" -->
	<!-- 					alt="Third slide"> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 		<a class="carousel-control-prev" href="#carouselExampleIndicators" -->
	<!-- 			role="button" data-slide="prev"> <span -->
	<!-- 			class="carousel-control-prev-icon" aria-hidden="true"></span> <span -->
	<!-- 			class="sr-only">Previous</span> -->
	<!-- 		</a> <a class="carousel-control-next" href="#carouselExampleIndicators" -->
	<!-- 			role="button" data-slide="next"> <span -->
	<!-- 			class="carousel-control-next-icon" aria-hidden="true"></span> <span -->
	<!-- 			class="sr-only">Next</span> -->
	<!-- 		</a> -->
	<!-- 	</div> -->

	<div class="owl-carousel owl-theme">
		<c:forEach items="${prodottiScontati}" var="prodottoScontato">
			<div class="card" style="width: 18rem;">
				<img width="200" height="200" style="object-fit: cover;"
					class="card-img-top" src="${prodottoScontato.imagePath}"
					alt="Card image cap">
				<div class="card-body">
					<h4 class="card-title">${prodottoScontato.name}</h4>
					<h5>${prodottoScontato.superMarket.name}</h5>
					<p>
						<small>${prodottoScontato.price}</small>
					</p>
					<p>
						<small style="color: red;">${prodottoScontato.discount}</small>
					</p>
					<a href="#" class="btn btn-primary">Aggiungi al carrello</a>
				</div>
			</div>
		</c:forEach>

	</div>

	<!-- Footer (da mettere: link a github e a sito unical) -->
	<footer class="py-3 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Trispesa
				2020</p>
		</div>
	</footer>
</body>
</html>