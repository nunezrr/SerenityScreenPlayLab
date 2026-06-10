package com.parabank.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Step;

/**
 * Task: abre la página de login de ParaBank.
 *
 * La URL base se configura en serenity.conf → environments.default.base.url
 * Open.url() la toma automáticamente cuando se usa una ruta relativa.
 * Aquí usamos la URL completa para garantizar el parámetro ConnType=JDBC.
 */
public class AbrirParaBank implements Task {

    private static final String URL =
            "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC";

    @Override
    @Step("{0} abre la página de login de ParaBank")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(URL));
    }

    public static AbrirParaBank loginPage() {
        return new AbrirParaBank();
    }
}
