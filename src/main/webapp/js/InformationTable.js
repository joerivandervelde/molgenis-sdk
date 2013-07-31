function informationTable(features){
	var results_div = document.getElementById('tableHolder');
	
	var table = '<table id="patientData" class="display" border="1">';
	table += '<thead><tr><th>ID</th><th>cDNA change</th><th>Protein change</th><th>Exon/Intron</th><th>Consequence</th><th>Phenotype</th><th>PubMed ID</th><th>Reference</th></tr></thead>';
	table += '<tbody>';
	
	for(m in features.mut){
		table += '<tr>';
			table += '<td rowspan="2">' + features.mut[m].PatientID + '</td>';
			table += '<td>' + features.mut[m].cDNAchange1 + '</td>';
			table += '<td>' + features.mut[m].ProteinChange1 + '</td>';
			table += '<td>' + features.mut[m].ExonIntron1 + '</td>';
			table += '<td>' + features.mut[m].Consequence1 + '</td>';
			table += '<td rowspan="2">' + features.mut[m].Pheno + '</td>';
			table += '<td rowspan="2">' + features.mut[m].PubMedID + '</td>';
			table += '<td rowspan="2"><a href="' + features.mut[m].Reference + '">' + features.mut[m].Reference + '</a></td>';
		table +='</tr>';
		table +='<tr>';
			table += '<td>' + features.mut[m].cDNAchange2 + '</td>';
			table += '<td>' + features.mut[m].ProteinChange2 + '</td>';
			table += '<td>' + features.mut[m].ExonIntron2 + '</td>';
			table += '<td>' + features.mut[m].Consequence2 + '</td>';
		table +='</tr>';
	}
	
	table += '</tbody>';
	table += '</table>';
	
	results_div.innerHTML = table;
}
