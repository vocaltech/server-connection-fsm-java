package fr.vocaltech.fsm;

public class ServerConnectionFsm {
    enum State {
        Init {
            @Override
            public State next(boolean isReachable) {
                return (isReachable ? Reachable: NotReachable);
            }
        },
        NotReachable {
            @Override
            public State next(boolean isReachable) {
                return (isReachable ? Recovery: this);
            }
        },
        Reachable {
            @Override
            public State next(boolean isReachable) {
                return (isReachable ? this: State.NotReachable);
            }
        },
        Recovery {
            @Override
            public State next(boolean isReachable) {
                return (isReachable ? State.Reachable: State.NotReachable);
            }
        };

        public abstract State next(boolean isReachable);
    }
}
