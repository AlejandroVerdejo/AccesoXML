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
        //Creamos un array de strings donde almacenar los datos del XML que vamos leyendo para mostrarlos mas tarde
        String[] datos = new String[3];
        Node nodo = null;
        //Creamos el nodo root cogiendo el primer nodo en el documento, en este caso el nodo 'Libros'
        Node root = doc.getFirstChild();
        //Ahora de este sacamos los nodos hijo, lo que nos dara en este caso los nodos 'Libro'
        NodeList nodelist = root.getChildNodes();
        System.out.println(" -------------------- ");
        //Ahora recorremos la lista de nodos
        for (int i=0;i<nodelist.getLength();++i)
        {
            //Sacamos el nodo de la posicion en la que estemos de la lista de nodos
            nodo = nodelist.item(i);
            //El codigo se ejecurata si el nodo que hemos sacado es un elemento
            if (nodo.getNodeType()==Node.ELEMENT_NODE)
            {
                Node ntemp = null;
                //Como primer dato guardaremos el atributo del nodo, en este caso 'publicado'
                datos[0]=nodo.getAttributes().item(0).getNodeValue();
                //El contador indicara la posicion 1 para despues guardar en esa posicion del array los datos
                int contador = 1;
                //Ahora crearemos una segunda lista de nodos en la que almacenar los nodos hijo del nodo en el que estamos, en este caso los nodos de 'Titulo' y 'Autor'
                NodeList nl2 = nodo.getChildNodes();
                //Ahora recorreremos la lista de nodos hijos
                for (int j=0;j<nl2.getLength();++j)
                {
                    //Recorremos la lista de nodos y guardamos el nodo por el que vamos en otro
                    ntemp = nl2.item(j);
                    //Ejecutara el codigo si el nodo es un elemento
                    if (ntemp.getNodeType()==Node.ELEMENT_NODE)
                    {
                        //Guardara el contenido del nodo en la posicion que indicamos anteriormente con el contador
                        datos[contador] = ntemp.getTextContent();
                        //Incrementa la posicion del contador para guardar el siguiente dato
                        contador++;
                    }
                }
                //Mostrara al usuario los datos extraidos del XML
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
            //Crearemos todos los elementos que tendremos que insertar en el nodo principal 'Libro' en orden descendente
            //Crearemos cada elemento indicando su nombre y creando un textnode con el contenido de estos, que se añadira despues
            //Titulo
            //Creamos el nodo
            Node ntitulo = doc.createElement("Titulo");
            //Creamos el contenido del nodo
            Node ntitulo_text = doc.createTextNode(titulo);
            //Introducimos el contenido en el nodo
            ntitulo.appendChild(ntitulo_text);
            //Autor
            //Creamos el nodo
            Node nautor = doc.createElement("Autor");
            //Creamos el contenido del nodo
            Node nautor_text = doc.createTextNode(autor);
            //Introducimos el contenido en el nodo
            nautor.appendChild(nautor_text);
            //Libro 
            //Ahora crearemos el nodo principal
            Node nlibro = doc.createElement("Libro");
            //Le añadiremos el atributo
            ((Element)nlibro).setAttribute("publicado", fecha);
            //Añadiremos los nodos correspondientes 
            nlibro.appendChild(ntitulo);
            nlibro.appendChild(nautor);

            nlibro.appendChild(doc.createTextNode("\n"));
            //Añadimos el nodo a la raiz del 
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
            //Creamos el nodo raiz
            Node raiz = doc.getDocumentElement();
            //Creamos un nodelist con todos los nodos 'Titulo'
            NodeList nl1 = doc.getElementsByTagName("Titulo");
            Node n1;
            //Recorreremos la lista de nodos que acabamos de crear
            for (int i=0;i<nl1.getLength();++i)
            {
                
                n1 = nl1.item(i);
                //El codigo se ejecutara si el nodo es un elemento
                if (n1.getNodeType() == Node.ELEMENT_NODE)
                {
                    //El codigo se ejecutara si el valor del elemento es el titulo que buscamos
                    if (n1.getChildNodes().item(0).getNodeValue().equals(titulo))
                    {
                        System.out.println("Borrando el nodo <Libro> con titulo " + titulo);
                        //Sacaremos el nodo padre del nodo en el que nos encontramos y eliminaremos el nodo con el titulo que bucabamos
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
