package tn.esprit.service.evaluation;

import org.springframework.http.ResponseEntity;

import tn.esprit.exception.UnauthorizedException;
import tn.esprit.model.evaluation.Badge;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.PagedResponse;
import tn.esprit.security.UserPrincipal;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
public interface BadgeService {

	PagedResponse<Badge> getAllBadges(int page, int size);

	ResponseEntity<Badge> getBadge(Long id);

	ResponseEntity<Badge> addBadge(Badge badge, UserPrincipal currentUser);

	ResponseEntity<Badge> updateBadge(Long id, Badge newBadge, UserPrincipal currentUser)
			throws UnauthorizedException;

	ResponseEntity<ApiResponse> deleteBadge(Long id, UserPrincipal currentUser) throws UnauthorizedException;
	
	ResponseEntity<ApiResponse> appendBadgeToUser(Long id, User user) throws UnauthorizedException;
}
