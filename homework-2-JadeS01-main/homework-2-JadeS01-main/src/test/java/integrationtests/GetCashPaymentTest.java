package integrationtests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dao.PaymentDao;
import dto.CashPayment;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import server.Server;

public class GetCashPaymentTest {

  private static final Gson gson = new Gson();

  @Test
  public void getPayment(){
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    PaymentDao dao = PaymentDao.getInstance(mongoCollection);;

    var id = new ObjectId();
    String test1 = "GET /getPayment?id=" + id.toString() +" HTTP/1.1\n"
        + "User-Agent: made up browser\n"
        + "\n";
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    Mockito.doReturn(findIterable).when(mongoCollection).find((Bson) Mockito.any());
    var sampleDoc = new Document("amount", 123.3)
        .append("type", "cash")
        .append("_id", id);
    List<Document> payments = new ArrayList<>();
    payments.add(sampleDoc);
    Mockito.doReturn(sampleDoc).when(findIterable).first();
    String response = Server.processRequest(test1);
    var body = response.substring(response.indexOf('\n')+1).trim();
    CashPayment data = gson.fromJson(body, CashPayment.class);
    Assert.assertEquals(data.getUniqueId(), id.toString());
    Assert.assertEquals(data.amount, 123.3);
  }

}
