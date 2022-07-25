package tn.esprit.repository.user;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.user.Message;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	Optional<Message> findById(@NotBlank Long id);

	List<Message> findByPostedBy(@NotBlank Long postedBy);

	List<Message> findByConversationId(@NotBlank Long conversationId);
}
