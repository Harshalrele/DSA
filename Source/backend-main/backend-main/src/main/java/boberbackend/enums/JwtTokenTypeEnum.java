/*
 * Bober Clinic note: Defines fixed allowed values used by the backend.
 * File: backend-main/backend-main/src/main/java/boberbackend/enums/JwtTokenTypeEnum.java
 */
package boberbackend.enums;

import lombok.Getter;

@Getter
public enum JwtTokenTypeEnum {
    BEARER("Bearer");

    private final String header;

    JwtTokenTypeEnum(String header) {
        this.header = header;
    }
}



