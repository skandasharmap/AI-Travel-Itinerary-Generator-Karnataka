package Travel_planApi.PlanApi.Entity;



import Travel_planApi.PlanApi.Repository.UserMoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMoodService {

    @Autowired
    private UserMoodRepository userMoodRepository;

    public List<String> getRecommendations(String mood) {
        List<String> recommendedPlaces = new ArrayList<>();

        switch (mood.toLowerCase()) {
            case "happy":
                recommendedPlaces.add("Goa Beach");
                recommendedPlaces.add("Manali Hills");
                break;
            case "adventurous":
                recommendedPlaces.add("Rishikesh River Rafting");
                recommendedPlaces.add("Ladakh Bike Trip");
                break;
            case "relaxed":
                recommendedPlaces.add("Kerala Backwaters");
                recommendedPlaces.add("Coorg Coffee Plantations");
                break;
            default:
                recommendedPlaces.add("No specific recommendations. Explore freely!");
        }
        return recommendedPlaces;
    }
}