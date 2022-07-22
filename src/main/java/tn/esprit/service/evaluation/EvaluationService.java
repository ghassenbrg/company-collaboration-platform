package tn.esprit.service.evaluation;

import org.springframework.http.ResponseEntity;

import tn.esprit.exception.UnauthorizedException;
import tn.esprit.model.evaluation.Evaluation;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.EvaluationDTO;
import tn.esprit.payload.PagedResponse;
import tn.esprit.security.UserPrincipal;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
public interface EvaluationService {

	PagedResponse<Evaluation> getAllEvaluations(int page, int size);

	ResponseEntity<Evaluation> getEvaluation(Long id);

	ResponseEntity<ApiResponse> deleteEvaluation(Long id, UserPrincipal currentUser) throws UnauthorizedException;

	ResponseEntity<ApiResponse> addEvaluation(EvaluationDTO evaluationDTO, Long userIdToBeEvaluated,
			UserPrincipal currentUser) throws UnauthorizedException;

	ResponseEntity<ApiResponse> updateEvaluation(Long id, EvaluationDTO evaluationDTO, Long userIdEvaluated,
			UserPrincipal currentUser) throws UnauthorizedException;

}
