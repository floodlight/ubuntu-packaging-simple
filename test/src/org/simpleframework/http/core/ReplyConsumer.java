package org.simpleframework.http.core;

import org.simpleframework.http.StatusLine;
import org.simpleframework.util.buffer.Allocator;
import org.simpleframework.util.buffer.ArrayAllocator;

class ReplyConsumer extends BuilderConsumer {

   public Builder builder;

   public ReplyConsumer() {
      this(new ArrayAllocator(), new ResponseBuilder());
   }

   public ReplyConsumer(Allocator allocator, Builder builder) {
      super(allocator, builder, null);
      this.header = new ResponseConsumer();
      this.factory = new ConsumerFactory(allocator, header);
      this.builder = builder;
   }

   public StatusLine getStatusLine() {
      return (ResponseConsumer) header;
   }

   public Body getBody() {
      return builder.getBody();
   }

   public Message getMessage() {
      return this.header.header;
   }

   public String toString() {
      return header.toString();
   }
}