package Travel_planApi.PlanApi.Repository;

import Travel_planApi.PlanApi.Entity.UserMood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMoodRepository extends JpaRepository<UserMood, Long> {
    List<UserMood> findByMood(String mood);
}
