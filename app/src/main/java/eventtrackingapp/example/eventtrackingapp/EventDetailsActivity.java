package eventtrackingapp.example.eventtrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eventtime.eventtrackingapp.R;

public class EventDetailsActivity extends AppCompatActivity {

    private EditText editTextName, editTextDate, editTextTime, editTextDetails;
    private Event selectedEvent;

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


    // method removing events from database
    public void deleteEvent(View view)    {
        Intent previousIntent = getIntent();

        int passedEventID = previousIntent.getIntExtra(Event.EVENT_EDIT_EXTRA, -1);
        EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);
        databaseManager.deleteEventInDB(passedEventID);

        finish();
    }
}