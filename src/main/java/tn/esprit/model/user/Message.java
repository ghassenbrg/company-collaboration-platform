package tn.esprit.model.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "message")
public class Message extends BaseEntity {

	private static final long serialVersionUID = -1689584148227165112L;

	private String text;

	private Long postedBy;

	@ManyToOne
	@JoinColumn(name = "conversation_id")
	private Conversation conversation;

}
