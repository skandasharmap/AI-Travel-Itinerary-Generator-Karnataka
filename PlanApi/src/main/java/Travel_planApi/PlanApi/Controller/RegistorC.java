package Travel_planApi.PlanApi.Controller;

import Travel_planApi.PlanApi.Entity.Registor;
import Travel_planApi.PlanApi.Repository.RegisterInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class RegistorC {

    @Autowired

    private RegisterInter reg;
    @GetMapping("/all")
    public List<Registor> getall() {
        return reg.findAll();
    }

    @PostMapping("/PostR")
    public String push(@RequestBody Registor obj){
        reg.save(obj);
        return"Data Posted";
    }

}
