package Travel_planApi.PlanApi.Controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TripRecommendationController {

    @PostMapping("/classifyMood")
    public ResponseEntity<String> classifyMood(@RequestBody Map<String, String> answers) {
        System.out.println("Received request: " + answers); // Log request payload

        if (answers.isEmpty() || !answers.containsKey("tripWith") || !answers.containsKey("budget") ||
                !answers.containsKey("duration") || !answers.containsKey("activityPreference")) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        String mood = classifyUserMood(answers);
        return ResponseEntity.ok(mood);
    }


    private String classifyUserMood(Map<String, String> answers) {
        if ("Adventure".equalsIgnoreCase(answers.get("tripWith"))) {
            return "Exciting Adventure";
        } else if ("Relaxing".equalsIgnoreCase(answers.get("tripWith"))) {
            return "Peaceful Retreat";
        } else {
            return "Cultural Exploration";
        }
    }
}
