package response;

import java.util.Map;
import java.util.Map.Entry;

public class CustomHttpResponse {
  public final Map<String,String> headers;
  public final String status;
  public final String version;
  public final String body;

  public CustomHttpResponse(Map<String, String> headers, String status, String version,
      String body) {
    this.headers = headers;
    this.status = status;
    this.version = version;
    this.body = body;
  }

  public String toString(){
    StringBuilder string = new StringBuilder(this.version + " " + this.status + "\n");
    for (Map.Entry<String, String> entry : this.headers.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      string.append(key + ": " + value + "\n");
    }
    if(this.body != null){
      string.append("\n" + this.body);
    }
    return string.toString();
  }
}
