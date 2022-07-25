package tn.esprit.service.impl.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.AccessDeniedException;
import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.user.Conversation;
import tn.esprit.model.user.Message;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.repository.user.ConversationsRepository;
import tn.esprit.repository.user.MessageRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.user.MessageService;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@Service
public class MessagingServiceImpl implements MessageService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	ConversationsRepository conversationsRepository;

	private static final int CONVERSATION_LIMIT = 3;

	@Override
	public Set<Conversation> fetchUsersConversations(UserPrincipal currentUser, int index) {
		User user = userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getUsername()));
		return user.getConversations().stream().sorted((m1, m2) -> m2.getCreatedDate().compareTo(m1.getCreatedDate()))
				.skip((long) index * CONVERSATION_LIMIT).limit(CONVERSATION_LIMIT).collect(Collectors.toSet());
	}

	@Override
	public Conversation createNewConversation(Long authorId, String title, List<String> members) {
		Conversation newConversation = new Conversation(title.isEmpty() ? "Unnamed" : title);
		newConversation.setOwnerId(authorId);
		User currentUser = userRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", authorId));
		for (String memberName : members) {
			User member = userRepository.findByUsername(memberName)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", memberName));
			newConversation.getUsers().add(member);
			member.getConversations().add(newConversation);
		}
		userRepository.save(currentUser);
		for (Iterator<Conversation> it = currentUser.getConversations().iterator(); it.hasNext();) {
			Conversation conv = it.next();
			if (conv.getTitle().equals(newConversation.getTitle())
					&& conv.getCreatedDate().equals(newConversation.getCreatedDate())) {
				newConversation.setId(conv.getId());
				break;
			}

		}
		return newConversation;
	}

	@Override
	public Message addNewMessageToConversation(Long convId, String text, Long authorId) {
		Message newMessage = new Message();
		newMessage.setText(text);
		newMessage.setPostedBy(authorId);
		Conversation existingConversation = getExistingConversation(convId);
		newMessage.setConversation(existingConversation);
		messageRepository.save(newMessage);
		return newMessage;
	}

	@Override
	public List<Message> fetchConversationMessages(UserPrincipal userPrincipal, Long conversationId, int index,
			int limit) {
		User currentUser = userRepository.findByUsername(userPrincipal.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getUsername()));
		if (!currentUser.getConversations().stream().anyMatch(c -> c.getId().equals(conversationId))) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
					"You don't have permission to fech User Conversations of: " + conversationId);
			throw new AccessDeniedException(apiResponse);
		}
		Conversation existingConversation = getExistingConversation(conversationId);
		if (existingConversation != null) {
			return existingConversation.getMessages().stream()
					.sorted((m1, m2) -> m2.getCreatedDate().compareTo(m1.getCreatedDate())).skip(index) // skips first n
					.limit(limit).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<String> fetchConversationMembers(Long conversationId) {
		Conversation existingConversation = getExistingConversation(conversationId);
		if (existingConversation != null) {
			return existingConversation.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public Conversation getExistingConversation(Long conversationId) {
		return conversationsRepository.findById(conversationId)
				.orElseThrow(() -> new ResourceNotFoundException("Conversation", "id", conversationId));
	}

	@Override
	public ApiResponse deleteConversation(Long id, User author) {

		Conversation existingConversation = getExistingConversation(id);
		if (!(author.getId().equals(existingConversation.getOwnerId()))) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
					"You don't have permission to delete convertion named: " + existingConversation.getTitle());
			throw new AccessDeniedException(apiResponse);
		}
		conversationsRepository.delete(existingConversation);
		for (User user : existingConversation.getUsers()) {
			user.getConversations().remove(existingConversation);
		}

		return new ApiResponse(Boolean.TRUE,
				"You successfully deleted Conversation named : " + existingConversation.getTitle());
	}

	@Override
	public Conversation removeConversationMember(Long conversationId, User user) {
		Conversation existingConversation = getExistingConversation(conversationId);
		if (!(user.getId().equals(existingConversation.getOwnerId()))) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
					"You don't have permission to delete convertion named: " + existingConversation.getTitle());
			throw new AccessDeniedException(apiResponse);
		}
		if (existingConversation != null) {
			existingConversation.getUsers().removeIf(u -> u.getId().equals(user.getId()));
			user.getConversations().removeIf(c -> c.getId().equals(conversationId));
			userRepository.save(user);
		}
		return existingConversation;
	}

	@Override
	public Conversation addMemberToConversation(Long conversationId, List<String> members) {
		Conversation conversation = conversationsRepository.findById(conversationId)
				.orElseThrow(() -> new ResourceNotFoundException("Conversation", "id", conversationId));
		if (conversation != null) {
			conversation.getMessages().size();
			for (String memberName : members) {
				User member = userRepository.findByUsername(memberName)
						.orElseThrow(() -> new ResourceNotFoundException("User", "id", memberName));
				if (member != null) {
					conversation.getUsers().add(member);
					member.getConversations().add(conversation);
				}
			}
			conversationsRepository.save(conversation);
		}
		return conversation;
	}

}
