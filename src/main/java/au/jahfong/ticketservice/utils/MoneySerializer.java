package au.jahfong.ticketservice.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Custom Serializer for BigDecimal to display in 2 decimal places.
 */
public class MoneySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
        if (value != null) {
            BigDecimal formatted = value.setScale(2, RoundingMode.HALF_UP);
            gen.writeNumber(formatted);
        } else {
            gen.writeNumber(value);
        }
    }
}