package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.Cookies;

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

        // Read cookies file
        Cookies cookies = new Cookies();
        cookies.readCookieFile(dirPath + File.separator + fileName);
        
        // Init server
        ServerSocket ss = new ServerSocket(Integer.parseInt(portNumber));
        
        // Create a thread pool
        ExecutorService thrPool = Executors.newFixedThreadPool(2);
        String threadName = Thread.currentThread().getName();

        while(true) 
        {
            System.out.printf("[%s] Waiting for connection on port %s\n", threadName, portNumber);

            // Waiting for incoming connection (listens to port for connection)
            Socket sock = ss.accept();  

            System.out.println("Received new connection.");

            // Create a "worker" to handle work
            CookieClientHandler cHandler = new CookieClientHandler(sock, cookies);

            // Pass the work to the worker
            thrPool.submit(cHandler);
        }
    }
}
