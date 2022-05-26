package sg.edu.np.mad.mad_exercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Connect to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mad-exercise-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();

        // Get EditText views
        EditText usernameView = findViewById(R.id.username);
        EditText passwordView = findViewById(R.id.password);

        // Get button view
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get login details from view
                String nameValue = usernameView.getText().toString();
                String passwordValue = passwordView.getText().toString();

                // Read from the database
                myRef.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        // For each user in database
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            // Get password and username
                            String password = dataSnapshot1.child("password").getValue(String.class);
                            String username = dataSnapshot1.child("username").getValue(String.class);

                            if (TextUtils.equals(nameValue, username) && TextUtils.equals(passwordValue, password) ){
                                // Change to listActivity
                                Intent intent = new Intent(LoginActivity.this,ListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                // Tell user that username or password is not correct
                                Toast.makeText(getApplicationContext(),"Username or Password is Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}