# COMP90015-Distributed-System Assignment 1 - Multi-thread Dictionary Server
### Configuration
1. Download the zip with the name of the jar files from folder "Assignement1 Submission", and unzip it, all supporting files to run the system already in the package code.
2. Then right-click the package code to build two terminals in this directory.
### Execute the system
-	Execute the Server
`java -jar DictionaryServer.jar <server-ipaddress> <server-port>` 
-	Execute the Clients
```java --module-path "javafx-sdk-20.0.2/lib" --add-modules javafx.controls,javafx.fxml -jar DictionaryClient.jar <server-ipaddress> <server-port>``` 

- ```<server-ipaddress>``` : the IP address for server waited to be connect
- ```<server-port>``` : the port for connecting to server
