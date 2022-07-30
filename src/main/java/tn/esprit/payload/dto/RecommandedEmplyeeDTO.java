package tn.esprit.payload.dto;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommandedEmplyeeDTO {
	private UserDTO user;
	private Duration totalTime;
}
