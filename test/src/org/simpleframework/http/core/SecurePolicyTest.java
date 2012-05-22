package org.simpleframework.http.core;

import junit.framework.TestCase;

import org.simpleframework.http.Cookie;

public class SecurePolicyTest extends TestCase {
   
   public void testPolicy() {
      Header header = new RequestConsumer();
      Policy policy = new SecurePolicy(header);
      Cookie cookie = policy.getSession(false);
      
      assertNull(cookie);
      
      cookie = policy.getSession(true);
      
      assertNotNull(cookie);
      assertEquals(cookie.getName(), "JSESSIONID");
      assertNotNull(cookie.getValue());
      assertEquals(cookie, policy.getSession(false));
      assertEquals(cookie, policy.getSession(true));
      
      System.out.println(cookie);
      System.out.println(cookie.toClientString());
   }

}
