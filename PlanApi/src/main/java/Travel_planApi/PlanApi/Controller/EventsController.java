package Travel_planApi.PlanApi.Controller;

import Travel_planApi.PlanApi.Entity.Events;
import Travel_planApi.PlanApi.Repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventsController {

    @Autowired
    private EventsRepository eventsRepository;

    @GetMapping("/getevent")
    public List<Events> getAllEvents() {
        return eventsRepository.findAll();
    }

    @GetMapping("/district/{id}")
    public ResponseEntity<Events> getEventById(@PathVariable Long id) {
        return eventsRepository.findById(id)
                .map(event -> ResponseEntity.ok(event))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody Events event) {
        Events saved = eventsRepository.save(event);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Events> updateEvent(@PathVariable Long id, @RequestBody Events updatedEvent) {
        return eventsRepository.findById(id)
                .map(event -> {
                    event.setDistrict(updatedEvent.getDistrict());
                    event.setDescription(updatedEvent.getDescription());
                    event.setLocationUrl(updatedEvent.getLocationUrl());
                    eventsRepository.save(event);
                    return ResponseEntity.ok(event);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        if (eventsRepository.existsById(id)) {
            eventsRepository.deleteById(id);
            return ResponseEntity.ok("Event deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bydistrict/{district}")
    public List<Events> getEventsByDistrict(@PathVariable String district) {
        return eventsRepository.findByDistrictIgnoreCase(district);
    }
}
