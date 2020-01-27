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

</head>
<body>

	<!-- Navigation NON TOCCARE!!! -->
	<nav id="nav"
		class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">Trispesa</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link" href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Dieta</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Ordini</a></li>
					<li class="nav-item"><a class="nav-link"
						href="user?page=profile">Profilo</a></li>
					<li class="nav-item"><a class="nav-link" href="#"><img
							src="images/cart.png" width="30" /></a></li>
					<!-- Menu form login -->
					<div class="dropdown">
						<a class="btn btn-secondary dropdown-toggle login" href="#"
							role="button" id="buttonLogin" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Login</a>

						<div class="dropdown-menu">
							<form class="px-4 py-3">
								<div class="form-group">
									<label for="exampleDropdownFormEmail1">Indirizzo Email</label>
									<input type="email" class="form-control"
										id="exampleDropdownFormEmail1" placeholder="email@example.com">
								</div>
								<div class="form-group">
									<label for="exampleDropdownFormPassword1">Password</label> <input
										type="password" class="form-control"
										id="exampleDropdownFormPassword1" placeholder="Password">
								</div>
								<button type="submit" class="btn btn-primary">Autenticati</button>
							</form>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">Effettua registrazione</a> <a
								class="dropdown-item" href="#">Password dimenticata?</a>
						</div>
					</div>
					<!--Chiusura Menu form login -->
					<li class="nav-item"><a class="nav-link" href="administration">Parte
							admin (NON TOCCARE!)</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row">
			<div id="colonna" class="col-sm-8 col-md-5 col-lg-5">
				<div>
					<h3>Dati personali</h3>
				</div>
				<div>
					<h4>Username</h4>
					<p>${sessionScope.customer.username}</p>
				</div>
				<div>
					<h4>Password</h4>
					<p>${sessionScope.customer.password}</p>
				</div>
				<div>
					<h4>Nome</h4>
					<p>${sessionScope.customer.name}</p>
				</div>
				<div>
					<h4>Cognome</h4>
					<p>${sessionScope.customer.surname}</p>
				</div>
				<div>
					<h4>Email</h4>
					<p>${sessionScope.customer.email}</p>
				</div>
				<div>
					<h4>Data di nascita</h4>
					<p>${sessionScope.customer.birthDate}</p>
				</div>
			</div>

			<div id="colonna" class="col-sm-8 col-md-5 col-lg-6">
				<div class="row">
					<h4>Indirizzi di consegna</h4>
					<table class="table table-hover table-responsive">
						<tr>
							<th>Paese</th>
							<th>Citt�</th>
							<th>Indirizzo</th>
							<th colspan="2"><button id="add-deivery-address"
									onclick="prepareAddDeliveryAddress()" class="btn btn-success"
									role="button">+ Aggiungi indirizzo di consegna</button></th>
						</tr>
						<c:forEach items="${sessionScope.customer.deliveryAddresses}"
							var="deliveryAddress">
							<tr>
								<td>${deliveryAddress.country}</td>
								<td>${deliveryAddress.city}</td>
								<td>${deliveryAddress.address}</td>
								<td width="10%"><button
										id="modify-delivery-address-${deliveryAddress.id}"
										onclick="prepareModDeliveryAddress(${deliveryAddress.id})"
										class="btn btn-info" role="button">Modifica indirizzo
										di consegna</button></td>
								<td width="10%"><button
										id="delete-delivery-address-${deliveryAddress.id}"
										onclick="deleteDeliveryAddress(${deliveryAddress.id})"
										class="btn btn-danger" role="button">Elimina
										indirizzo di consegna</button></td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<div class="row">
					<h4>Metodi di pagamento</h4>
					<table class="table table-hover table-responsive">
						<tr>
							<th>Numero carta</th>
							<th>Proprietario</th>
							<th>Data scadenza</th>
							<th colspan="2"><button id="add-payment-method"
									onclick="prepareAddPaymentMethod()" class="btn btn-success"
									role="button">+ Aggiungi metodo di pagamento</button></th>
						</tr>
						<c:forEach items="${sessionScope.customer.paymentMethods}"
							var="paymentMethod">
							<tr>
								<td>${paymentMethod.cardNumber}</td>
								<td>${paymentMethod.owner}</td>
								<td>${paymentMethod.expirationDate}</td>
								<td width="10%"><button
										id="modify-payment-method-${paymentMethod.id}"
										onclick="prepareModPaymentMethod(${paymentMethod.id})"
										class="btn btn-info" role="button">Modifica metodo di
										pagamento</button></td>
								<td width="10%">
									<button id="delete-payment-method-${paymentMethod.id}"
										onclick="deletePaymentMethod(${paymentMethod.id})"
										class="btn btn-danger" role="button">Elimina metodo
										di pagamento</button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
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
							<span id="result-modal-object"></span>
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

	<div class="modal" id="manage-payment-method-modal">
		<div class="modal-dialog" style="max-width: 80%;">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="manage-payment-method-modal-title" class="modal-title"></h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<!-- form -->
					<form id="manage-payment-method-form" class="needs-validation"
						novalidate autocomplete="on">
						<div class="form-group">
							<label for="company">Circuito di pagamento:</label>
							<div id="input">
								<input type="text" class="form-control" id="company"
									placeholder="Visa, Mastercard, Paypal, ..." name="company"
									required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="cardNumber">Numero carta:</label>
							<div id="input">
								<input type="text" class="form-control" id="card-number"
									placeholder="XXXX-XXXX-XXXX-XXXX" name="cardNumber" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="owner">Titiolare della carta:</label>
							<div id="input">
								<input type="text" class="form-control" id="owner"
									placeholder="Titolare" name="owner" required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="expirationDate">Data di scadenza:</label>
							<div id="input">
								<input type="month" class="form-control" id="expiration-date"
									name="expirationDate" required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="securityCode">Codice di sicurezza:</label>
							<div id="input">
								<input type="number" class="form-control" id="security-code"
									placeholder="XXX" name="securityCode" required
									autocomplete="off">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button id="manage-payment-method-button" type="button"
						class="btn btn-success"></button>
					<button type="button" class="btn btn-secondary"
						onclick="clearForm()">Reset</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Chiudi</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="manage-delivery-address-modal">
		<div class="modal-dialog" style="max-width: 80%;">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="manage-delivery-address-modal-title" class="modal-title"></h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<!-- form -->
					<form id="manage-delivery-address-form" class="needs-validation"
						novalidate autocomplete="on">
						<div class="form-group">
							<label for="country">Nazione:</label>
							<div id="input">
								<input type="text" class="form-control" id="country"
									placeholder="Nazione" name="country" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="province">Provincia:</label>
							<div id="input">
								<input type="text" class="form-control" id="province"
									placeholder="Provincia" name="province" required
									autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="city">Citt�:</label>
							<div id="input">
								<input type="text" class="form-control" id="city"
									placeholder="Citt�" name="city" required autocomplete="off">
							</div>
						</div>
						<div class="form-group">
							<label for="zipcode">CAP:</label>
							<div id="input">
								<input type="text" class="form-control" id="zipcode"
									placeholder="CAP" name="zipcode" required autocomplete="off">
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
					</form>
				</div>
				<div class="modal-footer">
					<button id="manage-delivery-address-button" type="button"
						class="btn btn-success"></button>
					<button type="button" class="btn btn-secondary"
						onclick="clearForm()">Reset</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Chiudi</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Trispesa
				2020</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/manageUser.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>

</body>

</html>