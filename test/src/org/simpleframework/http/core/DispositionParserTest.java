package org.simpleframework.http.core;

import junit.framework.TestCase;

public class DispositionParserTest extends TestCase {
	
   private DispositionParser parser;
	
   public void setUp() {
      parser = new DispositionParser();
   }

   public void testDisposition() {
      parser.parse("form-data; name=\"input_check\"");

      assertFalse(parser.isFile());
      assertEquals(parser.getName(), "input_check");

      parser.parse("form-data; name=\"input_password\"");

      assertFalse(parser.isFile());
      assertEquals(parser.getName(), "input_password");

      parser.parse("form-data; name=\"FileItem\"; filename=\"C:\\Inetpub\\wwwroot\\Upload\\file1.txt\"");

      assertTrue(parser.isFile());
      assertEquals(parser.getName(), "FileItem");
      assertEquals(parser.getFileName(), "C:\\Inetpub\\wwwroot\\Upload\\file1.txt");

   }
}
