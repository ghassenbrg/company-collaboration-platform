package tn.esprit.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Data
public class EvaluationDTO {

	@NotBlank
	@Min(value = 1)
	@Max(value = 5)
	private float rating;

	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String comment;

	public EvaluationDTO() {

	}

	public EvaluationDTO(String comment, float rating) {
		this.comment = comment;
		this.rating = rating;
	}
}
