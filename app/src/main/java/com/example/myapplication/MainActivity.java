package com.example.myapplication;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.util.Arrays;
        import java.util.List;
        import java.util.stream.Collectors;

        import io.realm.Realm;
        import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView loginStatusTextView;

    Button LoginButton, RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginStatusTextView = findViewById(R.id.login_status_text_view);
        LoginButton = findViewById(R.id.button_Login);
        RegisterButton = findViewById(R.id.button_Register);
        realm = Realm.getDefaultInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    public void login() {
        // Get the username and password from the edit texts
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the user exists in the database
        RealmResults<User> users = realm.where(User.class).findAll();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // The user exists, so log them in
                loginStatusTextView.setText("Login successful");
                return;
            }
        }

        // The user does not exist, so show an error message
        loginStatusTextView.setText("Login failed");
    }

    public void register() {
        // Get the username and password from the edit texts
        // Get the usernames and passwords from the edit texts
        String userN = usernameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();

        List<String> usernames = Arrays.asList(usernameEditText.getText().toString(), passwordEditText.getText().toString());

        // Create a new user object for each username and password
        List<User> users = usernames.stream().map(username -> new User(userN, pass)).collect(Collectors.toList());

        // Save the user to the database
        realm.beginTransaction();
        realm.insert(users);
        realm.commitTransaction();

        // Clear the edit texts
        usernameEditText.setText("");
        passwordEditText.setText("");

        // Show a message to indicate that the user has been registered
        loginStatusTextView.setText("User registered");
    }
}


