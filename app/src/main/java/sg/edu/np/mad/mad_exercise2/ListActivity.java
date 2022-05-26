package sg.edu.np.mad.mad_exercise2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    // Init user list
    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get recyclerView from xml
        RecyclerView recyclerView = findViewById(R.id.UserRecyclerView);

        // Get data from DB
        DbHandler db = new DbHandler(this,"mad_exercise6_db");
        userList = db.getUsers();

        // Create adapter and set recycler view
        User_RecyclerViewAdapter adapter = new User_RecyclerViewAdapter(this,userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}