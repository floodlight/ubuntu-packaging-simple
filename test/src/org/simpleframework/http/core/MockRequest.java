package org.simpleframework.http.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Map;

import org.simpleframework.http.ContentType;
import org.simpleframework.http.Cookie;
import org.simpleframework.http.Form;
import org.simpleframework.http.Part;
import org.simpleframework.http.Path;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.parse.AddressParser;
import org.simpleframework.http.parse.ContentParser;
import org.simpleframework.http.session.Session;
import org.simpleframework.http.session.SessionException;

public class MockRequest extends RequestMessage implements Request {
   
   private Message message;
   
   private String target;
   
   private String method = "GET";
   
   private String content;
   
   private String type;
   
   private int major = 1;
   
   private int minor = 1;
   
   public MockRequest() {
      this.header = new RequestConsumer();
      this.message = new Message();
   }
   
   public void set(String name, String value) {
      message.set(name, value);
   }
   
   public void add(String name, String value) {
      message.add(name, value);
   }
   
   public boolean isSecure(){
      return false;
   }
   
   public String getTarget() {
      return target;
   }
   
   public void setContentType(String value) {
      type = value;
   }
   
   public void setTarget(String target) {
      this.target = target;
   }
   
   public Path getPath() {
      return new AddressParser(target).getPath();
   }
  
   public Query getQuery() {
      return new AddressParser(target).getQuery();
   }
   
   public String getMethod() {
      return method;
   }

   public void setMethod(String method) {
      this.method = method;
   }

   public int getMajor() {
      return major;
   }

   public void setMajor(int major) {
      this.major = major;
   }

   public int getMinor() {
      return minor;
   }

   public void setMinor(int minor) {
      this.minor = minor;
   }
   
   public String getContent() {
      return content;
   }
   
   public void setContent(String content) {
      this.content = content;
   }

   public InputStream getInputStream() {
      return null;
   }
   
   public Session getSession() throws SessionException {
      return null;
   }
   
   public Session getSession(boolean create) throws SessionException {
      return null;
   }
   
   public Form getForm() {
      return null;
   }
   
   public Part getPart(String name) {
      return null;
   }
   
   public int size() {
      return 0;
   }

   public Cookie getCookie(String name) {     
      return null;
   }

   public String getParameter(String name) {      
      return null;
   }

   public Map getAttributes() {
      return null;
   }

   
   public ContentType getContentType() {
      return new ContentParser(type);
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
      return message.getValues(name);
   }
   
   public String getValue(String name) {
      return message.getValue(name);
   }

   public Object getAttribute(Object key) {
      return null;
   }

   public boolean isKeepAlive() {
      return true;
   }

   public InetSocketAddress getClientAddress() {
      return null;
   }

   public ReadableByteChannel getByteChannel() throws IOException {   
      return null;
   }
}
