package accesoxml;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
public class AccesoXML{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try { System.setOut(new PrintStream(System.out, true, "UTF-8"));} 
        catch (UnsupportedEncodingException e) {System.out.println(e);}
        int opc;
        String titulo,autor,fecha;
        accesoDOM a = new accesoDOM();
        File f = new File("Libros.xml");
        a.abrirXMLconDOM(f);
        /*
        a.insertarLibro("Libro1", "Autor1", "2020");
        a.recorrerDOM();
        a.elmininarLibro("Libro1");
        a.recorrerDOM();
        */
        do
        {
            System.out.println("Que quieres hacer con el XML:");
            System.out.println("    - 1 - Leer");
            System.out.println("    - 2 - Añadir");
            System.out.println("    - 3 - Eliminar");
            System.out.println("    - 4 - Guardar");
            System.out.println("    - 0 - Cerrar");
            opc = sc.nextInt();
            switch (opc)
            {
                case 1:
                    a.recorrerDOM();
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("Indica el titulo del libro: ");
                    titulo = sc.nextLine();
                    System.out.print("Indica el autor del libro: ");
                    autor = sc.nextLine();
                    System.out.print("Indica el año de salida del libro: ");
                    fecha = sc.nextLine();
                    a.insertarLibro(titulo, autor, fecha);
                    break;
                case 3:
                    sc.nextLine();
                    System.out.print("Escribe el titulo del libro que quieres borrar: ");
                    titulo = sc.nextLine();
                    a.elmininarLibro(titulo);
                    break;
                case 4:
                    sc.nextLine();
                    a.guardarDOMcomoArchivo("Libros.xml");
            }
        } while (opc != 0);
    }
    
    
}

