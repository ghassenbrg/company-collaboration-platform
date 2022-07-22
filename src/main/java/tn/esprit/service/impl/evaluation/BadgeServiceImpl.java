package tn.esprit.service.impl.evaluation;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.exception.UnauthorizedException;
import tn.esprit.model.evaluation.Badge;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.PagedResponse;
import tn.esprit.repository.evaluation.BadgeRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.evaluation.BadgeService;
import tn.esprit.utils.Utils;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Service
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	private BadgeRepository badgeRepository;

	@Override
	public PagedResponse<Badge> getAllBadges(int page, int size) {
		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

		Page<Badge> badges = badgeRepository.findAll(pageable);

		List<Badge> content = badges.getNumberOfElements() == 0 ? Collections.emptyList() : badges.getContent();

		return new PagedResponse<>(content, badges.getNumber(), badges.getSize(), badges.getTotalElements(),
				badges.getTotalPages(), badges.isLast());
	}

	@Override
	public ResponseEntity<Badge> getBadge(Long id) {
		Badge badge = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
		return new ResponseEntity<>(badge, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Badge> addBadge(Badge badge, UserPrincipal currentUser) {
		Badge newBadge = badgeRepository.save(badge);
		return new ResponseEntity<>(newBadge, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Badge> updateBadge(Long id, Badge newBadge, UserPrincipal currentUser)
			throws UnauthorizedException {
		Badge badge = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
		if (badge.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			badge.setName(newBadge.getName());
			Badge updatedBadge = badgeRepository.save(badge);
			return new ResponseEntity<>(updatedBadge, HttpStatus.OK);
		}

		throw new UnauthorizedException("You don't have permission to edit this category");
	}

	@Override
	public ResponseEntity<ApiResponse> deleteBadge(Long id, UserPrincipal currentUser) throws UnauthorizedException {
		Badge badge = badgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", "id", id));
		if (badge.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			badgeRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"),
					HttpStatus.OK);
		}
		throw new UnauthorizedException("You don't have permission to delete this category");
	}

	@Override
	public ResponseEntity<ApiResponse> appendBadgeToUser(Long id, User user) throws UnauthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

}
