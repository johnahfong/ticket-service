package au.jahfong.ticketservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import au.jahfong.ticketservice.config.TicketConfiguration;
import au.jahfong.ticketservice.model.MovieTicketType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TicketCalculatorTest {
    static BigDecimal ADULT_PRICE = new BigDecimal(25);
    static BigDecimal TEEN_PRICE = new BigDecimal(12);
    static BigDecimal CHILDREN_PRICE = new BigDecimal(5);
    static Double DISCOUNT_SENIOR_PERCENT = 30d;
    static Double DISCOUNT_CHILDREN_PERCENT = 25d;
    static int DISCOUNT_CHILDREN_MIN_QUANTITY = 3;

    @Test
    void testCalculation() {
        TicketCalculator calculator = new TicketCalculator(givenConfig());

        assertEquals(25, calculator.calculate(MovieTicketType.ADULT, 1).doubleValue());
        assertEquals(250, calculator.calculate(MovieTicketType.ADULT, 10).doubleValue());

        assertEquals(17.5, calculator.calculate(MovieTicketType.SENIOR, 1).doubleValue());
        assertEquals(17.5, calculator.calculate(MovieTicketType.SENIOR, 1).doubleValue());

        assertEquals(24, calculator.calculate(MovieTicketType.TEEN, 2).doubleValue());

        assertEquals(10, calculator.calculate(MovieTicketType.CHILDREN, 2).doubleValue());
        assertEquals(11.25, calculator.calculate(MovieTicketType.CHILDREN, 3).doubleValue());
    }

    private TicketConfiguration givenConfig() {
        TicketConfiguration config = new TicketConfiguration();
        config.setPrice(new TicketConfiguration.Price());
        config.getPrice().setAdult(ADULT_PRICE);
        config.getPrice().setTeen(TEEN_PRICE);
        config.getPrice().setChildren(CHILDREN_PRICE);

        config.setDiscount(new TicketConfiguration.Discount());
        config.getDiscount().setSeniorPercent(DISCOUNT_SENIOR_PERCENT);
        config.getDiscount().setChildrenPercent(DISCOUNT_CHILDREN_PERCENT);
        config.getDiscount().setChildrenMinQuantity(DISCOUNT_CHILDREN_MIN_QUANTITY);

        return config;
    }
}