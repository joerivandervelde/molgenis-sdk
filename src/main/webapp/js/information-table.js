/* -*- mode: javascript; c-basic-offset: 4; indent-tabs-mode: nil -*- */

// 
// MOLGENIS Dalliance Plug-in
// (c) Pieter Dopheide 2013
//
// information-table.js: table for displaying patient data
//

function informationTable(features){
	var results_div = document.getElementById('patientData');

	// create table
	var $table = $('<table>');
	// caption
	//$table.append('<caption>MyTable</caption>')
	// thead
	$table.append('<thead>').children('thead')
	.append('<tr />').children('tr').append('<th>ID</th><th>cDNA change</th><th>Protein change</th><th>Exon/Intron</th><th>Consequence</th><th>Phenotype</th><th>PubMed ID</th><th>Reference</th>');
	
	//tbody
	var $tbody = $table.append('<tbody />').children('tbody');
	
	for(m in features.mut){
		// add row
		var row = '<tr>';
			row += '<td id="pid" rowspan="2">' + features.mut[m].PatientID + '</td>';
			row += '<td>' + features.mut[m].cDNAchange1 + '</td>';
			row += '<td>' + features.mut[m].ProteinChange1 + '</td>';
			row += '<td>' + features.mut[m].ExonIntron1 + '</td>';
			row += '<td>' + features.mut[m].Consequence1 + '</td>';
			row += '<td rowspan="2">' + features.mut[m].Pheno + '</td>';
			
			if(features.mut[m].Reference != ""){
				row += '<td rowspan="2">' + features.mut[m].PubMedID + '</td>';
				row += '<td rowspan="2"><a href="' + features.mut[m].Reference + '">' + features.mut[m].Reference + '</a></td>';
			}else{
				row += '<td rowspan="2">Unpublished</td>';
				row += '<td rowspan="2">Unpublished</td>';
			}
			
		row +='</tr>';
		
		$tbody.append(row).find('tr').last().children('td:eq(0)').click(function(){
			var cell = $(this).html(); // get value from clicked cell
			var pattern2 = /Patient P\d+/g; // check track name
			var source = [{name: 'Patient ' + cell,
	                       uri: 'http://localhost:8080/das/patient&pid=' + cell,
	                       desc: 'experiment',
	                       stylesheet_uri: 'http://localhost:8080/css/patient-track.xml'}];
	        
	        // loop through tiers
        	for(var i = b.tiers.length - 1; i >= 0; --i) {
        		// if tier is 'Patient P\d+'
        		//alert(b.tiers[i].label);
        		if(pattern2.test(b.tiers[i].dasSource.name)){ // see if track label matches patient track
        			// remove tier
        			b.removeTier({index: i});
        		}
        	}
        	
        	// add new patient tier
	        b.addTier(source[0]); // b is divined in biodalliance-genome-browser.js
			
		});
		
		var subRow ='<tr>';
			subRow += '<td>' + features.mut[m].cDNAchange2 + '</td>';
			subRow += '<td>' + features.mut[m].ProteinChange2 + '</td>';
			subRow += '<td>' + features.mut[m].ExonIntron2 + '</td>';
			subRow += '<td>' + features.mut[m].Consequence2 + '</td>';
		subRow +='</tr>';
		
		$tbody.append(subRow);
	}
	
	// add table to dom
	$('#tableHolder').empty(); // first clear the div, else table gets appended to already visible table(s)
	$table.appendTo('#tableHolder');
}
