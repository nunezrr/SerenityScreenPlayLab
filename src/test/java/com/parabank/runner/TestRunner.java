package com.parabank.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.parabank.stepdefinitions",
        plugin = {
                "pretty",
                "json:build/cucumber-reports/cucumber.json"
        }
)
public class TestRunner {
}
