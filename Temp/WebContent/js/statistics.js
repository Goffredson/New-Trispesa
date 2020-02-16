var nChart = 0;
var chart = [];

function typeSelected(i) {
	if ($('#type-select-' + i.toString()).val() == 'null') {
		if ($('#all-supermarkets-' + i.toString()).css('display') != 'none') {
			$('#all-supermarkets-' + i.toString()).slideToggle('slow');
		}
		if ($('#all-categories-' + i.toString()).css('display') != 'none') {
			$('#all-categories-' + i.toString()).slideToggle('slow');
		}
		if ($('#all-products-' + i.toString()).css('display') != 'none') {
			$('#all-products-' + i.toString()).slideToggle('slow');
		}
		if ($('#data-type-' + i.toString()).css('display') != 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
			$('#all-supermarkets-check-' + i.toString()).prop('checked', false);
			$('#supermarket-string-' + i.toString()).prop('disabled', false);
			$('#all-categories-check-' + i.toString()).prop('checked', false);
			$('#category-string-' + i.toString()).prop('disabled', false);
			$('#all-products-check-' + i.toString()).prop('checked', false);
			$('#product-string-' + i.toString()).prop('disabled', false);
			$('#supermarket-string-' + i.toString()).val('');
			$('#category-string-' + i.toString()).val('');
			$('#product-string-' + i.toString()).val('');
			if (chart[i] != null) {
				chart[i].destroy();
			}
		}
	} else if ($('#type-select-' + i.toString()).val() == 'supermarket') {
		$('#all-supermarkets-' + i.toString()).slideToggle('slow');
		if ($('#all-categories-' + i.toString()).css('display') != 'none') {
			$('#all-categories-' + i.toString()).slideToggle('slow');
		}
		if ($('#all-products-' + i.toString()).css('display') != 'none') {
			$('#all-products-' + i.toString()).slideToggle('slow');
		}
		if ($('#data-type-' + i.toString()).css('display') != 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
			$('#all-supermarkets-check-' + i.toString()).prop('checked', false);
			$('#supermarket-string-' + i.toString()).prop('disabled', false);
			$('#all-categories-check-' + i.toString()).prop('checked', false);
			$('#category-string-' + i.toString()).prop('disabled', false);
			$('#all-products-check-' + i.toString()).prop('checked', false);
			$('#product-string-' + i.toString()).prop('disabled', false);
			$('#supermarket-string-' + i.toString()).val('');
			$('#category-string-' + i.toString()).val('');
			$('#product-string-' + i.toString()).val('');
			if (chart[i] != null) {
				chart[i].destroy();
			}
		}
	} else if ($('#type-select-' + i.toString()).val() == 'category') {
		$('#all-categories-' + i.toString()).slideToggle('slow');
		if ($('#all-supermarkets-' + i.toString()).css('display') != 'none') {
			$('#all-supermarkets-' + i.toString()).slideToggle('slow');
		}
		if ($('#all-products-' + i.toString()).css('display') != 'none') {
			$('#all-products-' + i.toString()).slideToggle('slow');
		}
		if ($('#data-type-' + i.toString()).css('display') != 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
			$('#all-supermarkets-check-' + i.toString()).prop('checked', false);
			$('#supermarket-string-' + i.toString()).prop('disabled', false);
			$('#all-categories-check-' + i.toString()).prop('checked', false);
			$('#category-string-' + i.toString()).prop('disabled', false);
			$('#all-products-check-' + i.toString()).prop('checked', false);
			$('#product-string-' + i.toString()).prop('disabled', false);
			$('#supermarket-string-' + i.toString()).val('');
			$('#category-string-' + i.toString()).val('');
			$('#product-string-' + i.toString()).val('');
			if (chart[i] != null) {
				chart[i].destroy();
			}
		}
	} else if ($('#type-select-' + i.toString()).val() == 'product') {
		$('#all-products-' + i.toString()).slideToggle('slow');
		if ($('#all-supermarkets-' + i.toString()).css('display') != 'none') {
			$('#all-supermarkets-' + i.toString()).slideToggle('slow');
		}
		if ($('#all-categories-' + i.toString()).css('display') != 'none') {
			$('#all-categories-' + i.toString()).slideToggle('slow');
		}
		if ($('#data-type-' + i.toString()).css('display') != 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
			$('#all-supermarkets-check-' + i.toString()).prop('checked', false);
			$('#supermarket-string-' + i.toString()).prop('disabled', false);
			$('#all-categories-check-' + i.toString()).prop('checked', false);
			$('#category-string-' + i.toString()).prop('disabled', false);
			$('#all-products-check-' + i.toString()).prop('checked', false);
			$('#product-string-' + i.toString()).prop('disabled', false);
			$('#supermarket-string-' + i.toString()).val('');
			$('#category-string-' + i.toString()).val('');
			$('#product-string-' + i.toString()).val('');
			if (chart[i] != null) {
				chart[i].destroy();
			}
		}
	}
}

function allSupermarketsSelected(i) {
	if ($('#all-supermarkets-check-' + i.toString()).is(':checked')) {
		$('#supermarket-string-' + i.toString()).prop('disabled', true);
		$('#supermarket-string-' + i.toString()).val('');
		if ($('#data-type-' + i.toString()).css('display') == 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
		}
	} else {
		$('#supermarket-string-' + i.toString()).prop('disabled', false);
		$('#data-type-' + i.toString()).slideToggle('slow');
		if (chart[i] != null) {
			chart[i].destroy();
		}
	}
}

function allCategoriesSelected(i) {
	if ($('#all-categories-check-' + i.toString()).is(':checked')) {
		$('#category-string-' + i.toString()).prop('disabled', true);
		$('#category-string-' + i.toString()).val('');
		if ($('#data-type-' + i.toString()).css('display') == 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
		}
	} else {
		$('#category-string-' + i.toString()).prop('disabled', false);
		$('#data-type-' + i.toString()).slideToggle('slow');
		if (chart[i] != null) {
			chart[i].destroy();
		}
	}
}

function allProductsSelected(i) {
	if ($('#all-products-check-' + i.toString()).is(':checked')) {
		$('#product-string-' + i.toString()).prop('disabled', true);
		$('#product-string-' + i.toString()).val('');
		if ($('#data-type-' + i.toString()).css('display') == 'none') {
			$('#data-type-' + i.toString()).slideToggle('slow');
		}
	} else {
		$('#product-string-' + i.toString()).prop('disabled', false);
		$('#data-type-' + i.toString()).slideToggle('slow');
		if (chart[i] != null) {
			chart[i].destroy();
		}
	}
}

function generateGraph(i) {
	$.ajax({
		type : "POST",
		url : 'statistics/manageData?action=generateGraph',
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify({
			type : $('#type-select-' + i.toString())
					.children('option:selected').val(),
			allSupermarkets : $('#all-supermarkets-check-' + i.toString()).is(
					':checked'),
			allCategories : $('#all-categories-check-' + i.toString()).is(
					':checked'),
			allProducts : $('#all-products-check-' + i.toString()).is(
					':checked'),
			supermarketId : $('#supermarket-id-' + i.toString()).val(),
			categoryId : $('#category-id-' + i.toString()).val(),
			productId : $('#product-id-' + i.toString()).val(),
			dataType : $('#data-type-select-' + i.toString()).children(
					'option:selected').val()
		}),
		success : function(graphData) {
			console.log(graphData);
			chart[i] = new CanvasJS.Chart("chart-container-" + i.toString(), {
				theme : "light1",
				animationEnabled : true,
				title : {
					text : $('#data-type-select-' + i.toString()).children(
							'option:selected').html()
				},
				data : [ {
					type : "column",
					dataPoints : graphData
				} ]
			});
			chart[i].render();
		}
	});
}

$('#add-chart')
		.click(
				function(e) {
					e.preventDefault();
					$('#charts-container')
							.append(
									'<div id="chart-'
											+ nChart.toString()
											+ '" class="col-lg-6 col-md-6 mb-4">'
											+ '<form>'
											+ '<div class="form-group">'
											+ '<label for="type">Tipo:</label> <select id="type-select-'
											+ nChart.toString()
											+ '"'
											+ 'onchange="typeSelected('
											+ nChart.toString()
											+ ')" name="type" class="form-control">'
											+ '<option value="null">---</option>'
											+ '<option value="supermarket">Supermercati</option>'
											+ '<option value="category">Categorie</option>'
											+ '<option value="product">Prodotti</option>'
											+ '</select>'
											+ '</div>'
											+ '<div id="all-supermarkets-'
											+ nChart.toString()
											+ '" class="form-group"'
											+ 'style="display: none;">'
											+ '<input id="all-supermarkets-check-'
											+ nChart.toString()
											+ '" name="allSupermarkets"'
											+ 'type="checkbox" onclick="allSupermarketsSelected('
											+ nChart.toString()
											+ ')" /> Tutti i '
											+ 'supermercati <input id="supermarket-string-'
											+ nChart.toString()
											+ '"'
											+ 'placeholder="Digita e scegli..." class="form-control"'
											+ 'name="supermarket" type="text" /> <input id="supermarket-id-'
											+ nChart.toString()
											+ '"'
											+ 'type="hidden" name="supermarket" />'
											+ '</div>'
											+ '<div id="all-categories-'
											+ nChart.toString()
											+ '" class="form-group" style="display: none;">'
											+ '<input id="all-categories-check-'
											+ nChart.toString()
											+ '" name="allCategories"'
											+ 'type="checkbox" onclick="allCategoriesSelected('
											+ nChart.toString()
											+ ')" /> Tutte le '
											+ 'categorie <input id="category-string-'
											+ nChart.toString()
											+ '"'
											+ 'placeholder="Digita e scegli..." class="form-control"'
											+ 'name="category" type="text" /><input id="category-id-'
											+ nChart.toString()
											+ '"'
											+ 'type="hidden" name="category" />'
											+ '</div>'
											+ '<div id="all-products-'
											+ nChart.toString()
											+ '" class="form-group" style="display: none;">'
											+ '<input id="all-products-check-'
											+ nChart.toString()
											+ '" name="allProducts" type="checkbox"'
											+ 'onclick="allProductsSelected('
											+ nChart.toString()
											+ ')" /> Tutti i prodotti <input '
											+ 'id="product-string-'
											+ nChart.toString()
											+ '" placeholder="Digita e scegli..."'
											+ 'class="form-control" name="product" type="text" /><input '
											+ 'id="product-id-'
											+ nChart.toString()
											+ '" type="hidden" name="product" />'
											+ '</div>'
											+ '<div id="data-type-'
											+ nChart.toString()
											+ '" class="form-group" style="display: none;">'
											+ '<label for="type">Tipo dei dati:</label> <select '
											+ 'id="data-type-select-'
											+ nChart.toString()
											+ '" name="dataType" class="form-control">'
											+ '<option value="cash" selected>Guadagni in &euro;</option>'
											+ '<option value="units">Unita\' vendute</option>'
											+ '</select>'
											+ '<button style="margin: 10px;" id="generate-graph-'
											+ nChart.toString()
											+ '" type="button" onclick="generateGraph('
											+ nChart.toString()
											+ ')"'
											+ 'class="btn add-item">Genera grafico</button>'
											+ '</div>'
											+ '</form>'
											+ '<div id="chart-container-'
											+ nChart.toString()
											+ '" style="height: 370px; width: 100%;"></div>'
											+ '</div>');

					$('#supermarket-string-' + nChart.toString())
							.autocomplete(
									{
										source : function(req, resp) {
											$
													.ajax({
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
											var string = $(this).attr('id');
											var last = string
													.charAt(string.length - 1);
											$('#supermarket-string-' + last)
													.val(ui.item.value);
											$('#supermarket-id-' + last).val(
													ui.item.id);
											if ($('#data-type-' + last).css(
													'display') == 'none') {
												$('#data-type-' + last)
														.slideToggle('slow');
											}
										}
									});

					$('#category-string-' + nChart.toString())
							.autocomplete(
									{
										source : function(req, resp) {
											$
													.ajax({
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
											var string = $(this).attr('id');
											var last = string
													.charAt(string.length - 1);
											$('#category-string-' + last).val(
													ui.item.value);
											$('#category-id-' + last).val(
													ui.item.id);
											if ($('#data-type-' + last).css(
													'display') == 'none') {
												$('#data-type-' + last)
														.slideToggle('slow');
											}
										}
									});

					$('#product-string-' + nChart.toString())
							.autocomplete(
									{
										source : function(req, resp) {
											$
													.ajax({
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
											var string = $(this).attr('id');
											var last = string
													.charAt(string.length - 1);
											$('#product-string-' + last).val(
													ui.item.value);
											$('#product-id-' + last).val(
													ui.item.id);
											if ($('#data-type-' + last).css(
													'display') == 'none') {
												$('#data-type-' + last)
														.slideToggle('slow');
											}
										}
									});

					nChart++;

					chart.push(null);
				});