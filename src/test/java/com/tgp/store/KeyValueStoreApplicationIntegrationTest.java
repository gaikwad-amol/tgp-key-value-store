package com.tgp.store;

import io.jooby.JoobyTest;
import io.jooby.StatusCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JoobyTest(KeyValueStoreApplication.class)
public class KeyValueStoreApplicationIntegrationTest {

  static OkHttpClient client = new OkHttpClient();

  @Test
  public void shouldPingPong(int serverPort) throws IOException {
    Request request = new Request.Builder()
        .url("http://localhost:" + serverPort + "/ping")
        .build();

    try (Response response = client.newCall(request).execute()) {
      assertEquals("pong", response.body().string());
      assertEquals(StatusCode.OK.value(), response.code());
    }
  }

  @Test
  public void shouldGetSetKeyValue(int serverPort) throws IOException {
    String givenKey = "some_key";
    String givenValue = "some_value";

    Request setKVRequest = new Request.Builder()
      .url("http://localhost:" + serverPort + "/")
      .method("post", RequestBody.create((givenKey + ":" + givenValue).getBytes()))
      .build();

    try (Response rsp = client.newCall(setKVRequest).execute()) {
      assertEquals(givenValue, rsp.body().string());
      assertEquals(StatusCode.OK.value(), rsp.code());
    }

    Request getKVRequest = new Request.Builder()
      .url("http://localhost:" + serverPort + "/" + givenKey)
      .build();

    try (Response rsp = client.newCall(getKVRequest).execute()) {
      assertEquals(givenValue, rsp.body().string());
      assertEquals(StatusCode.OK.value(), rsp.code());
    }
  }
}
