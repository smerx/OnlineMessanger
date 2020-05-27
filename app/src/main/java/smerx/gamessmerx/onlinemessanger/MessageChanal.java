package smerx.gamessmerx.onlinemessanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageChanal extends AppCompatActivity {
    String usernameto;
    TextView textView;
    DatabaseReference myRef;
    ArrayList<String> baseOfMessage;
    FirebaseDatabase database;
    EditText EditTexttextmessage;
    Button buttonSend;
    ArrayAdapter<String> mAdapter;
    ListView listView;
    String getTextStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");
        baseOfMessage= new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chanal);
        textView  = findViewById(R.id.username);
        usernameto = getIntent().getStringExtra("userClick");
        textView.setText("Диалог с "+ Group.getInf("Name", usernameto) + " " + Group.getInf("LastName", usernameto));
        buttonSend = findViewById(R.id.btSend);
        listView = findViewById(R.id.listView);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                getTextStr =dataSnapshot.child("key1").getValue().toString();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        myRef.addValueEventListener(postListener);

        EditTexttextmessage = findViewById(R.id.ettextuser);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
          //      grouptext = dataSnapshot.getValue().toString();
              //  baseOfMessage.add(grouptext);
           // Log.e("testsmerx", grouptext);
                for (int i = 0; i < baseOfMessage.size() ; i++) {
                    Log.e("eee",baseOfMessage.get(i));

                }
               // update();
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

    public void update(){
            baseOfMessage.add("text");
            mAdapter =  new ArrayAdapter<>(this,android.R.layout.simple_list_item_2,baseOfMessage);
            listView.setAdapter(mAdapter);
    }



    public void send(View view) {

        if (EditTexttextmessage.length() > 0){
            myRef.child("key1").setValue(getTextStr + EditTexttextmessage.getText().toString());
        }
    }

}
