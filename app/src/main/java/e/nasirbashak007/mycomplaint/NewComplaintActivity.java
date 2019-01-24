package e.nasirbashak007.mycomplaint;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewComplaintActivity extends AppCompatActivity {

    private ComplaintAdapter complaintAdapter;

    ListView mMessageListView;

    private RecyclerView recyclerView;


    private static final String TAG = "MainActivity";

    //vars




    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    context = this.getApplicationContext();


       // mMessageListView = (ListView) findViewById(R.id.list_view);


        //List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        //        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        //        mMessageListView.setAdapter(mMessageAdapter);

        //List<Complaint> complaints = new ArrayList<>();
        //complaintAdapter = new ComplaintAdapter(this, R.layout.item_message,complaints);
       // mMessageListView.setAdapter(complaintAdapter);



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("complaints");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                 ArrayList<String> myNames = new ArrayList<>();
                 ArrayList<String> myDates = new ArrayList<>();
                 ArrayList<String> myCats = new ArrayList<>();
                 ArrayList<String> myDescs = new ArrayList<>();
                 ArrayList<String> myVicNames = new ArrayList<>();




                for(DataSnapshot d : dataSnapshot.getChildren()){

                    Complaint c = (Complaint) d.getValue(Complaint.class);
                    Log.e("Get Data", c.getVicNameame());

                   // Toast.makeText(getApplicationContext(),c.getName(),Toast.LENGTH_SHORT).show();

                    //complaintAdapter.add(c);

                    myNames.add(c.getName());
                    myDescs.add(c.getDesc());
                    myDates.add(c.getDate());
                    myCats.add(c.getCategory());
                    myVicNames.add(c.getVicNameame());

                    recyclerView.setAdapter(new RecyclerViewAdapter(context,myNames,myCats,myDates,myVicNames,myDescs));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //mMessageListView.setAdapter(complaintAdapter);


        Log.d(TAG, "onCreate: started.");

       // initImageBitmaps();



    }
    private class GetDataFromFirebase extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }










}
