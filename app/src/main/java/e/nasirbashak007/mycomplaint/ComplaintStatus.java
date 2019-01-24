package e.nasirbashak007.mycomplaint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintStatus extends AppCompatActivity {

    EditText editText;
    TextView textView;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);

        editText = (EditText)findViewById(R.id.editTextKey);
        textView = (TextView)findViewById(R.id.textStatus);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
         ref = db.getReference().child("complaints");




    }

    public void status(View view) {



        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Complaint c = (Complaint)dataSnapshot.getValue(Complaint.class);

                String key = c.getKey();

                //textView.setText("key = "+key+"\n keyyy "+dataSnapshot.getKey());


                String userKey = editText.getText().toString().trim();



                if(key.equalsIgnoreCase(userKey)){



                    Toast.makeText(getApplicationContext(),c.getVicNameame(),Toast.LENGTH_SHORT).show();

                    textView.setText("Your status is "+c.getStatus());


                }






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
}
