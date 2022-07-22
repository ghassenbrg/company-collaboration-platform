package tn.esprit.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.evaluation.Evaluation;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

}
