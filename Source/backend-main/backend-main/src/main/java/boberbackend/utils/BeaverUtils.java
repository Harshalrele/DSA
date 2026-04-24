package boberbackend.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BeaverUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    public static LocalDateTime convertReqToDateTime(String req) {
        return LocalDateTime.parse(req, formatter);
    }

}

