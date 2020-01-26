<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Trispesa - Dieta</title>

<!-- Inclusioni (Bootstrap, JQuery) -->
<script src="../vendor/jquery/jquery.min.js"></script>
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Script -->
<script src="../js/order.js"></script>
<script src="../js/diet.js"></script>

<!-- CSS -->
<link href="../css/order-form.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body class="bg-light">

	<c:forEach items="${leafCategoriesList}" var="leafCategory">
		<script type="text/javascript">
			storeLeafCategory('${leafCategory.id}', '${leafCategory.name}');
		</script>
	</c:forEach>


	<button type="button" class="btn btn-info" onclick="addField()">Aggiungi
		alimento</button>
	<form id="dietForm"></form>


	<!-- Container principale -->
	<div class="container">
		<!-- Div logo -->
		<div class="py-5 text-center">
			<img class="d-block mx-auto mb-4"
				src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg"
				alt="" width="72" height="72">
			<h2>Conferma Ordine</h2>
			<p class="lead"></p>
		</div>

		<!-- Div carrello (manca thumbnail del prodotto) -->
		<div class="row">
			<div class="col-md-4 order-md-2 mb-4">
				<h4 class="d-flex justify-content-between align-items-center mb-3">
					<span class="text-muted">Il tuo carrello</span> <span
						class="badge badge-secondary badge-pill">${customer.cart.size()}</span>
				</h4>
				<ul class="list-group mb-3">
					<c:set var="totalCartPrice" scope="request" value="${0}" />
					<c:forEach items="${customer.cart}" var="product">
						<c:set var="totalCartPrice" scope="request"
							value="${totalCartPrice + product.key.price*product.value}" />
						<li
							class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">${product.key.name}</h6>
								<small class="text-muted">Quantita: ${product.value}</small>
							</div> <span class="text-muted">${product.key.price*product.value}&euro;</span>
						</li>
					</c:forEach>
					<li class="list-group-item d-flex justify-content-between"><span>Totale
					</span> <strong>${totalCartPrice}&euro;</strong></li>
				</ul>
			</div>
			<!-- Div dati account -->
			<div class="col-md-8 order-md-1">
				<h4 class="mb-3">Consegna</h4>
				<div class="row">
					<div class="col-md-6 mb-3">
						<label for="firstName">Nome</label> <input type="text"
							class="form-control" id="firstName" value="${customer.name}"
							readonly>
					</div>
					<div class="col-md-6 mb-3">
						<label for="lastName">Cognome</label> <input type="text"
							class="form-control" id="lastName" value="${customer.surname}"
							readonly>
					</div>
				</div>
				<div class="mb-3">
					<label for="email">Email <span class="text-muted"></span></label> <input
						type="email" class="form-control" id="email"
						value="${customer.email}" readonly>
				</div>

				<!-- Form indirizzo e metodo di pagamento -->
				<form method="POST" id="orderForm" action="manageOrder">
					<div class="mb-3">
						<div class="form-group">
							<label>Indirizzo di consegna</label> <select
								name="deliveryAddressId" id="selectAddress" class="form-control"
								form="orderForm">
								<option>Seleziona un indirizzo</option>
								<c:forEach items="${customer.deliveryAddresses}"
									var="deliveryAddress">
									<option value="${deliveryAddress.id}"
										data-address="${deliveryAddress.address}"
										data-address-city="${deliveryAddress.city}"
										data-address-province="${deliveryAddress.province}"
										data-address-zipcode="${deliveryAddress.zipcode}">${deliveryAddress}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<!-- Caselle da riempire con jQuery -->
					<div class="row">
						<div class="col-md-5 mb-3">
							<label for="province">Provincia</label> <input type="text"
								class="form-control" id="provinceField" value="" readonly>
						</div>
						<div class="col-md-4 mb-3">
							<label for="city">Comune</label> <input type="text"
								class="form-control" id="cityField" value="" readonly>
						</div>
						<div class="col-md-3 mb-3">
							<label for="zipcode">CAP</label> <input type="text"
								class="form-control" id="zipcodeField" value="" readonly>
						</div>
					</div>
					<hr class="mb-4">

					<h4 class="mb-3">Pagamento</h4>
					<div class="mb-3">
						<div class="form-group">
							<label>Metodo di pagamento</label> <select name="paymentId"
								id="selectPayment" class="form-control" form="orderForm">
								<option>Seleziona un metodo di pagamento</option>
								<c:forEach items="${customer.paymentMethods}"
									var="paymentMethod">
									<option value="${paymentMethod.id}">${paymentMethod}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="modal" id="paymentModal" style="display: none"
						tabindex="-1" role="dialog">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title">Conferma dati carta</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="col-md-3 mb-3" id="divSecurityCode">
										<label for="securityCode">CVV</label> <input type="text"
											class="form-control" id="securityCode" placeholder="CVV">
										<div class="invalid-feedback">CVV necessario</div>
									</div>
									<div class="col-md-3 mb-3" id="divExpirationDate">
										<label for="expirationDate">Data di scadenza</label> <input
											type="text" class="form-control" id="expirationDate"
											placeholder="MM-YY">
										<div class="invalid-feedback">Scadenza necessaria</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Chiudi</button>
									<button type="button" class="btn btn-primary"
										onclick="verifyPayment()">Conferma</button>
								</div>
							</div>
						</div>
					</div>

					<div id="paymentToast" class="toast notification-toast"
						role="alert" aria-live="assertive" aria-atomic="true"
						data-delay="5000">
						<div class="toast-header success-color-scheme">
							<strong class="mr-auto">Trispesa staff</strong> <small>ora</small>
							<button type="button" class="ml-2 mb-1 close"
								data-dismiss="toast" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="toast-body" id="toastMessage">Dati OK</div>
					</div>



					<!-- 					<div class="row"> -->
					<!-- 						<div class="col-md-6 mb-3"> -->
					<!-- 							<label for="cc-name">Nome intestatario</label> <input type="text" -->
					<!-- 								class="form-control" id="cc-name" placeholder="" required=""> -->
					<!-- 							<div class="invalid-feedback">Attenzione,inserire il nome -->
					<!-- 								intestatario dela carta</div> -->
					<!-- 						</div> -->
					<!-- 						<div class="col-md-6 mb-3"> -->
					<!-- 							<label for="cc-number">Numero carta di credito</label> <input -->
					<!-- 								type="text" class="form-control" id="cc-number" -->
					<!-- 								placeholder="0000-0000-0000-0000" required=""> -->
					<!-- 							<div class="invalid-feedback">Attenzione,inserire il numero -->
					<!-- 								della carta di credito</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="row"> -->
					<!-- 						<div class="col-md-3 mb-3"> -->
					<!-- 							<label for="cc-expiration">Scadenza</label> <input type="date" -->
					<!-- 								class="form-control" id="cc-expiration" placeholder="" -->
					<!-- 								required=""> -->
					<!-- 							<div class="invalid-feedback">Attenzione,inserire la data -->
					<!-- 								si scadenza</div> -->
					<!-- 						</div> -->
					<!-- 						<div class="col-md-3 mb-3"> -->
					<!-- 							<label for="cc-expiration">CVV</label> <input type="text" -->
					<!-- 								class="form-control" id="cc-cvv" placeholder="" required=""> -->
					<!-- 							<div class="invalid-feedback">Attenzione,inserire il codice -->
					<!-- 								di sicurezza</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<hr class="mb-4">
					<input type="submit" class="btn btn-primary btn-lg btn-block"
						value="Conferma Ordine">

				</form>
			</div>
		</div>

		<!-- Modal HTML -->
		<div id="orderConfirmed" class="modal fade">
			<div class="modal-dialog modal-confirm">
				<div class="modal-content">
					<div class="modal-header">
						<div class="icon-box">
							<i class="material-icons">&#xE876;</i>
						</div>
						<h4 class="modal-title">Ordine confermato</h4>
					</div>
					<div class="modal-body">
						<p class="text-center">La conferma dell'ordine � stata inviata
							via mail. Il riepilogo � disponibile nella sezione ordini</p>
					</div>
					<div class="modal-footer">
						<a href="../home" class="btn btn-success btn-block">Torna alla
							home</a>
					</div>
				</div>
			</div>
		</div>

		<footer class="py-5 bg-dark">
			<div class="container">
				<p class="m-0 text-center text-white">Copyright &copy; Trispesa
					2020</p>
			</div>
		</footer>
	</div>

	<!-- Check validit� form (da mettere) -->
</body>
</html>