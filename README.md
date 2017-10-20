#  overview
java component to populate moltin store with [adventure works](https://msftdbprodsamples.codeplex.com/releases/view/125550) data.

# pre-requisites
1- java jdk 1.7 or above.
	to download refer to- http://www.oracle.com/technetwork/java/javase/downloads/index.html

2- gradle build tool
	to download refer to- https://gradle.org/install/

after downloading both, add java and gradle in environment variable.
refer to https://www.java.com/en/download/help/path.xml on how to add path in windows and unix machine.

# prepare adventure.works data
download & extarct [Adventure Works](https://msftdbprodsamples.codeplex.com/releases/view/125550) data.
replace following line in product.csv 

814	ML Mountain Frame - Black, 38	FR-M63B-38	1	1	Black	500	375	185.8193	348.76	38	CM 	LB 	2.73	2	M 	M 	U 	12	**15**	2012-05-30 00:00:00	2013-05-29 00:00:00		{0D879312-A7D3-441D-9D23-B6550BAB3814}	2014-02-08 10:01:36.827000000

with

814	ML Mountain Frame - Black, 38	FR-M63B-38	1	1	Black	500	375	185.8193	348.76	38	CM 	LB 	2.73	2	M 	M 	U 	12	**14**	2012-05-30 00:00:00	2013-05-29 00:00:00		{0D879312-A7D3-441D-9D23-B6550BAB3814}	2014-02-08 10:01:36.827000000

Note: above line will fix data issue data.

# create jar
downlod/checkout source code into some directory. for example- moltin.java.adventure.works
open terminal, change directory to moltin.java.adventure.works

	gradle build

after successful build, go to moltin.java.adventure.works/build/libs and copy moltin.java.adventure.works-all.jar to directory from where you want to run java component.

# extract configuration files from jar-
open terminal, change your directory to location where you copied moltin.java.adventure.works-all.jar

issue comamnds

	jar xf moltin.java.adventure.works-all.jar sample.application.properties
	jar xf moltin.java.adventure.works-all.jar sample.log4j.properties

sample.application.properties and sample.log4j.properties will be created in current directory

# setup configuration files
issue commands
 
	mv sample.application.properties application.properties
	mv sample.log4j.properties log4j.properties`

# edit configuration files

1. application.properties - refer sample.application.properties.
2. log4j.properties - refer sample.log4j.properties (standard - https://logging.apache.org/log4j)

## application.properties
##########start - application.properties#############

####### moltiin api - client id

	moltin.api.client.id=MyKjhjJZNUnnVHFNPMeiifK7Ja9uEgGMStsSReLkJb

####### moltiin api - secret/password

	moltin.api.client.secret=8WQtgGKX41YKyK6mt4LSe95eirufujcc8lux4dzzuf

####### location of downloaded adventure works data (path of the folder containing csv files)
####### make sure path doesn't contain spaces

	adventure.works.data.location=d:/downloads/adventure.works data

##########end - application.properties#############

#  run moltin.java.adventure.works

change your directory to moltin.java.adventure.works-home-dir where moltin.java.adventure.works-app-jar is located. and issue a command

     java -DMOLTIN_JAVA_ADVENTURE_WORKS_HOME=<moltin.java.adventure.works-home-dir> -jar moltin.java.adventure.works-all.jar 

in this command jvm argument `moltin.java.adventure.works.home` specifies directory containing application.properties.