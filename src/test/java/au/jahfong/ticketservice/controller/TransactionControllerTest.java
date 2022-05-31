package au.jahfong.ticketservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.jahfong.ticketservice.model.MovieTicketType;
import au.jahfong.ticketservice.model.TransactionResponse;
import au.jahfong.ticketservice.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_200_on_post_transaction_successfully() throws Exception {
        // given
        TransactionResponse expectedResponse = createResponse();
        given(transactionService.getTransaction(any())).willReturn(expectedResponse);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"transactionId\":1,\"customers\":[{\"name\":\"test customer\",\"age\":40}]}")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        // Then
        JSONAssert.assertEquals(objectMapper.writeValueAsString(expectedResponse),
            result.getResponse().getContentAsString(), false);
    }

    @Test
    void should_return_400_on_post_transaction_with_missing_mandatory_fields() throws Exception {
        // When no transaction id provided
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"customers\":[{\"name\":\"test customer\",\"age\":40}]}")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    private TransactionResponse createResponse() {
        return TransactionResponse.builder()
            .transactionId(1)
            .tickets(Arrays.asList(TransactionResponse.Ticket.builder().ticketType(
                MovieTicketType.ADULT).cost(new BigDecimal(10)).quantity(1).build()))
            .totalCost(new BigDecimal(10))
            .build();
    }

}