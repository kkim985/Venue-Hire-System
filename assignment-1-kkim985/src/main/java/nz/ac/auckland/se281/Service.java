package nz.ac.auckland.se281;

public abstract class Service {
  protected String serviceName;
  protected int cost;
  protected String bookingReference;

  public Service(String serviceName, int cost, String bookingReference) {
    this.serviceName = serviceName;
    this.cost = cost;
    this.bookingReference = bookingReference;
  }

  public String getServiceName() {
    return serviceName;
  }

  public int getCost() {
    return cost;
  }

  public String getBookingReference() {
    return bookingReference;
  }
}
