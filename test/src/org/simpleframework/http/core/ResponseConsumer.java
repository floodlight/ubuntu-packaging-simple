package org.simpleframework.http.core;

import org.simpleframework.http.StatusLine;

class ResponseConsumer extends RequestConsumer implements StatusLine {

   private int status;
   private String text;

   public ResponseConsumer() {
      super();
   }

   private void status() {
      while(pos < count) {
         if(!digit(array[pos])) {
            break;
         }
         status *= 10;
         status += array[pos];
         status -= '0';
         pos++;
      }
   }

   private void text() {
      StringBuilder builder = new StringBuilder();

      while(pos < count) {
         if(terminal(array[pos])) {
            pos += 2;
            break;
         }
         builder.append((char) array[pos]);
         pos++;
      }
      text = builder.toString();
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public int getStatus() {
      return status;
   }

   public void setCode(int status) {
      this.status = status;
   }

   @Override
   protected void add(String name, String value) {
      if(equal("Set-Cookie", name)) { // A=b; version=1; path=/;  
         String[] list = value.split(";"); // "A=b", "version=1", "path=/" 

         if(list.length > 0) {
            String[] pair = list[0].split("=");

            if(pair.length > 1) {
               header.setCookie(pair[0], pair[1]); // "A", "b" 
            }
         }
      }
      super.add(name, value);
   }

   @Override
   protected void process() {
      version(); // HTTP/1.1 
      adjust();
      status(); // 200 
      adjust();
      text(); // OK 
      adjust();
      headers();
   }

   public int getCode() {
      return status;
   }

   public void setMajor(int major) {
      this.major = major;

   }

   public void setMinor(int minor) {
      this.minor = minor;

   }
}
