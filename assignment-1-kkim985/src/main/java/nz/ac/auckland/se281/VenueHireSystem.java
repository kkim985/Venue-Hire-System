package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class VenueHireSystem {

  private ArrayList<CreateVenue> venueList;
  private String systemDate;
  private ArrayList<MakeBooking> bookingList;
  private String venueCapacity;
  private ArrayList<String> bookedDateList;
  private ArrayList<CateringService> cateringServiceList;
  private ArrayList<MusicService> musicServiceList;
  private ArrayList<FloralService> floralServiceList;

  public VenueHireSystem() {
    this.venueList = new ArrayList<CreateVenue>();
    this.systemDate = null;
    this.bookingList = new ArrayList<MakeBooking>();
    this.bookedDateList = new ArrayList<>();
    this.cateringServiceList = new ArrayList<CateringService>();
    this.musicServiceList = new ArrayList<MusicService>();
    this.floralServiceList = new ArrayList<FloralService>();
  }

  public void printVenues() {
    // create a string array to store numbers in alphabets
    String[] number =
        new String[] {"two", "three", "four", "five", "six", "seven", "eight", "nine"};

    // print the NUMBER_VENUES message for different cases
    if (venueList.size() == 0) {
      MessageCli.NO_VENUES.printMessage();
    } else if (venueList.size() == 1) {
      MessageCli.NUMBER_VENUES.printMessage("is", "one", "");
    } else if (venueList.size() > 1 && venueList.size() < 10) {
      String numOfVenues = number[venueList.size() - 2];
      MessageCli.NUMBER_VENUES.printMessage("are", numOfVenues, "s");
    } else {
      String numberOfVenues = String.valueOf(venueList.size());
      MessageCli.NUMBER_VENUES.printMessage("are", numberOfVenues, "s");
    }

    if (systemDate == null) {
      for (CreateVenue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.getVenueName(),
            venue.getVenueCode(),
            venue.getCapacityInput(),
            venue.getHireFeeInput(),
            "");
      }
    } else {
      // print the list of venues that has been saved so far
      for (CreateVenue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.getVenueName(),
            venue.getVenueCode(),
            venue.getCapacityInput(),
            venue.getHireFeeInput(),
            nextAvailableDate(venue.getVenueName()));
      }
    }
  }

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {

    CreateVenue venue = new CreateVenue(venueName, venueCode, capacityInput, hireFeeInput);

    // check if venueName is empty
    if (venueName.isEmpty()) {
      MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
      return;
    }

    // check if there is a code identical to venueCode
    for (CreateVenue venueArrayList : venueList) {
      if (venueArrayList.getVenueCode().equals(venueCode)) {
        MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(
            venueCode, venueArrayList.getVenueName());
        return;
      }
    }

    // check if the value of the capacityInput is valid
    try {
      int capacityNumber = Integer.parseInt(capacityInput);
      if (capacityNumber < 0) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", " positive");
        return;
      }
    } catch (Exception e) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
      return;
    }

    // check if the value of the hireFeeInput is valid
    try {
      int hireFeeNumber = Integer.parseInt(hireFeeInput);
      if (hireFeeNumber < 0) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", " positive");
        return;
      }
    } catch (Exception e) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
      return;
    }

    // add the variables to the ArrayList to save them
    venueList.add(venue);
    MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
    return;
  }

  public void setSystemDate(String dateInput) {
    systemDate = dateInput;
    MessageCli.DATE_SET.printMessage(systemDate);
  }

  public void printSystemDate() {
    if (systemDate == null) {
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      MessageCli.CURRENT_DATE.printMessage(systemDate);
    }
  }

  public void makeBooking(String[] options) {

    // create variables
    String venueCode = options[0];
    String bookingDate = options[1];
    String email = options[2];
    String attendees = options[3];
    String venueName = null;
    Boolean checkVenueCode = false;

    // check if the system's date has not been set
    if (systemDate == null) {
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
      return;
    }

    // check if there are no venues in the system
    if (venueList.size() == 0) {
      MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      return;
    }

    // iterating through the venueList array and saving the venueName if the venueCode matches
    for (CreateVenue venue : venueList) {
      if (venue.getVenueCode().equals(venueCode)) {
        venueName = venue.getVenueName();
        checkVenueCode = true;
        break;
      }
    }

    // check if checkVenueCode is still false
    if (checkVenueCode == false) {
      MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }

    // splitting the bookingDate and systemDate into day, month, and year
    // bookingDate
    String[] bookingDateParts = bookingDate.split("/");
    String bookingDateDay = bookingDateParts[0];
    String bookingDateMonth = bookingDateParts[1];
    String bookingDateYear = bookingDateParts[2];
    // systemDate
    String[] systemDateParts = systemDate.split("/");
    String systemDateDay = systemDateParts[0];
    String systemDateMonth = systemDateParts[1];
    String systemDateYear = systemDateParts[2];

    // convert the string variables of the bookingDate and systemDate into integers
    // bookingDate
    int bookingDateDayInt = Integer.parseInt(bookingDateDay);
    int bookingDateMonthInt = Integer.parseInt(bookingDateMonth);
    int bookingDateYearInt = Integer.parseInt(bookingDateYear);
    // systemDate
    int systemDateDayInt = Integer.parseInt(systemDateDay);
    int systemDateMonthInt = Integer.parseInt(systemDateMonth);
    int systemDateYearInt = Integer.parseInt(systemDateYear);

    // check if the booking date is in the past
    if (bookingDateYearInt < systemDateYearInt
        || bookingDateYearInt < systemDateYearInt && bookingDateMonthInt < systemDateMonthInt
        || bookingDateYearInt < systemDateYearInt
            && bookingDateMonthInt < systemDateMonthInt
            && bookingDateDayInt < systemDateDayInt) {
      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(bookingDate, systemDate);
      return;
    }

    // check if there is a booking on the booking date
    for (MakeBooking currentBooking : bookingList) {
      if (currentBooking.getBookingDate().equals(bookingDate)
          && currentBooking.getVenueName().equals(venueName)) {
        MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(
            currentBooking.getVenueName(), bookingDate);
        return;
      }
    }

    // calling the capacity of the venue
    for (CreateVenue venue : venueList) {
      if (venue.getVenueCode().equals(venueCode)) {
        venueCapacity = venue.getCapacityInput();
        break;
      }
    }

    // check if the number of attendees needs adjustment
    if (Integer.parseInt(attendees) < (Integer.parseInt(venueCapacity) / 4)) {
      attendees = Integer.toString(Integer.parseInt(venueCapacity) / 4);
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], attendees, venueCapacity);
    } else if (Integer.parseInt(attendees) > Integer.parseInt(venueCapacity)) {
      attendees = venueCapacity;
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], attendees, venueCapacity);
    }

    MakeBooking booking = new MakeBooking(venueName, bookingDate, attendees, email);

    bookingList.add(booking);
    MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(
        booking.getBookingReference(), venueName, bookingDate, attendees);
    return;
  }

  public String nextDate(String currentDate) {
    // splitting the date variable into seperate variable (day, month, year)
    String[] dateParts = currentDate.split("/");
    String day = dateParts[0];
    String month = dateParts[1];
    String year = dateParts[2];

    // convert the string variables of the date into integers
    int dayInt = Integer.parseInt(day);
    int monthInt = Integer.parseInt(month);
    int yearInt = Integer.parseInt(year);

    dayInt = dayInt + 1;

    return String.format("%02d/%02d/%04d", dayInt, monthInt, yearInt);
  }

  public String nextAvailableDate(String venueName) {
    String date = systemDate;

    // iterate through the bookingList and if there is the same venue as the input venue, add to the
    // bookedDateList array
    for (MakeBooking booking : bookingList) {
      if (venueName.equals(booking.getVenueName())) {
        bookedDateList.add(booking.getBookingDate());
      }
    }
    while (bookedDateList.contains(date)) {
      date = nextDate(date);
    }

    // reset the bookedDateList for next time
    bookedDateList.removeAll(bookedDateList);
    return date;
  }

  public void printBookings(String venueCode) {

    String venueName = null;
    Boolean existVenueCode = false;
    Boolean existBooking = false;

    // finding the venueName that matches the venueCode
    for (CreateVenue venue : venueList) {
      if (venue.getVenueCode().equals(venueCode)) {
        venueName = venue.getVenueName();
        existVenueCode = true;
        MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venueName);
      }
    }

    if (existVenueCode == false) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }

    for (MakeBooking booking : bookingList) {
      if (booking.getVenueName().equals(venueName)) {
        MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(
            booking.getBookingReference(), booking.getBookingDate());
        existBooking = true;
      }
    }

    if (existBooking == false) {
      MessageCli.PRINT_BOOKINGS_NONE.printMessage(venueName);
    }
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    // create variables to initialise
    Boolean existBookingReference = false;
    String serviceName = "Catering";

    // iterate through the bookingList to check if the bookingReference from the input exists
    for (MakeBooking booking : bookingList) {
      if (booking.getBookingReference().equals(bookingReference)) {
        existBookingReference = true;
      }
    }
    if (existBookingReference == false) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage(serviceName, bookingReference);
      return;
    }

    CateringService cateringService =
        new CateringService(
            cateringType.getName(), cateringType.getCostPerPerson(), bookingReference);
    cateringServiceList.add(cateringService);

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        serviceName + " (" + cateringType.getName() + ")", bookingReference);
  }

  public void addServiceMusic(String bookingReference) {
    // initialise the variables
    Boolean existBookingReference = false;
    int cost = 500;
    String serviceName = "Music";

    // iterate through the bookingList to check if the bookingReference from the input exists
    for (MakeBooking booking : bookingList) {
      if (booking.getBookingReference().equals(bookingReference)) {
        existBookingReference = true;
      }
    }
    if (existBookingReference == false) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage(serviceName, bookingReference);
      return;
    }

    MusicService musicService = new MusicService(serviceName, cost, bookingReference);
    musicServiceList.add(musicService);

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(serviceName, bookingReference);
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    // initialise the variables
    Boolean existBookingReference = false;
    String serviceName = "Floral";

    // iterate through the bookingList to check if the bookingReference from the input exists
    for (MakeBooking booking : bookingList) {
      if (booking.getBookingReference().equals(bookingReference)) {
        existBookingReference = true;
      }
    }
    if (existBookingReference == false) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage(serviceName, bookingReference);
      return;
    }

    FloralService floralService =
        new FloralService(floralType.getName(), floralType.getCost(), bookingReference);
    floralServiceList.add(floralService);

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        serviceName + " (" + floralType.getName() + ")", bookingReference);
  }

  public void viewInvoice(String bookingReference) {
    // initialise the variables
    Boolean existBookingReference = false;
    String email = null;
    String dateOfBooking = systemDate;
    String partyDate = null;
    String numOfGuest = null;
    String venueName = null;
    String hireFee = null;
    String cateringType = null;
    int cateringCost = 0;
    int musicCost = 0;
    String floralType = null;
    int floralCost = 0;

    // iterate through the bookingList to check if the bookingReference from the input exists
    for (MakeBooking booking : bookingList) {
      if (booking.getBookingReference().equals(bookingReference)) {
        existBookingReference = true;
        email = booking.getEmail();
        partyDate = booking.getBookingDate();
        numOfGuest = booking.getAttendees();
        venueName = booking.getVenueName();
      }
    }
    if (existBookingReference == false) {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(bookingReference);
      return;
    }

    // printing the top half of the invoice
    MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(
        bookingReference, email, dateOfBooking, partyDate, numOfGuest, venueName);

    // getting the venue hire fee
    for (CreateVenue venue : venueList) {
      if (venue.getVenueName().equals(venueName)) {
        hireFee = venue.getHireFeeInput();
      }
    }
    MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(hireFee);

    // checking if the booking reference has Catering Service
    for (CateringService cateringService : cateringServiceList) {
      if (cateringService.getBookingReference().equals(bookingReference)) {
        cateringType = cateringService.getServiceName();
        cateringCost = cateringService.getCost() * Integer.parseInt(numOfGuest);
        MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(
            cateringType, Integer.toString(cateringCost));
      }
    }

    // checking if the booking reference has music service
    for (MusicService musicService : musicServiceList) {
      if (musicService.getBookingReference().equals(bookingReference)) {
        musicCost = musicService.getCost();
        MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage(Integer.toString(musicCost));
      }
    }

    // checking if the booking reference has floral service
    for (FloralService floralService : floralServiceList) {
      if (floralService.getBookingReference().equals(bookingReference)) {
        floralType = floralService.getServiceName();
        floralCost = floralService.getCost();
        MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(
            floralType, Integer.toString(floralCost));
      }
    }

    int totalCost = Integer.parseInt(hireFee) + cateringCost + musicCost + floralCost;

    MessageCli.INVOICE_CONTENT_BOTTOM_HALF.printMessage(Integer.toString(totalCost));
  }
}
