package au.jahfong.ticketservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TransactionResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testMarshalling() throws Exception {
        TransactionResponse.Ticket ticket =
            new TransactionResponse.Ticket(MovieTicketType.ADULT, 1, new BigDecimal(10.2333));

        TransactionResponse response =
            new TransactionResponse(1, Arrays.asList(ticket), new BigDecimal(10.2333));

        String marshalled = objectMapper.writeValueAsString(response);
        assertEquals("{\""
                + "transactionId\":1,\""
                + "tickets\":[{\"ticketType\":\"Adult\",\"quantity\":1,\"cost\":\"10.23\"}],"
                + "\"totalCost\":\"10.23\"}", marshalled);
    }
}