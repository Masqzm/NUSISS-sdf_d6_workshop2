package client;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Cookies {
    List<String> cookies = new ArrayList<>();

    public void readCookieFile(String dirPathFileName) throws IOException {
        FileReader fr = new FileReader(dirPathFileName);
        BufferedReader br = new BufferedReader(fr);

        // Read and add cookies from file to cookies list
        String cookie = "";
        while((cookie = br.readLine()) != null) {
            cookies.add(cookie);
        }

        br.close();
        fr.close();
    }

    public String getRandomCookie() {
        Random rand = new Random();

        if(cookies != null) {
            if(!cookies.isEmpty()) {
                // Get random cookie from list
                return cookies.get(rand.nextInt(cookies.size()));
            } else {
                return "No cookie found!!!";
            }
        }
        return "No cookie found!!!";
    }

    public void printCookies() {
        if(!cookies.isEmpty()) {
            cookies.forEach(System.out::println);
            // ALT:
            // cookies.forEach(c -> System.out.println(c));
        }
    }
}
