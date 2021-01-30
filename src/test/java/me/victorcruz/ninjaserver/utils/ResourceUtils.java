package me.victorcruz.ninjaserver.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceUtils {
    public static String readFile(String resourcePath) throws IOException {
        String basePath = "src/test/resources";
        String fullPath = String.format("%s/%s", basePath, resourcePath);

        return new String(
                Files.readAllBytes(Paths.get(fullPath))
        );
    }
}
