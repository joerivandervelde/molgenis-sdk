
#download some non-maven dependencies
wget http://molgenis26.target.rug.nl/downloads/omx/dazzle.jar
wget http://molgenis26.target.rug.nl/downloads/omx/biojava.jar
wget http://molgenis26.target.rug.nl/downloads/omx/bytecode.jar
wget http://molgenis26.target.rug.nl/downloads/omx/dasmi-model.jar
wget http://molgenis26.target.rug.nl/downloads/omx/servlet-api-3.0.jar

#install in local maven repo
mvn install:install-file -Dfile=dazzle.jar -DgroupId=dazzle -DartifactId=dazzle -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=biojava.jar -DgroupId=dazzle -DartifactId=biojava -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=bytecode.jar -DgroupId=dazzle -DartifactId=bytecode -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=dasmi-model.jar -DgroupId=dazzle -DartifactId=dasmi-model -Dversion=1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=servlet-api-3.0.jar -DgroupId=dazzle -DartifactId=servlet-api -Dversion=3.0 -Dpackaging=jar -DgeneratePom=true

#now, mvn install molgenis-sdk as usual, and then jetty:start

_____

example urls after starting MOLGENIS:
http://localhost:8080/das/
http://localhost:8080/das/demo/features?segment=chrV:500000,800000

