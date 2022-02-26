package fr.vocaltech.fsm.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @Autowired
    private RestartService restartService;

    @GetMapping("/")
    public String index() {
        return "ServerController works !";
    }

    @PostMapping("/restart")
    public void restartWithCloudContext() {
        restartService.restartApp();
    }
}
