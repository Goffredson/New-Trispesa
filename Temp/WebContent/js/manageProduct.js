var success;

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#image').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	} else {
		$('#image')
				.attr('src',
						"https://drive.google.com/uc?export=view&id=1DbMKHR-mObaG56QAVDqGHoO4XoXStC2M");
	}
}
$("#image-chooser").change(function() {
	readURL(this);
});

$(document).ready(function() {
	$('#manage-product-form').validate({
		rules : {
			barcode : {
				required : true,
				digits : true
			},
			name : "required",
			brand : "required",
			weight : {
				required : true,
				number : true
			},
			price : {
				required : true,
				number : true
			},
			quantity : {
				required : true,
				digits : true
			},
			discount : {
				required : true,
				number : true
			},
		},
		messages : {
			barcode : {
				required : "Inserisci codice a barre",
				digits : "Il codice a barre deve contenere solo numeri"
			},
			name : "Inserisci il nome",
			brand : "Inserisci la marca",
			weight : {
				required : "Inserisci il peso",
				number : "Il peso deve contenere solo numeri"
			},
			price : {
				required : "Inserisci il peso",
				number : "Il prezzo deve contenere solo numeri"
			},
			quantity : {
				required : "Inserisci il peso",
				number : "La quantità deve contenere solo numeri"
			},
			discount : {
				required : "Inserisci il peso",
				number : "Lo sconto deve contenere solo numeri"
			},
		},
		errorElement : "em",
		errorPlacement : function(error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");
			error.addClass("invalid-feedback");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element);
			}
		},
		highlight : function(element, errorClass, validClass) {
			$(element).addClass("is-invalid").removeClass("is-valid");
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).addClass("is-valid").removeClass("is-invalid");
		}
	});
});

function clearForm() {
	$('#barcode').val('');
	$('#name').val('');
	$('#brand').val('');
	$('#weight').val('');
	$('#price').val('');
	$('#quantity').val('');
	$('#discount').val('');
	$('#superMarket').empty();
	$('#category').empty();
	$('select').prop('disabled', false);
	$('input').prop('readonly', false);
	$('#image')
			.attr('src',
					'https://drive.google.com/uc?export=view&id=1DbMKHR-mObaG56QAVDqGHoO4XoXStC2M');
}

$('#result-modal').on('hide.bs.modal', function(event) {
	location.reload();
});

// TODO Autocomplete form

function prepareAddProduct() {
	$('#add-product')
			.html(
					'<span class="spinner-border spinner-border-sm"></span> Caricamento');
	$('.btn').prop('disabled', true);
	clearForm();
	manageAddProduct();
	$('#manage-product-modal-title').html('Aggiungi prodotto');
	$('#manage-product-button').html("Aggiungi prodotto");
	$('#manage-product-button').click(function(e) {
		addProduct()
	});
}

function prepareModProduct(id) {
	$('#modify-product-' + id.toString())
			.html(
					'<span class="spinner-border spinner-border-sm"></span> Caricamento');
	$('.btn').prop('disabled', true);
	clearForm();
	manageModProduct(id);
	$('#manage-product-modal-title').html('Modifica prodotto');
	$('#manage-product-button').html("Modifica prodotto");
	$('#manage-product-button').click(function(e) {
		modProduct()
	});
	$('#reset-button').prop('hidden', true);
}

function manageAddProduct() {
	$.ajax({
		type : "POST",
		url : "product/manageProductForm?action=add",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
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
				for (i in data.supermarkets)
					$('#superMarket').append(
							'<option value="' + data.supermarkets[i].id + '">'
									+ data.supermarkets[i].address
									+ '</option>');
				for (i in data.categories)
					$('#category').append(
							'<option value="' + data.categories[i].id + '">'
									+ data.categories[i].name + '</option>');
				success = true;
			}
		},
		complete : function() {
			if (success) {
				$('#manage-product-modal').modal('show');
			}
			$('#add-product').html('+ Aggiungi prodotto');
			$('.btn').prop('disabled', false);
		}
	});
}

function manageModProduct(id) {
	$.ajax({
		type : "POST",
		url : "product/manageProductForm?action=mod",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			product : id
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
				$('#barcode').val(data.product.barcode);
				$('#barcode').prop('readonly', true);
				$('#name').val(data.product.name);
				$('#name').prop('readonly', true);
				$('#brand').val(data.product.brand);
				$('#brand').prop('readonly', true);
				$('#weight').val(data.product.weight);
				$('#weight').prop('readonly', true);
				$('#price').val(data.product.price);
				$('#quantity').val(data.product.quantity);
				$('#discount').val(data.product.discount);
				$('#superMarket').append(
						'<option value="' + data.product.superMarket.id + '">'
								+ data.product.superMarket.address
								+ '</option>');
				$('#superMarket').prop('disabled', true);
				$('#supermarket-select').append(
						'<input type="hidden" name="superMarket" value="'
								+ data.product.superMarket.id + '">');
				$('#category').append(
						'<option value="' + data.product.category.id + '">'
								+ data.product.category.name + '</option>');
				$('#category').prop('disabled', true);
				$('#category-select').append(
						'<input type="hidden" name="category" value="'
								+ data.product.category.id + '">');
				if (data.product.offBrand) {
					$('#offbrand-false').prop('checked', false);
					$('#offbrand-true').prop('checked', true);
				} else {
					$('#offbrand-false').prop('checked', true);
					$('#offbrand-true').prop('checked', false);
				}
				$('#image').attr('src', data.product.imagePath);
				$('#product-id').html(
						'<input type="hidden" name="id" value="'
								+ data.product.id + '">');
				success = true;
			}
		},
		complete : function() {
			if (success) {
				$('#manage-product-modal').modal('show');
			}
			$('#modify-product-' + id.toString()).html('Modifica prodotto');
			$('.btn').prop('disabled', false);
		}
	});
}

function addProduct() {
	if ($('#manage-product-form').valid() === false) {
		$('#manage-product-form').addClass("was-validated").removeClass(
				"needs-validation");
		return;
	}
	var formData = new FormData($('#manage-product-form')[0]);
	$.ajax({
		type : "POST",
		url : "product/manage?action=add",
		contentType : false,
		cache : false,
		processData : false,
		encType : 'multipart/form-data',
		data : formData,
		success : function(data) {
			if (data.result === false) {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = false;
			} else {
				$('#result-modal-title').html('Operazione completata');
				$('#result-modal-body').addClass('success-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = true;
			}
		},
		complete : function() {
			$('#manage-product-modal').modal('hide');
		}
	});
}

function modProduct() {
	if ($('#manage-product-form').valid() === false) {
		$('#manage-product-form').addClass("was-validated").removeClass(
				"needs-validation");
		return;
	}
	var formData = new FormData($('#manage-product-form')[0]);
	$.ajax({
		type : "POST",
		url : "product/manage?action=mod",
		contentType : false,
		cache : false,
		processData : false,
		encType : 'multipart/form-data',
		data : formData,
		success : function(data) {
			if (data.result === false) {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = false;
			} else {
				$('#result-modal-title').html('Operazione completata');
				$('#result-modal-body').addClass('success-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = true;
			}
		},
		complete : function() {
			$('#manage-product-modal').modal('hide');
		}
	});
}

function deleteProduct(id) {
	$('#delete-product-' + id.toString())
			.html(
					'<span class="spinner-border spinner-border-sm"></span> Caricamento');
	$('.btn').prop('disabled', true);

	$.ajax({
		type : "POST",
		url : "product/manage?action=del",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			product : id
		}),
		success : function(data) {
			if (data.result === false) {
				$('#result-modal-title').html('Operazione annullata');
				$('#result-modal-body').addClass('error-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = false;
			} else {
				$('#result-modal-title').html('Operazione completata');
				$('#result-modal-body').addClass('success-message');
				$('#result-modal-type').html(data.type);
				$('#result-modal-object').html(data.object);
				$('#result-modal-state').html(data.state);
				$('#result-modal').modal('show');
				success = true;
			}
		},
		complete : function() {
			$('#delete-product-' + id.toString()).html('Elimina prodotto');
			$('.btn').prop('disabled', false);
		}
	});
}