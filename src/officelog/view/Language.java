package officelog.view;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * This class reads and stores Language XML files.
 *
 * @author Zooty
 */
public class Language {

    private static Document Lang = null;
    private static String src = "";
    
    /**
     * Don't use this! You have been warned.
     */
    private Language() {        
        System.out.println("http://i.imgur.com/AackmXV.gifv");
        System.out.println("You have been spooked!");
    }

    /**
     * Loads an xml file with given path.
     * 
     * @param path The path of the xml file.
     */
    public static void load(String path) {        
        File XmlFile = new File(path);
        load(XmlFile);
    }
    
    /**
     * Loads the xml file from the argument.
     * 
     * @param XmlFile the file that needs to be loaded.
     */
    public static void load(File XmlFile){
        try {            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Lang = dBuilder.parse(XmlFile);
            src = XmlFile.getPath();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the path of the loaded file.
     * 
     * @return the path of the loaded file.
     */
    public static String getSrc() {
        return src;
    }
    
    /**
     * Returns the DOM of the xml file.
     * 
     * @return the DOM of the xml file.
     */
    public static Document getLang() {
        return Lang;
    }

}
