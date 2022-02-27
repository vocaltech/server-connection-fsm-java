package fr.vocaltech.fsm.server;

import fr.vocaltech.fsm.ServerConnectionFsm;

import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.awaitility.Awaitility.await;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

/**
 * -----------------------------------------------------
 *  These tests need to start the Spring server before
 * -----------------------------------------------------
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringServerConnectionTest {
    private final static String URL_AVAILABLE = "http://localhost:8080/available";
    private final static String URL_SHUTDOWN = "http://localhost:8080/actuator/shutdown";

    private static ServerConnectionFsm.State currentState;

    @BeforeAll
    static void setup() {
        currentState = ServerConnectionFsm.State.Init;
    }

    @Test
    @Order(1)
    public void testIsServerAvailableBeforeShutdown() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL_AVAILABLE)
                    .build();

            Response response = client.newCall(request).execute();

            boolean isServerAvailable = Boolean.parseBoolean(response.body().string());

            // update current state
            if (isServerAvailable) {
                currentState = currentState.next(true);
                assertThat(currentState).isEqualTo(ServerConnectionFsm.State.Reachable);
            }

            System.out.println("[testIsServerAvailable()] currentState: " + currentState);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    @Order(2)
    public void testShutdownServer() {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create("", JSON);

            Request req = new Request.Builder()
                    .url(URL_SHUTDOWN)
                    .post(body)
                    .build();

            Response res = client.newCall(req).execute();

            JSONObject jsonRes = new JSONObject(res.body().string());
            assertThat(jsonRes.get("message")).isEqualTo("Shutting down, bye...");

            System.out.println("[testShutdownServer()] done");
        } catch (IOException | JSONException ioe) {
            ioe.printStackTrace();
        }
    }

    private boolean waitFor(long millis) throws InterruptedException {
        Thread.sleep(millis);
        return true;
    }

    @Test
    @Order(3)
    public void testIsServerAvailableAfterShutdown() {
        await().atLeast(1, TimeUnit.SECONDS).and().atMost(3, TimeUnit.SECONDS).until(() -> waitFor(2000));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_AVAILABLE)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException ioe) {
            assertThat(ioe.getMessage()).startsWith("Failed to connect to localhost");

            // update current state
            currentState = currentState.next(false);
            assertThat(currentState).isEqualTo(ServerConnectionFsm.State.NotReachable);

            System.out.println("[testIsServerAvailableAfterShutdown()] currentState: " + currentState);
        }
    }

    @Test
    @Order(4)
    public void testIsServerAvailableAfterRestart() {
        System.out.println("\n######################################################");
        System.out.println("# Please, restart Spring server within 10 seconds... #");
        System.out.println("######################################################");

        await().atLeast(10, TimeUnit.SECONDS).and().atMost(20, TimeUnit.SECONDS).until(() -> waitFor(15000));

        try {
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url(URL_AVAILABLE)
                    .build();

            Response res = client.newCall(req).execute();
            boolean isServerAvailable = Boolean.parseBoolean(res.body().string());

            // update current state
            if (isServerAvailable) {
                currentState = currentState.next(true);
                assertThat(currentState).isEqualTo(ServerConnectionFsm.State.Recovery);
            }

            System.out.println("[testIsServerAvailableAfterRestart()] currentState: " + currentState);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
