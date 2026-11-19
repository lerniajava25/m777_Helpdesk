
package se.m777.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.m777.helpdesk.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}







