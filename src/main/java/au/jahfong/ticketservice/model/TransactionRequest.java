package au.jahfong.ticketservice.model;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Defines the transaction request model.
 */
@Data
public class TransactionRequest {

    @NotNull
    private Integer transactionId;

    @Valid
    private List<Customer> customers;

    /**
     * Defines the Customer POJO.
     */
    @Data
    public static class Customer {
        @NotBlank
        private String name;

        @NotNull
        private int age;
    }
}
