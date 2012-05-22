package org.simpleframework.http.core;

import junit.framework.TestCase;

import org.simpleframework.transport.Cursor;
import org.simpleframework.util.buffer.ArrayAllocator;

public class PartConsumerTest extends TestCase {
   
   private static final String SOURCE =
   "Content-Disposition: form-data; name='pics'; filename='file1.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "... contents of file1.txt ...\r\n"+
   "--AaB03x\r\n";
   
   public void testHeader() throws Exception {
      PartList list = new PartList();
      PartConsumer consumer = new PartConsumer(new ArrayAllocator(), list, "AaB03x".getBytes("UTF-8"));
      Cursor cursor = new StreamCursor(SOURCE);
      
      while(!consumer.isFinished()) {
         consumer.consume(cursor);
      }   
      assertEquals(list.size(), 1);
      assertEquals(list.get(0).getContentType().getPrimary(), "text");
      assertEquals(list.get(0).getContentType().getSecondary(), "plain");
      assertEquals(list.get(0).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file1.txt'");         
   }
}
