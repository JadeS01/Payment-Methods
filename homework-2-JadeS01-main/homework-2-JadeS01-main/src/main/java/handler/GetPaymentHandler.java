package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;

public class GetPaymentHandler implements BaseHandler {

  private static final Gson gson = new Gson();

  @Override
  public String handleRequest(ParsedRequest request) { // get 1
    var type = PaymentDao.getInstance().getAll();
    String typePay = gson.toJson(type.get(0));
    CustomHttpResponse typeRes = new ResponseBuilder().setStatus("200 OK")
            .setVersion("HTTP/1.1").setBody(typePay).build();
    return typeRes.toString();
  }
}
