package server;

import java.io.*;
import java.net.*;

import client.Cookie;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //int port = 12345;
        //String file = "cookie_file.txt";

        String portNumber = "";
        String dirPath = "";        // directory to file
        String fileName = "";       

        if(args.length > 0) {
            portNumber = args[0];
            dirPath = args[1];
            fileName = args[2];
        } else {
            System.err.println("Missing arguments!!");
            System.exit(-1);
        }

        File newDirectory = new File(dirPath);
        if(!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        // Read and print cookies
        Cookie c = new Cookie();
        c.readCookieFile(dirPath + File.separator + fileName);
        
        // Init server
        ServerSocket ss = new ServerSocket(Integer.parseInt(portNumber));
        System.out.printf("Websocket server started on port %s. Waiting for connection...\n", portNumber);
        Socket s = ss.accept();

        try {
            InputStream is = s.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            String msgReceived = "";

            OutputStream os = s.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            while(!msgReceived.toLowerCase().equals("quit")) 
            {
                System.out.println("Waiting for client input...");
                
                // Get client input
                msgReceived = dis.readUTF();

                // Serve random cookie
                String retrievedCookie = c.getRandomCookie();
                dos.writeUTF(retrievedCookie);
                dos.flush();
            } 

            // Close io streams
            dos.close();
            bos.close();
            os.close();
            
            dis.close();
            bis.close();
            is.close(); 

            // Close socket
            s.close(); 
        } catch (EOFException ex) {
            System.err.println(ex.toString());
        } 
    }
}
