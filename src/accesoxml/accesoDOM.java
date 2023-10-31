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
}
