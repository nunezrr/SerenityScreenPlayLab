package com.parabank.tasks;

import com.parabank.ui.AbrirNuevaCuentaPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: abre una nueva cuenta CHECKING en ParaBank.
 *
 * Navega a openaccount.htm usando JavaScript (window.location) para
 * preservar la sesión activa sin destruir las cookies JSESSIONID.
 * Open.url() de Serenity reinicializa el driver y pierde la sesión.
 */
public class AbrirNuevaCuenta implements Task {

    @Override
    @Step("{0} abre una nueva cuenta CHECKING en ParaBank")
    public <T extends Actor> void performAs(T actor) {
        // Navegar sin perder la sesión usando JavaScript
        actor.attemptsTo(navegarConJS("/parabank/openaccount.htm"));

        // Esperar que el formulario Angular cargue completamente
        actor.attemptsTo(
                WaitUntil.the(AbrirNuevaCuentaPage.ACCOUNT_TYPE_SELECT, isVisible())
                         .forNoMoreThan(15).seconds(),

                // Seleccionar CHECKING (valor "0")
                SelectFromOptions.byValue("0").from(AbrirNuevaCuentaPage.ACCOUNT_TYPE_SELECT),

                // Clic en Open New Account
                Click.on(AbrirNuevaCuentaPage.OPEN_ACCOUNT_BUTTON),

                // Esperar confirmación
                WaitUntil.the(AbrirNuevaCuentaPage.NEW_ACCOUNT_ID, isVisible())
                         .forNoMoreThan(15).seconds()
        );
    }

    /**
     * Navega a una ruta relativa usando JavaScript window.location
     * para preservar la sesión HTTP (cookies JSESSIONID).
     */
    private Interaction navegarConJS(String path) {
        return Interaction.where("{0} navega a " + path, actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            ((JavascriptExecutor) driver).executeScript(
                    "window.location.href = arguments[0];", path);
            // Esperar que la página cambie
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        });
    }

    public static AbrirNuevaCuenta tipoChecking() {
        return new AbrirNuevaCuenta();
    }
}
