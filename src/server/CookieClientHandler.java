package server;

import java.io.*;
import java.net.Socket;

import client.Cookies;

public class CookieClientHandler implements Runnable {
    private final Socket s;
    private final Cookies cookies;

    public CookieClientHandler(Socket s, Cookies cookies) {
        this.s = s;
        this.cookies = cookies;
    }

    // Entry point for the thread (similar to main())
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

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
                System.out.printf("[%s] Waiting for client input...\n", threadName);
                
                // Get client input
                msgReceived = dis.readUTF();

                // Serve random cookie
                String retrievedCookie = cookies.getRandomCookie();
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
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } 
    }
}
