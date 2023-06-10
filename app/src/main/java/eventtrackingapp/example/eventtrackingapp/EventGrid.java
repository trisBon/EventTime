package eventtrackingapp.example.eventtrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.eventtime.eventtrackingapp.R;

public class EventGrid extends AppCompatActivity {

    private GridView eventListView;
    private String passedUsername; // string to hold username to pass down the stack in findUserPermissions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_grid);
        initWidgets();

        Intent previousIntent = getIntent();
        passedUsername = previousIntent.getStringExtra(User.USER_EXTRA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
        loadFromDBToMemory();
        setOnClickListener();

    }

    private void setOnClickListener()
    {
        eventListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Event selectedEvent = (Event) eventListView.getItemAtPosition(position);
            Intent editEventIntent = new Intent(getApplicationContext(), EventDetailsActivity.class);
            editEventIntent.putExtra(Event.EVENT_EDIT_EXTRA, selectedEvent.getEventID());
            editEventIntent.putExtra(User.USER_EXTRA,passedUsername);
            startActivity(editEventIntent);
        });
    }

    // method loading database information into memory to populate the Event Grid
    public void loadFromDBToMemory() {
        Event.eventArrayList.clear();
        EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);
        databaseManager.populateEventArrayList();
    }

    private void initWidgets() {
        eventListView = findViewById(R.id.eventListView);
    }

    private void setEventAdapter() {
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), Event.eventArrayList);
        eventListView.setAdapter(eventAdapter);
    }

    // method to open EventDetailsActivity
    public void onAddClicked(View view) {
        Intent newEventIntent = new Intent (this, EventDetailsActivity.class);
        newEventIntent.putExtra(User.USER_EXTRA,passedUsername);
        startActivity(newEventIntent);
    }
}