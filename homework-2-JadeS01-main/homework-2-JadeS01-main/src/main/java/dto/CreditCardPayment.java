package dto;

import org.bson.Document;

public class CreditCardPayment extends BasePaymentDto {

  private String number;
  private String securityCode;
  private static String type = "credit";

  public CreditCardPayment(Double amount, String number, String securityCode) {
    super();
    this.amount = amount;
    this.number = number;
    this.securityCode = securityCode;
  }

  @Override
  public Document toDocument() {
    Document toDocument = new Document();
    toDocument.put("amount", amount);
    toDocument.put("type", type);
    toDocument.put("number", number);
    toDocument.put("securityCode", securityCode);
    return toDocument;
  }

  public static CreditCardPayment fromDocument(Document document){
    CreditCardPayment CCP = new CreditCardPayment(
            document.getDouble("amount"),
            document.getString("number"),
            document.getString("securityCode")

    );
    return CCP;
  }

  public String getNumber() {
    return number;
  }

  public String getSecurityCode() {
    return securityCode;
  }

  public String getType() {
    return type;
  }
}
