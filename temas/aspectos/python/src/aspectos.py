import aspectlib                    # Importar la librería de aspectos
import logging                      # Importar logging para registrar eventos
import time                         # Importar time para medir el tiempo de ejecución

# Configuración básica de logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class AspectosCalculadora:
    """
    Clase que contiene los aspectos a aplicar a la Calculadora
    """
    
    @staticmethod
    @aspectlib.Aspect                                                                   # Definir un aspecto para loggear antes y después de la ejecución
    def log_antes_despues(cutpoint, *args, **kwargs):
        """Aspecto para loggear antes y después de ejecutar un método"""
        func_name = getattr(cutpoint, '__name__', str(cutpoint))                        # Obtener el nombre de la función
        logger.info(f"Antes de ejecutar: {func_name}, args={args}, kwargs={kwargs}")
        result = yield aspectlib.Proceed                                                # Continuar con la ejecución del método
        logger.info(f"Después de ejecutar: {func_name}")
        return result

    @staticmethod
    @aspectlib.Aspect                                                                   # Definir un aspecto para manejar los errores en los métodos
    def manejo_errores(cutpoint, *args, **kwargs):
        func_name = getattr(cutpoint, '__name__', str(cutpoint))                        # Obtener el nombre de la función
        try:
            result = yield aspectlib.Proceed                                            # Continuar con la ejecución del método
            return result
        except Exception as e:                                                          # Si ocurre un error, loguearlo
            logger.error(f"Error en {func_name}: {e}")
            raise                                                                       # Propagar el error

    @staticmethod
    @aspectlib.Aspect                                                                   # Definir un aspecto para medir el tiempo de ejecución
    def tiempo_ejecucion(cutpoint, *args, **kwargs):
        func_name = getattr(cutpoint, '__name__', str(cutpoint))                        # Obtener el nombre de la función
        inicio = time.time()                                                            # Marcar el inicio del tiempo
        result = yield aspectlib.Proceed                                                # Continuar con la ejecución del método
        fin = time.time()                                                               # Marcar el final del tiempo
        logger.info(f"{func_name} ejecutado en {fin - inicio:.4f} segundos")            # Loguear el tiempo de ejecución
        return result

def aplicar_aspectos():
    # Importar la clase Calculadora
    from operaciones import Calculadora                     

    aspectos = [
        AspectosCalculadora.manejo_errores,     # Aspecto para manejar errores
        AspectosCalculadora.tiempo_ejecucion,   # Aspecto para medir el tiempo de ejecución
        AspectosCalculadora.log_antes_despues   # Aspecto para loggear antes y después
    ]

    # Aplicar los aspectos a los métodos de la clase Calculadora
    for metodo in ['suma', 'resta', 'multiplicacion', 'division']:
        original_func = getattr(Calculadora, metodo)                                    # Obtener la función original
        
        # Aplicar los aspectos en orden (anidándolos como decoradores)
        for aspecto in reversed(aspectos):
            original_func = aspecto(original_func)
        setattr(Calculadora, metodo, original_func)                                     # Reemplazar la función original con la decorada
