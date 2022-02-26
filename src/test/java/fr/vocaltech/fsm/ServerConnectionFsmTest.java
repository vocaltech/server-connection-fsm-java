package fr.vocaltech.fsm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerConnectionFsmTest {
    @Test
    void testInitState() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.Init;
        assertEquals(state.next(true), ServerConnectionFsm.State.Reachable);
        assertEquals(state.next(false), ServerConnectionFsm.State.NotReachable);
    }

    @Test
    void testNotReachableState() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.NotReachable;
        assertEquals(state.next(true), ServerConnectionFsm.State.Recovery);
        assertEquals(state.next(false), state);
    }

    @Test
    void testReachableState() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.Reachable;
        assertEquals(state.next(true), state);
        assertEquals(state.next(false), ServerConnectionFsm.State.NotReachable);
    }

    @Test
    void testRecoveryState() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.Recovery;
        assertEquals(state.next(true), ServerConnectionFsm.State.Reachable);
        assertEquals(state.next(false), ServerConnectionFsm.State.NotReachable);
    }

    @Test
    void testInitToReachableToNotReachable() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.Init;
        state = state.next(true);
        assertEquals(state, ServerConnectionFsm.State.Reachable);
        state = state.next(false);
        assertEquals(state, ServerConnectionFsm.State.NotReachable);
    }

    @Test
    void testNotReachableToRecoveryToReachable() {
        ServerConnectionFsm.State state = ServerConnectionFsm.State.NotReachable;
        state = state.next(true);
        assertEquals(state, ServerConnectionFsm.State.Recovery);
        state = state.next(true);
        assertEquals(state, ServerConnectionFsm.State.Reachable);
    }
}