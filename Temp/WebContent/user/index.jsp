<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Trispesa</title>
<!-- Inclusioni (bootstrap, JQuery, assets esterni)  -->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../vendor/owl.carousel.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
	crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Script -->
<script src="../js/cart.js"></script>
<script src="../js/login.js"></script>
<script src="../js/animations.js"></script>
<script src="../js/order.js"></script>
<!-- CSS -->
<link href="../css/owl.carousel.css" rel="stylesheet" />
<link href="../css/footer.css" rel="stylesheet" />
<link href="../css/owl.theme.default.css" rel="stylesheet" />
<link href="../css/main.css" rel="stylesheet">
</head>

<body class="bg-light">
	<!-- Navbar principale  -->
	<nav id="nav"
		class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<!-- Logo -->
			<ul style="list-style: none;">
				<li class="nav-item py-0 title-trispesa"><a
					class="navbar-brand" href="home"><h2>
							Tri<span class="span-title">Spesa</span>
						</h2></a></li>
			</ul>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<!-- UL di carrello, login, etc. -->
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto" id="ulNavBar">
					<li class="nav-item py-0">
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
									<input type="button" class="btn color-scheme"
										value="Autenticati" onclick="ajaxLog('login', 500)">

								</form>
								<div class="dropdown-item" id="credenzialiErrate"
									style="color: red; display: none;">Username o password
									errati.</div>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="" data-toggle="modal"
									data-target="#modalLogin">Effettua registrazione</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#recoveryModal" href="#">Password dimenticata?</a>
							</div>
						</div>
					</li>
					<!-- Pulsanti login-dependent -->
					<li class="nav-item py-0 login-dependent" id="ordini"><a
						class="nav-link" href="../user?page=orders"><button
								type="button" class="btn btn-primary order-button"
								data-toggle="modal">Ordini</button></a></li>
					<li class="nav-item py-0 login-dependent" id="profilo"><a
						class="nav-link" href="../user?page=profile"><button
								type="button" class="btn btn-primary profile-button"
								data-toggle="modal">Profilo</button></a></li>
					<li class="nav-item py-0"><input type="button"
						id="logoutButton" class="btn login-dependent logout-button"
						value="Logout" onclick="ajaxLog('logout', 500)"></li>
				</ul>
			</div>
		</div>
		<!-- Aggiorno la navbar se c'è un cliente in sessione -->
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
					<form action="signup" class="text-center border border-light p-5"
						method="post" name="registrationForm">
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
						<input required type="email" name="email"
							class="form-control mb-4" placeholder="E-mail"> <input
							required type="text" name="username" class="form-control mb-4"
							placeholder="Username"> <input
							pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required
							type="password" name="password" class="form-control"
							placeholder="Password"> 
						<small id="passwordHelp" class="form-text text-muted mb-4">
							Almeno un numero, una maiuscola e una minuscola, 8 caratteri</small> <input
							required type="text" placeholder="Data di nascita"
							name="birthDate" onfocus="(this.type='date')"
							onblur="(this.type='text')" class="form-control">
						<div class="modal-footer">
							<input type="submit"
								class="btn color-scheme my-4 btn-block waves-effect waves-light"
								value="Registrati">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Modale recupero password -->
	<div class="modal" id="recoveryModal" style="display: none"
		tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Password dimenticata</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<label for="usernameRecovery">Inserisci qui il tuo
							username</label> <input type="text" class="form-control"
							id="usernameRecovery" placeholder="Username">
					</div>
					<label><small>Ti verrà inviata per e-mail la nuova
							password.</small></label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Chiudi</button>
					<button type="button" class="btn btn-primary"
						onclick="passwordRecovery($('#usernameRecovery').val());">Conferma</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Chiusura modale registrazione e pwd -->

	<!-- Sezione toast -->
	<!-- Toast di notifica login -->
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
	<!-- Toast di aggiunta prodotto -->
	<div id="addToCartToast" class="toast notification-toast" role="alert"
		aria-live="assertive" aria-atomic="true" data-delay="5000">
		<div class="toast-header color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="toastMessage">Prodotto aggiunto al
			carrello.</div>
	</div>
	<!-- Toast di warning no login -->
	<div id="loginToast" class="toast notification-toast" role="alert"
		aria-live="assertive" aria-atomic="true" data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="loginToastMessage">Devi fare il
			login prima di poter completare l'ordine.</div>
	</div>
	<!-- Toast prodotto terminato -->
	<div id="unavailableProductToast" class="toast notification-toast"
		role="alert" aria-live="assertive" aria-atomic="true"
		data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="unavailableProductToastMessage"></div>
	</div>
	<!-- Toast carrello svuotato -->
	<div id="cartToast" class="toast notification-toast" role="alert"
		aria-live="assertive" aria-atomic="true" data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="cartToastMessage">Hai esaurito il
			tempo a disposizione, il tuo carrello è stato svuotato.</div>
	</div>
	<div id="noProductsInCart" class="toast notification-toast"
		role="alert" aria-live="assertive" aria-atomic="true"
		data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="noProductsInCartMessage">Non puoi
			effettuare un ordine senza prodotti nel carrello.</div>
	</div>
	<div id="noOrderData" class="toast notification-toast" role="alert"
		aria-live="assertive" aria-atomic="true" data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="noOrderDataMessage">Prima di
			effettuare un ordine, devi registrare almeno un metodo di pagamento
			ed un indirizzo di consegna.</div>
	</div>
	<div id="passwordRecovered" class="toast notification-toast"
		role="alert" aria-live="assertive" aria-atomic="true"
		data-delay="5000">
		<div class="toast-header color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="passwordRecoveredMessage">La tua
			nuova password è stata mandata all'indirizzo: </div>
	</div>
	<div id="passwordNotRecovered" class="toast notification-toast"
		role="alert" aria-live="assertive" aria-atomic="true"
		data-delay="5000">
		<div class="toast-header error-color-scheme">
			<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
			<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="toast-body" id="passwordNotRecoveredMessage">Questo
			username non esiste.</div>
	</div>
	<!-- Chiusura sezione toast -->


	<!-- Carousel di categorie -->
	<div class="py-2 text-center">
		<h2 class="title-secondary">CATEGORIE</h2>
	</div>
	<div class="container col-md-10 h-100 ">
		<div id="categoryCarousel"
			style="margin-top: 30px; margin-bottom: 30px;"
			class="owl-carousel owl-theme">
			<c:forEach items="${listaMacroCategorie}" var="categoria">
				<c:if test="${not fn:startsWith(categoria.name, 'Altro')}">
					<a
						class="w3-bar-item w3-mobile w3-button w3-hover-none w3-border-white w3-bottombar w3-hover-border-green"
						href="">${categoria.name}</a>
				</c:if>
			</c:forEach>
		</div>
		<!-- Form di ricerca -->
		<form id="searchProduct" action="" method="post">
			<div class="d-flex justify-content-center h-100">
				<div class="searchbar">
					<input class="search_input" id="nomeProdotto" name="nomeProdotto"
						type="text" placeholder="Cerca prodotto.."> <a
						class="search_icon"><i onclick='$("#searchProduct").submit();'
						class="fas fa-search"></i></a>
				</div>
			</div>
		</form>
		<!-- Chiusura form di ricerca -->
		<div class="py-5 text-center">
			<h2 class="title-secondary">OGGI IN OFFERTA</h2>
		</div>
		<!-- Carousel prodotti -->
		<div id="productCarousel" class="owl-carousel owl-theme">
			<c:forEach items="${prodottiScontati}" var="prodottoScontato">
				<div class="card">
					<img width="200" height="250" class="card-img-top"
						src="${prodottoScontato.imagePath}">
					<div class="card-body h-20">
						<h5>${prodottoScontato.name} ${prodottoScontato.brand}</h5>

						<div>
							<del style="color: red;">
								${prodottoScontato.roundedPrice}&euro; </del>
						</div>

						<b>${prodottoScontato.roundedDiscountedPrice}&euro;</b>
						<button style="float: right; color: #e9b96e;"
							class="btn fa fa-shopping-cart item-icon-cart"
							onclick="$('#addToCartToast').toast('show')"
							id="addToCartProductDiscounted">+</button>
					</div>
					<div class="card-footer">Venduto da:
						${prodottoScontato.superMarket.name}</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- Chiusura carousel di categorie -->

	<!-- Footer della pagina -->
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
				dipartimento di Matematica e Informatica, per l'esame di Web Computing.
			</p>
			<div class="footer-icons">
				<a href="https://www.mat.unical.it/demacs"><img
					src="../images/logo_unical.png" width="24" height="20"></img></a> <a
					href="https://github.com/Goffredson/New-Trispesa"><i
					class="fa fa-github"></i></a>
			</div>
		</div>
	</footer>
	<!-- Chiusura footer -->
</body>
</html>