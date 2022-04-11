package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;

public class GetAllPaymentsHandler implements BaseHandler {

  private static final Gson gson = new Gson();

  @Override
  public String handleRequest(ParsedRequest request) { // get all
    var type = PaymentDao.getInstance().getAll();
    String typePay = gson.toJson(type.get(0));
    CustomHttpResponse typeRes = new ResponseBuilder().setStatus("200 OK")
            .setVersion("HTTP/1.1").setBody(typePay).build();
    return typeRes.toString();
  }
}
