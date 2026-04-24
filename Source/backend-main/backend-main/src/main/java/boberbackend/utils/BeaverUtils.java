/*
 * Bober Clinic note: Provides helper methods reused by backend code.
 * File: backend-main/backend-main/src/main/java/boberbackend/utils/BeaverUtils.java
 */
package boberbackend.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BeaverUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    public static LocalDateTime convertReqToDateTime(String req) {
        return LocalDateTime.parse(req, formatter);
    }

}



