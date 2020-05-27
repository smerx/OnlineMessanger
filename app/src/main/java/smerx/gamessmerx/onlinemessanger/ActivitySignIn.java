package smerx.gamessmerx.onlinemessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivitySignIn extends AppCompatActivity {
    TextView textView;
    String user, username;
    Integer PriorityUser;
    SharedPreferences mSettings;
    ArrayList<String> base;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        user = getIntent().getStringExtra("user");
        username  = getUserName(user);
        PriorityUser = getUserPriority(user);
        textView = findViewById(R.id.username);
        textView.setText(username+'(' +PriorityUser.toString()+')');
        mSettings = getSharedPreferences("LogIn", Context.MODE_PRIVATE);


       // group = new Group(base, Group.getInf("Code",user));
        // Log.e("infoEC", group.getTeacher());
      //  Log.e("infoEC", Integer.toString(base.size()));

try {
    base = getIntent().getStringArrayListExtra("BASE");
    group = new Group(base, Group.getInf("Code",user));
} catch (Exception e){}




    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // ваш код
    }


    Integer getUserPriority(String user){
        if (Group.getInf("Type", user).equals("teacher")) {return 1;}
        if (Group.getInf("Type", user).equals("student")) {return 2;}
        if (Group.getInf("Type", user).equals("admin")) {return 0;}
        return 2;
    }

    String  getUserName(String user){
        int endpfrace = 1;
        for (int i = 8; i < user.length(); i++) {
            Character ch = user.charAt(i);
             endpfrace = i;
            if (ch.equals('}'))
            {break;}
        }
        String answer = user.substring(8,endpfrace);
        if (answer.length() > 14) {
            answer = answer.substring(0,15) + "...";
        }
       return answer;
    }

    public void OnClickMessengers(View view) {

        Intent intent = new Intent(ActivitySignIn.this, MessengersActivity.class);
        intent.putExtra("BASE", base);
       intent.putExtra("username",username);
       intent.putExtra("user",user);
       intent.putExtra("userpriority", PriorityUser);
        startActivity(intent);
    }

    public void OnClickLogOut(View view) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("UserGone","NO");
        editor.apply();
        Intent intent = new Intent(ActivitySignIn.this,MainActivity.class);
        startActivity(intent);
    }


}
