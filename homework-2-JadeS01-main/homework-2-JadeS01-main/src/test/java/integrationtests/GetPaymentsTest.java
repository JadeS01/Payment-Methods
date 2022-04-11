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
import org.bson.types.ObjectId;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import server.Server;

public class GetPaymentsTest {

  private static final Gson gson = new Gson();

  @Test
  public void getAllPayment(){
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    PaymentDao dao = PaymentDao.getInstance(mongoCollection);
    String test1 = "GET /getAllPayments HTTP/1.1\n"
        + "User-Agent: made up browser\n"
        + "\n";
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    Mockito.doReturn(findIterable).when(mongoCollection).find();
    List<Document> payments = new ArrayList<>();
    Mockito.doReturn(payments).when(findIterable).into(Mockito.any());
    String response = Server.processRequest(test1);
    Assert.assertEquals(response, "HTTP/1.1 200 OK\n\n" + gson.toJson(payments));
    var sampleDoc = new Document("amount", 123.3)
        .append("type", "cash")
            .append("_id", new ObjectId());
    payments.add(sampleDoc);
    response = Server.processRequest(test1);
    var body = response.substring(response.indexOf('\n')+1);
    Type collectionType = new TypeToken<ArrayList<CashPayment>>(){}.getType();
    ArrayList<CashPayment> data = gson.fromJson(body, collectionType);
    System.out.println(data);
    Assert.assertEquals(data.size(), 1);
    Assert.assertEquals(data.get(0).amount, 123.3);
    Assert.assertEquals(data.get(0).getUniqueId(), sampleDoc.get("_id").toString());
  }

}
