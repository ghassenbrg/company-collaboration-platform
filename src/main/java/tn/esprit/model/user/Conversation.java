package tn.esprit.model.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "conversation")
public class Conversation extends BaseEntity {

	private static final long serialVersionUID = 3885355119175299569L;

	private String title;

	private Long ownerId;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "conversations")
	private Set<User> users = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "conversation", cascade = CascadeType.ALL)
	List<Message> messages = new ArrayList<>();

	public Conversation(String title) {
		this.title = title;
	}

}
