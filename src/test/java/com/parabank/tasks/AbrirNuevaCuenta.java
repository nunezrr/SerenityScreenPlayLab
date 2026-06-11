package com.parabank.tasks;

import com.parabank.ui.AbrirNuevaCuentaPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: abre una nueva cuenta CHECKING en ParaBank.
 *
 * Usa driver.navigate().to() para preservar la sesión HTTP (cookies JSESSIONID).
 * Serenity Open.url() e Interaction.where() causan problemas con ByteBuddy.
 */
public class AbrirNuevaCuenta implements Task {

    private static final String OPEN_ACCOUNT_URL =
            "https://parabank.parasoft.com/parabank/openaccount.htm";

    @Override
    @Step("{0} abre una nueva cuenta CHECKING en ParaBank")
    public <T extends Actor> void performAs(T actor) {
        // Navegar preservando la sesión
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.navigate().to(OPEN_ACCOUNT_URL);

        // Espera explícita para que la sesión se estabilice en ParaBank
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        // Esperar formulario Angular
        actor.attemptsTo(
                WaitUntil.the(AbrirNuevaCuentaPage.ACCOUNT_TYPE_SELECT, isVisible())
                         .forNoMoreThan(15).seconds(),
                SelectFromOptions.byValue("0").from(AbrirNuevaCuentaPage.ACCOUNT_TYPE_SELECT),
                Click.on(AbrirNuevaCuentaPage.OPEN_ACCOUNT_BUTTON),
                WaitUntil.the(AbrirNuevaCuentaPage.NEW_ACCOUNT_ID, isVisible())
                         .forNoMoreThan(15).seconds()
        );
    }

    public static AbrirNuevaCuenta tipoChecking() {
        return new AbrirNuevaCuenta();
    }
}
