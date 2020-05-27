package smerx.gamessmerx.onlinemessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class LogUpActivityStudent extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UsersBase");
    EditText LoginReg,PasswordReg,PasswordTestReg,CallReg, FirstName, LastName;
    TextView UniqueCode, Status;
    ImageButton UncodeBReg;
    Button RegButton;
    ArrayList<String> base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up_student);
        LoginReg = findViewById(R.id.loginReg);
        PasswordReg = findViewById(R.id.passwordReg);
        PasswordTestReg = findViewById(R.id.passwordtestReg);
        CallReg = findViewById(R.id.CallReg);
        UniqueCode = findViewById(R.id.uncodeReg);
        UncodeBReg = findViewById(R.id.uncodeBReg);
        Status = findViewById(R.id.status);
        RegButton = findViewById(R.id.RegButton);
        FirstName = findViewById(R.id.FirstReg);
        LastName = findViewById(R.id.LastReg);
        base = getIntent().getStringArrayListExtra("BASE");
        Log.e("Information", Integer.toString(base.size()));
        for (int i = 0; i < base.size(); i++) {
            Log.e("Information", getInf("Code", base.get(i)));
        }
    }

    public void OnClickRegistration(View view) {
         if (!(PasswordReg.getText().toString().equals(PasswordTestReg.getText().toString()))) {
            Status.setTextColor(Color.RED);
            Status.setText("Ошибка: пароль подтверждения не совпадает с паролем.");
        } else {
            if (((PasswordReg.length() > 4) && (LoginReg.length() > 3))) {
                boolean isconsist = false;
                boolean iscode = false;
                for (int counter = 0; counter < base.size(); counter++) {
                    if (getInf("Login",base.get(counter)).equals(LoginReg.getText().toString())) {
                        isconsist = true;
                        break;
                    }}


                for (int counter = 0; counter < base.size(); counter++) {
                    if (getInf("Code", base.get(counter)).equals(CallReg.getText().toString())) {
                        iscode = true;
                        break;
                    }
                }
                if (!isconsist) {
                    if (FirstName.getText().length() > 2 && LastName.getText().length() > 1){

                     if(iscode){
                        String type = "student";
                        myRef.push().setValue("Login: {" + LoginReg.getText().toString() + "} " + "Name: {"+ FirstName.getText()+"} " + "LastName: {"+ LastName.getText()+"} " + "Password: {" + PasswordReg.getText().toString() + "} " + "Type: {" + type + "} " + "Code: {" + CallReg.getText() +"}");
                        Status.setTextColor(Color.GREEN);
                        Status.setText("Регистрация успешна.");
                        Intent intent = new Intent(LogUpActivityStudent.this, GoodReg.class);

                        intent.putExtra("BASE", getIntent().getStringArrayListExtra("BASE"));
                        startActivity(intent);}
                     else{
                         Status.setTextColor(Color.RED);
                         Status.setText("Учитель с таким кодом не найден.");}
                    }else {
                        Status.setTextColor(Color.RED);
                        Status.setText("Ваша фамилия или имя введено некорректно");
                    }}
                else {
                    Status.setTextColor(Color.RED);
                    Status.setText("Извините, но аккаунт с таким логином уже существует.");
                }
            } else {
                Status.setTextColor(Color.RED);
                Status.setText("Длинна логина и пароля не может быть меньше 4 символов.");
            }
        }
    }

    public String getInf(String inf, String st){
        String answer = "";
        Integer endi = st.length();
                for (int i = 0; i < endi; i++) {
                    if (st.startsWith(inf)) {
                        for (int j = 0; j < st.length(); i++) {
                            if (st.startsWith("{")) {
                                st = st.substring(1);
                               while (!st.startsWith("}"))
                               {answer += st.charAt(0); st = st.substring(1);}
                               return answer;
                            } else {st = st.substring(1);}
                       }
                    }
                    else {st=st.substring(1);}
                }

        return "NO";
    }
}
