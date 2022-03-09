package fr.vocaltech.fsm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import fr.vocaltech.fsm.ServerConnectionEventFsm.States;
import fr.vocaltech.fsm.ServerConnectionEventFsm.Events;

class ServerConnectionEventFsmTest {
    ServerConnectionEventFsm serverConnectionEventFsm = new ServerConnectionEventFsm();

    @BeforeEach
    void initEach() {
        serverConnectionEventFsm.start();
    }

    @AfterEach
    void cleanupEach() {
        serverConnectionEventFsm.stop();
    }

    @Test
    void test_Init_State() {
        States initState = States.STATE_INIT;
        assertEquals(States.STATE_INIT, initState);
    }

    @Test
    void test_Init_to_State_Reachable() {
        States currentState = States.STATE_INIT;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_REACHABLE);

        assertEquals(States.STATE_REACHABLE, currentState);
    }

    @Test
    void test_Init_to_State_Not_Reachable() {
        States currentState = States.STATE_INIT;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_NOT_REACHABLE);

        assertEquals(States.STATE_NOT_REACHABLE, currentState);
    }

    @Test
    void test_State_Reachable_to_State_Not_Reachable() {
        States currentState = States.STATE_REACHABLE;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_NOT_REACHABLE);

        assertEquals(States.STATE_NOT_REACHABLE, currentState);
    }

    @Test
    void test_State_Not_Reachable_to_State_Recovery() {
        States currentState = States.STATE_NOT_REACHABLE;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_REACHABLE);

        assertEquals(States.STATE_RECOVERY, currentState);
    }

    @Test
    void test_State_Recovery() {
        States currentState = States.STATE_RECOVERY;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_REACHABLE);

        assertEquals(States.STATE_REACHABLE, currentState);

        currentState = States.STATE_RECOVERY;
        currentState = currentState.sendEvent(Events.EVENT_SERVER_NOT_REACHABLE);

        assertEquals(States.STATE_NOT_REACHABLE, currentState);
    }
}