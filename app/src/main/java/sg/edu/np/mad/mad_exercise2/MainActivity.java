package sg.edu.np.mad.mad_exercise2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent from ListActivity
        Intent i = getIntent();
        TextView name = findViewById(R.id.maintext);
        TextView desc = findViewById(R.id.subtext);
        // Set name and desc
        User user = i.getParcelableExtra("user");
        name.setText(user.getNameOfUser());
        desc.setText(user.getDescription());

        // Get follow button from xml
        Button followButton = findViewById(R.id.followButton);

        // Create Dbhandler object
        DbHandler db = new DbHandler(this,"mad_exercise6_db");

        // Setting button button text
        // If user is followed, set text to unfollow
        if (user.isFollowed()){
            followButton.setText("Unfollow");
        }
        // If user is not followed, set text to follow
        else{
            followButton.setText("Follow");
        }

        // OnClick event listener
        followButton.setOnClickListener(new View.OnClickListener() {
            // set Follow variable
            boolean follow;

            @Override
            public void onClick(View view) {
                // Unfollow user (currently set to true (followed))
                if (user.isFollowed()){
                    followButton.setText("Follow");
                    follow = false;
                    // Toast notification
                    Toast.makeText(getApplicationContext(),"Unfollowed", Toast.LENGTH_SHORT).show();
                }
                // Follow user (Currently set to false(unfollowed))
                else{
                    followButton.setText("Unfollow");
                    follow = true;
                    // Toast notification
                    Toast.makeText(getApplicationContext(),"Followed", Toast.LENGTH_SHORT).show();
                }
                // Set State of follow to user object
                user.setFollowed(follow);
                // Update user data in database
                db.updateUser(user);
            }

        });

        // Get message button
        Button messageButton = findViewById(R.id.messageButton);
        // OnClick event listener
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent
                Intent mainToMessage = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(mainToMessage);
                finish();
            }
        });
    }

}