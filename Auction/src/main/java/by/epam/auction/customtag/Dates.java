package by.epam.auction.customtag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class Dates.
 */
public final class Dates {
     
     /**
      * Instantiates a new dates.
      */
     private Dates() {}

     /**
      * Format local date time.
      *
      * @param localDateTime the local date time
      * @param pattern the pattern
      * @return the string
      */
     public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
         return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
     }
}
