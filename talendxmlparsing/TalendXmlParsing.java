/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package talendxmlparsing;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ugupta
 */

class UserHandler extends DefaultHandler {
    String componentName=null,elementName=null,commentValue=null,uniqueNamevalue=null,labelValue=null;
    boolean bComment=false,bLabel=false;

   @Override
   public void startElement(String uri, 
      String localName, String qName, Attributes attributes)
         throws SAXException {
      if (qName.equalsIgnoreCase("node")) {
                    componentName = attributes.getValue("componentName");
      }
      if((qName.equalsIgnoreCase("node")) && (("tOracleRow").equals(componentName) || ("tFileOutputDelimited").equals(componentName)|| ("tSetGlobalVar").equals(componentName)|| ("tOracleOutput").equals(componentName))){
                    System.out.println("Start Component : " + componentName);  
      }
      if((qName.equalsIgnoreCase("elementParameter")) && (("tOracleRow").equals(componentName)|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName))){
                    elementName = attributes.getValue("name");
      }
      if((qName.equalsIgnoreCase("elementParameter")) && (("tOracleRow").equals(componentName)|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) &&(("UNIQUE_NAME").equals(elementName))){
                    uniqueNamevalue = attributes.getValue("value");
      } 
      else if((qName.equalsIgnoreCase("elementParameter")) && (("tOracleRow").equals(componentName)|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) &&(("LABEL").equals(elementName))){
                    bLabel=true;
                    labelValue = attributes.getValue("value");
      }
      else if((qName.equalsIgnoreCase("elementParameter")) && (("tOracleRow").equals(componentName)|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) &&(("COMMENT").equals(elementName))){
                    bComment=true;
                    commentValue = attributes.getValue("value");
      }
   }

   @Override
   public void endElement(String uri, 
      String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("node")) {
          if((("tOracleRow").equals(componentName)|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) && (bComment) &&(bLabel)){
                    bComment=false;
                    bLabel=false;
                    System.out.println("uniquename : " + uniqueNamevalue);
                    System.out.println("labelvalue : " + labelValue);
                    System.out.println("fieldName : " + elementName);
                    System.out.println("value : " + commentValue);
                    System.out.println("End Element :" + componentName);
                    System.out.println("-----------------------------");
          }else if((componentName.equals("tOracleRow")|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) && (bComment==false) && (bLabel)){
                    bLabel=false;
                    System.out.println("uniquename : " + uniqueNamevalue);
                    System.out.println("labelvalue : " + labelValue);
                    System.out.println("There is no comment given for this component");
                    System.out.println("End Element :" + componentName);
                    System.out.println("-----------------------------");
          }else if((componentName.equals("tOracleRow")|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) && (bComment) && (bLabel==false)){
                    bComment=false;
                    System.out.println("uniquename : " + uniqueNamevalue);
                    System.out.println("There is no Label given for this component");
                    System.out.println("fieldName : " + elementName);
                    System.out.println("Comment : " + commentValue);
                    System.out.println("End Element :" + componentName);
                    System.out.println("-----------------------------");
          }else if((componentName.equals("tOracleRow")|| ("tFileOutputDelimited").equals(componentName) || ("tSetGlobalVar").equals(componentName) || ("tOracleOutput").equals(componentName)) && (bComment==false) && (bLabel==false)){
                    System.out.println("uniquename : " + uniqueNamevalue);
                    System.out.println("There is no comment & label given for this component");
                    System.out.println("End Element :" + componentName);
                    System.out.println("-----------------------------");
          }
      }
   }
}

public class TalendXmlParsing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {	
         //File inputFile = new File("src/inputfiles/WorkFlowExport.xml");
     File inputFile = new File("src/inputfiles/withoutComment.xml");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         UserHandler userhandler = new UserHandler();
         saxParser.parse(inputFile, userhandler);     
      } catch (ParserConfigurationException | SAXException | IOException e) {
         e.printStackTrace();
      }
    }
}
