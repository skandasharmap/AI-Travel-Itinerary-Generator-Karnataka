package Travel_planApi.PlanApi.Controller;

import Travel_planApi.PlanApi.Entity.Adminlogin;
import Travel_planApi.PlanApi.Repository.AdminLogrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminLog {

    @Autowired
    private AdminLogrepo adminLogrepo;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Adminlogin adminlogin) {
        Adminlogin admin = adminLogrepo.findByUsername(adminlogin.getUsername());

        if (admin != null && admin.getPassword().equals(adminlogin.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Adminlogin adminlogin) {
        adminLogrepo.save(adminlogin);
        return ResponseEntity.ok("Admin added successfully");
    }
    @GetMapping("/getdata")
    public ResponseEntity<List<Adminlogin>> getAllItineraries() {
        List<Adminlogin> adminLogins = adminLogrepo.findAll();
        return ResponseEntity.ok(adminLogins);
    }
}