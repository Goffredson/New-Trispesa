var mymap = null;
$('#manage-supermarket-modal').on('show.bs.modal', function(event) {
	if (mymap == null) {
		setTimeout(function() {
			mymap = L.map('mapid').setView([41.458, 12.706], 6);
			L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(mymap);
		}, 200);
	}
});

// var mymap = L.map('mapid').setView([41.458, 12.706], 6);
// L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
// attribution: '&copy; <a
// href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>
// contributors'
// }).addTo(mymap);

var marker = null;

$('#super-market-name').keyup(event => {
    if(event.keyCode == 13)
        $('#super-market-button').click();
});

/*
 * $('#main-box-button').on('click', function() {
 * $('#main-box').slideToggle("slow"); });
 */

// $('#main-box').hide();

$('#lat').change(event => {
    if($('#lat').val() != '' && $('#lon').val() != '')
        addMarkerOnMap($('#lat').val(), $('#lon').val());
    else 
        mymap.setView([41.458, 12.706], 6);
});

$('#lon').change(event => {
    if($('#lat').val() != '' && $('#lon').val() != '')
        addMarkerOnMap($('#lat').val(), $('#lon').val());
    else 
        mymap.setView([41.458, 12.706], 6);
});

function querySuperMarkets () {
    var superMarketName = $('#super-market-name').val();
    var queryResults = $('#query-result');
    if(superMarketName == "") {
        queryResults.empty();
        mymap.setView([41.458, 12.706], 6);
    }
    else {
        superMarketName = encodeURI(superMarketName);
        var url = 'https://nominatim.openstreetmap.org/search?q=?' + superMarketName + '&format=json&polygon=1&addressdetails=1';
        queryResults.empty();
        fetch(url).then(response => {return response.json()}).then(data => {
            for(i in data) {
            	if(data[i].class == 'shop' || data[i].type == 'retail' || data[i].type == 'commercial'){
                	var town;
                	var country = data[i].address.country;
                	if (data[i].address.town != undefined)
                		town = data[i].address.town;
                	else if(data[i].address.city != undefined)
                		town = data[i].address.city;
                	else
                		town = data[i].address.village;
                	var parameterString = data[i].lat + ', ' + data[i].lon + ', \'' + removeSingleQuotes(data[i].address[data[i].type]) + '\', \'' + removeSingleQuotes(country) + '\', \''+ removeSingleQuotes(town) + '\', \'' + removeSingleQuotes(data[i].display_name) + '\'';
                    queryResults.append('<div id="query-result-element" class="p-2 bd-highlight" onClick="addMarkerOnSuperMarket(' + parameterString + ')">'+ data[i].display_name +'</div>');
            	}
            }
        }).catch(err => {console.log(err)});
    }
}

function addMarkerOnMap(lat, lon) {
    if(marker != null)
        marker.removeFrom(mymap);
    marker = L.marker([lat, lon]).addTo(mymap);
    mymap.setView([lat, lon], 18);
}

function addMarkerOnSuperMarket(lat, lon, name, country, town, address) {
    var parameterString = lat + ', ' + lon + ', \'' + name + '\', \'' + country + '\', \'' + town + '\', \'' + address + '\'';
    addMarkerOnMap(lat, lon);
    marker.bindPopup('<button type="button" class="btn btn-primary" onClick="fillMapForm(' + parameterString + ')">Seleziona il supermercato</button>')
          .openPopup();
}

function fillMapForm(lat, lon, name, country, town, address) {
    $('#name').val(name);
    $('#country').val(country);
    $('#town').val(town);
    $('#address').val(address);
    $('#lat').val(lat);
    $('#lon').val(lon);
    $('#manage-supermarket-form').valid();
}

function removeSingleQuotes(string) {
    if(string != undefined)
        return string.replace(/'/g, "");
    return 'Da specificare';
}

function clearMapForm() {
    $('#name').val('');
    $('#country').val('');
    $('#town').val('');
    $('#address').val('');
    $('#lat').val('');
    $('#lon').val('');
    $('#query-result').empty();
    if (marker != null)
    	marker.removeFrom(mymap);
    if (mymap != null)
    	mymap.setView([41.458, 12.706], 6);
}
	
// MANAGE SUPERMERCATI
function prepareAddSupermarket() {
	clearMapForm();
	$('#manage-supermarket-modal-title').html('Aggiungi supermercato');
	$('#manage-supermarket-button').html("Aggiungi supermercato");
	$('#manage-supermarket-button').click(function(e){addSupermarket()});
	$('#manage-supermarket-modal').modal('show');
}

function prepareModSupermarket(id) {
	$('#modify-supermarket-' + id.toString()).html(
			'<span class="spinner-border spinner-border-sm"></span> Caricamento');
	$('.btn').prop('disabled', true);
	clearMapForm();
	manageModSupermarket(id);
	$('#manage-supermarket-modal-title').html('Modifica supermercato');
	$('#manage-supermarket-button').html("Modifica supermercato");
	$('#manage-supermarket-button').click(function(e){modSupermarket(id)});
}
	
function addSupermarket() {
	if ($('#manage-supermarket-form').valid() === false) {
		$('#manage-supermarket-form').addClass("was-validated").removeClass("needs-validation");
		return;
	}
	$.ajax({
		type : "POST",
		url : "supermarket/manage?action=add",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			id : '0',
			name : $('#name').val(),
			country : $('#country').val(),
			city : $('#town').val(),
			address : $('#address').val(),
			latitude : $('#lat').val(),
			longitude : $('#lon').val(),
			affiliate : true,
		}),
		success : function(data) {
			$('#manage-supermarket-modal').modal('hide');
			if (data.result == true) {
				$('#result-modal-title').html('Operazione eseguita con successo');
				$('#result-modal-body').addClass('success-message');
			} else {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
			}
			$('#result-modal-type').html(data.type);
			$('#result-modal-object').html(data.object);
			$('#result-modal-state').html(data.state);
			$('#result-modal').modal('show');
		}
	});
}

$('#result-modal').on('hide.bs.modal', function(event) {
	location.reload();
});

$( document ).ready( function () {
	$('#manage-supermarket-form').validate({
		rules : {
			name : "required",
			country : "required",
			city : "required",
			address : "required",
			latitude : {
				required : true,
				number : true
			},
			longitude : {
				required : true,
				number : true
			}
		},
		messages : {
			name : "Inserisci nome",
			country : "Inserisci nazione",
			city : "Inserisci citta'",
			address : "Inserisci indirizzo",
			latitude : {
				required : "Inserisci latitudine",
				number : "La latitudine deve essere un numero"
			},
			longitude : {
				required : "Inserisci longitudine",
				number : "La longitudine deve essere un numero"
			}
		},
		errorElement: "em",
		errorPlacement: function ( error, element ) {
			// Add the `help-block` class to the error element
			error.addClass( "help-block" );
			error.addClass("invalid-feedback");

			if ( element.prop( "type" ) === "checkbox" ) {
				error.insertAfter( element.parent( "label" ) );
			} else {
				error.insertAfter( element );
			}
		},
		highlight: function ( element, errorClass, validClass ) {
			$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
		},
		unhighlight: function (element, errorClass, validClass) {
			$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
		}
	});
});

function deleteSupermarket(id, cont) {
	$.ajax({
		type : "POST",
		url : "supermarket/manage?action=del",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			supermarket : id
		}),
		success : function(data) {
			$('#delete-modal-' + cont.toString()).modal('hide');
			if (data.result == true) {
				$('#result-modal-title').html('Operazione eseguita con successo');
				$('#result-modal-body').addClass('success-message');
			} else {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
			}
			$('#result-modal-type').html(data.type);
			$('#result-modal-object').html(data.object);
			$('#result-modal-state').html(data.state);
			$('#result-modal').modal('show');
		}
	});
}

function affiliateSupermarket(id) {
	$.ajax({
		type : "POST",
		url : "supermarket/manage?action=aff",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			supermarket : id
		}),
		success : function(data) {
			if (data.result == true) {
				$('#result-modal-title').html('Operazione eseguita con successo');
				$('#result-modal-body').addClass('success-message');
			} else {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
			}
			$('#result-modal-type').html(data.type);
			$('#result-modal-object').html(data.object);
			$('#result-modal-state').html(data.state);
			$('#result-modal').modal('show');
		}
	});
}

function manageModSupermarket(id) {
	$.ajax({
		type : "POST",
		url : "supermarket/manageSuperMarketForm",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			supermarket : id
		}),
		success : function(data) {
			if (data.result.result === false) {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
				$('#result-modal-type').html(data.result.type);
				$('#result-modal-object').html(data.result.object);
				$('#result-modal-state').html(data.result.state);
				$('#result-modal').modal('show');
				success = false;
			} else {
				$('#name').val(data.supermarket.name);
				$('#country').val(data.supermarket.country);
				$('#town').val(data.supermarket.city);
				$('#address').val(data.supermarket.address);
				$('#lat').val(data.supermarket.latitude);
				$('#lon').val(data.supermarket.longitude);
				success = true;
			}
		},
		complete : function() {
			if (success) {
				$('#manage-supermarket-modal').modal('show');
				setTimeout(function() {
					addMarkerOnMap($('#lat').val(), $('#lon').val())
				}, 200);
			}
			$('#modify-supermarket-' + id.toString()).html('Modifica supermercato');
			$('.btn').prop('disabled', false);
		}
	});
}

function modSupermarket(supermarket) {
	if ($('#manage-supermarket-form').valid() === false) {
		$('#manage-supermarket-form').addClass("was-validated").removeClass("needs-validation");
		return;
	}
	$.ajax({
		type : "POST",
		url : "supermarket/manage?action=mod",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			id : supermarket,
			name : $('#name').val(),
			country : $('#country').val(),
			city : $('#town').val(),
			address : $('#address').val(),
			latitude : $('#lat').val(),
			longitude : $('#lon').val(),
			affiliate : false,
		}),
		success : function(data) {
			$('#manage-supermarket-modal').modal('hide');
			if (data.result == true) {
				$('#result-modal-title').html('Operazione eseguita con successo');
				$('#result-modal-body').addClass('success-message');
			} else {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
			}
			$('#result-modal-type').html(data.type);
			$('#result-modal-object').html(data.object);
			$('#result-modal-state').html(data.state);
			$('#result-modal').modal('show');
		}
	});
}

function querySuperMarkets() {
    var queryString = $('#query-string').val().toUpperCase();
    var tableBody = $('#super-market-table-body');
    var rows = tableBody.find('tr');

    rows.each( function() {
        var cols = $(this).find('td');
        var name = $(cols)[0];
        var country = $(cols)[1];
        var city = $(cols)[2];
        var address = $(cols)[3];
        if(name && country && city && address) {
            var textName = $(name).text().trim();
            var textCountry = $(country).text().trim();
            var textCity = $(city).text().trim();
            var textAddress = $(address).text().trim();
            if((textName.toUpperCase().indexOf(queryString) > -1) || (textCountry.toUpperCase().indexOf(queryString) > -1) || (textCity.toUpperCase().indexOf(queryString) > -1) || (textAddress.toUpperCase().indexOf(queryString) > -1))
                $(this).show();
            else
                $(this).hide();
        }
    });
}