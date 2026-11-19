
package se.m777.helpdesk;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import se.m777.helpdesk.entity.Role;
import se.m777.helpdesk.entity.Ticket;
import se.m777.helpdesk.entity.User;
import se.m777.helpdesk.repository.CommentRepository;
import se.m777.helpdesk.repository.TicketRepository;
import se.m777.helpdesk.repository.UserRepository;
import se.m777.helpdesk.service.TicketService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;

class TicketServiceTest {

    @Test
    void userCannotCommentOnAnotherUsersTicket() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        CommentRepository commentRepository = mock(CommentRepository.class);

        TicketService service = new TicketService(
                ticketRepository, userRepository, commentRepository);


        User owner = mock(User.class);
        when(owner.getId()).thenReturn(1L);

        User otherUser = mock(User.class);
        when(otherUser.getId()).thenReturn(2L);
        when(otherUser.getRole()).thenReturn(Role.USER);

        Ticket ticket = new Ticket();
        ticket.setCreatedBy(owner);

        when(userRepository.findByUsername("user2"))
                .thenReturn(Optional.of(otherUser));

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(ticket));


        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.addComment(1L, "Test", "user2")
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());

        verify(commentRepository, never()).save(any());
    }
}







