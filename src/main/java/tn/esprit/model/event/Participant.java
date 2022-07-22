package tn.esprit.model.event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.user.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "participant")
public class Participant extends BaseEntity {

	private static final long serialVersionUID = -7166693347797822924L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;
	
	@Column(name = "invitation_status", columnDefinition = "varchar(32) default 'PENDING'")
	@Enumerated(EnumType.STRING)
	private InvitationStatus invitationStatus = InvitationStatus.PENDING;

}
