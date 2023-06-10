package eventtrackingapp.example.eventtrackingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eventtime.eventtrackingapp.R;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(Context context, List<Event> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView name = convertView.findViewById(R.id.cellName);
        TextView date = convertView.findViewById(R.id.cellDate);
        TextView time = convertView.findViewById(R.id.cellTime);
        TextView details = convertView.findViewById(R.id.cellDetails);


        name.setText(event.getEventName());
        date.setText(event.getEventDate());
        time.setText(event.getEventTime());
        details.setText(event.getEventDetails());

        return convertView;
    }

}