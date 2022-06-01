package au.jahfong.ticketservice.controller;

import au.jahfong.ticketservice.model.TransactionRequest;
import au.jahfong.ticketservice.model.TransactionResponse;
import au.jahfong.ticketservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines the Ticket REST API.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Transaction API")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Post a ticket transaction.
     *
     * @param request the transaction request
     * @return the transaction response
     */
    @Operation(summary = "Post transaction", responses = {
        @ApiResponse(responseCode = "200", description = "Successful"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse postTransaction(
        @RequestBody @Valid TransactionRequest request) {
        log.debug("post transaction with id: {}", request.getTransactionId());
        return transactionService.getTransaction(request);
    }
}
