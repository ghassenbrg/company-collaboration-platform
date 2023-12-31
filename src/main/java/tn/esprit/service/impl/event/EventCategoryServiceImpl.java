package tn.esprit.service.impl.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.BadRequestException;
import tn.esprit.model.event.Category;
import tn.esprit.model.event.Event;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.CategoryDTO;
import tn.esprit.repository.event.EventCategoryRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventCategoryService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
public class EventCategoryServiceImpl implements EventCategoryService {

	@Autowired
	private EventCategoryRepository eventCategoryRepository;

	@Override
	public void addEventCategory(UserPrincipal currentUser, CategoryDTO categoryInput, Event event) {
		if (categoryInput.getId() != null) {
			Category category = eventCategoryRepository.findById(categoryInput.getId()).orElse(null);
			if (category == null) {
				ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Category does not exist");
				throw new BadRequestException(apiResponse);
			}
			category.getEvents().add(event);
			eventCategoryRepository.save(category);	
		}
	}



	

}
