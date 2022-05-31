package au.jahfong.ticketservice.config;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Ticket Configuration.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ticket")
public class TicketConfiguration {
    private Price price;
    private Discount discount;

    /**
     * Holding Price Configuration.
     */
    @Data
    public static class Price {
        private BigDecimal adult;
        private BigDecimal teen;
        private BigDecimal children;
    }

    /**
     * Holding Discount Configuration.
     */
    @Data
    public static class Discount {
        private Double seniorPercent;
        private Double childrenPercent;
        private Integer childrenMinQuantity;
    }
}
