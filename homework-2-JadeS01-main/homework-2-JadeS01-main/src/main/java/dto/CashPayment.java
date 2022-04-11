package dto;

import org.bson.Document;

public class CashPayment extends BasePaymentDto {

  private String type = "cash";

  public CashPayment() {
  }

  public CashPayment(String uniqueId, Double amount) {
    super(uniqueId);
    this.amount = amount;
  }

  public CashPayment(Double amount) {
    super();
    this.amount = amount;
  }

  @Override
  public Document toDocument() {
    Document toDocument = new Document();
    toDocument.put("amount", amount);
    toDocument.put("type", type);
    toDocument.put("uniqueId", getUniqueId());
    return toDocument;
  }

  public static CashPayment fromDocument(Document document) {
    CashPayment CP = new CashPayment(
            document.getString("_id"),
            document.getDouble("amount"));
    return CP;
  }
}
