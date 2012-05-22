package org.simpleframework.http.core;

import junit.framework.TestCase;

import org.simpleframework.http.Form;

public class FormBuilderTest extends TestCase{
   
   public void testBuilder() throws Exception {
      MockRequest request = new MockRequest();
      
      request.setTarget("/path?a=query_A&b=query_B&c=query_C&d=query_D");
      request.setContentType("application/x-www-form-urlencoded");
      request.setContent("a=post_A&c=post_C&e=post_E");
      
      MockBody body = new MockBody();
      MockEntity entity = new MockEntity(body);
      FormCreator builder = new FormCreator(request, entity);
      PartList list = body.getParts();
      
      list.add(new MockPart("a", "part_A", false));
      list.add(new MockPart("b", "part_B", false));
      list.add(new MockPart("c", "part_C", false));
      list.add(new MockPart("f", "part_F", true));
      list.add(new MockPart("g", "part_G", false));
      
      Form form = builder.getInstance();
      
      assertEquals(form.getAll("a").size(), 3);
      assertEquals(form.getAll("b").size(), 2);
      assertEquals(form.getAll("c").size(), 3);
      assertEquals(form.getAll("e").size(), 1);
      assertEquals(form.getPart("a").getContent(), "part_A");
      assertEquals(form.getPart("b").getContent(), "part_B");
      assertEquals(form.getPart("c").getContent(), "part_C");
      assertEquals(form.getPart("f").getContent(), "part_F");
      assertEquals(form.getPart("g").getContent(), "part_G");
      assertEquals(form.get("a"), "query_A");
      assertEquals(form.get("b"), "query_B");
      assertEquals(form.get("c"), "query_C");
      assertEquals(form.get("d"), "query_D");
      assertEquals(form.get("e"), "post_E");
      assertEquals(form.get("f"), null);
      assertEquals(form.get("g"), "part_G");      
   }

}
