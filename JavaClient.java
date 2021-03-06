import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javazoom.jl.player.Player;

class TCPClient {
    public static boolean stream;

    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost",8888);

        while (true) {

            System.out.print("Nombre de la Cancion:");
            String sentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            if(inFromUser.toString().equals("Close")){
                clientSocket.close();
                return;
            }



            sentence = inFromUser.readLine();

//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//
//            // root element
//            Element SongElement = doc.createElement("Message");
//            doc.appendChild(SongElement);
//
//            Element carname = doc.createElement("Cancion");
//            Attr attrType = doc.createAttribute("type");
//            attrType.setValue("formula one");
//            carname.setAttributeNode(attrType);
//            carname.appendChild(doc.createTextNode(sentence));
//            SongElement.appendChild(carname);
//
//            XML_to_String converter = new XML_to_String();
//            String xml = converter.toString(doc).replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
            System.out.println("SENDING... " + sentence);
            outToServer.writeBytes(sentence + '\n');


            /*BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);*/




            // run in new thread to play in background
            new Thread() {
                int counter = 0;
                public void run() {
                    try {
                        String modifiedSentence;

                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        modifiedSentence = inFromServer.readLine();
                        System.out.println("FROM SERVER: " + modifiedSentence);

                        byte[] bytes =  Base64.getDecoder().decode(modifiedSentence.getBytes(StandardCharsets.UTF_8));
                        System.out.println(bytes);
                        MP3 mp3 = new MP3(bytes);
                        mp3.play();
                        TimeUnit.SECONDS.sleep(6);

                        counter++;
                        outToServer.writeBytes(String.valueOf(counter) + '\n');

                        run();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }.start();


        }


    }
}




