package tn.esprit.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.model.evaluation.Badge;
/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long>  {

	@Query(value = "SELECT"
			+ "            CASE WHEN EXISTS ("
			+ "                SELECT *"
			+ "                FROM employee_badges"
			+ "                WHERE employee_id = :userId AND badge_id= :badgeId"
			+ "            )"
			+ "            THEN 'true'"
			+ "            ELSE 'false'"
			+ "            END", nativeQuery = true)
	boolean checkIfUserHasBadge(Long userId, Long badgeId);
}
