package Travel_planApi.PlanApi.Repository;


import Travel_planApi.PlanApi.Entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaceRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findByCategory(String category);
}
