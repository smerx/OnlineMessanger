package smerx.gamessmerx.onlinemessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GoodReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_reg);
    }

    public void GoMain(View view) {
        Intent intent = new Intent(GoodReg.this, MainActivity.class);
        intent.putExtra("BASE", getIntent().getStringArrayListExtra("BASE"));
        startActivity(intent);
    }
}
