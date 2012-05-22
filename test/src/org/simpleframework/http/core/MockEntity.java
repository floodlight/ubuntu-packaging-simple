
package org.simpleframework.http.core;

import org.simpleframework.http.session.Session;
import org.simpleframework.http.session.SessionException;

public class MockEntity implements Entity {

  private Body body;
   
  public MockEntity() {
    super();          
  }  
  
  public MockEntity(Body body) {
     this.body = body;
  }
  
  public Session getSession() throws SessionException {
     return null;
  }
  
  public Session getSession(boolean create) throws SessionException {
     return null;
  }

  public Body getBody() {
    return body;
  }

  public Header getHeader() {
    return null;
  }

  public Channel getChannel() {
    return null;
  }

  public void close() {}

  public long getStart() {
    return 0;
  }
}
