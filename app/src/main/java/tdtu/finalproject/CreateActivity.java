package tdtu.finalproject;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {
    private EditText title, desciption, addDate, addTime, anEvent;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    public static final String TITLE = "TITLE";
    public static final String DESC = "DESCRIPTION";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String EVENT = "EVENT";


    private FirebaseDatabase FireData;
    DatabaseReference eventDB;

    private Button addTask;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        FireData = FirebaseDatabase.getInstance();

        title = findViewById(R.id.addTaskTitle);
        desciption = findViewById(R.id.addTaskDescription);
        addDate = findViewById(R.id.taskDate);
        addTime = findViewById(R.id.taskTime);
        anEvent = findViewById(R.id.taskEvent);
        addTask = findViewById(R.id.addTaskEvent);

        eventDB = FirebaseDatabase.getInstance().getReference().child("Event");




        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()){
                    addNewTask();
                    Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is24HView = true;
                int selectedHour = 10;
                int selectedMinute = 20;
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        addTime.setText(hourOfDay + ":" + minute);
                        lastSelectedHour = hourOfDay;
                        lastSelectedMinute = minute;
                    }
                };

                // Create TimePickerDialog:
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateActivity.this,
                        timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);

                // Show
                timePickerDialog.show();
            }
        });

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedYear = 2000;
                int selectedMonth = 5;
                int selectedDayOfMonth = 10;

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        CreateActivity.this.addDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivity.this, dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.show();
            }
        });

    }

    public boolean validateFields() {
        if (title.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateActivity.this, "Please enter a valid title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (desciption.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateActivity.this, "Please enter a valid description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateActivity.this, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (anEvent.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateActivity.this, "Please enter an event", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;


        }


    }

    //Firebase RealTime Database
    private void addNewTask(){
        String addTitle = title.getText().toString();
        String addDescription = desciption.getText().toString();
        String Date = addDate.getText().toString();
        String Time = addTime.getText().toString();
        String Event = anEvent.getText().toString();
        String id = eventDB.push().getKey();

        Event newTask = new Event(id, addTitle, addDescription, Date, Time, Event);
        assert id != null;

        eventDB.child(id).setValue(newTask);
        Toast.makeText(CreateActivity.this,"Add Successfully",Toast.LENGTH_SHORT).show();
    }


}