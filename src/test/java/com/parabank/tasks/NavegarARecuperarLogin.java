package com.parabank.tasks;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import com.parabank.ui.CustomerLookupPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

/**
 * Task: abre la página de Customer Lookup (Forgot Login Info).
 */

public class NavegarARecuperarLogin implements Task {

    private static final String LOOKUP_URL =
            "https://parabank.parasoft.com/parabank/lookup.htm";
            
    @Override
    @Step("{0} navega a la página de recuperación de login")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(LOOKUP_URL),
                WaitUntil.the(CustomerLookupPage.FIRST_NAME, isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }

    public static NavegarARecuperarLogin desdeLaPaginaPrincipal() {
        return new NavegarARecuperarLogin();
    }
}