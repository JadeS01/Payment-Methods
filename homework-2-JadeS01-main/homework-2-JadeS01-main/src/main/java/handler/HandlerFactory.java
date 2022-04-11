package handler;

import request.ParsedRequest;
import request.CustomParser;

public class HandlerFactory {

  public static BaseHandler getHandler(ParsedRequest request) {
    String path = request.getPath();
    String method = request.getMethod();
    /** cash: only post */
    if(path.equals("/makeCashPayment") && method.equals("POST")){
      return new CashPaymentHandler();
    }
    /** credit: only post*/
    if(path.equals("/makeCreditCardPayment") && method.equals("POST")){
      return new CreditCardPaymentHandler();
    }
    /** get methods */
    if(path.equals("/getAllPayments") && method.equals("GET")){
      return new GetAllPaymentsHandler();
    }
    if(path.equals("/getPayment") && method.equals("GET")){
      return new GetPaymentHandler();
    }
    /** default */
    return new FallbackHandler();
  }

}
