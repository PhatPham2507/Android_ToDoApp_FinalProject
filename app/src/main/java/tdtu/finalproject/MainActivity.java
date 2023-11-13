package tdtu.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView addTask, emailMain;
    ImageView calender;
    RecyclerView recyclerView;
    DatabaseReference DB;

    FirebaseAuth DB1;
    EventAdapter eventAdapter;

    private BottomNavigationView navigationView;
    private ViewPager viewPager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addTask =findViewById(R.id.addTask);
//
//
//        addTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
//                startActivity(intent);
//            }
//        });


        calender = findViewById(R.id.calendar);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

//        Hiển thị database
        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Event"),Event.class)
                .build();
        eventAdapter= new EventAdapter(options);
        recyclerView.setAdapter(eventAdapter);


//        BottomNavigate
        navigationView = findViewById(R.id.bottom_nav);




        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        Intent intent = new Intent(MainActivity.this,CreateActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                        Intent intent1 = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_my:
                        Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true  ;
            }
        });



        emailMain = findViewById(R.id.emailMain);

    }



    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter.startListening();
        DB1 = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = DB1.getCurrentUser();
        if(firebaseUser != null){
            emailMain.setText((firebaseUser.getEmail()));
        }else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventAdapter.stopListening();
    }
}