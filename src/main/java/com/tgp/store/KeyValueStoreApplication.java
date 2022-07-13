package com.tgp.store;

import io.jooby.Jooby;

import java.util.HashMap;

public class KeyValueStoreApplication extends Jooby {

    private static final String VALUE_NOT_FOUND = "NOT_FOUND";

    {
        InMemoryKVStore inMemoryKVStore = new InMemoryKVStore(new HashMap<>());
        get("/ping", ctx -> "pong");
        post("/", ctx -> {
            String keyValue = ctx.body().value();
            String[] splitKeyValue = keyValue.split(":");
            inMemoryKVStore.set(splitKeyValue[0], splitKeyValue[1]);
            return splitKeyValue[1];
        });

        get("/{key}", ctx -> {
            String key = ctx.path("key").value();
            String value = inMemoryKVStore.get(key);
            return value == null ? VALUE_NOT_FOUND : value;
        });
    }

    public static void main(final String[] args) {
        System.out.println("Available processors " + Runtime.getRuntime().availableProcessors());
        runApp(args, KeyValueStoreApplication::new);
    }

}
