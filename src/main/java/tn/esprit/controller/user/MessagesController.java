package tn.esprit.controller.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.user.Conversation;
import tn.esprit.model.user.Message;
import tn.esprit.model.user.User;
import tn.esprit.payload.ConversationDTO;
import tn.esprit.payload.ConversationListDTO;
import tn.esprit.payload.MessageDTO;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.user.MessageService;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate template;

	@GetMapping(value = "/getConversationMessages")
	public ResponseEntity<?> getMessages(@RequestParam("conversationId") Long conversationId,
			@RequestParam("index") int index, @CurrentUser UserPrincipal currentUser) {

		List<MessageDTO> messages = new ArrayList<>();
		for (Message mes : messageService.fetchConversationMessages(currentUser, conversationId, index, 20)) {
			messages.add(MessageDTO.builder().text(mes.getText()).authorId(mes.getPostedBy())
					.authorUsername(userRepository.findById(mes.getPostedBy())
							.orElseThrow(() -> new ResourceNotFoundException("User", "id", mes.getPostedBy()))
							.getUsername())
					.createdDate(mes.getCreatedDate()).build());
		}
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

	@RequestMapping("/getConversations")
	public ResponseEntity<?> getConversations(@CurrentUser UserPrincipal currentUser, @RequestParam("page") int page) {
		List<ConversationDTO> conversationDTOs = new ArrayList<>();
		for (Conversation conv : messageService.fetchUsersConversations(currentUser, page)) {
			ConversationDTO conversationDTO = ConversationDTO.builder().id(conv.getId()).title(conv.getTitle())
					.date(conv.getCreatedDate())
					.members(conv.getUsers().stream().map(User::getUsername).collect(Collectors.toList()))
					.ownerId(conv.getOwnerId()).build();
			conversationDTOs.add(conversationDTO);
		}
		ConversationListDTO conversationListDTO = new ConversationListDTO();
		conversationListDTO.setConversationDTO(conversationDTOs);
		conversationListDTO.setTotal(userRepository.findByUsername(currentUser.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()))
				.getConversations().size());
		return new ResponseEntity<>(conversationListDTO, HttpStatus.OK);
	}

	@MessageMapping("/messages/{convId}")
	public void addMessage(@DestinationVariable String convId, MessageDTO message) throws Exception {
		messageService.addNewMessageToConversation(Long.valueOf(convId), message.getText(), message.getAuthorId());
		this.template.convertAndSend("/topic/conversation/" + convId,
				MessageDTO.builder().text(message.getText()).authorId(message.getAuthorId())
						.authorUsername(userRepository.findById(message.getAuthorId())
								.orElseThrow(() -> new ResourceNotFoundException("User", "id", message.getAuthorId()))
								.getUsername())
						.createdDate(Instant.now()).messageType("RECEIVE_MESSAGE").build());
	}

	@MessageMapping("/src/{userId}")
	@SendTo("/topic/conversations")
	public ConversationDTO createConversation(@DestinationVariable String userId, ConversationDTO conv)
			throws Exception {
		Conversation newConversation;
		String eventType = "CONVERSATION_CREATED";
		if (conv.getId() != null) { // already existed conversation
			eventType = "MEMBER_ADDED";
			newConversation = messageService.addMemberToConversation(conv.getId(), conv.getMembers());
		} else {
			newConversation = messageService.createNewConversation(Long.valueOf(userId), conv.getTitle(),
					conv.getMembers());

		}
		return ConversationDTO.builder().id(newConversation.getId()).title(newConversation.getTitle())
				.date(newConversation.getCreatedDate())
				.members(newConversation.getUsers().stream().map(User::getUsername).collect(Collectors.toList()))
				.ownerId(Long.valueOf(userId)).messagesCount(newConversation.getMessages().size()).eventType(eventType)
				.build();
	}

	@MessageMapping("/src/delete/{userId}")
	@SendTo("/topic/conversations")
	public ConversationDTO deleteConversation(@DestinationVariable String userId, ConversationDTO conv)
			throws Exception {
		User user = userRepository.findById(Long.valueOf(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.valueOf(userId)));
		String eventType = "DELETE_CONVERSATION";
		Conversation existingConversation = messageService.getExistingConversation(conv.getId());
		if (!user.getId().equals(conv.getOwnerId())) { // if is not the owner
			existingConversation = messageService.removeConversationMember(conv.getId(), user); // conversation
			eventType = "LEAVE_CONVERSATION";
		} else {
			messageService.deleteConversation(conv.getId(), user); // delete entire conversation
		}
		return ConversationDTO.builder().id(conv.getId()).title(conv.getTitle()).ownerId(Long.valueOf(userId))
				.eventType(eventType)
				.members(existingConversation.getUsers().stream().map(User::getUsername).collect(Collectors.toList()))
				.build();
	}

	@MessageMapping("/messages/typing/{convId}")
	@SendTo("/topic/conversation/{convId}")
	public MessageDTO getTyping(@DestinationVariable String convId, MessageDTO message) {
		return MessageDTO.builder().authorId(message.getAuthorId())
				.authorUsername(userRepository.findById(message.getAuthorId())
						.orElseThrow(() -> new ResourceNotFoundException("User", "id", message.getAuthorId()))
						.getUsername())
				.messageType("TYPING").build();
	}

}
