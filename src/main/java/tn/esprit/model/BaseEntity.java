package tn.esprit.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6520392150085945982L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@CreatedBy
	private String createdBy;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@LastModifiedBy
	private String lastModifiedBy;
	
	@Version
	private Integer version;
}
