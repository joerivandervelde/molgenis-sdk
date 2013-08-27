/* -*- mode: javascript; c-basic-offset: 4; indent-tabs-mode: nil -*- */

// 
// MOLGENIS Dalliance Plug-in
// (c) Pieter Dopheide 2013
//
// information-table.js: table for displaying patient data
//

function informationTable(features){
	$('#tableHolder').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="patientData"><thead><tr><th>ID</th><th>cDNA change</th><th>Protein change</th><th>Consequence</th><th>Exon/Intron</th><th>cDNA change</th><th>Protein change</th><th>Consequence</th><th>Exon/Intron</th><th>Phenotype</th><th>PubMed ID</th><th>Reference</th></tr></thead></table>' );
	
	$('#patientData').dataTable( {
		"aaData": features.mut,
		"aoColumns": [
           { "mData": "PatientID" },
           { "mData": "cDNAchange1" },
           { "mData": "ProteinChange1" },
           { "mData": "Consequence1" },
           { "mData": "ExonIntron1" },
           { "mData": "cDNAchange2" },
           { "mData": "ProteinChange2" },
           { "mData": "Consequence2" },
           { "mData": "ExonIntron2" },
           { "mData": "Pheno" },
           { "mData": "PubMedID" },
           { "mData": "Reference" }
       ]
    });
    
    $('#patientData tbody td').die('click').live('click', function(){
		var cell = $(this).html(); // get value from clicked cell
		var pattern = /P\d+/g; // check if patient id
		var pattern2 = /Patient P\d+/g; // check track name
		var match = pattern.test(cell);
		
		if(match){ // check if we have a patient id
			var source = [{name: 'Patient ' + cell,
	                       uri: 'http://localhost:8080/das/patient&pid=' + cell,
	                       desc: 'experiment',
	                       stylesheet_uri: 'http://localhost:8080/css/patient-track.xml',
	                       id: 'LeCustomPatientTrack'}];
	        
	        // loop through tiers
//        	for(var i = b.tiers.length - 1; i >= 0; --i) {
//        		// if tier is 'Patient P\d+'
        		//if(pattern2.test(b.tiers[i].dasSource.name)){
//        		if(b.tiers[i].dasSource.id == 'LeCustomPatientTrack'){
        			// remove tier
//        			b.removeTier({index: i});
//        		}
//        	}
        	
        	// add new patient tier
	        b.addTier(source[0]); // b comes from biodalliance-genome-browser.js
	        
	    }
    });
}
