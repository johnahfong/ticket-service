package au.jahfong.ticketservice.exception;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception Handler to handle exceptions thrown from the API.
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Handle runtime error as internal server error.
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(RuntimeException exception) {
        log.error("Unexpected error happened: ", exception);
        return new ErrorResponse(
            ErrorType.GENERIC_ERROR,
            "An internal error occurred"
        );
    }

    /**
     * Handle constraint validation error as bad request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(
            ErrorType.VALIDATION_FAILED,
            "Validation Failed",
            e.getBindingResult().getFieldErrors().stream()
                .map(it -> new FieldValidationError(it.getField(), it.getDefaultMessage())).collect(
                    Collectors.toList())
        );
    }


}
