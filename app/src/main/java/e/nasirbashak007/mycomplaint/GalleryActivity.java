


package e.nasirbashak007.mycomplaint;

        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    TextView title;


    private String key ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);
        Log.d(TAG, "onCreate: started.");

        title = (TextView) findViewById(R.id.textTitle);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("myNames") && getIntent().hasExtra("myDates")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String name = getIntent().getStringExtra("myNames");
            String date = getIntent().getStringExtra("myDates");
            String cat = getIntent().getStringExtra("myCats");
            String vic = getIntent().getStringExtra("myVicNames");
            String des = getIntent().getStringExtra("myDescs");
            key = getIntent().getStringExtra("myKeys");
            setImage(name,cat, date,vic,des);
        }
    }


    private void setImage(String name, String cat, String date, String vic, String des){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView tname = findViewById(R.id.image_description);
        TextView tdate = findViewById(R.id.image_name);

        String content = "\t\t\t\t\t\t\t\t\t\t\tVIEW COMPLAINT\n\n\n"
                        +"\tName        : "+name+"\n\n"
                        +"\tCategory    : "+cat+"\n\n"
                        +"\tDate        : "+date+"\n\n"
                        +"\tVictim Name : "+vic+"\n\n"
                        +"\tDescription : "+des;

        //title.setText("View Complaint");

        tname.setText(content);
        //date.setText(imageUrl);

        //ImageView image = findViewById(R.id.image);
        //Glide.with(this)
                // .asBitmap()
          //      .load(imageUrl)
            //    .into(image);
    }

    public void ClearTheComplaint(View view) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
       DatabaseReference ref = db.getReference().child("complaints");


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Complaint c = (Complaint) dataSnapshot.getValue(Complaint.class);

                String Fkey = c.getKey();

                //textView.setText("key = "+key);
                //Toast.makeText(getApplicationContext(),"clicked key "+key,Toast.LENGTH_SHORT).show();


                //String userKey = editText.getText().toString().trim();


                if (key.equalsIgnoreCase(Fkey)) {

                      FirebaseDatabase.getInstance().getReference().child("complaints").child(dataSnapshot.getKey()).child("status").setValue("solved", new DatabaseReference.CompletionListener() {
                      @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {


                     //FirebaseDatabase.getInstance().getReference().child(dataSnapshot.getKey())


                    Toast.makeText(getApplicationContext(), databaseReference.getParent()+"", Toast.LENGTH_SHORT).show();




                }
                });

                // Toast.makeText(getApplicationContext(),"child value "+dref,Toast.LENGTH_SHORT).show();



              /*
               dref.addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                   }
               });


                    new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                    Toast.makeText(getApplicationContext(),"Complaint Solved "+databaseReference,Toast.LENGTH_SHORT).show();



                                }
                            }


                    );

*/


                //  Toast.makeText(getApplicationContext(),c.getVicNameame(),Toast.LENGTH_SHORT).show();

                //textView.setText("Your status is "+c.getStatus());


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
