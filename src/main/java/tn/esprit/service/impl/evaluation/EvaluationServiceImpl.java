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
import tn.esprit.model.evaluation.Evaluation;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.EvaluationDTO;
import tn.esprit.payload.PagedResponse;
import tn.esprit.repository.evaluation.EvaluationRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.evaluation.EvaluationService;
import tn.esprit.utils.Utils;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public PagedResponse<Evaluation> getAllEvaluations(int page, int size) {
		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, Utils.CREATED_DATE);

		Page<Evaluation> evaluations = evaluationRepository.findAll(pageable);

		List<Evaluation> content = evaluations.getNumberOfElements() == 0 ? Collections.emptyList() : evaluations.getContent();

		return new PagedResponse<>(content, evaluations.getNumber(), evaluations.getSize(), evaluations.getTotalElements(),
				evaluations.getTotalPages(), evaluations.isLast());
	}

	@Override
	public ResponseEntity<Evaluation> getEvaluation(Long id) {
		Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evaluation", "id", id));
		return new ResponseEntity<>(evaluation, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> deleteEvaluation(Long id, UserPrincipal currentUser)
			throws UnauthorizedException {
		Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evaluation", "id", id));
		if (evaluation.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			evaluationRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted Evaluation"),
					HttpStatus.OK);
		}
		throw new UnauthorizedException("You don't have permission to delete this Evaluation");
	}

	@Override
	public ResponseEntity<ApiResponse> addEvaluation(EvaluationDTO evaluationDTO, Long userIdToBeEvaluated,
			UserPrincipal currentUser) throws UnauthorizedException {
		User userToBeEvaluated = userRepository.findById(userIdToBeEvaluated).orElseThrow(() -> new ResourceNotFoundException("User", "id", userIdToBeEvaluated));
		User evaluatedBy = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
		
		Evaluation evaluation = new Evaluation();
		evaluation.setEvaluatedBy(evaluatedBy);
		evaluation.setEvaluated(userToBeEvaluated);
		evaluation.setComment(evaluationDTO.getComment());
		evaluation.setRating(evaluationDTO.getRating());
		
		evaluationRepository.save(evaluation);
		
		return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully evaluated user : " + userToBeEvaluated.getUsername()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> updateEvaluation(Long id ,EvaluationDTO evaluationDTO, Long userIdToBeEvaluated,
			UserPrincipal currentUser) throws UnauthorizedException {
		User userToBeEvaluated = userRepository.findById(userIdToBeEvaluated).orElseThrow(() -> new ResourceNotFoundException("User", "id", userIdToBeEvaluated));
		User evaluatedBy = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
		
		Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evaluation", "id", id));

		if (evaluation.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			evaluation.setComment(evaluationDTO.getComment());
			evaluation.setRating(evaluationDTO.getRating());
			evaluationRepository.save(evaluation);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully update evaluation for user : " + userToBeEvaluated.getUsername()),
					HttpStatus.OK);
		}
		
		throw new UnauthorizedException("You don't have permission to update this Evaluation");
	}

}
