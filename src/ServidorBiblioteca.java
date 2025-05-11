
import Biblioteca.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import java.util.*;

public class ServidorBiblioteca {
    // Clase que implementa la interfaz CORBA generada
    static class GestionBibliotecaImpl extends GestionBibliotecaPOA {
        private ORB orb;
        private Map<String, String> libros = new HashMap<>();

        public void setORB(ORB orb_val) {
            orb = orb_val;
        }

        // Metodo para agregar un libro a la biblioteca
        public void agregarLibro(String titulo, String autor) {
            libros.put(titulo, autor);
            System.out.println("Libro agregado: " + titulo + " de " + autor);
        }

        // Metodo para obtener el autor de un libro por su título
        public String obtenerLibro(String titulo) {
            if (libros.containsKey(titulo)) {
                return "Autor: " + libros.get(titulo);
            } else {
                return "Libro no encontrado.";
            }
        }

        // Metodo nuevo: buscar todos los libros de un autor
        public String buscarPorAutor(String autor) {
            List<String> encontrados = new ArrayList<>();
            for (Map.Entry<String, String> entrada : libros.entrySet()) {
                if (entrada.getValue().equalsIgnoreCase(autor)) {
                    encontrados.add(entrada.getKey());
                }
            }
            return encontrados.isEmpty() ? "No hay libros de ese autor." : "Libros: " + String.join(", ", encontrados);
        }

        // Eliminar un libro de la biblioteca por su titulo
        public void eliminarLibro(String titulo) {
            if (libros.containsKey(titulo)) {
                libros.remove(titulo);
                System.out.println("Libro eliminado: " + titulo);
            } else {
                System.out.println("El libro no existe: " + titulo);
            }
        }

    }

    public static void main(String args[]) {
        try {
            // Crear e inicializar el ORB para gestionar las comunicaciones CORBA
            ORB orb = ORB.init(args, null);
            // Obtener y activar el POA
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Crear la implementación del servidor
            GestionBibliotecaImpl bibliotecaImpl = new GestionBibliotecaImpl();
            bibliotecaImpl.setORB(orb);

            // Obtener la referencia del objeto
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bibliotecaImpl);
            GestionBiblioteca href = GestionBibliotecaHelper.narrow(ref);

            // Registrar el objeto en el Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String nombre = "GestionBiblioteca";
            NameComponent path[] = ncRef.to_name(nombre);
            ncRef.rebind(path, href);

            System.out.println("Servidor listo y esperando conexiones...");

            // Mantener al servidor corriendo
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("Servidor cerrado.");
    }
}

