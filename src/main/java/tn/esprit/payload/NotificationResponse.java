package tn.esprit.payload;

import lombok.Data;
import tn.esprit.model.user.NotificationType;

@Data
public class NotificationResponse {

	private String content;
	private NotificationType type;
}
