function passwordRecovery(username) {
	$.ajax({
		type : "GET",
		url : "effettuaLogin",
		data : {username: username},
		success : function(response) {
			var prevHtml = $("#passwordRecoveredMessage").html();
			$("#passwordRecoveredMessage").html(prevHtml + response);
			$('.modal-backdrop').hide();
			$('#recoveryModal').modal('hide');
			$("#passwordRecovered").toast("show");
		},
		error : function() {
			$('.modal-backdrop').hide();
			$('#recoveryModal').modal('hide');
			$("#passwordNotRecovered").toast("show");
		}
	});
}


function updateNavbarDOM(operation, animDelay) {
	if (operation == "login") {
		$("#loginDropdown").hide(animDelay);
		$("#logoutButton").show(animDelay);
		$("#dieta").show(animDelay);
		$("#ordini").show(animDelay);
		$("#profilo").show(animDelay);
		//$("#orderButton").prop("onclick", null).off("click");
		
		$("#orderAnchor").attr("href", "manageOrder");
	} else {
		$("#logoutButton").hide(animDelay);
		$("#dieta").hide(animDelay);
		$("#ordini").hide(animDelay);
		$("#profilo").hide(animDelay);
		$("#loginDropdown").show(animDelay);
		//$("#orderButton").prop("onclick", null).on("click");
		$("#orderAnchor").attr("href", "");
	}
}

function fillCartAfterLogin(cartHashMap) {
	var totalPrice = 0;
	for ( var product in cartHashMap) {
		totalPrice += cartHashMap[product][0].roundedDiscountedPrice * cartHashMap[product][1];
		$("#listaProdottiCarrello").append(
				'<tr id="product_"' + cartHashMap[product][0].id + '>' + '<th scope="row" id="productQuantity">' + cartHashMap[product][1] + '</th>'
						+ '<td id="productName">' + cartHashMap[product][0].name + '</td>' + '<td id="productPrice">'
						+ Number.parseFloat(cartHashMap[product][0].roundedDiscountedPrice * cartHashMap[product][1]).toFixed(2) + '&euro;</td>'
						+ '<td><a><i class="fas fa-times"></i></a></td>' + '<td><button type="button"' + 'onclick="updateCart('
						+ cartHashMap[product][0].id + ', \'' + cartHashMap[product][0].name + '\', ' + cartHashMap[product][0].discountedPrice
						+ ', \'' + cartHashMap[product][0].superMarket.name + '\', \'remove\');"' + 'class="btn btn-danger">Rimuovi</button></td>'
						+ '</tr>');
	}
	$("#totalCartPrice").html(Number.parseFloat(totalPrice).toFixed(2) + "&euro;");
}

function emptyCartAfterLogout() {
	$("#listaProdottiCarrello").empty();
	$("#totalCartPrice").html("0");
}

function ajaxLog(operation, animDelay) {
	$.ajax({
		type : "POST",
		url : "effettuaLogin",
		datatype : "JSON",
		data : JSON.stringify([ $("#inputUsername").val(), $("#inputPassword").val(), operation ]),
		success : function(response) {
			if (operation == "login") {
				startTimer(30 * 60, $("#timer"));
				sessionStorage.setItem("remainingTime", 30 * 60);
				if (response.redirect === true)
					window.location.href = "../administration";
				else {
					$("#toastMessage").html("Bentornato in trispesa, " + $("#inputUsername").val());
					fillCartAfterLogin(response);
					startTimer(30 * 60, $("#timer"));
				}
			} else {
				clearInterval(intervalId);
				sessionStorage.removeItem("remainingTime");
				$("#toastMessage").html("A presto " + $("#inputUsername").val());
				emptyCartAfterLogout(response);
				clearInterval(intervalId);
				$("#timer").empty();
				sessionStorage.removeItem("remainingTime");
			}
			$('#welcomeToast').toast('show');
			updateNavbarDOM(operation, animDelay);
			$('#credenzialiErrate').hide();
		},
		error : function(httpObj, textStatus) {
			if (httpObj.status == 401) {
				if ($("#credenzialiErrate").css('display') == 'none') {
					$("#credenzialiErrate").toggle(animDelay);
				} else {
					$("#credenzialiErrate").animate({
						opacity : 0
					}, 200, "linear", function() {
						$(this).animate({
							opacity : 1
						}, 200);
					});
				}
			}
		}
	});
}
