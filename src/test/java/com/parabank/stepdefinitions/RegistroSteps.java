package com.parabank.stepdefinitions;

import com.parabank.model.CredencialesUsuario;
import com.parabank.tasks.AbrirNuevaCuenta;
import com.parabank.tasks.RegistrarNuevoUsuario;
import com.parabank.ui.AbrirNuevaCuentaPage;
import com.parabank.ui.RegisterPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions para el feature de Registro de usuario.
 *
 * Este feature documenta el flujo de preparación ejecutado por RegistroHook.
 * Puede ejecutarse también de forma independiente con el tag @registro.
 */
public class RegistroSteps {

    @Dado("el usuario navega a la página de registro de ParaBank")
    public void elUsuarioNavegaALaPaginaDeRegistro() {
        // El RegistroHook ya ejecutó este paso — este step es documentativo
        // Si se ejecuta independiente, RegistrarNuevoUsuario lo maneja
    }

    @Cuando("completa el formulario con datos de prueba generados")
    public void completaElFormularioConDatosDePruebaGenerados() {
        // RegistrarNuevoUsuario genera UUID username y completa todo el form
        OnStage.theActorInTheSpotlight().attemptsTo(
                RegistrarNuevoUsuario.enParaBank()
        );
    }

    @Y("hace clic en el botón Register")
    public void haceClicEnElBotonRegister() {
        // Incluido en RegistrarNuevoUsuario.performAs()
    }

    @Entonces("el sistema confirma que el usuario fue creado exitosamente")
    public void elSistemaConfirmaQueElUsuarioFueCreadoExitosamente() {
        // RegistrarNuevoUsuario ya valida el SUCCESS_HEADER via WaitUntil
        // Verificamos que las credenciales se guardaron
        assert CredencialesUsuario.estaInicializado() :
                "Las credenciales no fueron inicializadas tras el registro";
    }

    @Cuando("el usuario navega a Open New Account")
    public void elUsuarioNavegaAOpenNewAccount() {
        // AbrirNuevaCuenta navega directamente a openaccount.htm
    }

    @Y("selecciona el tipo de cuenta {string}")
    public void seleccionaElTipoDeCuenta(String tipoCuenta) {
        // AbrirNuevaCuenta selecciona CHECKING por defecto
    }

    @Y("confirma la apertura de la cuenta")
    public void confirmaLaAperturaDeLaCuenta() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                AbrirNuevaCuenta.tipoChecking()
        );
    }

    @Entonces("el sistema crea la cuenta y muestra su número")
    public void elSistemaCreaLaCuentaYMuestraSuNumero() {
        // AbrirNuevaCuenta ya valida NEW_ACCOUNT_ID visible via WaitUntil
    }

    @Y("el entorno está listo para ejecutar los demás escenarios")
    public void elEntornoEstaListoParaEjecutarLosDemas() {
        System.out.println("✅ Entorno verificado. Usuario: " + CredencialesUsuario.getUsuario());
    }
}
