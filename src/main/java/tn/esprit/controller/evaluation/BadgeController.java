package tn.esprit.controller.evaluation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.exception.UnauthorizedException;
import tn.esprit.model.evaluation.Badge;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.PagedResponse;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.evaluation.BadgeService;
import tn.esprit.utils.Utils;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@RestController
@RequestMapping("/badges")
public class BadgeController {

	@Autowired
	private BadgeService badgeService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public PagedResponse<Badge> getAllBadges(
			@RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) Integer size) {
		return badgeService.getAllBadges(page, size);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Badge> addBadge(@Valid @RequestBody Badge badge, @CurrentUser UserPrincipal currentUser) {

		return badgeService.addBadge(badge, currentUser);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Badge> getBadge(@PathVariable(name = "id") Long id) {
		return badgeService.getBadge(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Badge> updateBadge(@PathVariable(name = "id") Long id, @Valid @RequestBody Badge badge,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return badgeService.updateBadge(id, badge, currentUser);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteBadge(@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return badgeService.deleteBadge(id, currentUser);
	}
}
