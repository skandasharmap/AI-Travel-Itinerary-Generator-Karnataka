package Travel_planApi.PlanApi.Entity;

import Travel_planApi.PlanApi.Repository.TripPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripPlaceService {

    @Autowired
    private TripPlaceRepository tripPlaceRepository;

    public TripPlace saveTripPlace(TripPlace tripPlace) {
        return tripPlaceRepository.save(tripPlace);
    }

    public List<TripPlace> getAllTripPlaces() {
        return tripPlaceRepository.findAll();
    }

    public void deleteTripPlace(Long id) {
        tripPlaceRepository.deleteById(id);
    }
}
