# language: es
Característica: Solicitud de préstamo

  Antecedentes:
    Dado el usuario se encuentra en la página de login de ParaBank
    Cuando ingresa el usuario "admin" y la contraseña "admin"
    Entonces el sistema muestra el dashboard de la cuenta

  @prestamo
  Escenario: Solicitar un préstamo exitosamente
    Cuando el usuario navega a la sección de préstamos
    Y ingresa el monto del préstamo "1000"
    Y ingresa el pago inicial "100"
    Y confirma la solicitud del préstamo
    Entonces el sistema muestra la aprobación del préstamo