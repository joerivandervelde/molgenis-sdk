var b = new Browser({
   chr:          '3',
   viewStart:    48601506,
   viewEnd:      48632700,
   cookieKey:    'human',
   
   coordSystem: {
       speciesName: 'Human',
       taxon: 9606,
       auth: 'GRCh',
       version: '37'
   },
   
   chains: {
       hg18ToHg19: new Chainset('http://www.derkholm.net:8080/das/hg18ToHg19/', 'NCBI36', 'GRCh37',
                                {
                                   speciesName: 'Human',
                                   taxon: 9606,
                                   auth: 'NCBI',
                                   version: 36
                                })
   },
   
   sources:     [{name:                 'Genome',
                  uri:                  'http://www.derkholm.net:8080/das/hg19comp/',
                  desc:                 'Human reference genome build GRCh37',
                  tier_type:            'sequence',
                  provides_entrypoints: true},
                 {name:                 'Genes',     
                  desc:                 'Gene structures from Ensembl 59 (GENCODE 4)',
                  uri:                  'http://www.derkholm.net:8080/das/hsa_59_37d/',      
                  collapseSuperGroups:  true,
                  provides_karyotype:   true,
                  provides_search:      true},
                 {name:                 'GoNL',
                  desc:                 'Data from the GoNL project',
                  uri:                  'http://localhost:8080/das/gonl',
                  stylesheet_uri:       'http://localhost:8080/css/gonl-variants.xml'},
                 {name:                 'col7a1 mutations',
                  uri:                  'http://localhost:8080/das/col7a1',
                  desc:                 'Patient mutations in the COL7A1 gene'}],
   
   browserLinks: {
       Ensembl: 'http://www.ensembl.org/Homo_sapiens/Location/View?r=${chr}:${start}-${end}',
       UCSC: 'http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg19&position=chr${chr}:${start}-${end}',
       Sequence: 'http://www.derkholm.net:8080/das/hg19comp/sequence?segment=${chr}:${start},${end}'
   },
   
searchEndpoint: new DASSource('http://www.derkholm.net:8080/das/hsa_59_37d/'),
karyotypeEndpoint: new DASSource('http://www.derkholm.net:8080/das/hsa_59_37d/')
});

var url = 'http://localhost:8080/mutation?hit='
console.log(url);
$.ajax({
	url: url,
	type: "GET",
	dataType: "json",
	success: function(data) {
		console.log("Data returned : " + data);

		if (typeof data == 'object') {
			//informationTable(data);
		}
	},
	error: function(jqXHR, textStatus, errorThrown) {
		console.log("jqXHR : "+jqXHR + " text status : " + textStatus + " error : " + errorThrown);
	}
});
