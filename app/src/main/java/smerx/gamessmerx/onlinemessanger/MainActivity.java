package smerx.gamessmerx.onlinemessanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UsersBase");
    TextView statusText,statustogo;
    EditText login,password;
    RadioButton studentRB,teacherRB;
    String UserNow="NO";
    SharedPreferences mSettings; // fail for save
    ArrayList<String> base= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusText = findViewById(R.id.textstatus);
        statustogo = findViewById(R.id.statustogo);
        statustogo.setTextColor(Color.RED);
        login = findViewById(R.id.editlogin);
        password = findViewById(R.id.editpassword);
        mSettings = getSharedPreferences("LogIn", Context.MODE_PRIVATE);

        if (mSettings.contains("UserGone")) {
            // Получаем число из настроек
            UserNow = mSettings.getString("UserGone", "NO");
        }


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String people = dataSnapshot.getValue().toString();
                base.add(people);
                statusText.setText(base.toString());

                try {
                    while (base.size() == 0) {
                        Thread.sleep(100);
                    }
                    if (!UserNow.equals("NO")) {
                        Intent intent = new Intent(MainActivity.this, ActivitySignIn.class);
                        intent.putExtra("user", UserNow);
                        intent.putExtra("BASE", base);
                        startActivity(intent);}
                    } catch(Exception e){}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void OnClickButtonLogUp(View view) {
        Intent intentLogUp = new Intent(MainActivity.this, LogUpActivity.class);
        intentLogUp.putExtra("BASE", base);
        startActivity(intentLogUp);
    }

    public void OnClickButtonLogIn(View view) {
        boolean isfound=false;
        for (int counter = 0; counter < base.size(); counter++) {
            if ((base.get(counter).indexOf(login.getText().toString()) != -1)
                    && (base.get(counter).indexOf(password.getText().toString()) != -1)){
                UserNow=base.get(counter);
                isfound=true;
                break;
            }}
            if (!isfound){statustogo.setText("Пользователь с таким логином и паролем не найден.");}
            else {

                Intent intent = new Intent(MainActivity.this, ActivitySignIn.class);
                intent.putExtra("user", UserNow);
                intent.putExtra("BASE", base);

                //блок автосохранения данных для входа
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString("UserGone",UserNow);
                editor.apply();

                startActivity(intent);}

        statusText.setText(base.toString() + "  " + myRef.push().toString());

    }




}
