package eventtrackingapp.example.eventtrackingapp;

import java.util.ArrayList;

public class Event {

    public static ArrayList<Event> eventArrayList = new ArrayList<>(); // arrayList to hold events
    public static String EVENT_EDIT_EXTRA="editEvent";
    private int eventID;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventDetails;

    public Event(int id, String name, String date, String time, String details )   {
        this.eventID = id;
        this.eventName = name;
        this.eventDate = date;
        this.eventTime = time;
        this.eventDetails = details;
    }

    public static Event getEventForID(int passedEventID) {
        for (Event event: eventArrayList)   {
            if(event.getEventID() == passedEventID)
                return event;
        }
        return null;
    }

    public int getEventID()  {
        return eventID;
    }
    public String getEventName()    {
        return eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public String getEventTime() {
        return eventTime;
    }
    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventID(int id){
        this.eventID = id;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public void setEventDate(String date) {
        this.eventDate = date;
    }

    public void setEventTime(String time) {
        this.eventTime = time;
    }

    public void setEventDetails(String details) {
        this.eventDetails = details;
    }
}
