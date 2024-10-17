package client;

import java.io.*;
import java.net.*;

public class ClientApp {
    // Run client cmd: java -cp classes client.ClientApp 3000 
    public static void main(String[] args) throws IOException {
        String portNumber = "";

        if(args.length > 0) {
            portNumber = args[0];
        } else {
            System.err.println("Missing arguments!!");
            System.exit(-1);
        }
        
        Socket s = new Socket("localhost", Integer.parseInt(portNumber));
        
        try {

            InputStream is = s.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            OutputStream os = s.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console cons = System.console();
            String input = "";
            String cookie = "";

            while(!input.toLowerCase().equals("quit"))
            {
                input = cons.readLine("Enter anything to request for a cookie, 'quit' to exit.\n");

                // Send server request
                dos.writeUTF(input);
                dos.flush();

                if(input.equals("quit"))
                    break;

                // Read cookie (received from server)
                cookie = dis.readUTF();
                System.out.println(cookie);
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
        } catch(Exception ex) {

        }
    }
}
