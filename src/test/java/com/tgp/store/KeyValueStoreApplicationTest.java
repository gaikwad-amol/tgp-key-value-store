package com.tgp.store;

import io.jooby.MockRouter;
import io.jooby.StatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyValueStoreApplicationTest {

    @Test
    public void welcome() {
        MockRouter router = new MockRouter(new KeyValueStoreApplication());
        router.get("/", rsp -> {
            assertEquals("Welcome to Jooby!", rsp.value());
            assertEquals(StatusCode.OK, rsp.getStatusCode());
        });
    }
}
