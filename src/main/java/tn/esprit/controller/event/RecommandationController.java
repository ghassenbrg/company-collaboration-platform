package tn.esprit.controller.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.payload.dto.RecommandedEmplyeeDTO;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.RecommandationsService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/events")
public class RecommandationController {

	@Autowired
	private RecommandationsService recommandationsService;

	@GetMapping("/recommandations")
	public ResponseEntity<List<RecommandedEmplyeeDTO>> getRecommandedEmployees(@CurrentUser UserPrincipal currentUser) {
		recommandationsService.findRecommandedEmployees(currentUser);
		List<RecommandedEmplyeeDTO> RecommandedEmplyees = recommandationsService.findRecommandedEmployees(currentUser);
		return new ResponseEntity<>(RecommandedEmplyees, HttpStatus.OK);
	}

}
