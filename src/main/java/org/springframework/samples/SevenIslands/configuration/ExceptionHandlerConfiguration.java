package org.springframework.samples.SevenIslands.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration {
	
    
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<String> handleControllerException(Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}