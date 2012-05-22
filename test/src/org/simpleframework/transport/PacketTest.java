package org.simpleframework.transport;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.PriorityQueue;

import junit.framework.TestCase;

public class PacketTest extends TestCase {
   
   public void testPacket() throws IOException {
      byte[] content = "Hello World".getBytes();
      ByteBuffer buffer = ByteBuffer.wrap(content);
      Packet packet = new Wrapper(buffer, 0);
      
      assertEquals(packet.length(), content.length);
      assertEquals(packet.encode(), "Hello World");
      
      Packet extract = packet.extract();
      
      assertEquals(packet.length(), 0);
      assertEquals(packet.encode(), "");
      assertEquals(extract.length(), content.length);
      assertEquals(extract.encode(), "Hello World");
   }
   
   public void testComparison() throws IOException {
      Packet less = new Wrapper(ByteBuffer.wrap("Least".getBytes()), 1);
      Packet greater = new Wrapper(ByteBuffer.wrap("Greater".getBytes()), 10);
      
      assertEquals(less.compareTo(less), 0); // ensure comparison contract adhered to
      assertEquals(less.compareTo(greater), -1);
      assertEquals(greater.compareTo(less), 1);
      
      Packet first = new Wrapper(ByteBuffer.wrap("First".getBytes()), 1);
      Packet second = new Wrapper(ByteBuffer.wrap("Second".getBytes()), 2);
      Packet third = new Wrapper(ByteBuffer.wrap("Third".getBytes()), 3);
      PriorityQueue<Packet> queue = new PriorityQueue<Packet>();
      
      queue.offer(first);
      queue.offer(third);
      queue.offer(second);
      
      assertEquals(queue.poll().encode(), "First"); // first in sequence should be first out
      assertEquals(queue.poll().encode(), "Second");
      assertEquals(queue.poll().encode(), "Third");   
   }

}
