package org.simpleframework.http.resource;

import java.io.File;

import junit.framework.TestCase;

import org.simpleframework.http.Address;
import org.simpleframework.http.parse.AddressParser;

public class FileIndexerTest extends TestCase {
   
   public void testFileIndexer() {
      FileIndexer indexer = new FileIndexer(new File("."));
      Address address = new AddressParser("/path/index.html");
      
      assertEquals(indexer.getContentType(address), "text/html");
      assertEquals(indexer.getPath(address).getName(), "index.html");
      assertEquals(indexer.getPath(address).getExtension(), "html");
      assertEquals(indexer.getPath(address).getDirectory(), "/path/");
      
   }

}
