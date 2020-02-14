This is the command line client used to test the Person RestAPI webservice and should be
run after the API is installed and running. Please note that the client connects to the API running on port 8080 on the same server. If the host or port is different then you may need to modify the url string in the code. 
1. Clone the GIT repo into the server where Java and Maven is installed using the command:
git clone https://github.com/avinashhiriadka/PersonAPIClient.git
2. cd PersonAPIClient. Set JAVA_HOME path variable to the Java installation directory if not already set.
3. Run the maven command to build the client
mvn clean package
4. This should create a target directory with the classes and jar files
5. You can add the persons in the json file using the command:
java -jar PersonAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar classes/addpersons.json add
This will store the persons in the json file in the inmemory database on the API service
6. You can view the persons store in the database using the command:
java -jar PersonAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar list
This will list the persons in the standard command line console
7.You can delete the persons in the json file using the command:
java -jar PersonAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar classes/deletepersons.json delete
This will delete the persons from the database
8. You can list the persons in the database using the command in step 6 and see that the record is deleted
9. You can update the persons in the json file using the command:
java -jar PersonAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar classes/updatepersons.json update
10. You can list the persons in the databse using the command in step 6 and see that the record is updated

