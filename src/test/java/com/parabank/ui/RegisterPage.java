package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Locators para la página de registro de nuevo usuario.
 * URL: /parabank/register.htm
 *
 * El formulario crea una cuenta nueva y devuelve el username generado.
 * ParaBank permite elegir username y password libremente en el registro.
 */
public class RegisterPage {

    // ── Enlace desde login ─────────────────────────────────────────────────
    public static final Target REGISTER_LINK =
            Target.the("enlace Register").located(By.linkText("Register"));

    // ── Campos del formulario ──────────────────────────────────────────────
    public static final Target FIRST_NAME =
            Target.the("campo First Name").located(By.id("customer.firstName"));

    public static final Target LAST_NAME =
            Target.the("campo Last Name").located(By.id("customer.lastName"));

    public static final Target ADDRESS =
            Target.the("campo Address").located(By.id("customer.address.street"));

    public static final Target CITY =
            Target.the("campo City").located(By.id("customer.address.city"));

    public static final Target STATE =
            Target.the("campo State").located(By.id("customer.address.state"));

    public static final Target ZIP_CODE =
            Target.the("campo Zip Code").located(By.id("customer.address.zipCode"));

    public static final Target PHONE =
            Target.the("campo Phone").located(By.id("customer.phoneNumber"));

    public static final Target SSN =
            Target.the("campo SSN").located(By.id("customer.ssn"));

    public static final Target USERNAME =
            Target.the("campo Username").located(By.id("customer.username"));

    public static final Target PASSWORD =
            Target.the("campo Password").located(By.id("customer.password"));

    public static final Target CONFIRM_PASSWORD =
            Target.the("campo Confirm").located(By.id("repeatedPassword"));

    // ── Botón de envío ─────────────────────────────────────────────────────
    public static final Target REGISTER_BUTTON =
            Target.the("botón Register").located(By.cssSelector("input[value='Register']"));

    // ── Mensaje de resultado ───────────────────────────────────────────────
    public static final Target SUCCESS_HEADER =
            Target.the("encabezado registro exitoso").located(By.cssSelector("h1.title"));

    public static final Target SUCCESS_MESSAGE =
            Target.the("párrafo registro exitoso")
                  .located(By.cssSelector("#rightPanel p"));

    public static final Target ERROR_MESSAGE =
            Target.the("mensaje de error").located(By.cssSelector(".error"));
}
