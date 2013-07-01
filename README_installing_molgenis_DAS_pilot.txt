MOLGENIS-DAS database integration pilot


-- Installing --

1) download some non-maven dependencies
wget http://molgenis26.target.rug.nl/downloads/omx/dazzle.jar
wget http://molgenis26.target.rug.nl/downloads/omx/biojava.jar
wget http://molgenis26.target.rug.nl/downloads/omx/bytecode.jar
wget http://molgenis26.target.rug.nl/downloads/omx/dasmi-model.jar
wget http://molgenis26.target.rug.nl/downloads/omx/servlet-api-3.0.jar

2) install in local maven repo
mvn install:install-file -Dfile=dazzle.jar -DgroupId=dazzle -DartifactId=dazzle -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=biojava.jar -DgroupId=dazzle -DartifactId=biojava -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=bytecode.jar -DgroupId=dazzle -DartifactId=bytecode -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=dasmi-model.jar -DgroupId=dazzle -DartifactId=dasmi-model -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=servlet-api-3.0.jar -DgroupId=dazzle -DartifactId=servlet-api -Dversion=3.0 -Dpackaging=jar -DgeneratePom=true

3) run mvn install to generate the app

4) run /molgenis-sdk/src/main/java/org/molgenis/example/ImportGenes.java to populate the database with chromosomes and genes

5) start app using jetty:start


-- You can now: --

a) browse chromosome and their genes using the MOLGENIS GUI @ http://localhost:8080/

b) query via DAS using e.g. @ localhost:8080/das/wormgenes/features?segment=IV:1000000,3000000

c) customize how the DAS is served from the database by editing
/molgenis-sdk/src/main/java/services/XgapGenePlugin.java

NOTICE:
/molgenis-sdk/src/main/java/services/XgapGenePlugin2.java is a second attempt at a more powerful service


_____

example urls after starting MOLGENIS:
http://localhost:8080/das/
http://localhost:8080/das/dsn
http://localhost:8080/das/demo/features?segment=chrV:500000,800000
http://localhost:8080/das/wormgenes/features?segment=I:1000,2000000

http://localhost:8080/das/wormgenes2/features?segment=I:1000,2000000
http://localhost:8080/das/wormgenes2/entry_points

similarly
http://www.derkholm.net:8080/das/cel_61_220/entry_points
http://www.derkholm.net:8080/das/cel_61_220/sequence?segment=V



