package com.tgp.store;

import io.jooby.MockContext;
import io.jooby.MockRouter;
import io.jooby.StatusCode;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class KeyValueStoreApplicationTest {

    @Test
    public void shouldPingPong() {
        MockRouter router = new MockRouter(new KeyValueStoreApplication());
        router.get("/ping", response -> {
            assertEquals("pong", response.value());
            assertEquals(StatusCode.OK, response.getStatusCode());
        });
    }

    @Test
    public void shouldSetKeyValue() {
        MockRouter router = new MockRouter(new KeyValueStoreApplication());
        router.post("/",new MockContext().setBody("some_key:some_value"), response -> {
            assertEquals("some_value", response.value());
            assertEquals(StatusCode.OK, response.getStatusCode());
        });
    }

    @Test
    public void shouldGetKeyValue() {
        MockRouter router = new MockRouter(new KeyValueStoreApplication());
        router.post("/",new MockContext().setBody("some_key:some_value"), rsp -> {
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });

        router.get("/some_key", response -> {
            assertEquals("some_value", response.value());
            assertEquals(StatusCode.OK, response.getStatusCode());
        });
    }

    @Test
    public void shouldNotGetKeyValueIfNotPresent() {
        File waLFile = new File("WALs/wal.tmp");
        waLFile.delete();
        MockRouter router = new MockRouter(new KeyValueStoreApplication());
        router.get("/some_key", response -> {
            assertEquals(StatusCode.OK, response.getStatusCode());
            assertEquals("NOT_FOUND", response.value());
        });
    }
}
