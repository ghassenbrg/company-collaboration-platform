package tn.esprit.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.partner.Reservation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreDTO {
	private String title;
	private String description;
	private Double price;
	private Float remise;
	private Long partnerId;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	//private List<Reservation> reservations;
}
