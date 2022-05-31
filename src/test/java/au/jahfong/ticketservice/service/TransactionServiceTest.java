package au.jahfong.ticketservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import au.jahfong.ticketservice.config.TicketConfiguration;
import au.jahfong.ticketservice.model.MovieTicketType;
import au.jahfong.ticketservice.model.TransactionRequest;
import au.jahfong.ticketservice.model.TransactionResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {
    private static final TransactionRequest.Customer ADULT_CUSTOMER = givenCustomer("Am an Adult", 40);
    private static final TransactionRequest.Customer TEEN_CUSTOMER = givenCustomer("Am a Teen", 17);
    private static final TransactionRequest.Customer CHILD_CUSTOMER = givenCustomer("Am a child", 10);
    private static final TransactionRequest.Customer SENIOR_CUSTOMER = givenCustomer("Am a senior", 65);

    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        transactionService = new TransactionService(new TicketCalculator(givenConfig()));
    }

    @Test
    void should_handle_one_customer() {
        TransactionRequest request = givenTransactionRequest(ADULT_CUSTOMER);

        TransactionResponse transaction = transactionService.getTransaction(request);
        assertEquals(1, transaction.getTransactionId());
        assertEquals(1, transaction.getTickets().size());

        assertEquals(MovieTicketType.ADULT, transaction.getTickets().get(0).getTicketType());
        assertEquals(25, transaction.getTickets().get(0).getCost().doubleValue());

        assertEquals(25, transaction.getTotalCost().doubleValue());
    }

    private TransactionRequest givenTransactionRequest(TransactionRequest.Customer... customers) {
        TransactionRequest request = new TransactionRequest();
        request.setTransactionId(1);
        request.setCustomers(Arrays.asList(customers));
        return request;
    }

    @Test
    void should_handle_multiple_customers() {
        TransactionRequest request = givenTransactionRequest(SENIOR_CUSTOMER, CHILD_CUSTOMER,
            ADULT_CUSTOMER, TEEN_CUSTOMER, CHILD_CUSTOMER, CHILD_CUSTOMER);

        TransactionResponse transaction = transactionService.getTransaction(request);
        assertEquals(1, transaction.getTransactionId());
        assertEquals(4, transaction.getTickets().size());

        assertEquals(MovieTicketType.ADULT, transaction.getTickets().get(0).getTicketType());
        assertEquals(1, transaction.getTickets().get(0).getQuantity());
        assertEquals(25, transaction.getTickets().get(0).getCost().doubleValue());

        assertEquals(MovieTicketType.CHILDREN, transaction.getTickets().get(1).getTicketType());
        assertEquals(3, transaction.getTickets().get(1).getQuantity());
        assertEquals(11.25, transaction.getTickets().get(1).getCost().doubleValue());

        assertEquals(MovieTicketType.SENIOR, transaction.getTickets().get(2).getTicketType());
        assertEquals(1, transaction.getTickets().get(2).getQuantity());
        assertEquals(17.5, transaction.getTickets().get(2).getCost().doubleValue());

        assertEquals(MovieTicketType.TEEN, transaction.getTickets().get(3).getTicketType());
        assertEquals(1, transaction.getTickets().get(3).getQuantity());
        assertEquals(12, transaction.getTickets().get(3).getCost().doubleValue());

        assertEquals(65.75, transaction.getTotalCost().doubleValue());
    }

    private TicketConfiguration givenConfig() {
        TicketConfiguration config = new TicketConfiguration();
        config.setPrice(new TicketConfiguration.Price());
        config.getPrice().setAdult(new BigDecimal(25));
        config.getPrice().setTeen(new BigDecimal(12));
        config.getPrice().setChildren(new BigDecimal(5));

        config.setDiscount(new TicketConfiguration.Discount());
        config.getDiscount().setSeniorPercent(30d);
        config.getDiscount().setChildrenPercent(25d);
        config.getDiscount().setChildrenMinQuantity(3);

        return config;
    }

    static TransactionRequest.Customer givenCustomer(String name, int age) {
        TransactionRequest.Customer customer = new TransactionRequest.Customer();
        customer.setName(name);
        customer.setAge(age);
        return customer;
    }
}