package com.amanso.backend.file;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    private FileUtils() {
        // Prevent instantiation
    }

    /*
     * Returns the bytes of the file at the given path.
     */
    public static byte[] readFileFromLocation(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            log.warn("File path is blank or null.");
            return new byte[0];
        }
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            log.error("Error reading file from path: {}", filePath, e);
            return new byte[0];
        }

    }

}
