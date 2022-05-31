package au.jahfong.ticketservice.service;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import au.jahfong.ticketservice.model.MovieTicketType;
import au.jahfong.ticketservice.model.TransactionRequest;
import au.jahfong.ticketservice.model.TransactionRequest.Customer;
import au.jahfong.ticketservice.model.TransactionResponse;
import au.jahfong.ticketservice.model.TransactionResponse.Ticket;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service around transaction.
 */
@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TicketCalculator ticketCalculator;

    /**
     * Get transaction cost response give a transaction request.
     */
    public TransactionResponse getTransaction(TransactionRequest request) {
        Map<MovieTicketType, Ticket> ticketsByType = getTicketsByType(request);

        return TransactionResponse.builder()
            .transactionId(request.getTransactionId())
            .tickets(ticketsByType.values().stream().collect(toList()))
            .totalCost(calculateTotalCost(ticketsByType))
            .build();
    }

    private Map<MovieTicketType, Ticket> getTicketsByType(TransactionRequest request) {
        return request.getCustomers().stream().collect(
            groupingBy(
                c -> MovieTicketType.getTicketType(c.getAge()),
                TreeMap::new,
                collectTickets()));
    }

    private Collector<Customer, Object, Ticket> collectTickets() {
        return collectingAndThen(
            toList(), list -> {
                MovieTicketType type = MovieTicketType.getTicketType(list.get(0).getAge());
                BigDecimal ticketCost = ticketCalculator.calculate(type, list.size());

                return TransactionResponse.Ticket.builder()
                    .ticketType(type)
                    .cost(ticketCost)
                    .quantity(list.size()).build();
            }
        );
    }

    private BigDecimal calculateTotalCost(Map<MovieTicketType, Ticket> ticketsByType) {
        return ticketsByType.values().stream()
            .map(Ticket::getCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
