package Travel_planApi.PlanApi.Controller;


import Travel_planApi.PlanApi.Entity.Itinerary;
import Travel_planApi.PlanApi.Repository.ItineraryRepo;

import Travel_planApi.PlanApi.Repository.PlaceRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mcq")
public class PlaceController {

    private final PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @GetMapping("/places")
    public ResponseEntity<List<Itinerary>> getPlacesByCategory(@RequestParam String category) {
        List<Itinerary> places = placeRepository.findByCategory(category);
        return ResponseEntity.ok(places);
    }
}
