package tn.esprit.service.user;

import java.util.List;
import java.util.Set;

import tn.esprit.model.user.Conversation;
import tn.esprit.model.user.Message;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface MessageService {

	Set<Conversation> fetchUsersConversations(UserPrincipal currentUser, int index);

	Conversation createNewConversation(Long authorId, String title, List<String> members);

	Message addNewMessageToConversation(Long conversationId, String text, Long authorId);

	List<Message> fetchConversationMessages(UserPrincipal userPrincipal, Long conversationId, int index, int limit);

	List<String> fetchConversationMembers(Long conversationId);

	Conversation getExistingConversation(Long conversationId);

	ApiResponse deleteConversation(Long conversationId, User author);

	Conversation removeConversationMember(Long conversationId, User user);

	Conversation addMemberToConversation(Long conversationId, List<String> members);

}
