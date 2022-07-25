package tn.esprit.service.user;

import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.InfoRequest;
import tn.esprit.payload.UserIdentityAvailability;
import tn.esprit.payload.UserProfile;
import tn.esprit.payload.UserSummary;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface UserService {

	UserSummary getCurrentUser(UserPrincipal currentUser);
	
	User getCurrentUserEntity(UserPrincipal currentUser);
	
	User getUserByName(String username);

	UserIdentityAvailability checkUsernameAvailability(String username);

	UserIdentityAvailability checkEmailAvailability(String email);

	UserProfile getUserProfile(String username);

	User addUser(User user);

	User updateUser(User newUser, String username, UserPrincipal currentUser);

	ApiResponse deleteUser(String username, UserPrincipal currentUser);

	ApiResponse giveAdmin(String username);

	ApiResponse removeAdmin(String username);

	UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);
}
