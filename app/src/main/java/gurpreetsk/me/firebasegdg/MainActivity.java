package gurpreetsk.me.firebasegdg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText msgET;
    Button btn;
    TextView tvMyMessageData;

    //Write msg to db
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHandles();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gurpreetsk.me.firebasegdg.MyMessages myMessages=new gurpreetsk.me.firebasegdg.MyMessages(msgET.getText().toString(),"Android");
                myRef.push().setValue(myMessages);
                /**remove .push() and test
                 detValue updates the value and not add a new value to it
                 push() adds new value and not just updates the previous value*/

                msgET.setText("");
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String myNames="";
                for (DataSnapshot myChild : dataSnapshot.getChildren()) {
                    myNames=myNames+"\n\n"+ myChild.child("chat").getValue();
                }
                tvMyMessageData.setText(myNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", databaseError.getDetails());
            }
        });


    }

    public void getHandles(){
        msgET = (EditText) findViewById(R.id.msgET);
        btn = (Button) findViewById(R.id.sendBtn);
        tvMyMessageData = (TextView)findViewById(R.id.tvMyMsg);

    }

}
