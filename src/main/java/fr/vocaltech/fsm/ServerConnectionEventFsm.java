package fr.vocaltech.fsm;

public class ServerConnectionEventFsm {
    private static boolean _isStarted = false;

    public void start() {
        _isStarted = true;
    }

    public void stop() {
        _isStarted = false;
    }

    public enum States {
        STATE_INIT {
            @Override
            public States sendEvent(Events evt) {
                switch (evt) {
                    case EVENT_SERVER_REACHABLE:
                        return _isStarted ? STATE_REACHABLE : null;

                    case EVENT_SERVER_NOT_REACHABLE:
                        return _isStarted ? STATE_NOT_REACHABLE: null;
                }
                return null;
            }
        },
        STATE_REACHABLE {
            @Override
            public States sendEvent(Events e) {
                if (e == Events.EVENT_SERVER_NOT_REACHABLE && _isStarted)
                    return STATE_NOT_REACHABLE;
                return null;
            }
        },
        STATE_NOT_REACHABLE {
            @Override
            public States sendEvent(Events e) {
                if (e == Events.EVENT_SERVER_REACHABLE && _isStarted)
                    return STATE_RECOVERY;
                return null;
            }
        },
        STATE_RECOVERY {
            @Override
            public States sendEvent(Events e) {
                switch (e) {
                    case EVENT_SERVER_REACHABLE:
                        return _isStarted ? STATE_REACHABLE : null;

                    case EVENT_SERVER_NOT_REACHABLE:
                        return _isStarted ? STATE_NOT_REACHABLE: null;
                }
                return null;
            }
        };

        public abstract States sendEvent(Events e);
    }

    public enum Events {
        EVENT_SERVER_REACHABLE,
        EVENT_SERVER_NOT_REACHABLE
    }
}
