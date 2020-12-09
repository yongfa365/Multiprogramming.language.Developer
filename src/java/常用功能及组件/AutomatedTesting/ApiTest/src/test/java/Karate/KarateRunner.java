package Karate;

import com.intuit.karate.junit5.Karate;

public class KarateRunner {
    @Karate.Test
    Karate testCalc() {
        return Karate.run("classpath:Calc.Karate.feature");
    }
}

