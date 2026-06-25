package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Locators para la página de recupera.
 * URL: https://parabank.parasoft.com/parabank/lookup.htm
 *
 * El formulario crea una cuenta nueva y devuelve el username generado.
 * ParaBank permite elegir username y password libremente en el registro.
 */
public class CustomerLookupPage {

    // ── Enlace desde login ─────────────────────────────────────────────────
    public static final Target FORGOT_LINK =
            Target.the("enlace Forgot login info?").located(By.linkText("Forgot login info?"));

    // ── Campos del formulario ──────────────────────────────────────────────
    public static final Target FIRST_NAME =
            Target.the("campo First Name").located(By.id("firstName"));

    public static final Target LAST_NAME =
            Target.the("campo Last Name").located(By.id("lastName"));

    public static final Target ADDRESS =
            Target.the("campo Address").located(By.id("address.street"));

    public static final Target CITY =
            Target.the("campo City").located(By.id("address.city"));

    public static final Target STATE =
            Target.the("campo State").located(By.id("address.state"));

    public static final Target ZIP_CODE =
            Target.the("campo Zip Code").located(By.id("address.zipCode"));

    public static final Target SSN =
            Target.the("campo SSN").located(By.id("ssn"));


    // ── Botón de envío ─────────────────────────────────────────────────────
    public static final Target FIND_BUTTON =
            Target.the("botón Find My Login Info").located(By.cssSelector("input[value='Find My Login Info']"));

    // ── Mensaje de resultado ───────────────────────────────────────────────
    public static final Target RESULT_MESSAGE =
            Target.the("titulo de resultado").located(By.cssSelector("h1.title"));

    public static final Target SUCCESS_HEADER =
            Target.the("encabezado registro exitoso").located(By.cssSelector("h1.title"));            
    
}
