var chart;

function typeSelected() {
	if ($('#type-select').val() == 'null') {
		if ($('#all-supermarkets').css('display') != 'none') {
			$('#all-supermarkets').slideToggle('slow');
		}
		if ($('#all-categories').css('display') != 'none') {
			$('#all-categories').slideToggle('slow');
		}
		if ($('#all-products').css('display') != 'none') {
			$('#all-products').slideToggle('slow');
		}
		if ($('#data-type').css('display') != 'none') {
			$('#data-type').slideToggle('slow');
			$('#all-supermarkets-check').prop('checked', false);
			$('#supermarket-string').prop('disabled', false);
			$('#all-categories-check').prop('checked', false);
			$('#category-string').prop('disabled', false);
			$('#all-products-check').prop('checked', false);
			$('#product-string').prop('disabled', false);
		}
	} else if ($('#type-select').val() == 'supermarket') {
		$('#all-supermarkets').slideToggle('slow');
		if ($('#all-categories').css('display') != 'none') {
			$('#all-categories').slideToggle('slow');
		}
		if ($('#all-products').css('display') != 'none') {
			$('#all-products').slideToggle('slow');
		}
		if ($('#data-type').css('display') != 'none') {
			$('#data-type').slideToggle('slow');
			$('#all-supermarkets-check').prop('checked', false);
			$('#supermarket-string').prop('disabled', false);
			$('#all-categories-check').prop('checked', false);
			$('#category-string').prop('disabled', false);
			$('#all-products-check').prop('checked', false);
			$('#product-string').prop('disabled', false);
		}
	} else if ($('#type-select').val() == 'category') {
		$('#all-categories').slideToggle('slow');
		if ($('#all-supermarkets').css('display') != 'none') {
			$('#all-supermarkets').slideToggle('slow');
		}
		if ($('#all-products').css('display') != 'none') {
			$('#all-products').slideToggle('slow');
		}
		if ($('#data-type').css('display') != 'none') {
			$('#data-type').slideToggle('slow');
			$('#all-supermarkets-check').prop('checked', false);
			$('#supermarket-string').prop('disabled', false);
			$('#all-categories-check').prop('checked', false);
			$('#category-string').prop('disabled', false);
			$('#all-products-check').prop('checked', false);
			$('#product-string').prop('disabled', false);
		}
	} else if ($('#type-select').val() == 'product') {
		$('#all-products').slideToggle('slow');
		if ($('#all-supermarkets').css('display') != 'none') {
			$('#all-supermarkets').slideToggle('slow');
		}
		if ($('#all-categories').css('display') != 'none') {
			$('#all-categories').slideToggle('slow');
		}
		if ($('#data-type').css('display') != 'none') {
			$('#data-type').slideToggle('slow');
			$('#all-supermarkets-check').prop('checked', false);
			$('#supermarket-string').prop('disabled', false);
			$('#all-categories-check').prop('checked', false);
			$('#category-string').prop('disabled', false);
			$('#all-products-check').prop('checked', false);
			$('#product-string').prop('disabled', false);
		}
	}
}

function allSupermarketsSelected() {
	if ($('#all-supermarkets-check').is(':checked')) {
		$('#supermarket-string').prop('disabled', true);
		$('#supermarket-string').val('');
		$('#data-type').slideToggle('slow');
	} else {
		$('#supermarket-string').prop('disabled', false);
		$('#data-type').slideToggle('slow');
	}
}

function allCategoriesSelected() {
	if ($('#all-categories-check').is(':checked')) {
		$('#category-string').prop('disabled', true);
		$('#category-string').val('');
		$('#data-type').slideToggle('slow');
	} else {
		$('#category-string').prop('disabled', false);
		$('#data-type').slideToggle('slow');
	}
}

function allProductsSelected() {
	if ($('#all-products-check').is(':checked')) {
		$('#product-string').prop('disabled', true);
		$('#product-string').val('');
		$('#data-type').slideToggle('slow');
	} else {
		$('#product-string').prop('disabled', false);
		$('#data-type').slideToggle('slow');
	}
}

$('#supermarket-string').autocomplete({
	source : function(req, resp) {
		$.ajax({
			type : "GET",
			url : 'statistics/manageData?action=retrieveSupermarkets',
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			data : {
				term : req.term
			},
			success : function(data) {
				resp(data);
			}
		});
	},
	minLength : 2,
	select : function(e, ui) {
		$('#supermarket-string').val(ui.item.value);
		$('#supermarket-id').val(ui.item.id);
		$('#data-type').slideToggle('slow');
	}
});

$('#category-string').autocomplete({
	source : function(req, resp) {
		$.ajax({
			type : "GET",
			url : 'statistics/manageData?action=retrieveCategories',
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			data : {
				term : req.term
			},
			success : function(data) {
				resp(data);
			}
		});
	},
	minLength : 2,
	select : function(e, ui) {
		$('#category-string').val(ui.item.value);
		$('#category-id').val(ui.item.id);
		$('#data-type').slideToggle('slow');
	}
});

$('#product-string').autocomplete({
	source : function(req, resp) {
		$.ajax({
			type : "GET",
			url : 'statistics/manageData?action=retrieveProducts',
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			data : {
				term : req.term
			},
			success : function(data) {
				resp(data);
			}
		});
	},
	minLength : 2,
	select : function(e, ui) {
		$('#product-string').val(ui.item.value);
		$('#product-id').val(ui.item.id);
		$('#data-type').slideToggle('slow');
	}
});

$('#generate-graph').click(function(e) {
	e.preventDefault();
	generateGraph();
});

function generateGraph() {
	$.ajax({
		type : "POST",
		url : 'statistics/manageData?action=generateGraph',
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			type : $('#type-select').children('option:selected').val(),
			allSupermarkets : $('#all-supermarkets-check').is(':checked'),
			allCategories : $('#all-categories-check').is(':checked'),
			allProducts : $('#all-products-check').is(':checked'),
			supermarketId : $('#supermarket-id').val(),
			categoryId : $('#category-id').val(),
			productId : $('#product-id').val(),
			dataType : $('#data-type-select').children('option:selected').val()
		}),
		success : function(graphData) {
			console.log(graphData);
			chart = new CanvasJS.Chart("chart-container-1", {
				theme : "light1",
				animationEnabled : true,
				title : {
					text : $('#data-type-select').children('option:selected').html()
				},
				data : [ {
					type : "column",
					dataPoints : graphData
				} ]
			});
			chart.render();
		}
	});
}