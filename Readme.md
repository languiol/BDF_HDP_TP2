 _du_fichierMapReduce TP2

Build projects with Maven
Download the git repository


There is three projects in this repository, to build one go in its directory

**Processus de compilation de l'algorithme Java avec maven sur le terminal:**

- Taper sur le terminal à l'adresse de l'algorithme:
mvn compile

mvn package

mvn install

**Dépose du fichier .jar créé sur le serveur:**

- Déposer le fichier .jar à la racine du répertoire (c/user/Olivier)

- Taper: scp <nom_du_fichier.jar> olivier@ns_du_server:/home/olivier

scp CountryName-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean

scp NumberOfOrigin-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean

scp SexProportion-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean

**Exercution du fichier .jar à partir du serveur:**

TP2.1 :     hadoop jar CountryName-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/CountryName/output

TP2.2 :     hadoop jar NumberOfOrigin-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/NumberOfOrigin/output

TP2.3 :     hadoop jar SexProportion-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/SexProportion/output

**Lecture des résultats sur le serveur:**

hdfs dfs -cat /user/lgrosjean/CountryName/output/part-r-00000

hdfs dfs -cat /user/lgrosjean/NumberOfOrigin/output/part-r-00000

hdfs dfs -cat /user/lgrosjean/SexProportion/output/part-r-00000
