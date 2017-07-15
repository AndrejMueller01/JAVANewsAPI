package javanews.utils;

import java.sql.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author AM
 */
public class StringParser {
    
      public static long convertNewsApiDateStringToMilliseconds(String date) {
          String year = date.substring(0, 4);
          String month = date.substring(5, 7);
          String day = date.substring(8, 10);
          String hour =  date.substring(11, 13);
          String minute =  date.substring(14, 16);
          String second  = date.substring(17, 19);    
          
          return new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second)).getTimeInMillis();
      }
      
      // parse the date from the news api json response
      public static Date convertNewsApiDateStringToDate(String date) {
          String year = date.substring(0, 4);
          String month = date.substring(5, 7);
          String day = date.substring(8, 10);
          // there is an java issue, that months starts from 0
          long timeInMilliSec = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)).getTimeInMillis();
          return new Date(timeInMilliSec);
      }
  }
