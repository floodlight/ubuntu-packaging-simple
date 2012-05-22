package org.simpleframework.http.core;

import org.simpleframework.http.StatusLine;
import org.simpleframework.http.session.Session;
import org.simpleframework.util.buffer.Allocator;
import org.simpleframework.util.buffer.ArrayAllocator;

class ResponseBuilder implements Builder {

   private Header header;
   private Body body;

   public ResponseBuilder() {
      super();
   }

   public void setHeader(Header header) {
      this.header = header;
   }

   public Header getHeader() {
      return header;
   }

   public void setBody(Body body) {
      this.body = body;
   }

   public Body getBody() {
      return body;
   }

   public Channel getChannel() {
      return null;
   }

   public Session getSession(boolean create) {
      return null;
   }

   public void close() {
   }
}
