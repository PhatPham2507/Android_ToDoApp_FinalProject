package tdtu.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, confirmPassword, fullname, phone;
    Button registerButton;
    TextView already;
    private FirebaseAuth sAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        registerButton = findViewById(R.id.register);
        already = findViewById(R.id.loginNow);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phoneNumber);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alreadyAccount();
            }
        });
    }

        private void register() {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String confirm = confirmPassword.getText().toString();
            String fullName = fullname.getText().toString();
            String phoneNumber = phone.getText().toString();


            if(TextUtils.isEmpty(phoneNumber)||TextUtils.isEmpty(fullName) ||TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirm)){
                Toast.makeText(RegisterActivity.this, "All field Required",Toast.LENGTH_SHORT).show();
                return;
            }else{
                if(confirm.equals(pass)){
                    sAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"Register Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }else{
                    Toast.makeText(RegisterActivity.this, "Passwords not matching",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        private void alreadyAccount () {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
}
