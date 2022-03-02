package fr.vocaltech.fsm.server;

import fr.vocaltech.fsm.server.services.RestartService;
import fr.vocaltech.fsm.server.services.ServerConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @Autowired
    private RestartService restartService;

    @Autowired
    private ServerConnectionService serverConnectionService;

    @GetMapping("/")
    public String index() {
        return "ServerController works !";
    }

    @PostMapping("/restart")
    public void restartWithCloudContext() {
        restartService.restartApp();
    }

    @GetMapping("/available")
    public boolean checkServerConnection() {
        return serverConnectionService.isServerAvailable();
    }

    @GetMapping("/health")
    public String health() {
        return serverConnectionService.checkHealth();
    }
}
