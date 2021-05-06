package com.lib;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * For full use of app configure postgres in application-guest.properties.
 * Otherwise a search will be disabled
 */
@Log4j2
@SpringBootApplication
public class LibEntry {
    public enum EnvType {
        DEV("1111", "dev"),
        GUEST("guest", "guest");

        private static final Map<String, EnvType> hash = new HashMap<>();

        static {
            Arrays.stream(values()).forEachOrdered(envType -> hash.put(envType.hostName, envType));
        }

        private final String hostName;
        private final String profile;

        EnvType(String hostName, String profile) {
            this.hostName = hostName;
            this.profile = profile;
        }

        public static EnvType getEnvironment(String hostName) {
            return hash.getOrDefault(hostName, GUEST);
        }
    }

    public static void main(String[] args) {
        String host = System.getenv("current.env");
        log.info("Host: " + host);
        EnvType env = EnvType.getEnvironment(host);
        String profile = env.profile;
        new SpringApplicationBuilder(LibEntry.class).profiles(profile).build().run(args);
    }
}
