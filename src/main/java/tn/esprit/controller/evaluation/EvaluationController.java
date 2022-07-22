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
import tn.esprit.model.evaluation.Evaluation;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.EvaluationDTO;
import tn.esprit.payload.PagedResponse;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.evaluation.EvaluationService;
import tn.esprit.utils.Utils;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public PagedResponse<Evaluation> getAllEvaluations(
			@RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) Integer size) {
		return evaluationService.getAllEvaluations(page, size);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Evaluation> getEvaluation(@PathVariable(name = "id") Long id) {
		return evaluationService.getEvaluation(id);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteBadge(@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return evaluationService.deleteEvaluation(id, currentUser);
	}

	@PostMapping("/{userIdEvaluated}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateEvaluation(@PathVariable(name = "userIdEvaluated") Long userIdEvaluated,
			@Valid @RequestBody EvaluationDTO evaluationDTO, @CurrentUser UserPrincipal currentUser)
			throws UnauthorizedException {
		return evaluationService.addEvaluation(evaluationDTO, userIdEvaluated, currentUser);
	}

	@PutMapping("/{idEvaluation}/{userIdEvaluated}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateEvaluation(@PathVariable(name = "idEvaluation") Long idEvaluation,
			@PathVariable(name = "userIdEvaluated") Long userIdEvaluated,
			@Valid @RequestBody EvaluationDTO evaluationDTO, @CurrentUser UserPrincipal currentUser)
			throws UnauthorizedException {
		return evaluationService.updateEvaluation(idEvaluation, evaluationDTO, userIdEvaluated, currentUser);
	}

}
