MapReduce TP2

Build projects with Maven

Download the git repository

git clone "https://github.com/LeoGrosjean/Hadoop-TP2.git
There is three projects in this repository, to build one go in its directory

mvn compile

mvn package

mvn install

You now have your .jar file, you need to put it on the serveur :

scp CountryName-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean

scp NumberOfOrigin-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean

scp SexProportion-1.0-SNAPSHOT.jar lgrosjean@ns3024382.ip-149-202-81.eu:/home/lgrosjean


To run the .jar :

TP2.1
hadoop jar CountryName-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/CountryName/output

TP2.2
hadoop jar NumberOfOrigin-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/NumberOfOrigin/output

TP2.3
hadoop jar SexProportion-1.0-SNAPSHOT.jar /res/prenoms.csv /user/lgrosjean/SexProportion/output

To read the output :

hdfs dfs -cat /user/lgrosjean/CountryName/output/part-r-00000

hdfs dfs -cat /user/lgrosjean/NumberOfOrigin/output/part-r-00000

hdfs dfs -cat /user/lgrosjean/SexProportion/output/part-r-00000

