package com.parabank.tasks;

import com.parabank.ui.CustomerLookupPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/*
 * Task: Completa el formulario de "Forgot login info?" para recuperar el username/password
 *
    * Este Task se puede reutilizar para probar tanto la recuperación de username como de password
 */
public class Customerlookup implements Task {

    // Datos fijos del perfil — solo username/password son únicos por ejecución
    private static final String FIRST_NAME    = "Test";
    private static final String LAST_NAME     = "Automation";
    private static final String ADDRESS       = "123 Test Street";
    private static final String CITY          = "Springfield";
    private static final String STATE         = "IL";
    private static final String ZIP_CODE      = "62701";
    private static final String SSN           = "123-45-6789";

    @Override
    @Step("{0} Completa el formulario de recuperación de login info en ParaBank")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(

                Enter.theValue(FIRST_NAME).into(CustomerLookupPage.FIRST_NAME),
                Enter.theValue(LAST_NAME).into(CustomerLookupPage.LAST_NAME),
                Enter.theValue(ADDRESS).into(CustomerLookupPage.ADDRESS),
                Enter.theValue(CITY).into(CustomerLookupPage.CITY),
                Enter.theValue(STATE).into(CustomerLookupPage.STATE),
                Enter.theValue(ZIP_CODE).into(CustomerLookupPage.ZIP_CODE),
                Enter.theValue(SSN).into(CustomerLookupPage.SSN),

                Click.on(CustomerLookupPage.FIND_BUTTON),
                WaitUntil.the(CustomerLookupPage.SUCCESS_HEADER, isVisible()).forNoMoreThan(10).seconds()
        );
    }
    public static Customerlookup datoPersonales() {
        return new Customerlookup();
    }
}
