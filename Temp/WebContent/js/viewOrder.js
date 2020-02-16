function viewOrder(id, state) {
	$('#details-' + id.toString())
			.html(
					'<span class="spinner-border spinner-border-sm"></span> Caricamento');
	$('.btn').prop('disabled', true);

	$('#products-table').empty();
	$('#products-table').append(
			'<tr><th></th><th>Prodotto</th><th>Supermercato</th></tr>');
	$('#progress-container').empty();

	$
			.ajax({
				type : "POST",
				url : "user/viewOrder",
				dataType : "json",
				contentType : "application/json; charset=UTF-8",
				data : JSON.stringify({
					order : id
				}),
				success : function(data) {
					for (i in data) {
						$('#result-modal-title').html('ORDINE #' + id);
						var tr = '<tr><td><img height="70px" src="'
								+ data[i].img + '"/></td><td>' + data[i].name
								+ '</td><td>' + data[i].supermarket
								+ '</td></tr>';
						$('#products-table').append(tr);
					}
				},
				complete : function() {
					$('#order-details-modal').modal('show');
					$('#details-' + id.toString()).html('Dettagli');
					$('.btn').prop('disabled', false);

					$('#progress-container')
							.append(
									'<div class="progress">'
											+ '<div id="progress-bar" class="progress-bar progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>'
											+ '<div id="received" class="milestone" data-toggle="tooltip" data-placement="top" title="L\'ordine e\' stato ricevuto."></div>'
											+ '<div id="processing" class="milestone" data-toggle="tooltip" data-placement="top" title="L\'ordine e\' stato inviato ai nostri supermercati affiliati."></div>'
											+ '<div id="delivering" class="milestone" data-toggle="tooltip" data-placement="top" title="L\'ordine e\' stato processato ed e\' in consegna."></div>'
											+ '<div id="delivered" class="milestone" data-toggle="tooltip" data-placement="top" title="L\'ordine e\' stato consegnato."></div>'
											+ '</div>');

					var progress;
					if (state == "ORDINATO") {
						progress='33.5%';
					} else if (state == "SPEDITO") {
						progress='67%';
					} else if (state == "CONSEGNATO") {
						progress='100%';
					}
					
					// 33.5%
					setTimeout(() => {
						$('#progress-bar').css('width', progress);
					}, 20);
					$('#progress-bar').attr('aria-valuenow', '100');
					$('.milestone').html(
							'<div class="milestone-inner-circle"></div>');
					var temp1 = $('#received').find('div');
					setTimeout(function() {
						$(temp1).toggleClass('active-circle');
					}, 20);
					if (state == "ORDINATO") {
						var temp2 = $('#processing').find('div');
						setTimeout(function() {
							$(temp2).toggleClass('active-circle');
						}, 2000);
					}
					if (state == "SPEDITO") {
						var temp2 = $('#processing').find('div');
						setTimeout(function() {
							$(temp2).toggleClass('active-circle');
						}, 1000);
						var temp3 = $('#delivering').find('div');
						setTimeout(function() {
							$(temp3).toggleClass('active-circle');
						}, 2000);
					}
					if (state == "CONSEGNATO") {
						var temp2 = $('#processing').find('div');
						setTimeout(function() {
							$(temp2).toggleClass('active-circle');
						}, 666.6);
						var temp3 = $('#delivering').find('div');
						setTimeout(function() {
							$(temp3).toggleClass('active-circle');
						}, 1333.2);
						var temp4 = $('#delivered').find('div');
						setTimeout(function() {
							$(temp4).toggleClass('active-circle');
						}, 2000);
					}
					$('[data-toggle="tooltip"]').tooltip();
				}
			});
}
