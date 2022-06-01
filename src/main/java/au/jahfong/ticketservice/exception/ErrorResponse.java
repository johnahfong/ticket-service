package au.jahfong.ticketservice.exception;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Defines error response.
 */
@Slf4j
@Data
public class ErrorResponse {
    private final ErrorType type;
    private final String message;
    private final List<FieldValidationError> violations;

    /**
     * Constructor.
     */
    public ErrorResponse(final ErrorType type, final String message) {
        this(type, message, Collections.emptyList());
    }

    /**
     * Constructor.
     */
    public ErrorResponse(final ErrorType type, final String message,
                         final List<FieldValidationError> violations) {
        this.type = type;
        this.message = message;
        this.violations = violations;
    }
}
