/*
 * Bober Clinic note: Describes response data sent from the backend to the frontend.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/common/BadRequestRes.java
 */
package boberbackend.controllers.common;

import boberbackend.enums.BadRequestDictEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestRes {

    @JsonProperty("code")
    private final String code;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("value")
    private final String value;

    public BadRequestRes(BadRequestDictEnum badRequestDict, String value) {
        this.code = badRequestDict.name();
        this.description = badRequestDict.getDescription();
        this.value = value;
    }
}



