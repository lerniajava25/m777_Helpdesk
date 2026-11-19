
package se.m777.helpdesk.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.m777.helpdesk.entity.Role;
import se.m777.helpdesk.entity.Ticket;
import se.m777.helpdesk.entity.User;
import se.m777.helpdesk.repository.TicketRepository;
import se.m777.helpdesk.repository.UserRepository;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Ticket> getTickets(String username) {
        User user = getUser(username);

        if (user.getRole() == Role.AGENT || user.getRole() == Role.ADMIN) {
            return ticketRepository.findAll();
        }

        return ticketRepository.findByCreatedById(user.getId());
    }

    public Ticket getTicket(Long id, String username) {
        User user = getUser(username);

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getRole() == Role.USER &&
                !ticket.getCreatedBy().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ticket;
    }

    public void createTicket(String title, String description, String username) {
        User user = getUser(username);

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setCreatedBy(user);

        ticketRepository.save(ticket);
    }
}



