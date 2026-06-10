package com.parabank.tasks;

import com.parabank.model.CredencialesUsuario;
import com.parabank.ui.RegisterPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import java.util.UUID;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: registra un nuevo usuario en ParaBank y guarda sus credenciales
 * en CredencialesUsuario para ser reutilizadas en todos los escenarios.
 *
 * Genera un username único usando UUID para evitar colisiones en
 * ejecuciones sucesivas contra la misma instancia de ParaBank.
 */
public class RegistrarNuevoUsuario implements Task {

    private static final String REGISTER_URL =
            "https://parabank.parasoft.com/parabank/register.htm";

    // Datos fijos del perfil — solo username/password son únicos por ejecución
    private static final String FIRST_NAME    = "Test";
    private static final String LAST_NAME     = "Automation";
    private static final String ADDRESS       = "123 Test Street";
    private static final String CITY          = "Springfield";
    private static final String STATE         = "IL";
    private static final String ZIP_CODE      = "62701";
    private static final String PHONE         = "5551234567";
    private static final String SSN           = "123-45-6789";
    private static final String PASSWORD      = "Test1234*";

    @Override
    @Step("{0} registra un nuevo usuario en ParaBank")
    public <T extends Actor> void performAs(T actor) {
        // Generar username único para esta ejecución
        String username = "user" + UUID.randomUUID().toString().substring(0, 8);

        actor.attemptsTo(
                Open.url(REGISTER_URL),
                WaitUntil.the(RegisterPage.FIRST_NAME, isVisible()).forNoMoreThan(10).seconds(),

                Enter.theValue(FIRST_NAME).into(RegisterPage.FIRST_NAME),
                Enter.theValue(LAST_NAME).into(RegisterPage.LAST_NAME),
                Enter.theValue(ADDRESS).into(RegisterPage.ADDRESS),
                Enter.theValue(CITY).into(RegisterPage.CITY),
                Enter.theValue(STATE).into(RegisterPage.STATE),
                Enter.theValue(ZIP_CODE).into(RegisterPage.ZIP_CODE),
                Enter.theValue(PHONE).into(RegisterPage.PHONE),
                Enter.theValue(SSN).into(RegisterPage.SSN),
                Enter.theValue(username).into(RegisterPage.USERNAME),
                Enter.theValue(PASSWORD).into(RegisterPage.PASSWORD),
                Enter.theValue(PASSWORD).into(RegisterPage.CONFIRM_PASSWORD),

                Click.on(RegisterPage.REGISTER_BUTTON),
                WaitUntil.the(RegisterPage.SUCCESS_HEADER, isVisible()).forNoMoreThan(10).seconds()
        );

        // Guardar credenciales para reutilizar en todos los escenarios
        CredencialesUsuario.guardar(username, PASSWORD);
        System.out.println("✅ Usuario registrado: " + username);
    }

    public static RegistrarNuevoUsuario enParaBank() {
        return new RegistrarNuevoUsuario();
    }
}
