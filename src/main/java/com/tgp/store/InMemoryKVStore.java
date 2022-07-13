package com.tgp.store;

import java.util.Map;

public class InMemoryKVStore {

  private final Map<String, String> kvStore;

  public InMemoryKVStore(final Map<String, String> kvStore) {
    this.kvStore = kvStore;
  }

  public String get(final String key) {
    return kvStore.get(key);
  }

  public void set(final String key, final String value) {
    kvStore.put(key, value);
  }
}
