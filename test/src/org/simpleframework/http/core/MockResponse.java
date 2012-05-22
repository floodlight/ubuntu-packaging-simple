package org.simpleframework.http.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.WritableByteChannel;
import java.util.Map;

import org.simpleframework.http.Response;
import org.simpleframework.http.core.ResponseMessage;

public class MockResponse extends ResponseMessage implements Response {

   private boolean committed;
   
   public MockResponse() {
      super();
   }
   
   public OutputStream getOutputStream() {
      return System.out;
   }
   
   public boolean isCommitted() {
      return committed;
   }
   
   public void commit() {
      committed = true;
   }
   
   public void reset() {
      return;
   }
   
   public void close() {
      return;
   }

   public Object getAttribute(String name) {
      return null;
   }

   public Map getAttributes() {
      return null;
   }

   public OutputStream getOutputStream(int size) throws IOException {
      return null;
   }

   public PrintStream getPrintStream() throws IOException {
      return null;
   }

   public PrintStream getPrintStream(int size) throws IOException {
      return null;
   }

   public void setContentLength(int length) {
      set("Content-Length", length);
   }

   public WritableByteChannel getByteChannel() throws IOException {
      return null;
   }

   public WritableByteChannel getByteChannel(int size) throws IOException {
      return null;
   }

   public boolean isEmpty() {
      return false;
   }
}
