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
 * Task: selecciona la cuenta destino en el formulario de transferencia.
 * Elige una cuenta diferente a la primera (origen) cuando hay más de una disponible.
 */
public class SeleccionarCuentaDestino implements Task {

    @Override
    @Step("{0} selecciona la cuenta destino (diferente al origen)")
    public <T extends Actor> void performAs(T actor) {
        List<WebElement> opciones = TransferFundsPage.TO_ACCOUNT_SELECT
                .resolveFor(actor)
                .findElements(By.tagName("option"));

        String cuentaDestino = opciones.size() > 1
                ? opciones.get(1).getAttribute("value")
                : opciones.get(0).getAttribute("value");

        actor.attemptsTo(
                SelectFromOptions.byValue(cuentaDestino).from(TransferFundsPage.TO_ACCOUNT_SELECT)
        );
    }

    public static SeleccionarCuentaDestino diferenteAlOrigen() {
        return new SeleccionarCuentaDestino();
    }
}
