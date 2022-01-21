package moin.miley.cse489.stock_prediction_project.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import moin.miley.cse489.stock_prediction_project.MainActivity;
import moin.miley.cse489.stock_prediction_project.R;
import moin.miley.cse489.stock_prediction_project.databinding.ActivityMainBinding;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, emailID, FirstName, LastName, PhoneNo, Password1, Password2;
    TextView warning;
    private boolean isUserExist;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefEditor;
    private String existingUserId = "";
    private boolean wasOpened = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);

        sharedPreferences = getSharedPreferences("LoginSharedPrefs", MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();

        username = findViewById(R.id.etUsernameSignUp);
        emailID = findViewById(R.id.emailId);
        FirstName = findViewById(R.id.firstName);
        LastName = findViewById(R.id.lastName);
        PhoneNo = findViewById(R.id.PhoneNo);
        Password1 = findViewById(R.id.pass1);
        Password2 = findViewById(R.id.pass2);
        warning = findViewById(R.id.warning);

        findViewById(R.id.registerBtn).setOnClickListener(r->{
            String userName = username.getText().toString();
            String email = emailID.getText().toString();
            String firstName = FirstName.getText().toString();
            String lastName = LastName.getText().toString();
            String phone = PhoneNo.getText().toString();
            String pass1 = Password1.getText().toString();
            String pass2 = Password2.getText().toString();

            StringBuilder strb = new StringBuilder();
            if(TextUtils.isEmpty(userName) || userName.length() < 4 || userName.length() > 30){
                strb.append("Invalid Username");
            }
            if(TextUtils.isEmpty(email) || email.length() < 4 || email.length() > 30){
                strb.append("Invalid Email ID");
            }
            if(TextUtils.isEmpty(firstName) || firstName.length() < 2 || firstName.length() > 20){
                strb.append("Invalid First Name");
            }
            if(TextUtils.isEmpty(lastName) || lastName.length() < 2 || lastName.length() > 20){
                strb.append("Invalid Last Name");
            }
            if(TextUtils.isEmpty(phone) || phone.length() < 4 || phone.length() > 11){
                strb.append("Invalid Phone Number");
            }
            if(TextUtils.isEmpty(pass1) || pass1.length() < 4 || pass1.length() > 10){
                strb.append("Invalid Password");
            }
            if(!isUserExist && TextUtils.isEmpty(pass2) || pass2.length() < 4 || pass2.length() > 10){
                strb.append("Invalid Password Re-entered");
            }
            if (strb.toString().length() != 0) {
                warning.setText(strb.toString());
                return;
            }

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Username",userName));
            params.add(new BasicNameValuePair("Email",email));
            params.add(new BasicNameValuePair("FirstName",firstName));
            params.add(new BasicNameValuePair("LastName",lastName));
            params.add(new BasicNameValuePair("Phone",phone));
            params.add(new BasicNameValuePair("Password1",pass1));
            params.add(new BasicNameValuePair("Password2",pass2));

            JSONParser.getInstance().makeHttpRequest("http://10.0.2.2:80/StockApp/Dbconfig.php", params);

            prefEditor = sharedPreferences.edit();
            prefEditor.putString("USERNAME", userName);
            prefEditor.putString("EMAIL", email);
            prefEditor.putString("FIRST_NAME", firstName);
            prefEditor.putString("LAST_NAME", lastName);
            prefEditor.putString("PHONE", phone);
            prefEditor.putString("PASSWORD1", pass1);
            prefEditor.putString("PASSWORD2", pass2);
            prefEditor.apply();

            Toast.makeText(this, "Your details has been saved" , Toast.LENGTH_SHORT).show();

            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(i);
        });
    }

}
