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
    public String usernameto, userfrom;
    TextView textView;
    DatabaseReference myRef;
    ArrayList<String> baseOfMessage;
    FirebaseDatabase database;
    EditText EditTexttextmessage;
    Button buttonSend;
    ArrayAdapter<String> mAdapter;
    ListView listView;
    String text;
    MessageAllText messageAllText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");
        baseOfMessage= new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chanal);
        textView  = findViewById(R.id.username);
        usernameto = getIntent().getStringExtra("userClick");
        userfrom = getIntent().getStringExtra("user");
        textView.setText("Диалог с "+ Group.getInf("Name", usernameto) + " " + Group.getInf("LastName", usernameto));
        buttonSend = findViewById(R.id.btSend);
        listView = findViewById(R.id.ListView);
        messageAllText = new MessageAllText(userfrom,usernameto);

        mAdapter = new ArrayAdapter<>(MessageChanal.this,
               R.layout.list_itm, messageAllText.messages);
        listView.setAdapter(mAdapter);
        //myRef.child("key1").setValue(getTextStr + messageAllText.getString());
       // if (getTextStr.length() > 0) {
        //myRef.child("new").setValue(getTextStr + messageAllText.getString());}
        //else {
         //   myRef.child("new").setValue(messageAllText.getString());
        //}

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
              messageAllText.MessageText(dataSnapshot.child("new").getValue().toString());
                text =dataSnapshot.child("new").getValue().toString();
                messageAllText.UpdateArray(dataSnapshot.child("new").getValue().toString(), userfrom, usernameto);
                listView.invalidateViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        myRef.addValueEventListener(postListener);

        EditTexttextmessage = findViewById(R.id.ettextuser);

    }




    public void send(View view) {
        if (EditTexttextmessage.length() > 0){
            myRef.child("new").setValue(text + Group.getInf("Name",userfrom) + "<"
            + EditTexttextmessage.getText()+">");
            messageAllText.UpdateArray(text + Group.getInf("Name",userfrom) + "<"
                    + EditTexttextmessage.getText()+">", userfrom, usernameto);
        }
        EditTexttextmessage.setText("");
    }
}

class MessageAllText{
    String FromSend = "", ToSend = "";
    ArrayList<String> messages = new ArrayList<String>();
    ArrayList<String> messagesTime = new ArrayList<String>();

    public MessageAllText(String fromSend, String toSend) {
        FromSend = fromSend;
        ToSend = toSend;
    }

    void UpdateArray(String s, String userFrom, String userTo){
        messages.clear();
        while (s.length()>0){
            Boolean You = false;
            if (s.startsWith(Group.getInf("Name",userTo)) || s.startsWith(Group.getInf("Name",userFrom))){
                if (s.startsWith(Group.getInf("Name",userTo))){You = true;}
                int k = 0;
                while (s.charAt(k) != '<')
                { s= s.substring(1);}
                k = 1;
                String MessageT = "";
                while (s.charAt(k) != '>')
                {MessageT += Character.toString(s.charAt(k)); s = s.substring(1);}
                if (!You){messages.add("Вы: " + MessageT);} else {
                messages.add(Group.getInf("Name", userTo) + ": " + MessageT);} }
          s = s.substring(1);
        }
    }

    public void MessageText(String string) {
        String copystring = string;
        for (int i = 0; i < string.length(); i++) {
            if (string.startsWith("FromSend")){
                int k = 0;
                while (string.charAt(k) != '<')
                { string = string.substring(1);}
                k = 1;
                while (string.charAt(k) != '>')
                {this.FromSend += Character.toString(string.charAt(k)); string = string.substring(1);}
            }
            if (string.startsWith("ToSend")){
                int k = 0;
                while (string.charAt(k) != '<')
                { string = string.substring(1);}
                k = 1;
                while (string.charAt(k) != '>')
                {ToSend += Character.toString(string.charAt(k)); string = string.substring(1);}
                }
            if (string.startsWith("Message")){
                int k = 0;
                while (string.charAt(k) != '<')
                { string = string.substring(1);}
                k = 1;
                String MessageT = "";
                while (string.charAt(k) != '>')
                {MessageT += Character.toString(string.charAt(k)); string = string.substring(1);}
                //messages.add(MessageT);
            }
            string = string.substring(1);
        }
    }

   void addMessage(String text)
   {
        messages.add(text);
   }


    String getString(){
        String answer = "FromSend<"+ Group.getInf("Login",FromSend)+">"+"ToSend<"+Group.getInf("Login",ToSend)+"> ";
       for (int i = 0; i < messages.size(); i++) {
           answer+="Message<"+messages.get(i) + "> ";
       }
        return answer;
   }
}

