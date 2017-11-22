package Max.Che;

/**
 * Created by maxch on 14-Sep-17.
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;


public class HttpServer {

    public static void main(String[] args) throws Throwable {
        WebConnection test = new WebConnection();
        HttpURLConnection connection = null;
        test.readConfig();
        int count =0;
        try {
            while (count++<test.getRequestCount()) {
            connection = getHttpURLConnection(test, connection);
            StringBuilder stringBuilder = new StringBuilder();
            connection.connect();
                if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "cp1251"));
                    if(bufferedReader.ready()) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {      // чтение полученого ответа
                            stringBuilder.append(line);                           // фотмирование его в строку
                            stringBuilder.append("\n");                           //
                        }
                        System.out.println(stringBuilder.toString());             //вывод всего ответа
                        System.out.println(count);
                        connection.disconnect();
                    }

                   Thread.sleep(1000 / test.getRequestCount());

                }else{
                System.out.println(count+ " ((((( "+ connection.getResponseCode()+ ", "+connection.getResponseMessage());
                }
            }
        }
        catch (Throwable t){
            t.printStackTrace();
            System.err.println(t.getMessage());
            System.out.println("try count "+count);
        }finally {
            if(connection!=null)
            connection.disconnect();
        }
        System.out.println("count try: "+count);
    }

    private static HttpURLConnection getHttpURLConnection(WebConnection test, HttpURLConnection connection) throws IOException {
        connection = (HttpURLConnection) new URL(test.getUrl()).openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        //connection.setConnectTimeout(200);
        //connection.setReadTimeout(1000);
        return connection;
    }
}
