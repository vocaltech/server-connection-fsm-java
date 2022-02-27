## Server connection with FSM

### Description

Check if a server is reachable or not, using a Finite State Machine (FSM) to update the different states.<br>
This FSM has 3 states (see diagram below):
- NotReachable: the server is not reachable
- Reachable: the server is reachable
- Recovery: the server was in NotReachable state, and the server is reachable

![img.png](docs/server_connection_uml.png)

### Usage

```
// example with the Initial state
CheckServerConnectionFsm.State currentState = CheckServerConnectionFsm.State.Init;

// if the server is reachable,
// then the transition is true,
// and we go to the next state (Reachable)
assertEquals(state.next(true), CheckServerConnectionFsm.State.Reachable);

// if the server is NOT reachable,
// then the transition is false,
// and we go to the next state (NotReachable)
assertEquals(state.next(false), CheckServerConnectionFsm.State.NotReachable);`
```

### Exposed endpoints
#### Default route
> `http://localhost:8080`
#### Shutdown server
> `http://localhost:8080/`
#### Check if server is available
> `http://localhost:8080/available`