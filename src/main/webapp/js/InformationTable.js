function informationTable(features){
	var results_div = document.getElementById('tableHolder');
	
	var table = '<table class="results" border="1">';
	table += '<thead><tr><th>Id</th><th>Type</th><th>Start</th><th>End</th><th>Seq</th></tr></thead>';
	table += '<tbody>';
	
	for(m in features.mut){
		table += '<tr>';
		table += '<td>' + features.mut[m].name + '</td>';
		table += '<td>' + features.mut[m].description + '</td>';
		table += '<td>' + features.mut[m].bpStart + '</td>';
		table += '<td>' + features.mut[m].bpEnd + '</td>';
		table += '<td>' + features.mut[m].label + '</td>';
		table +='</tr>';
	}
	
	table += '</tbody>';
	table += '</table>';
	
	results_div.innerHTML = table;
}
