package au.jahfong.ticketservice.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Holding data on field validation error.
 */
@Data
@RequiredArgsConstructor
public class FieldValidationError {
    private final String field;
    private final String message;
}
