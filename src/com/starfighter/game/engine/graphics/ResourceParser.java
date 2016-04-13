package com.starfighter.game.engine.graphics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;

public class ResourceParser {

    private static Dictionary<String, SpriteResource> resources;

    public static void init(String path) {
        resources = new Hashtable<String, SpriteResource>();

        try {
            InputStream atlasInfo = ResourceParser.class.getResourceAsStream(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(atlasInfo);

            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("SubTexture");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    SpriteResource resource = new SpriteResource();
                    resource.name = element.getAttribute("name");
                    resource.positionX = Integer.parseInt(element.getAttribute("x"));
                    resource.positionY = Integer.parseInt(element.getAttribute("y"));
                    resource.width = Integer.parseInt(element.getAttribute("width"));
                    resource.height = Integer.parseInt(element.getAttribute("height"));

                    resources.put(resource.name, resource);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SpriteResource getResource(String name) {
        return resources.get(name);
    }
}
