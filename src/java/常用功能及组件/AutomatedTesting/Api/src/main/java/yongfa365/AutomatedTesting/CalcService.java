package yongfa365.AutomatedTesting;

import org.springframework.stereotype.Service;


@Service
public class CalcService {

    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

}
