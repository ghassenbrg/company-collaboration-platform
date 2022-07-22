package tn.esprit.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.event.InvitationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDTO {
	private UserDTO user;
	private InvitationStatus invitationStatus;
}
