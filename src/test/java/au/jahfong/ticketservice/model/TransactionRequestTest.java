package au.jahfong.ticketservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TransactionRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testMarshalling() throws Exception {
        TransactionRequest.Customer customer = new TransactionRequest.Customer();
        customer.setName("John");
        customer.setAge(10);

        TransactionRequest request = new TransactionRequest();
        request.setTransactionId(1);
        request.setCustomers(Arrays.asList(customer));

        String marshalled = objectMapper.writeValueAsString(request);
        assertEquals("{\"transactionId\":1,\"customers\":[{\"name\":\"John\",\"age\":10}]}",
            marshalled);
    }

    @Test
    void testUnmarshalling() throws Exception {
        String json = "{\"transactionId\":1,\"customers\":[{\"name\":\"John\",\"age\":10}]}";

        TransactionRequest unmarshalled = objectMapper.readValue(json, TransactionRequest.class);
        assertEquals(1, unmarshalled.getTransactionId());
    }

}