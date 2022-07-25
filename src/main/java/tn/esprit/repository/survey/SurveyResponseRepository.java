package tn.esprit.repository.survey;

import java.util.List;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.model.survey.SurveyResponse;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

	@Query(value = "SELECT s FROM SurveyResponse s where s.survey.id = ?1 and s.user.id = ?2")
	SurveyResponse findByUserAndSurvey(String surveyId, Long userId);

	@Query(value = "SELECT s FROM SurveyResponse s where s.id = ?1 and s.survey.id = ?2")
	SurveyResponse findByIdAndSurvey(Long id, String surveyId);

	@Query(value = "SELECT s FROM SurveyResponse s where s.survey.id = ?1")
	List<SurveyResponse> findBySurvey(String surveyId);
}
