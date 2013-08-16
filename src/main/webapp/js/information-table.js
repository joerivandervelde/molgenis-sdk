/* -*- mode: javascript; c-basic-offset: 4; indent-tabs-mode: nil -*- */

// 
// MOLGENIS Dalliance Plug-in
// (c) Pieter Dopheide 2013
//
// information-table.js: table for displaying patient data
//

function informationTable(features){
	var results_div = document.getElementById('patientData');
	
	var table = '<thead><tr><th>ID</th><th>cDNA change</th><th>Protein change</th><th>Exon/Intron</th><th>Consequence</th><th>Phenotype</th><th>PubMed ID</th><th>Reference</th></tr></thead>';
	table += '<tbody>';
	
	for(m in features.mut){
		table += '<tr>';
			table += '<td id="pid" rowspan="2">' + features.mut[m].PatientID + '</td>';
			table += '<td>' + features.mut[m].cDNAchange1 + '</td>';
			table += '<td>' + features.mut[m].ProteinChange1 + '</td>';
			table += '<td>' + features.mut[m].ExonIntron1 + '</td>';
			table += '<td>' + features.mut[m].Consequence1 + '</td>';
			table += '<td rowspan="2">' + features.mut[m].Pheno + '</td>';
			
			if(features.mut[m].Reference != ""){
				table += '<td rowspan="2">' + features.mut[m].PubMedID + '</td>';
				table += '<td rowspan="2"><a href="' + features.mut[m].Reference + '">' + features.mut[m].Reference + '</a></td>';
			}else{
				table += '<td rowspan="2">Unpublished</td>';
				table += '<td rowspan="2">Unpublished</td>';
			}
			
		table +='</tr>';
		table +='<tr>';
			table += '<td>' + features.mut[m].cDNAchange2 + '</td>';
			table += '<td>' + features.mut[m].ProteinChange2 + '</td>';
			table += '<td>' + features.mut[m].ExonIntron2 + '</td>';
			table += '<td>' + features.mut[m].Consequence2 + '</td>';
		table +='</tr>';
	}
	
	table += '</tbody>';
	
	// jquery pseudo
	$('#patientData').click(function(e) {
		//alert('ai');
		var t = e.text();
		alert(t);
	});
	
	results_div.addEventListener('click', function delegate(e){
		var cell = e.target.innerText; // current cell
		//var row = e.target.parentNode.innerText; // current row
		var pattern = /P\d+/g; // check if patient id
		var pattern2 = /Patient P\d+/g; // check track name
		var match = pattern.test(cell);
		
		if(match){ // check if we have a patient id
			var source = [{name: 'Patient ' + cell,
	                       uri: 'http://localhost:8080/das/patient&pid=' + cell,
	                       desc: 'experiment',
	                       stylesheet_uri: 'http://localhost:8080/css/patient-track.xml'}];
        	
        	// loop through tiers
        	for(var i = b.tiers.length - 1; i >= 0; --i) {
        		// if tier is 'Patient P\d+'
        		if(pattern2.test(b.tiers[i].label.innerText)){
        			// remove tier
        			b.removeTier({index: i});
        		}
        	}
        	
        	// add new patient tier
	        b.addTier(source[0]); // b comes from biodalliance-genome-browser.js
	}
	});
	
	results_div.innerHTML = table;
}
