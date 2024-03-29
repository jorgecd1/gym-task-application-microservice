package com.epam.gymtaskapplication.cucumberglue;

import com.epam.gymtaskapplication.GymTaskApplication;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@CucumberContextConfiguration
@SpringBootTest(classes = GymTaskApplication.class)
public class GlueContext {

    @Before
    public void beforeScenario() {
        // You can add any setup logic here that needs to be executed before each scenario
        System.out.println("Before scenario setup...");
    }

    @After
    public void afterScenario() {
        // You can add any teardown logic here that needs to be executed after each scenario
        System.out.println("After scenario teardown...");
    }
}
