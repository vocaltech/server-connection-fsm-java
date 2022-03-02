package fr.vocaltech.fsm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ServerConnectionService {
    @Autowired
    private Environment env;

    public boolean isServerAvailable() {
        String urlServer = env.getProperty("url.server");
        int timeout = 500;

        try {
            URL url = new URL(urlServer);

            // TODO: detect if http or https protocol

            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(timeout);
            urlConn.connect();

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("isServerReachable: IOException: " + e.getMessage());
        }

        return false;
    }

    public void shutdown() {
        //String url =
    }

    public String checkHealth() {
        String url_server_health = env.getProperty("url.server.health");

        try {
            URL url = new URL(url_server_health);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setConnectTimeout(500);

            // read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            final StringBuilder res = new StringBuilder();

            String inputLine;
            while ( (inputLine = br.readLine()) != null ) {
                res.append(inputLine).append("\n");
            }
            br.close();

            return(res.toString());

        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return "";
    }
}
