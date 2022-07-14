package tn.esprit.service.user;

import tn.esprit.model.user.Notification;
import tn.esprit.model.user.NotificationType;
import tn.esprit.payload.PagedResponse;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface NotificationService {

	PagedResponse<Notification> getUserNotification(UserPrincipal currentUser, int page, int size);

	Notification notify(String username, String content, NotificationType type);

}
