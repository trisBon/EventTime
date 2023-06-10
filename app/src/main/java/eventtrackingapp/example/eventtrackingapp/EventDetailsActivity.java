package eventtrackingapp.example.eventtrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eventtime.eventtrackingapp.R;

public class EventDetailsActivity extends AppCompatActivity {

    private EditText editTextName, editTextDate, editTextTime, editTextDetails;
    private Event selectedEvent;
    private String passedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        initWidgets();
        checkForEditEvent();
    }

    //method for editing events
    private void checkForEditEvent() {
        Intent previousIntent = getIntent();
        int passedEventID = previousIntent.getIntExtra(Event.EVENT_EDIT_EXTRA, -1);
        selectedEvent = Event.getEventForID(passedEventID);

        if (selectedEvent != null)  {
            editTextName.setText(selectedEvent.getEventName());
            editTextDate.setText(selectedEvent.getEventDate());
            editTextTime.setText(selectedEvent.getEventTime());
            editTextDetails.setText(selectedEvent.getEventDetails());
        }
        passedUser = previousIntent.getStringExtra(User.USER_EXTRA);
    }

    public void initWidgets()   {
        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextDetails = findViewById(R.id.editTextDetails);
    }

    // method to save events to eventViewList and EventDatabase
    public void saveEvent(View view)  {
        EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);
        String name = String.valueOf(editTextName.getText());
        String date = String.valueOf(editTextDate.getText());
        String time = String.valueOf(editTextTime.getText());
        String details = String.valueOf(editTextDetails.getText());

        if (selectedEvent == null)  {
            int id = Event.eventArrayList.size();
            Event newEvent = new Event(id,name,date,time,details);
            Event.eventArrayList.add(newEvent);
            databaseManager.addDatabaseEvent(newEvent);


        }
        // used when events are updated instead of added.
        else    {
            selectedEvent.setEventName(name);
            selectedEvent.setEventDate(date);
            selectedEvent.setEventTime(time);
            selectedEvent.setEventDetails(details);
            databaseManager.updateEventInDB(selectedEvent);

        }

        finish();
    }

    /*
    // method for sending SMS messages when new events are created & setting up notifications
    public void addNotifications() {
        SmsManager smsManager = SmsManager.getDefault();
        try   {
            smsManager.sendTextMessage("+650-555-1212",null, "New Event added!", null, null);
        }
       catch (Exception e) {
            System.out.println(e);
       }

        // notifications text
        String text = "Check messages for new event!";
        Notification notification = new NotificationCompat.Builder(this,Registration.CHANNEL_APP_EVENTS)
                .setSmallIcon(R.drawable.eventtimelogo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }
    */

    // method removing events from database
    public void deleteEvent(View view)    {
        Intent previousIntent = getIntent();

        int passedEventID = previousIntent.getIntExtra(Event.EVENT_EDIT_EXTRA, -1);
        EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);
        databaseManager.deleteEventInDB(passedEventID);

        finish();
    }
}