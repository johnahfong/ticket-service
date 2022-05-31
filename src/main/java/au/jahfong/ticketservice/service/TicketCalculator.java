package au.jahfong.ticketservice.service;

import au.jahfong.ticketservice.config.TicketConfiguration;
import au.jahfong.ticketservice.model.MovieTicketType;
import java.math.BigDecimal;
import java.util.EnumMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to calculate cost of a ticket.
 */
@Service
public class TicketCalculator {
    public static final int PERCENTAGE_100 = 100;
    private final EnumMap<MovieTicketType, BigDecimal> priceByType =
        new EnumMap<>(MovieTicketType.class);

    private final TicketConfiguration config;

    /**
     * Store price by ticket type during construction.
     */
    @Autowired
    public TicketCalculator(TicketConfiguration config) {
        this.config = config;

        priceByType.put(MovieTicketType.ADULT, config.getPrice().getAdult());
        priceByType.put(MovieTicketType.TEEN, config.getPrice().getTeen());
        priceByType.put(MovieTicketType.CHILDREN, config.getPrice().getChildren());
        priceByType.put(MovieTicketType.SENIOR,
            percentOff(config.getPrice().getAdult(), config.getDiscount().getSeniorPercent()));
    }

    /**
     * Calculate cost given ticket type and quantity.
     */
    public BigDecimal calculate(MovieTicketType ticketType, int quantity) {
        BigDecimal totalCost = priceByType.get(ticketType).multiply(new BigDecimal(quantity));

        if (ticketType == MovieTicketType.CHILDREN
            && quantity >= config.getDiscount().getChildrenMinQuantity()) {
            totalCost = percentOff(totalCost, config.getDiscount().getChildrenPercent());
        }
        return totalCost;
    }

    private BigDecimal percentOff(BigDecimal value, Double percent) {
        return value.multiply(BigDecimal.valueOf((PERCENTAGE_100 - percent) / PERCENTAGE_100));
    }
}
