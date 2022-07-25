package tn.esprit.repository.user;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.user.Conversation;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@Repository
public interface ConversationsRepository extends JpaRepository<Conversation, Long> {

	Optional<Conversation> findById(@NotBlank Long id);
}
