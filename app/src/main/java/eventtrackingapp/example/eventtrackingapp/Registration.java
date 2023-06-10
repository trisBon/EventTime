package eventtrackingapp.example.eventtrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.eventtime.eventtrackingapp.R;

public class Registration extends AppCompatActivity {

    private User aUser; // never called, but used in onSaveClicked
    private EditText editTextUsername, editTextPassword;

    //private PermissionEnum permissionSelect = PermissionEnum.YES;
    public static final String CHANNEL_APP_EVENTS = "appChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initWidgets();
    }

    private void initWidgets() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupYNL);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButtonYes:
                 //   permissionSelect = PermissionEnum.YES;
                    break;

                case R.id.radioButtonNo:
                //    permissionSelect = PermissionEnum.NO;
                    break;

                case R.id.radioButtonAskLater:
                 //   permissionSelect = PermissionEnum.LATER;
                    break;
            }
        });
    }

    public void onSaveClicked(View view) {
        EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);

        String user = String.valueOf(editTextUsername.getText());
        String password = String.valueOf(editTextPassword.getText());

       // int permissionSelection = this.permissionSelect.getValue();

        if (aUser == null)  {
            User newUser = new User(user,password);
            databaseManager.addUser(newUser);
        }

        finish();

        Intent intent = new Intent(this, EventGrid.class);
        intent.putExtra(User.USER_EXTRA,user);
        startActivity(intent);
    }

}