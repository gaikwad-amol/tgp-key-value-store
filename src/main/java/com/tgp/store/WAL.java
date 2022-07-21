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

  private FileOutputStream fileOutputStream;
  private DataOutputStream dataOutputStream;
  private DataInputStream dataInputStream;

  public WAL() {
    File waLFile = new File("WALs/wal.tmp");
    try {
      if (!waLFile.exists()) {
        boolean isFileCreated = waLFile.createNewFile();
        log.info("Is WAL file created?:{}", isFileCreated);
      }
      fileOutputStream = new FileOutputStream(waLFile, true);
//      dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
      dataOutputStream = new DataOutputStream(fileOutputStream);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(waLFile));
      this.dataInputStream = new DataInputStream(bufferedInputStream);
    } catch (IOException e) {
      log.error("Error creating the WAL file" , e);
      e.printStackTrace();
    }
  }

  public boolean append(String key, String value) {
    boolean isAppended = false;
    try {
      dataOutputStream.writeUTF(key + ":" + value);
      dataOutputStream.flush();
      fileOutputStream.getFD().sync();
      isAppended = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return isAppended;
  }

  public DataInputStream getInputStream() {
    return dataInputStream;
  }
}
