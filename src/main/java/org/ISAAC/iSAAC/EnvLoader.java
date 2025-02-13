package org.ISAAC.iSAAC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> envVariables = new HashMap<>();

    static {
        try {
            Files.lines(Paths.get("plugins/ISAAC/.env"))
                    .filter(line -> line.contains("=") && !line.startsWith("#"))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        envVariables.put(parts[0].trim(), parts[1].trim());
                    });
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier .env", e);
        }
    }

    public static String get(String key) {
        return envVariables.get(key);
    }

    public static void main(String[] args) {
        System.out.println("Database URL: " + get("DATABASE_URL"));
        System.out.println("Database User: " + get("DATABASE_USER"));
    }
}
