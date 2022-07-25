package tn.esprit.payload;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Builder
@Data
public class MessageDTO {

	private String text;
	private Long authorId;
	private String authorUsername;
	private String messageType;
	private Instant createdDate;

}
