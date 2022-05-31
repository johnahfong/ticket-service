package au.jahfong.ticketservice.model;

import au.jahfong.ticketservice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Defines the transaction response model.
 */
@Data
@Builder
public class TransactionResponse {
    private final int transactionId;
    private final List<Ticket> tickets;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal totalCost;

    /**
     * Defines the Ticket POJO.
     */
    @Data
    @Builder
    public static class Ticket {
        private final MovieTicketType ticketType;
        private final int quantity;

        @JsonSerialize(using = MoneySerializer.class)
        private final BigDecimal cost;
    }
}
