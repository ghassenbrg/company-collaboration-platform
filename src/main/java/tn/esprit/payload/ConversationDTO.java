package tn.esprit.payload;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@Builder
@Data
public class ConversationDTO {

	private Long id;
	private String title;
	private Instant date;
	private List<String> members;
	private int messagesCount;
	private String eventType;
	private Long ownerId;

}
