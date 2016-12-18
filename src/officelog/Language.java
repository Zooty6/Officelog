package officelog;

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
     * 
     * @param path The path of the xml file.
     */
    public static void load(String path) {        
        File XmlFile = new File(path);
        load(XmlFile);
    }
    
    /**
     * 
     * @param XmlFile 
     */
    public static void load(File XmlFile){
        try {            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Lang = dBuilder.parse(XmlFile);
            src = XmlFile.getPath();
            System.out.println(src);
        } catch (Exception e) {
	e.printStackTrace();
        }
    }

    public static String getSrc() {
        return src;
    }
    
    public static Document getLang() {
        return Lang;
    }

}
