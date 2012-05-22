package org.simpleframework.http.parse;

import junit.framework.TestCase;

public class DateParserTest extends TestCase {
   
   /**
    * Sun, 06 Nov 2009 08:49:37 GMT  ; RFC 822, updated by RFC 1123
    * Sunday, 06-Nov-09 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
    * Sun Nov  6 08:49:37 2009       ; ANSI C's asctime() format
    */
   public void testDate() {
      DateParser rfc822 = new DateParser("Sun, 06 Nov 2009 08:49:37 GMT");
      DateParser rfc850 = new DateParser("Sunday, 06-Nov-09 08:49:37 GMT");
      DateParser asctime = new DateParser("Sun Nov  6 08:49:37 2009");

      assertEquals(rfc822.toLong() >> 10, rfc850.toLong() >> 10); // shift out seconds
      assertEquals(rfc822.toLong() >> 10, asctime.toLong() >> 10); // shift out seconds   
      assertEquals(rfc822.toString(), rfc850.toString());
      assertEquals(rfc822.toString(), asctime.toString());
      assertEquals(rfc850.toString(), "Sun, 06 Nov 2009 08:49:37 GMT");
      assertEquals(rfc850.toString().length(), 29);
      assertEquals(rfc822.toString(), "Sun, 06 Nov 2009 08:49:37 GMT");
      assertEquals(rfc822.toString().length(), 29);
      assertEquals(asctime.toString(), "Sun, 06 Nov 2009 08:49:37 GMT");
      assertEquals(asctime.toString().length(), 29);
   }

}
