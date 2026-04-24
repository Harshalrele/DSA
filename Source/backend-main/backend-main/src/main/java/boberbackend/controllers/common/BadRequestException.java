package boberbackend.controllers.common;

import boberbackend.enums.BadRequestDictEnum;
import org.springframework.security.core.AuthenticationException;

public class BadRequestException extends AuthenticationException {

    private final BadRequestDictEnum badRequestCode;
    private final String value;

    public BadRequestException(BadRequestDictEnum badRequestCode, String value) {
        super("");
        this.badRequestCode = badRequestCode;
        this.value = value;
    }

    public BadRequestRes getResponse() {
        return new BadRequestRes(this.badRequestCode, this.value);
    }

}

