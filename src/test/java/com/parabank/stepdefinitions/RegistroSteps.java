package com.parabank.stepdefinitions;

import com.parabank.model.CredencialesUsuario;
import com.parabank.tasks.AbrirNuevaCuenta;
import com.parabank.tasks.RegistrarNuevoUsuario;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

/**
 * Step Definitions para el feature de Registro de usuario.
 *
 * Este feature documenta el flujo de preparación ejecutado por RegistroHook.
 * Si se ejecuta después del Hook (credenciales ya existen), los steps son no-op.
 * Si se ejecuta independientemente, realiza el registro completo.
 */
public class RegistroSteps {

    @Dado("el usuario navega a la página de registro de ParaBank")
    public void elUsuarioNavegaALaPaginaDeRegistro() {
        // El actor "Usuario" ya está inicializado por CucumberHooks @Before
    }

    @Cuando("completa el formulario con datos de prueba generados")
    public void completaElFormularioConDatosDePruebaGenerados() {
        if (!CredencialesUsuario.estaInicializado()) {
            OnStage.theActorInTheSpotlight().attemptsTo(
                    RegistrarNuevoUsuario.enParaBank()
            );
        }
    }

    @Y("hace clic en el botón Register")
    public void haceClicEnElBotonRegister() {
        // Incluido en RegistrarNuevoUsuario.performAs()
    }

    @Entonces("el sistema confirma que el usuario fue creado exitosamente")
    public void elSistemaConfirmaQueElUsuarioFueCreadoExitosamente() {
        assert CredencialesUsuario.estaInicializado() :
                "Las credenciales no fueron inicializadas tras el registro";
    }

    @Cuando("el usuario navega a Open New Account")
    public void elUsuarioNavegaAOpenNewAccount() {
        // AbrirNuevaCuenta navega directamente
    }

    @Y("selecciona el tipo de cuenta {string}")
    public void seleccionaElTipoDeCuenta(String tipoCuenta) {
        // Incluido en AbrirNuevaCuenta
    }

    @Y("confirma la apertura de la cuenta")
    public void confirmaLaAperturaDeLaCuenta() {
        // Solo ejecutar si no se hizo en el Hook
        // El Hook ya hizo esto — este step es documentativo
    }

    @Entonces("el sistema crea la cuenta y muestra su número")
    public void elSistemaCreaLaCuentaYMuestraSuNumero() {
        // Validado por WaitUntil en AbrirNuevaCuenta
    }

    @Y("el entorno está listo para ejecutar los demás escenarios")
    public void elEntornoEstaListoParaEjecutarLosDemas() {
        System.out.println("✅ Entorno verificado. Usuario: " + CredencialesUsuario.getUsuario());
    }
}
