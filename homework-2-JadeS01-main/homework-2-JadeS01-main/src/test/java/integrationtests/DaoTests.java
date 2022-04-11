package integrationtests;
import com.mongodb.client.MongoCollection;
import dao.PaymentDao;
import dto.CashPayment;
import org.mockito.Mockito;
import org.testng.annotations.Test;

public class DaoTests {

  @Test
  public void testPut() {
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    PaymentDao dao = PaymentDao.getInstance(mongoCollection);
    Mockito.doNothing().when(mongoCollection).insertOne(Mockito.any());
    CashPayment dto = new CashPayment(null, null);
    dao.put(dto);
    Mockito.verify(mongoCollection).insertOne(Mockito.any());
  }
}
