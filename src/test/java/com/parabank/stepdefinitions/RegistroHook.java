package com.parabank.stepdefinitions;

import com.parabank.model.CredencialesUsuario;
import com.parabank.tasks.AbrirNuevaCuenta;
import com.parabank.tasks.RegistrarNuevoUsuario;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Hook de setup que prepara el entorno ANTES de ejecutar los escenarios:
 *
 *   1. Registra un usuario nuevo con datos generados dinámicamente.
 *      ParaBank hace login automático tras el registro exitoso.
 *   2. Con la sesión activa, abre una cuenta bancaria CHECKING.
 *      Sin cuenta bancaria el menú lateral no muestra Transfer Funds
 *      ni Account Activity.
 *   3. Guarda las credenciales en CredencialesUsuario (singleton)
 *      para reutilizarlas en todos los escenarios.
 *
 * Solo se ejecuta UNA VEZ por suite — si las credenciales ya están
 * inicializadas, el hook no hace nada.
 *
 * order = 1 garantiza que corre ANTES del CucumberHooks (order = 2).
 */
public class RegistroHook {

    @Before(order = 1)
    public void prepararEntorno() {
        if (CredencialesUsuario.estaInicializado()) {
            return; // Ya configurado — no repetir para los demás escenarios
        }

        System.out.println("🔧 [Setup] Iniciando preparación del entorno...");

        // Inicializar stage para el actor de setup
        OnStage.setTheStage(new OnlineCast());
        net.serenitybdd.screenplay.Actor setup = OnStage.theActorCalled("Setup");

        // Paso 1: Registrar usuario
        // RegistrarNuevoUsuario navega a register.htm, completa el formulario
        // y guarda las credenciales en CredencialesUsuario.
        // ParaBank hace login automático tras el registro exitoso, por lo que
        // el actor queda con sesión activa — NO se necesita un login separado.
        System.out.println("🔧 [Setup] Paso 1: Registrando usuario...");
        setup.attemptsTo(RegistrarNuevoUsuario.enParaBank());
        System.out.println("✅ [Setup] Usuario registrado: " + CredencialesUsuario.getUsuario());

        // Paso 2: Abrir cuenta bancaria CHECKING
        // Con la sesión activa del registro, navega a openaccount.htm
        // y crea la cuenta necesaria para que el menú muestre las opciones.
        System.out.println("🔧 [Setup] Paso 2: Abriendo cuenta CHECKING...");
        setup.attemptsTo(AbrirNuevaCuenta.tipoChecking());
        System.out.println("✅ [Setup] Entorno listo. Usuario: "
                + CredencialesUsuario.getUsuario());

        // Cerrar el Stage del setup — CucumberHooks lo reiniciará para cada escenario
        OnStage.drawTheCurtain();
    }
}
