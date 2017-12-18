// Creates SQL table with data from given xml file.
// adudyak
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class XmlToSql {
    static String dbUrl = "jdbc:mysql://localhost/";
    static String user = "admin";
    static String pass = "Temp1234%";
    static String dbName = "GFL";
    static String tableName = "PERSONS";
    static String xmlFilePath = "\\Users\\AlexD\\IdeaProjects\\XmlToSql\\persons.xml";

    public static void xmlToSql() {
        try {
            //read from XML
            File xmlFile = new File(xmlFilePath); //Get xml file
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xml = dBuilder.parse(xmlFile);
            NodeList nodeListFirstNames = xml.getElementsByTagName("firstName");
            NodeList nodeListLastNames = xml.getElementsByTagName("lastName");

            //Connect, create database and table
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE DATABASE "+ dbName);
            st.executeUpdate("USE " + dbName);
            st.executeUpdate("CREATE TABLE " + tableName +
                    "(FirstName VARCHAR (255), " +
                    "LastName VARCHAR (255), " +
                    "PRIMARY KEY (LastName))");

            //Insert values to database
            for (int i = 0; i < nodeListFirstNames.getLength(); i++) {
                st.executeUpdate("INSERT INTO " + tableName +
                        " VALUES ('" + nodeListFirstNames.item(i).getTextContent() + "', '" + nodeListLastNames.item(i).getTextContent() + "')");
                System.out.println(nodeListFirstNames.item(i).getTextContent() + " " + nodeListLastNames.item(i).getTextContent() + " добавлен в таблицу.");
            }

            //Close
            st.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        xmlToSql();
    }
}