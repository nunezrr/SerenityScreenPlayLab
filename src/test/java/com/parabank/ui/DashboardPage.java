package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DashboardPage {

    public static final Target ACCOUNT_OVERVIEW_HEADER =
            Target.the("encabezado de la cuenta").located(By.cssSelector("h1.title"));

    public static final Target WELCOME_MESSAGE =
            Target.the("mensaje de bienvenida").located(By.cssSelector("#leftPanel p.smallText"));

    public static final Target TRANSFER_FUNDS_LINK =
            Target.the("enlace Transfer Funds").located(By.linkText("Transfer Funds"));

    public static final Target ACCOUNT_ACTIVITY_LINK =
            Target.the("enlace Account Activity").located(By.linkText("Account Activity"));

    public static final Target ACCOUNT_LIST =
            Target.the("listado de cuentas").located(By.cssSelector("#accountTable tbody tr td:first-child a"));

    public static final Target FIRST_ACCOUNT_LINK =
            Target.the("primera cuenta del listado").located(By.cssSelector("#accountTable tbody tr:first-child td:first-child a"));

    public static final Target LOGOUT_LINK =
            Target.the("enlace logout").located(By.linkText("Log Out"));
}
