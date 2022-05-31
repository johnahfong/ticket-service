package au.jahfong.ticketservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class MovieTicketTypeTest {

    @Test
    void should_get_proper_ticket_type_by_age() {
        assertEquals(MovieTicketType.ADULT, MovieTicketType.getTicketType(18));
        assertEquals(MovieTicketType.ADULT, MovieTicketType.getTicketType(64));
        assertEquals(MovieTicketType.SENIOR, MovieTicketType.getTicketType(65));
        assertEquals(MovieTicketType.SENIOR, MovieTicketType.getTicketType(100));
        assertEquals(MovieTicketType.TEEN, MovieTicketType.getTicketType(11));
        assertEquals(MovieTicketType.TEEN, MovieTicketType.getTicketType(17));
        assertEquals(MovieTicketType.CHILDREN, MovieTicketType.getTicketType(10));
    }

    @Test
    void should_deserialize_enum_as_title_case() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        assertEquals("{\"type\":\"Adult\"}",
            mapper.writeValueAsString(new MovieTicket(MovieTicketType.ADULT)));
        assertEquals("{\"type\":\"Senior\"}",
            mapper.writeValueAsString(new MovieTicket(MovieTicketType.SENIOR)));
        assertEquals("{\"type\":\"Children\"}",
            mapper.writeValueAsString(new MovieTicket(MovieTicketType.CHILDREN)));
        assertEquals("{\"type\":\"Teen\"}",
            mapper.writeValueAsString(new MovieTicket(MovieTicketType.TEEN)));
    }

    static class MovieTicket {
        private MovieTicketType type;

        public MovieTicket(MovieTicketType type) {
            this.type = type;
        }

        public MovieTicketType getType() {
            return type;
        }

        public void setType(MovieTicketType type) {
            this.type = type;
        }
    }
}