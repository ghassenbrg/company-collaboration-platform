package tn.esprit.payload;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class ConversationListDTO {

	private int total;
	private List<ConversationDTO> conversationDTO;
}
