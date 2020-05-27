package smerx.gamessmerx.onlinemessanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengersActivity extends AppCompatActivity {
    TextView textView;
    String username, user;
    Integer userpriority;
    ArrayList<String> base;
    ArrayList<String> baseOfUsersForChat;
    ArrayAdapter<String> mAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Messages");
    String[] catNamesArray = new String[]{"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Бобик", "Кристина", "Пушок",
            "Дымка", "Кузя", "Китти", "Барбос", "Масяня", "Симба"};
    ListView listView;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messengers);
        username = getIntent().getStringExtra("username");
        userpriority = getIntent().getIntExtra("userpriority", 0);
        textView = findViewById(R.id.username);
        user = getIntent().getStringExtra("user");
        base =  getIntent().getStringArrayListExtra("BASE");
        baseOfUsersForChat= new ArrayList<>();

        for (int i = 0; i < base.size(); i++) {
            if (Group.getInf("Code",base.get(i)).equals(Group.getInf("Code", user)) &&
                    (!Group.getInf("Login",base.get(i)).equals(Group.getInf("Login", user))))
            {baseOfUsersForChat.add(base.get(i));}
        }

        String[] minibase = new String[baseOfUsersForChat.size()];
        for (int i = 0; i < baseOfUsersForChat.size() ; i++) {
            minibase[i] = "";
            if (Group.getInf("Type",baseOfUsersForChat.get(i)).equals("teacher"))
            { minibase[i] +="Учитель: ";} else {minibase[i] +="Ученик: ";}
            minibase[i] += Group.getInf("Name",baseOfUsersForChat.get(i)) + " " + Group.getInf("LastName",baseOfUsersForChat.get(i));
        }
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, minibase);
        listView = findViewById(R.id.listView);
        listView.setAdapter(mAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента
                String userClick = "";
                for (int i = 0; i < baseOfUsersForChat.size(); i++) {
                    strText = textView.getText().toString();
                    while (strText.length()>2) {
                        if (strText.startsWith(Group.getInf("Name", baseOfUsersForChat.get(i)) + " " +
                                Group.getInf("LastName", baseOfUsersForChat.get(i)))) {
                            userClick=baseOfUsersForChat.get(i);
                            break;
                        } else  {
                           strText = strText.substring(1);
                        }
                    }
                }

                Intent intent = new Intent(MessengersActivity.this, MessageChanal.class);
                intent.putExtra("BASE", base);
                intent.putExtra("user",user);
                intent.putExtra("userClick", userClick);
                startActivity(intent);

                }

        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    public void OnClickAddContact(View view) {
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, base);
        listView.setAdapter(mAdapter);

    }
}
