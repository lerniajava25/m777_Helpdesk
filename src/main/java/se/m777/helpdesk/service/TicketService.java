
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

import se.m777.helpdesk.entity.Comment;
import se.m777.helpdesk.repository.CommentRepository;
import se.m777.helpdesk.entity.TicketStatus;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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

    public List<Comment> getComments(Long ticketId, String username) {
        getTicket(ticketId, username);

        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    public void addComment(Long ticketId, String content, String username) {
        Ticket ticket = getTicket(ticketId, username);
        User user = getUser(username);

        if (content == null || content.isBlank() || content.length() > 2000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setTicket(ticket);
        comment.setUser(user);

        commentRepository.save(comment);
    }


    public void assignTicket(Long ticketId, String username) {
        User user = getUser(username);

        if (user.getRole() != Role.AGENT && user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Ticket ticket = getTicket(ticketId, username);
        ticket.setAssignedTo(user);

        ticketRepository.save(ticket);
    }

    public void updateStatus(Long ticketId, TicketStatus status, String username) {
        User user = getUser(username);

        if (user.getRole() != Role.AGENT && user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Ticket ticket = getTicket(ticketId, username);
        ticket.setStatus(status);

        ticketRepository.save(ticket);
    }


}



