package com.parabank.tasks;

import com.parabank.ui.TransferFundsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Task: selecciona la primera cuenta disponible como cuenta origen en el formulario
 * de transferencia de fondos.
 */
public class SeleccionarCuentaOrigen implements Task {

    @Override
    @Step("{0} selecciona la cuenta origen disponible")
    public <T extends Actor> void performAs(T actor) {
        List<WebElement> opciones = TransferFundsPage.FROM_ACCOUNT_SELECT
                .resolveFor(actor)
                .findElements(By.tagName("option"));

        String cuentaOrigen = opciones.get(0).getAttribute("value");

        actor.attemptsTo(
                SelectFromOptions.byValue(cuentaOrigen).from(TransferFundsPage.FROM_ACCOUNT_SELECT)
        );
    }

    public static SeleccionarCuentaOrigen disponible() {
        return new SeleccionarCuentaOrigen();
    }
}
