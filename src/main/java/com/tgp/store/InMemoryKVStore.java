package com.tgp.store;

import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class InMemoryKVStore {

  private final Map<String, String> kvStore;
  private final WAL wal;

  public InMemoryKVStore(final Map<String, String> kvStore, WAL wal) {
    this.kvStore = kvStore;
    this.wal = wal;
    loadWAL();
  }

  private void loadWAL() {
    try {
      while (true) {
        String s = wal.getInputStream().readUTF();
        String[] splitStrings = s.split(":");
        kvStore.put(splitStrings[0], splitStrings[1]);
      }
    } catch (EOFException e) {
      log.warn("reached EOF!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String get(final String key) {
    return kvStore.get(key);
  }

  public String set(final String key, final String value) {
    boolean isAppended = wal.append(key, value);
    log.info("is Appended: {}", isAppended);
    kvStore.put(key, value);
    return kvStore.get(key);
  }
}
