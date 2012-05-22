package org.simpleframework.http.core;

import java.io.IOException;


public class MockMonitor implements Monitor {
   
   private boolean close;
   
   private boolean error;
   
   private boolean ready;
   
   public MockMonitor() {
      super();
   }
   
   public void close(Sender sender) {
      close = true;
   }
   
   public boolean isClose() {
      return close;
   }
   
   public boolean isError() {
      return error;
   }
   
   public void ready(Sender sender) {
      ready = true;
   }
   
   public boolean isReady() {
      return ready;
   }

   public void error(Sender sender) {
      error = true;      
   }

   public boolean isClosed() {
      return close || error;
   }

}
