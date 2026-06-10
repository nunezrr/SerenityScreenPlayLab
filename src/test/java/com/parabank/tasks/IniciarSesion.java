package com.parabank.tasks;

import com.parabank.ui.DashboardPage;
import com.parabank.ui.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: ingresa las credenciales, hace clic en Login y espera a que el
 * dashboard esté completamente cargado (menú AngularJS incluido).
 *
 * ParaBank usa AngularJS para renderizar el menú lateral después del login.
 * El WaitUntil sobre ACCOUNT_OVERVIEW_HEADER garantiza que el DOM
 * esté listo antes de que los siguientes steps interactúen con el menú.
 */
public class IniciarSesion implements Task {

    private final String usuario;
    private final String contrasena;

    public IniciarSesion(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    @Override
    @Step("{0} inicia sesión con usuario '#usuario'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(usuario).into(LoginPage.USERNAME_FIELD),
                Enter.theValue(contrasena).into(LoginPage.PASSWORD_FIELD),
                Click.on(LoginPage.LOGIN_BUTTON),
                // Esperar que el dashboard esté completamente cargado
                WaitUntil.the(DashboardPage.ACCOUNT_OVERVIEW_HEADER, isVisible())
                         .forNoMoreThan(15).seconds()
        );
    }

    public static IniciarSesion conCredenciales(String usuario, String contrasena) {
        return new IniciarSesion(usuario, contrasena);
    }
}
