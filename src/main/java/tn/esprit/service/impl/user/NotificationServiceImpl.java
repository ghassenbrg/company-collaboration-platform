package tn.esprit.service.impl.user;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tn.esprit.model.user.Notification;
import tn.esprit.model.user.NotificationType;
import tn.esprit.model.user.User;
import tn.esprit.payload.PagedResponse;
import tn.esprit.repository.user.NotificationRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.user.NotificationService;
import tn.esprit.utils.Utils;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public PagedResponse<Notification> getUserNotification(UserPrincipal currentUser, int page, int size) {
		Utils.validatePageNumberAndSize(page, size);
		User user = userRepository.getUserByName(currentUser.getUsername());
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, Utils.CREATED_DATE);
		Page<Notification> notifications = notificationRepository.findByUser(user, pageable);
		List<Notification> content = notifications.getNumberOfElements() == 0 ? Collections.emptyList()
				: notifications.getContent();

		return new PagedResponse<>(content, notifications.getNumber(), notifications.getSize(),
				notifications.getTotalElements(), notifications.getTotalPages(), notifications.isLast());
	}

	@Override
	public Notification notify(String username, String content, NotificationType type) {
		User user = userRepository.getUserByName(username);

		Notification notification = new Notification();
		notification.setContent(content);
		notification.setType(type);
		notification.setUser(user);

		return notificationRepository.save(notification);
	}

}
