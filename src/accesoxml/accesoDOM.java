package accesoxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
    
    public int elmininarLibro(String titulo)
    {
        System.out.println("Buscando el libro " + titulo + " para borrarlo");
        try
        {
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("Titulo");
            Node n1;
            for (int i=0;i<nl1.getLength();++i)
            {
                n1 = nl1.item(i);
                if (n1.getNodeType() == Node.ELEMENT_NODE)
                {
                    if (n1.getChildNodes().item(0).getNodeValue().equals(titulo))
                    {
                        System.out.println("Borrando el nodo <Libro> con titulo " + titulo);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    }
                }
            }
            System.out.println("Nodo borrado");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    public void guardarDOMcomoArchivo(String nuevoarchivo)
    {
        try
        {
            Source src = new DOMSource(doc);
            StreamResult rst = new StreamResult(new File(nuevoarchivo));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con exito");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
