package au.jahfong.ticketservice.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = TicketConfiguration.class)
@TestPropertySource("classpath:application.properties")
class TicketConfigurationTest {

    @Autowired
    private TicketConfiguration ticketConfiguration;

    @Test
    void should_load_config() {
        assertEquals(25, ticketConfiguration.getPrice().getAdult().intValue());
        assertEquals(12, ticketConfiguration.getPrice().getTeen().intValue());
        assertEquals(5, ticketConfiguration.getPrice().getChildren().intValue());

        assertEquals(30, ticketConfiguration.getDiscount().getSeniorPercent());
        assertEquals(25, ticketConfiguration.getDiscount().getChildrenPercent());
        assertEquals(3, ticketConfiguration.getDiscount().getChildrenMinQuantity());
    }

}