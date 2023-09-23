[Versão em português](README.md)

# Lista 5 - Unidade III - Chat em Java - Redes de Computadores I

Assignment on implementing an instant messaging program in Java for the subject Computer Networks I of the Computer Science course at the Pontifical Catholic University of Minas Gerais.

## Concept

The following idea was conceived for this implementation: two or more clients exchange messages with each other, through a server.

The communication between the clients and the server happens with the exchange of commands from the client to the server. When the server receives a command, it  returns a response. The main steps to the exchange of messages between clients are:

- The sender client sends a command to the server requesting the delivery of a message to the other client (recipient), and on this request it is sent the message. The server responds with a success response.

- The server receives and stores the messages sent from one client (sender) to the other, until the recipient client request the receipt of its messages.

- Each client, every minute, requests the server the receipt of its messages. And the server responds with a list with all messages addressed to him. If there is no message, the list will be empty.

Is possible to make the communication between the server and the client using TCP or UDP. During the initialization, both the client and the server will ask for the user to choose a transport layer protocol (TCP ou UDP).

## Source code

### How to compile

To compile this program, open the terminal on its folder and type the following command:

On Linux or Mac Terminal:

```
./gradlew build
```

On Windows Command Prompt:

```
gradlew build
```

### How to generate the .jar files

The .jar files can be generated using gradle. To generate the .jar files, on the terminal, on the same folder, type the following commands:

#### Client

On Linux or Mac Terminal:

```
./gradlew jarCliente
```

On Windows Command Prompt:

```
gradlew jarCliente
```

#### Server

On Linux or Mac Terminal:

```
./gradlew jarServidor
```

On Windows Command Prompt:

```
gradlew jarServidor
```

####

After executed those commands above, the .jar files will be generated on the folder "[app/build/libs](app/build/libs)".

### How to run the .jar files

To run the .jar files, on the folder where those files were downloaded or generated, open the terminal and type:

```
java -jar <.jar-file-name>
```

In which <.jar-file-name> should be replaced with the name of the .jar file which you want to run: cliente.jar or servidor.jar.