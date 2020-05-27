package smerx.gamessmerx.onlinemessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);
    }

    public void OnClickStudent(View view) {
        Intent intent = new Intent(LogUpActivity.this, LogUpActivityStudent.class);

        intent.putExtra("BASE", getIntent().getStringArrayListExtra("BASE"));
        startActivity(intent);
    }

    public void OnClickTeacher(View view) {
        Intent intent = new Intent(LogUpActivity.this, LogUpActivityTeacher.class);
        intent.putExtra("BASE", getIntent().getStringArrayListExtra("BASE"));
        startActivity(intent);
    }
}
