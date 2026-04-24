/*
 * Bober Clinic note: Contains code or settings for ControllerExceptionHandler.java.
 * File: backend-main/backend-main/src/main/java/boberbackend/controllers/common/ControllerExceptionHandler.java
 */
package boberbackend.controllers.common;

import boberbackend.enums.BadRequestDictEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<BadRequestRes> handeJsonParseException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new BadRequestRes(BadRequestDictEnum.BAD_VALUE, e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<BadRequestRes> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getResponse());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<BadRequestRes> handleMethodeNotAllowedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.badRequest().body(new BadRequestRes(BadRequestDictEnum.METHOD_NOT_ALLOWED, e.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<BadRequestRes> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.badRequest().body(new BadRequestRes(BadRequestDictEnum.BAD_AUTH, e.getMessage()));
    }

}



