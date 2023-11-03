package accesoxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;

public class accesoDOM {
    Document doc;
    public int abrirXMLconDOM(File f)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            System.out.println("DOM creado con exito");
            return 0;
        }
        catch (IOException e)
        {
            System.out.println(e);
            return -1;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }
    public void recorrerDOM()
    {
        String[] datos = new String[3];
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();
        System.out.println(" -------------------- ");
        for (int i=0;i<nodelist.getLength();++i)
        {
            nodo = nodelist.item(i);
            if (nodo.getNodeType()==Node.ELEMENT_NODE)
            {
                Node ntemp = null;
                int contador = 1;
                datos[0]=nodo.getAttributes().item(0).getNodeValue();
                NodeList nl2 = nodo.getChildNodes();
                for (int j=0;j<nl2.getLength();++j)
                {
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType()==Node.ELEMENT_NODE)
                    {
                        datos[contador] = ntemp.getTextContent();
                        contador++;
                    }
                }
                System.out.println("  " + datos[1]);
                System.out.println("    " + datos[2] + " --- " + datos[0]);
                System.out.println(" -------------------- ");
            }
        }
    }
    
    public int insertarLibro(String titulo, String autor, String fecha)
    {
        try
        {
            System.out.println("Añadir libro al arbol DOM: " + titulo + ";" + autor + ";" + fecha);
            //Titulo
            Node ntitulo = doc.createElement("Titulo");
            Node ntitulo_text = doc.createTextNode(titulo);
            ntitulo.appendChild(ntitulo_text);
            //Autor
            Node nautor = doc.createElement("Autor");
            Node nautor_text = doc.createTextNode(autor);
            nautor.appendChild(nautor_text);
            //Libro 
            Node nlibro = doc.createElement("Libro");
            ((Element)nlibro).setAttribute("publicado", fecha);
            nlibro.appendChild(ntitulo);
            nlibro.appendChild(nautor);
            
            nlibro.appendChild(doc.createTextNode("\n"));
            
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nlibro);
            System.out.println("Libro insertado en DOM");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }
}
