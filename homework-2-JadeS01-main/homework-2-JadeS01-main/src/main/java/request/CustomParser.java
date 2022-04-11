package request;

public class CustomParser {

  public static ParsedRequest parse(String request){
    ParsedRequest PR = new ParsedRequest();
    String[] parsed = request.split("[\\n\\s\\t\\r\\?\\=]");
    PR.setPath(parsed[1]);
    PR.setMethod(parsed[0]);
    PR.setPath(parsed[1]);
    PR.setQueryParam(parsed[2], parsed[3]);
    PR.setBody(parsed[parsed.length - 1]);
    return PR;
  }
}
