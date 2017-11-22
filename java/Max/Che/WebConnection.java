package Max.Che;

import java.io.*;

/**
 * Created by maxch on 15-Sep-17.
 */
public class WebConnection {
   private String url = "http://localhost:8080";
   private short requestCount = 300;
   private String configPath = "target/classes/config.txt";

   public WebConnection() {
   }

   public WebConnection(String url, short requestCount) {
      if(url.toLowerCase().startsWith("http:"))
         this.url = url;
      if(requestCount>0)
         this.requestCount = requestCount;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public short getRequestCount() {
      return requestCount;
   }

   public void setRequestCount(short requestCount) {
      this.requestCount = requestCount;
   }
   private void parseURL() {
      BufferedReader bufferedReader = null;
      String temp;
      try {
         bufferedReader = new BufferedReader(new FileReader(configPath));
         while ((temp = bufferedReader.readLine()) != null) {
            int start = temp.indexOf("\"");
            int end = 0;
            if (start >= 0) {
               end = temp.indexOf("\"", start + 1);
               if (end == -1)
                  end = temp.length();
               url = temp.substring(start+1, end);
               break;

            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   private void parseCountTry(){
      BufferedReader bufferedReader = null;
      String temp;
      try {
         bufferedReader = new BufferedReader(new FileReader(configPath));
         while ((temp = bufferedReader.readLine())!=null){
            int start = temp.indexOf("count=\"");
            int end=0;
            if(start>=0){
               start+=7;
               end = temp.indexOf("\"",start+1);
               if(end==-1)
                  end = temp.length();
               requestCount = Short.parseShort(temp.substring(start,end));
               break;
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void readConfig(){
      parseURL();
      parseCountTry();
   }

}
