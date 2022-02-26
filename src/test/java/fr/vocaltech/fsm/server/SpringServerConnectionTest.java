package fr.vocaltech.fsm.server;

import fr.vocaltech.fsm.ServerConnectionFsm;
import fr.vocaltech.fsm.server.services.ServerConnectionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringServerConnectionTest {
    @Autowired
    private ServerConnectionService serverConnectionService;

    @Autowired
    private static ServerConnectionFsm.State state;

    @BeforeAll
    static void setup() {
        state = ServerConnectionFsm.State.Init;
    }

    @Test
    public void testIsServerAvailable() {
        boolean isServerAvailable = serverConnectionService.isServerAvailable();

        assertThat(state.next(isServerAvailable)).isEqualTo(ServerConnectionFsm.State.Reachable);

    }
}
