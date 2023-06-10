package eventtrackingapp.example.eventtrackingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eventtime.eventtrackingapp.R;

public class MainActivity extends Activity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button signInButton;
    Button registerButton;

    public static String usernameInput;
    public static String passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton =findViewById(R.id.buttonSignIn);
        registerButton= findViewById(R.id.buttonRegister);
        editTextUsername = findViewById(R.id.usernameText);
        editTextUsername.addTextChangedListener(textWatcher);
        editTextPassword = findViewById(R.id.passwordText);
        editTextPassword.addTextChangedListener(textWatcher);
        signInButton.setEnabled(false);
    }

    public void onSignInClicked(View view) {

        if(view.getId() == R.id.buttonSignIn)   {
            String name = ((EditText)this.editTextUsername).getText().toString();
            String pass = ((EditText)this.editTextPassword).getText().toString();

            EventDatabase databaseManager = EventDatabase.instanceOfDatabase(this);
            boolean result = databaseManager.findUserInDB(name,pass);
            if (result == true) {
                Intent newEventIntent = new Intent (this, EventGrid.class);
                newEventIntent.putExtra(User.USER_EXTRA,name);
                startActivity(newEventIntent);
            }

            // resets editText fields and presents makeText toast to user to retry
            else    {
                this.editTextUsername.setText("");
                this.editTextPassword.setText("");
                Toast.makeText(this,R.string.toastText,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onRegisterClicked(View view) {
        Intent newEventIntent = new Intent (this, Registration.class);
        startActivity(newEventIntent);
    }

    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            usernameInput = editTextUsername.getText().toString();
            passwordInput = editTextPassword.getText().toString();

            signInButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
