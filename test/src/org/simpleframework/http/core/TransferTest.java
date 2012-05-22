package org.simpleframework.http.core;

import java.io.IOException;

import org.simpleframework.http.core.Conversation;
import org.simpleframework.http.core.Transfer;

import junit.framework.TestCase;

public class TransferTest extends TestCase {

   public void testTransferEncoding() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);
      
      // Start a HTTP/1.1 conversation
      request.setMajor(1);
      request.setMinor(1);
      transfer.start();
      
      assertEquals(response.getValue("Connection"), "keep-alive");
      assertEquals(response.getValue("Transfer-Encoding"), "chunked");
      assertEquals(response.getValue("Content-Length"), null);
      assertEquals(response.getContentLength(), -1);
      assertTrue(response.isCommitted());
      
      sender = new MockSender();
      monitor = new MockMonitor();
      request = new MockRequest();
      response = new MockResponse();
      support = new Conversation(request, response);
      transfer = new Transfer(support, sender, monitor);
      
      // Start a HTTP/1.0 conversation
      request.setMajor(1);
      request.setMinor(0);
      transfer.start();
      
      assertEquals(response.getValue("Connection"), "close");
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getValue("Content-Length"), null);
      assertEquals(response.getContentLength(), -1);
      assertTrue(response.isCommitted());
   }
   
   public void testContentLength() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);
      
      // Start a HTTP/1.1 conversation
      request.setMajor(1);
      request.setMinor(1);
      transfer.start(1024);
      
      assertEquals(response.getValue("Connection"), "keep-alive");
      assertEquals(response.getValue("Content-Length"), "1024");
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 1024);
      assertTrue(response.isCommitted());
      
      sender = new MockSender();
      monitor = new MockMonitor();
      request = new MockRequest();
      response = new MockResponse();
      support = new Conversation(request, response);
      transfer = new Transfer(support, sender, monitor);
      
      // Start a HTTP/1.0 conversation
      request.setMajor(1);
      request.setMinor(0);
      transfer.start(1024);
      
      assertEquals(response.getValue("Connection"), "close");
      assertEquals(response.getValue("Content-Length"), "1024");
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 1024);
      assertTrue(response.isCommitted());
      
      sender = new MockSender();
      monitor = new MockMonitor();
      request = new MockRequest();
      response = new MockResponse();
      support = new Conversation(request, response);
      transfer = new Transfer(support, sender, monitor);
      
      // Start a HTTP/1.0 conversation
      request.setMajor(1);
      request.setMinor(1);
      response.set("Content-Length", "2048");
      response.set("Connection", "close");
      response.set("Transfer-Encoding", "chunked");
      transfer.start(1024);      
      
      assertEquals(response.getValue("Connection"), "close");
      assertEquals(response.getValue("Content-Length"), "1024"); // should be 1024
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 1024);
      assertTrue(response.isCommitted());
   }
   
   public void  testHeadMethodWithConnectionClose() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);

      request.setMajor(1);
      request.setMinor(0);
      request.setMethod("HEAD");
      request.set("Connection", "keep-alive");     
      response.setContentLength(1024);
      response.set("Connection", "close");
      
      transfer.start();
      
      assertEquals(response.getValue("Connection"), "close");
      assertEquals(response.getValue("Content-Length"), "1024"); // should be 1024
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 1024);
   }
   
   public void  testHeadMethodWithSomethingWritten() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);

      request.setMajor(1);
      request.setMinor(1);
      request.setMethod("HEAD");
      request.set("Connection", "keep-alive");     
      response.setContentLength(1024);
      
      transfer.start(512);
      
      assertEquals(response.getValue("Connection"), "keep-alive");
      assertEquals(response.getValue("Content-Length"), "512"); // should be 512
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 512);
   }
   
   public void testHeadMethodWithNoContentLength() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);

      request.setMajor(1);
      request.setMinor(1);
      request.setMethod("HEAD");
      request.set("Connection", "keep-alive");     
      
      transfer.start();
      
      assertEquals(response.getValue("Connection"), "keep-alive");
      assertEquals(response.getValue("Content-Length"), null); 
      assertEquals(response.getValue("Transfer-Encoding"), "chunked");
      assertEquals(response.getContentLength(), -1);
   }
   
   public void testHeadMethodWithNoContentLengthAndSomethingWritten() throws IOException {
      MockSender sender = new MockSender();
      MockMonitor monitor = new MockMonitor();
      MockRequest request = new MockRequest();
      MockResponse response = new MockResponse();
      Conversation support = new Conversation(request, response);
      Transfer transfer = new Transfer(support, sender, monitor);

      request.setMajor(1);
      request.setMinor(1);
      request.setMethod("HEAD");
      request.set("Connection", "keep-alive");     
      
      transfer.start(32);
      
      assertEquals(response.getValue("Connection"), "keep-alive");
      assertEquals(response.getValue("Content-Length"), "32"); 
      assertEquals(response.getValue("Transfer-Encoding"), null);
      assertEquals(response.getContentLength(), 32);
   }
}
