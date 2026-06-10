package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Locators para la página Open New Account.
 * URL: /parabank/openaccount.htm
 *
 * ParaBank requiere que el usuario tenga al menos una cuenta para
 * que el menú lateral muestre las opciones Transfer Funds, etc.
 * Esta página permite crear la cuenta inicial tras el registro.
 */
public class AbrirNuevaCuentaPage {

    // ── Enlace en el menú ──────────────────────────────────────────────────
    public static final Target OPEN_ACCOUNT_LINK =
            Target.the("enlace Open New Account")
                  .located(By.linkText("Open New Account"));

    // ── Formulario ─────────────────────────────────────────────────────────
    public static final Target ACCOUNT_TYPE_SELECT =
            Target.the("select tipo de cuenta").located(By.id("type"));

    public static final Target FROM_ACCOUNT_SELECT =
            Target.the("select cuenta origen").located(By.id("fromAccountId"));

    public static final Target OPEN_ACCOUNT_BUTTON =
            Target.the("botón Open New Account")
                  .located(By.cssSelector("input[value='Open New Account']"));

    // ── Resultado ──────────────────────────────────────────────────────────
    public static final Target NEW_ACCOUNT_HEADER =
            Target.the("encabezado cuenta creada")
                  .located(By.cssSelector("h1.title"));

    public static final Target NEW_ACCOUNT_ID =
            Target.the("ID de la nueva cuenta").located(By.id("newAccountId"));
}
