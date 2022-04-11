package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.BasePaymentDto;
import dto.CashPayment;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;

public class CashPaymentHandler  implements BaseHandler{

  private static final Gson gson = new Gson();

  // Only Post
  @Override
  public String handleRequest(ParsedRequest request) {
    var type = PaymentDao.getInstance();
    BasePaymentDto typePay = gson.fromJson(request.getBody(), CashPayment.class);
    type.put(typePay.setUniqueId(String.valueOf(Math.random())));
    CustomHttpResponse typeRes = new ResponseBuilder().setStatus("200 Ok")
            .setVersion("HTTP/1.1").build();
    return typeRes.toString();
  }
}
