package Travel_planApi.PlanApi.Controller;


import Travel_planApi.PlanApi.Entity.Registor;
import Travel_planApi.PlanApi.Repository.RegisterInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UserLogin {
    @Autowired
    private RegisterInter repo;
    @PostMapping("/ulogin")
    public ResponseEntity<?> ulogin(@RequestBody Registor obj) {
        var hospital = repo.findByuseremail(obj.getUseremail()).orElseThrow(() -> new RuntimeException("user not found"));
        if (hospital.getPassword().equals(obj.getPassword())) {
            return new ResponseEntity<>(hospital, HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid Password");
        }
    }
}
