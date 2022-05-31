package au.jahfong.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the Ticket Type.
 */
public enum MovieTicketType {
    @JsonProperty("Adult")
    ADULT,
    @JsonProperty("Children")
    CHILDREN,
    @JsonProperty("Senior")
    SENIOR,
    @JsonProperty("Teen")
    TEEN;

    /**
     * Utility method to get ticket type by age.
     */
    public static MovieTicketType getTicketType(int age) {
        if (age < 11) {
            return CHILDREN;
        } else if (age < 18) {
            return TEEN;
        } else if (age < 65) {
            return ADULT;
        } else {
            return SENIOR;
        }
    }
}
