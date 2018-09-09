package in.ecelladgitm.modelclasses;


import java.io.Serializable;

import androidx.annotation.Keep;


/* Event is a class having member variables and functions to represent an event.
   If event is of one day then no need to provide the end date of event
 */

@Keep
public class Event implements Serializable {

    public static int PAST_EVENT = 0;
    public static int CURRENT_EVENT = 1;

    // All the data members are of type String so that it can be easily saved on firebase.
    private String eventName;
    private String eventVenue;
    private String startDateOfEvent;
    private String endDateOfEvent;
    private String aboutEvent;
    private String organiserOfEvent;
    private String organiserContactNumber;
    private String numberOfDays;
    private String startTimeOfEvent;
    private String endTimeOfEvent;
    private String isRegistrationCompulsory;
    private String registrationLink;
    private String registrationPrice;
    private String featuredPhoto;
    private String venueOnGoogleMap;
    private String fbLink;                      // facebook event link
    private String webLink;

    public Event() {
        startDateOfEvent = "Not Available";
        aboutEvent = "Description is not available";
        organiserOfEvent = "will be updated";
        organiserContactNumber = "will be updated";
        numberOfDays = "Not Decided";
        isRegistrationCompulsory = "No";
        registrationPrice = "Free";
        featuredPhoto = "NA";
        fbLink = null;
        webLink = null;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getStartDateOfEvent() {
        return startDateOfEvent;
    }

    public void setStartDateOfEvent(String startDateOfEvent) {
        this.startDateOfEvent = startDateOfEvent;
    }

    public String getAboutEvent() {
        return aboutEvent;
    }

    public void setAboutEvent(String aboutEvent) {
        this.aboutEvent = aboutEvent;
    }

    public String getOrganiserOfEvent() {
        return organiserOfEvent;
    }

    public void setOrganiserOfEvent(String organiserOfEvent) {
        this.organiserOfEvent = organiserOfEvent;
    }

    public String getOrganiserContactNumber() {
        return organiserContactNumber;
    }

    public void setOrganiserContactNumber(String organiserContactNumber) {
        this.organiserContactNumber = organiserContactNumber;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getIsRegistrationCompulsory() {
        return isRegistrationCompulsory;
    }

    public void setIsRegistrationCompulsory(String isRegistrationCompulsory) {
        this.isRegistrationCompulsory = isRegistrationCompulsory;
    }

    public String getRegistrationPrice() {
        return registrationPrice;
    }

    public void setRegistrationPrice(String registrationPrice) {
        this.registrationPrice = registrationPrice;
    }

    public String getFeaturedPhoto() {
        return featuredPhoto;
    }

    public void setFeaturedPhoto(String featuredPhoto) {
        this.featuredPhoto = featuredPhoto;
    }

    public String getEndDateOfEvent() {
        return endDateOfEvent;
    }

    public void setEndDateOfEvent(String endDateOfEvent) {
        this.endDateOfEvent = endDateOfEvent;
    }

    public String getStartTimeOfEvent() {
        return startTimeOfEvent;
    }

    public void setStartTimeOfEvent(String startTimeOfEvent) {
        this.startTimeOfEvent = startTimeOfEvent;
    }

    public String getEndTimeOfEvent() {
        return endTimeOfEvent;
    }

    public void setEndTimeOfEvent(String endTimeOfEvent) {
        this.endTimeOfEvent = endTimeOfEvent;
    }

    public String getVenueOnGoogleMap() {
        return venueOnGoogleMap;
    }

    public void setVenueOnGoogleMap(String venueOnGoogleMap) {
        this.venueOnGoogleMap = venueOnGoogleMap;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }

    public void setRegistrationLink(String registrationLink) {
        this.registrationLink = registrationLink;
    }
}
