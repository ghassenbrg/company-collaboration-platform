package tn.esprit.repository.survey;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.survey.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
