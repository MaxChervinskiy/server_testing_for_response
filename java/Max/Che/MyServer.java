package Max.Che;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpServer;

/**
 * Created by maxch on 01-Nov-17.
 */
public class MyServer {
    public static void main(String[] args) {

        HttpServer server = null;
        try {
            server = HttpServer.create();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.bind(new InetSocketAddress(8080), 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                StringBuilder builder = new StringBuilder();

                builder.append("<h1>URI: ").append(httpExchange.getRequestURI()).append("</h1>");

                Headers headers = httpExchange.getRequestHeaders();
                for (String header : headers.keySet()) {
                    builder.append("<p>").append(header).append("=")
                            .append(headers.getFirst(header)).append("</p>");
                }

                byte[] bytes = builder.toString().getBytes();
                httpExchange.sendResponseHeaders(200, bytes.length);


                OutputStream os = httpExchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        });
        server.start();
    }
}
