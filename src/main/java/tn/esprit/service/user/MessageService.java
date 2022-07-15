package tn.esprit.service.user;

import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.MessageRequest;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface MessageService {

	ApiResponse sendMessage(UserPrincipal currentUser, MessageRequest messageRequest);
	
}
