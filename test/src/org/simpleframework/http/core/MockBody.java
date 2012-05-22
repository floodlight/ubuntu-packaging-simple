package org.simpleframework.http.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.simpleframework.http.Part;

public class MockBody implements Body {
   
   protected PartList list;
   
   protected String body;
   
   public MockBody() {
      this("");
   }
   
   public MockBody(String body) {
      this.list = new PartList();
      this.body = body;
   }
   
   public PartList getParts() {
      return list;
   }
   
   public Part getPart(String name) {
      return list.getPart(name);
   }
   
   public String getContent(String charset) {
      return body;
   }
   
   public InputStream getInputStream() throws IOException {
      return new ByteArrayInputStream(body.getBytes("UTF-8"));
   }

   public String getContent() throws IOException {
      return body;
   }

}
