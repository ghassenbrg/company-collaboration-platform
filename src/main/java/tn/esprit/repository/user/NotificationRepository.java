package tn.esprit.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.user.Notification;
import tn.esprit.model.user.User;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	Page<Notification> findByUser(User user, Pageable pageable);
}
