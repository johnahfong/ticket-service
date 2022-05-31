package au.jahfong.ticketservice.config;

import com.fasterxml.jackson.databind.MapperFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson Configuration.
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Jackson mapper customizer.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(
            MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
    }
}

