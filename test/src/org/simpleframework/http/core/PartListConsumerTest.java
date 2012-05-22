package org.simpleframework.http.core;

import junit.framework.TestCase;

import org.simpleframework.transport.Cursor;
import org.simpleframework.util.buffer.ArrayAllocator;

public class PartListConsumerTest extends TestCase {
   
   private static final String SIMPLE = 
   "--AaB03x\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file1.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file1.txt ...\r\n"+
   "--AaB03x--\r\n";
   
   private static final String NORMAL =
   "--AaB03x\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file1.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file1.txt\r\n"+   
   "--AaB03x\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file2.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file2.txt\r\n"+
   "--AaB03x\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file3.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file3.txt ...\r\n"+
   "--AaB03x--\r\n";
   
   private static final String MIXED =  
   "--AaB03x\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file1.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file1.txt\r\n"+   
   "--AaB03x\r\n"+   
   "Content-Type: multipart/mixed; boundary=BbC04y\r\n\r\n"+
   "--BbC04y\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file2.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file2.txt ...\r\n"+
   "--BbC04y\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file3.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file3.txt ...\r\n"+
   "--BbC04y\r\n"+
   "Content-Disposition: form-data; name='pics'; filename='file4.txt'\r\n"+
   "Content-Type: text/plain\r\n\r\n"+
   "example contents of file4.txt ...\r\n"+ 
   "--BbC04y--\r\n"+
   "--AaB03x--\r\n";
   
   public void n_testSimple() throws Exception {
      PartList list = new PartList();
      PartListConsumer consumer = new PartListConsumer(new ArrayAllocator(), list, "AaB03x".getBytes("UTF-8"));
      Cursor cursor = new StreamCursor(SIMPLE);
      
      while(!consumer.isFinished()) {
         consumer.consume(cursor);
      }   
      assertEquals(list.size(), 1);
      assertEquals(list.get(0).getContentType().getPrimary(), "text");
      assertEquals(list.get(0).getContentType().getSecondary(), "plain");
      assertEquals(list.get(0).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file1.txt'");
      assertEquals(list.get(0).getContent(), "example contents of file1.txt ...");
      assertEquals(cursor.ready(), -1);     
      assertEquals(consumer.getContent(), SIMPLE);
   }
   
   public void testNormal() throws Exception {
      PartList list = new PartList();
      PartListConsumer consumer = new PartListConsumer(new ArrayAllocator(), list, "AaB03x".getBytes("UTF-8"));
      Cursor cursor = new StreamCursor(NORMAL);
      
      while(!consumer.isFinished()) {
         consumer.consume(cursor);
      }   
      assertEquals(list.size(), 3);
      assertEquals(list.get(0).getContentType().getPrimary(), "text");
      assertEquals(list.get(0).getContentType().getSecondary(), "plain");
      assertEquals(list.get(0).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file1.txt'");
      assertEquals(list.get(0).getContent(), "example contents of file1.txt");
      assertEquals(list.get(1).getContentType().getPrimary(), "text");
      assertEquals(list.get(1).getContentType().getSecondary(), "plain");
      assertEquals(list.get(1).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file2.txt'");
      assertEquals(list.get(1).getContent(), "example contents of file2.txt");
      assertEquals(list.get(2).getContentType().getPrimary(), "text");
      assertEquals(list.get(2).getContentType().getSecondary(), "plain");
      assertEquals(list.get(2).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file3.txt'");
      assertEquals(list.get(2).getContent(), "example contents of file3.txt ...");
      assertEquals(cursor.ready(), -1);     
      assertEquals(consumer.getContent(), NORMAL);
   }
   
   public void testMixed() throws Exception {
      PartList list = new PartList();
      PartListConsumer consumer = new PartListConsumer(new ArrayAllocator(), list, "AaB03x".getBytes("UTF-8"));
      Cursor cursor = new StreamCursor(MIXED);
      
      while(!consumer.isFinished()) {
         consumer.consume(cursor);
      }   
      assertEquals(list.size(), 4);
      assertEquals(list.get(0).getContentType().getPrimary(), "text");
      assertEquals(list.get(0).getContentType().getSecondary(), "plain");
      assertEquals(list.get(0).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file1.txt'");
      assertEquals(list.get(0).getContent(), "example contents of file1.txt");
      assertEquals(list.get(1).getContentType().getPrimary(), "text");
      assertEquals(list.get(1).getContentType().getSecondary(), "plain");
      assertEquals(list.get(1).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file2.txt'");
      assertEquals(list.get(1).getContent(), "example contents of file2.txt ...");
      assertEquals(list.get(2).getContentType().getPrimary(), "text");
      assertEquals(list.get(2).getContentType().getSecondary(), "plain");
      assertEquals(list.get(2).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file3.txt'");
      assertEquals(list.get(2).getContent(), "example contents of file3.txt ...");
      assertEquals(list.get(3).getContentType().getPrimary(), "text");
      assertEquals(list.get(3).getContentType().getSecondary(), "plain");
      assertEquals(list.get(3).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file4.txt'");
      assertEquals(list.get(3).getContent(), "example contents of file4.txt ...");
      assertEquals(cursor.ready(), -1);           
      assertEquals(consumer.getContent(), MIXED);
   }
   
   public void testDribble() throws Exception {
      PartList list = new PartList();
      PartListConsumer consumer = new PartListConsumer(new ArrayAllocator(), list, "AaB03x".getBytes("UTF-8"));
      Cursor cursor = new DribbleCursor(new StreamCursor(NORMAL), 1);
      
      while(!consumer.isFinished()) {
         consumer.consume(cursor);
      }   
      assertEquals(list.size(), 3);
      assertEquals(list.get(0).getContentType().getPrimary(), "text");
      assertEquals(list.get(0).getContentType().getSecondary(), "plain");
      assertEquals(list.get(0).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file1.txt'");
      assertEquals(list.get(0).getContent(), "example contents of file1.txt");
      assertEquals(list.get(1).getContentType().getPrimary(), "text");
      assertEquals(list.get(1).getContentType().getSecondary(), "plain");
      assertEquals(list.get(1).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file2.txt'");
      assertEquals(list.get(1).getContent(), "example contents of file2.txt");
      assertEquals(list.get(2).getContentType().getPrimary(), "text");
      assertEquals(list.get(2).getContentType().getSecondary(), "plain");
      assertEquals(list.get(2).getHeader("Content-Disposition"), "form-data; name='pics'; filename='file3.txt'");
      assertEquals(list.get(2).getContent(), "example contents of file3.txt ...");
      assertEquals(cursor.ready(), -1);     
      assertEquals(consumer.getContent(), NORMAL);
   }
   
   public static void main(String[] list) throws Exception {
      new PartListConsumerTest().testMixed();
   }   
}