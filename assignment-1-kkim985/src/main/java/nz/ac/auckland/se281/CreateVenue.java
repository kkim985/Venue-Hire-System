package nz.ac.auckland.se281;

public class CreateVenue {
  private String venueName;
  private String venueCode;
  private String capacityInput;
  private String hireFeeInput;

  public CreateVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    this.venueName = venueName;
    this.venueCode = venueCode;
    this.capacityInput = capacityInput;
    this.hireFeeInput = hireFeeInput;
  }

  // create getter methods to be able to call them
  public String getVenueName() {
    return venueName;
  }

  public String getVenueCode() {
    return venueCode;
  }

  public String getCapacityInput() {
    return capacityInput;
  }

  public String getHireFeeInput() {
    return hireFeeInput;
  }
}
