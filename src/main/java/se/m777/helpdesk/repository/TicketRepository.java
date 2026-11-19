
package se.m777.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.m777.helpdesk.entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedById(Long userId);
}





