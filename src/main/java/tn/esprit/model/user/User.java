package tn.esprit.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.event.Rating;
import tn.esprit.model.forums.Comment;
import tn.esprit.model.forums.Like;
import tn.esprit.model.forums.Post;
import tn.esprit.model.partner.PartnerRating;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public abstract class User extends BaseEntity {

	private static final long serialVersionUID = 8628262968737538116L;

	@NotBlank
	@Column(name = "first_name")
	@Size(max = 40)
	private String firstName;

	@NotBlank
	@Column(name = "last_name")
	@Size(max = 40)
	private String lastName;

	@NotBlank
	@Column(name = "username")
	@Size(max = 15)
	private String username;

	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@NotBlank
	@NaturalId
	@Size(max = 40)
	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "score")
	private float score;

	@Column(name = "birthday")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;

	@ElementCollection
	@CollectionTable(name = "favoris", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "favoris")
	private List<String> favoris;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private String info;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private UserAddress address;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Event> events;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Participant> participants;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Like> likes;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Rating> ratings;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PartnerRating> partnerRatings;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Notification> notifications;

	@JsonIgnore
	@ManyToMany(mappedBy = "follow", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<User> follows;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> follow;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "conversation_user", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "conversation_id") })
	private Set<Conversation> conversations = new HashSet<>();

	public User(String firstName, String lastName, String username, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

}
