package tn.esprit.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationDTO {
	private Long id;
	private String title;
	private String description;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	private Long partnerId;
}
