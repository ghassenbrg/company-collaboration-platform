package tn.esprit.model.evaluation;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.forums.Post;
import tn.esprit.model.user.Employee;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "badges")
public class Badge extends BaseEntity {
	
	private static final long serialVersionUID = -1348651020654338522L;
	
	@Column(name = "name")
	private String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_badges", joinColumns = @JoinColumn(name = "badge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
	private List<Employee> employees;

}
