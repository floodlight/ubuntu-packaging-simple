package org.simpleframework.http.session;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.simpleframework.util.lease.Lease;

public class SessionTest extends TestCase {
   
   private static final int ITERATIONS = 5000;
   
   public void testSession() throws Exception {
      SessionProvider<Integer> manager = new SessionManager<Integer>(5, TimeUnit.SECONDS);
      
      for(int i = 0; i < ITERATIONS; i++) {
         Session<Integer> session = manager.open(i);
         
         session.put("key", i);
      }
      for(int i = 0; i < ITERATIONS; i++) {
         Session<Integer> session = manager.open(i);
         
         assertEquals(i, session.get("key"));
      }      
      Thread.sleep(6000); // wait for expiry
     
      for(int i = 0; i < ITERATIONS; i++) {
         Session<Integer> session = manager.open(i);
         
         //assertNull(session.get("key"));
         
         session.put("key", i);
      }    
      for(int i = 0; i < ITERATIONS; i++) {
         Session<Integer> session = manager.open(i);
         Lease<Integer> lease = session.getLease();
         
         assertEquals(i, session.get("key"));
         assertEquals(new Integer(i), lease.getKey());
         
         System.err.printf("lease=[%s] expiry=[%s]%n", lease.getKey(), lease.getExpiry(TimeUnit.MILLISECONDS));
         lease.cancel();
      }  
      Thread.sleep(1000);
      
      for(int i = 0; i < ITERATIONS; i++) {
         Session<Integer> session = manager.open(i);
         
         assertNull(session.get("key"));
      }  
      
   }

}
