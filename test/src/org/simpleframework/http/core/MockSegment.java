package org.simpleframework.http.core;

import java.util.List;

import org.simpleframework.http.ContentType;
import org.simpleframework.http.parse.ContentParser;

public class MockSegment implements Segment {
   
   private Message header;
   
   public MockSegment() {
      this.header = new Message();
   }
   
   public boolean isFile() {
      return false;
   }
   
   public ContentType getContentType() {
      String value = getValue("Content-Type");
      
      if(value == null) {
         return null; 
      }
      return new ContentParser(value);
   }
   
   public int getContentLength() {
      String value = getValue("Content-Length");
      
      if(value != null) {
         return new Integer(value);
      }
      return -1;
   }
   
   public String getTransferEncoding() {
      List<String> list = getValues("Transfer-Encoding");
      
      if(list.size() > 0) {
         return list.get(0);
      }
      return null;
   }
   
   public Disposition getDisposition() {
      String value = getValue("Content-Disposition");
      
      if(value == null) {
         return null;
      }
      return new DispositionParser(value);
   }
   
   public List<String> getValues(String name) {
      return header.getValues(name);
   }
   
   public String getValue(String name) {
      return header.getValue(name);
   }

   protected void add(String name, String value) {
      header.add(name, value);
   }

   public String getName() {
      return null;
   }

   public String getFileName() {
      return null;
   }       
}