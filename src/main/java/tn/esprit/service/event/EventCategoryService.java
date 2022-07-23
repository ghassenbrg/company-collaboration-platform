package tn.esprit.service.event;

import tn.esprit.model.event.Event;
import tn.esprit.payload.dto.CategoryDTO;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface EventCategoryService {
	
	void addEventCategory(UserPrincipal currentUser, CategoryDTO categoryInput, Event event);
	
}
