package com.tgp.store;

import io.jooby.Jooby;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KeyValueStoreApplication extends Jooby {

  private static final String VALUE_NOT_FOUND = "NOT_FOUND";

  {
    WAL wal = new WAL();
    Map<String, String> kvStore = new ConcurrentHashMap<>();
    InMemoryKVStore inMemoryKVStore = new InMemoryKVStore(kvStore, wal);

    get("/ping", ctx -> "pong");

    post("/", ctx -> {
      String keyValue = ctx.body().value();
      String[] splitKeyValue = keyValue.split(":");
      return inMemoryKVStore.set(splitKeyValue[0], splitKeyValue[1]);
    });

//    post("/", ctx -> CompletableFuture.supplyAsync(() -> {
//        String keyValue = ctx.body().value();
//        String[] splitKeyValue = keyValue.split(":");
//        return inMemoryKVStore.set(splitKeyValue[0], splitKeyValue[1]);
//      }));

    get("/{key}", ctx -> {
      String key = ctx.path("key").value();
      String value = inMemoryKVStore.get(key);
      return value == null ? VALUE_NOT_FOUND : value;
    });
  }

  public static void main(final String[] args) {
    log.info("Available processors " + Runtime.getRuntime().availableProcessors());
    runApp(args, KeyValueStoreApplication::new);
  }

}
