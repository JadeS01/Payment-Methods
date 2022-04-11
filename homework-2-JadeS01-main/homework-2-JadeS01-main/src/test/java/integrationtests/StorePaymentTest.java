package integrationtests;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import dao.PaymentDao;
import dto.BasePaymentDto;
import dto.CashPayment;
import dto.CreditCardPayment;
import java.util.List;
import org.bson.Document;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import server.Server;

public class StorePaymentTest {

  private static final Gson gson = new Gson();

  @Test
  public void makeCashPayment(){
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    PaymentDao dao = PaymentDao.getInstance(mongoCollection);;
    double value = Math.random();
    CashPayment cashPayment = new CashPayment(value);
    ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
    String test1 = "POST /makeCashPayment HTTP/1.1\n"
        + "User-Agent: made up browser\n"
        + "\n"
        + gson.toJson(cashPayment);
    String response = Server.processRequest(test1);
    Assert.assertEquals(response, "HTTP/1.1 200 OK\n");
    Mockito.verify(mongoCollection).insertOne(captor.capture());
    Document doc = captor.getValue();
    Assert.assertEquals(doc.get("amount"), value);
    Assert.assertEquals(doc.get("type"), "cash");
  }

  @Test
  public void makeCreditPayment(){
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    PaymentDao dao = PaymentDao.getInstance(mongoCollection);;
    double value = Math.random();
    var code = String.valueOf(Math.random());
    var number = String.valueOf(Math.random());

    CreditCardPayment payment = new CreditCardPayment(value, number, code);
    ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
    String test1 = "POST /makeCreditCardPayment HTTP/1.1\n"
        + "User-Agent: made up browser\n"
        + "\n"
        + gson.toJson(payment);
    String response = Server.processRequest(test1);
    Assert.assertEquals(response, "HTTP/1.1 200 OK\n");
    Mockito.verify(mongoCollection).insertOne(captor.capture());
    Document doc = captor.getValue();
    Assert.assertEquals(doc.get("amount"), value);
    Assert.assertEquals(doc.get("type"), "credit");
    Assert.assertEquals(doc.get("number"), number);
    Assert.assertEquals(doc.get("securityCode"), code);
  }
}
