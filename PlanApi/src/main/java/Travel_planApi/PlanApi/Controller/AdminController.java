package Travel_planApi.PlanApi.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @GetMapping("/categories")
    public List<String> getTripTypes() {
        return Arrays.asList("Adventurous","Relaxed","Romantic","Cultural","Social & Fun","Spiritual","Entertainment");
    }

    @GetMapping("/tripTypes")
    public List<String> getCategories() {

        return Arrays.asList("Solo", "Family", "Couple", "Friends", "Backpacking");
    }

}
