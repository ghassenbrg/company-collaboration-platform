package tn.esprit.repository.user;

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

}
