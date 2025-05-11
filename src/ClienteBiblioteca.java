import Biblioteca.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class ClienteBiblioteca {
    public static void main(String args[]) {
        try {
            // Inicializamos el ORB para permitir la comunicación CORBA
            ORB orb = ORB.init(args, null);

            // Obtenemos una referencia al Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Buscamos el objeto remoto del servidor llamado "GestionBiblioteca"
            GestionBiblioteca biblioteca = GestionBibliotecaHelper.narrow(ncRef.resolve_str("GestionBiblioteca"));

            // Pruebas
            biblioteca.agregarLibro("1984", "George Orwell");
            biblioteca.agregarLibro("Rebelión en la granja", "George Orwell");
            biblioteca.agregarLibro("Cien años de soledad", "Gabriel García Márquez");
            biblioteca.eliminarLibro("1984");
            System.out.println(biblioteca.obtenerLibro("1984"));  // "Libro no encontrado."


            System.out.println(biblioteca.obtenerLibro("1984"));
            System.out.println(biblioteca.obtenerLibro("Don Quijote"));
            System.out.println(biblioteca.buscarPorAutor("George Orwell"));
            System.out.println(biblioteca.buscarPorAutor("Mario Vargas Llosa"));

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
