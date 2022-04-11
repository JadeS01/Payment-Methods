package server;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.CashPayment;
import dto.CreditCardPayment;
import handler.BaseHandler;
import handler.HandlerFactory;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import request.CustomParser;
import request.ParsedRequest;

public class Server {

  public static void main(String[] args) {
    ServerSocket ding;
    Socket dong = null;
    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          dong = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = dong.getInputStream();
        byte[] b = new byte[1024*20];
        stream.read(b);
        String input = new String(b).trim();
        System.out.println(input);

        BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client

        // HTTP Response
        if(!input.isEmpty()){
          writer.println(processRequest(input));
        }else{
          writer.println("HTTP/1.1 200 OK");
          writer.println("Server: TEST");
          writer.println("Connection: close");
          writer.println("Content-type: text/html");
          writer.println("");
        }

        dong.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }

  public static String processRequest(String requestString){
    String header = requestString.split("\n")[0].split(" ")[2] + " 200 OK\n\n";
    String path = requestString.split("\n")[0].split(" ")[1];
    switch(path) {
      case "/getAllPayments":
        return header + new Gson().toJson(PaymentDao.getInstance().getAll());
      case "/makeCashPayment":
        String str[] = requestString.split("\n");
        String strlen = str[str.length - 1];
        PaymentDao.getInstance().put(new Gson().fromJson(strlen, CashPayment.class));
        return "HTTP/1.1 200 OK\n";
      case "/makeCreditCardPayment":
        String str2[] = requestString.split("\n");
        String strlen2 = str2[str2.length - 1];
        PaymentDao.getInstance().put(new Gson().fromJson(strlen2, CreditCardPayment.class));
        return "HTTP/1.1 200 OK\n";
    }
    return new Gson().toJson(PaymentDao.getInstance().get(""));
  }

}
