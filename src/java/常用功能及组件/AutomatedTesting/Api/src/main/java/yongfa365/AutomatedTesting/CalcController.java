package yongfa365.AutomatedTesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {

    @Autowired
    CalcService calcService;

    @GetMapping("/calc/add/{a}/{b}")
    public Integer add(@PathVariable Integer a, @PathVariable Integer b) {
        return calcService.add(a, b);
    }

    @GetMapping("/calc/divide/{a}/{b}")
    public Integer divide(@PathVariable Integer a, @PathVariable Integer b) {
        return calcService.divide(a, b);
    }
}
