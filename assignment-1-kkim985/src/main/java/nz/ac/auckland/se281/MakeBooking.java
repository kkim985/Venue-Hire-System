package nz.ac.auckland.se281;

public class MakeBooking {
  private String venueName;
  private String bookingDate;
  private String attendees;
  private String bookingReference;
  private String email;

  public MakeBooking(String venueName, String bookingDate, String attendees, String email) {
    this.venueName = venueName;
    this.bookingDate = bookingDate;
    this.attendees = attendees;
    this.bookingReference = BookingReferenceGenerator.generateBookingReference();
    this.email = email;
  }

  // create getter methods to be able to call them
  public String getVenueName() {
    return venueName;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public String getAttendees() {
    return attendees;
  }

  public String getBookingReference() {
    return bookingReference;
  }

  public String getEmail() {
    return email;
  }
}
