package fr.vocaltech.fsm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public String checkHealth() {
        String url_server_health = env.getProperty("url.server.health");

        System.out.println("url_server_health: " + url_server_health);

        return "";
    }
}
