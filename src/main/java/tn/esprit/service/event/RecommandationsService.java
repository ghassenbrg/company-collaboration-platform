package tn.esprit.service.event;

import java.util.List;

import tn.esprit.payload.dto.RecommandedEmplyeeDTO;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface RecommandationsService {

	List<RecommandedEmplyeeDTO> findRecommandedEmployees(UserPrincipal currentUser);

}
