package tn.esprit.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
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
@EntityListeners({ AuditingEntityListener.class })
public class BaseEntityNoId implements Serializable {

	private static final long serialVersionUID = 6896440922401088416L;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected Instant createdDate;

	@CreatedBy
	protected String createdBy;

	@LastModifiedDate
	@Column(nullable = false)
	protected Instant lastModifiedDate;

	@LastModifiedBy
	protected String lastModifiedBy;

	@Version
	protected Integer version;
}
