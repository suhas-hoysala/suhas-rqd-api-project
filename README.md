
# Message Processing Server

This project creates a server to which messages can be pushed. The server processes these messages and outputs the number of messages received, the average age of the records, and the number of sequence gaps detected.

## Requirements

- Java 17
- Gradle

## Build

To build the project, run the following command:

```sh
./gradlew install
```

## Run

To run the server (works without building), use the following command:

```sh
./gradlew run
```
this defaults to http://localhost:8080. You can change the server address and port by passing the following arguments:

```sh
./gradlew run -PserverAddress=[url] -PserverPort=[port]
```

Alternatively, after building the project, you can run the jar directly:
```sh
java -jar build/libs/message-processing-server.jar
```
or, you can pass the server address and port as arguments:
```sh
java -jar build/libs/message-processing-server.jar --serverAddress=[url] --serverPort=[port]
```

Replace `[serverAddress]` and `[port]` with the desired server url and port number. e.g. `127.0.0.1` and `8081`.

## Endpoints

- **POST /message**: Push a message to the server.

## Output

The server logs the following statistics every 10 seconds:
- Number of messages received
- Average age of the records
- Number of sequence gaps detected
```
