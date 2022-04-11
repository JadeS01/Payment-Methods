package dao;

import com.mongodb.client.MongoCollection;
import dto.BasePaymentDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.CashPayment;
import dto.CreditCardPayment;
import org.bson.Document;
import org.bson.types.ObjectId;

public class PaymentDao implements BaseDao<BasePaymentDto> {

  private static PaymentDao instance;
  public MongoCollection<Document> collection; // TODO instead of using a list, directly use mongo to load/store

  private PaymentDao(MongoCollection<Document> collection){
    this.collection = collection;
  }

  public static PaymentDao getInstance() {
    if (instance == null) {
      instance = new PaymentDao(MongoConnection.getCollection("Payments"));
    }
    return instance;
  }

  public static PaymentDao getInstance(MongoCollection<Document> collection) {
    instance = new PaymentDao(collection);
    return instance;
  }

  @Override
  public void put(BasePaymentDto basePaymentDto) {
    /** Directory loading */
    collection.insertOne(basePaymentDto.toDocument());
  }

  @Override
  public BasePaymentDto get(String id) {
    Document payment = collection.find(new Document()).first();
    CashPayment CP = new CashPayment();
    /** Populate CCP variables */
    CreditCardPayment CCP = new CreditCardPayment(
            payment.getDouble("amount"),
            payment.getString("number"),
            payment.getString("securityCode")
    );
    /** If it's a cash payment, set variables accordingly. */
    if(payment.get("type").equals("cash")){
      CP.setAmount(payment.getDouble("amount"));
      CP.setUniqueId(payment.get("_id").toString());
      return CP;
    }
    /** If it's a CC payment, set variables accordingly. */
    if(payment.get("type").equals("credit")){
      CCP.setUniqueId(payment.get("_id").toString());
      return CCP;
    }
    /** If it's neither type, return null. */
    return null;
  }

  @Override
  public List<BasePaymentDto> getAll(){
    List<BasePaymentDto> getAll = new ArrayList<>();
    List<Document> found = collection.find().into(new ArrayList<>());
    /** Insert all found items from mongo into the BPD list */
    for(int i = 0; i < found.size(); i++){
      /** Convert found document type items into BPD type */
      getAll.add(BasePaymentDto.toDto(found.get(i)));
    }
    return getAll;
  }
}
