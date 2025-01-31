package com.suhashoysala.api;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class MessageProcessingApp {
    public static void main(String[] args) throws ParseException {
        SpringApplication app = new SpringApplication(MessageProcessingApp.class);
        Options options = new Options();
        options.addOption(Option.builder()
                .longOpt("serverAddress")
                .hasArg(true)
                .desc("Server address")
                .build());
        options.addOption(Option.builder()
                .longOpt("serverPort")
                .hasArg(true)
                .desc("Server port")
                .build());

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String serverPort = cmd.getOptionValue("serverPort", "8080");
        String serverAddress = cmd.getOptionValue("serverAddress", "localhost");

        app.setDefaultProperties(Map.of(
                "server.port", serverPort,
                "server.address", serverAddress
        ));
        app.run(args);
    }
}
