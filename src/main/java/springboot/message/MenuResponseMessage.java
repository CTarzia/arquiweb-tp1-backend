package springboot.message;

public class MenuResponseMessage {
  private String message;

  public MenuResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
