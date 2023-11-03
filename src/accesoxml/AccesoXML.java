package accesoxml;
import java.io.File;
import java.util.Scanner;
public class AccesoXML{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        accesoDOM a = new accesoDOM();
        File f = new File("Libros.xml");
        a.abrirXMLconDOM(f);
        a.insertarLibro("Libro1", "Autor1", "2020");
        a.recorrerDOM();
    }
    
    
}

