package com.tgp.store;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class WAL {

  private DataOutputStream out;
  private DataInputStream in;

  public WAL() {
    File waLFile = new File("WALs/wal.tmp");
    try {
      if (!waLFile.exists()) {
        boolean isFileCreated = waLFile.createNewFile();
        log.info("Is WAL file created?:{}", isFileCreated);
      }
      out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(waLFile, true)));
      BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(waLFile));
      this.in = new DataInputStream(bufferedInputStream);
    } catch (IOException e) {
      log.error("Error creating the WAL file" , e);
      e.printStackTrace();
    }
  }

  public boolean append(String key, String value) {
    boolean isAppended = false;
    try {
      out.writeUTF(key + ":" + value);
      out.flush();
      isAppended = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return isAppended;
  }

  public DataInputStream getInputStream() {
    return in;
  }
}
