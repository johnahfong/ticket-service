package au.jahfong.ticketservice.model;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Defines the transaction request model.
 */
@Data
public class TransactionRequest {

    @NotNull
    @Min(1)
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
        @Min(1)
        @Max(120)
        private int age;
    }
}
