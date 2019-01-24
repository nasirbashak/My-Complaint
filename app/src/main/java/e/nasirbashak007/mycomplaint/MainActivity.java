package e.nasirbashak007.mycomplaint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.susmit.mailsender.MailSender;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    TextView textCat,textDate,textVicName,textDesc;
    ImageView textScreen;
    EditText eTextCat,eTextDate,eTextVicName,eTextDesc;
    Spinner spinner;
    Button button;
    String key;

    String sendersAddress,sendersAccPassword,receiversAddr,subject,content;



    public static final String ANONYMOUS = "anonymous";
    private boolean complaintEnabled;

    public FirebaseDatabase myFirebaseDatabase;
    public DatabaseReference myDatabaseReference;
    public ChildEventListener myChildEvenListener;

    private Animation topToBottom;

    final int RC_SIGN_IN = 1;

    public FirebaseAuth myFirebaseAuth;
    public FirebaseAuth.AuthStateListener myFirebaseAuthListener;
    String mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        sendersAddress = "nasirbashak007@gmail.com";
        sendersAccPassword = "#Nasir@1997";



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!complaintEnabled){




            //setEnable(false);
            //setContentView(R.layout.activity_new_complaint);




        }






        //FirebaseApp.initializeApp(this);
        //FirebaseApp.



        myFirebaseAuth = FirebaseAuth.getInstance();


         myFirebaseDatabase = FirebaseDatabase.getInstance();
         myDatabaseReference = myFirebaseDatabase.getReference("complaints");




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

                String num = "8722086222";

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",num,null)));




            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        myFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null){

                    Toast.makeText(getApplicationContext(),"You are signed in",Toast.LENGTH_SHORT).show();
                    receiversAddr = firebaseUser.getEmail();
                    onSignedInInitialise(firebaseUser.getDisplayName());

                }else{
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(true)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);


                }


            }
        };


        myChildEvenListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Complaint complaint = (Complaint) dataSnapshot.getValue(Complaint.class);
               // Toast.makeText(getApplicationContext(),complaint+"",Toast.LENGTH_SHORT).show();



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
        };
        myDatabaseReference.addChildEventListener(myChildEvenListener);










    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





        if(requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Signed in Successful", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Signed in notSuccessful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }





    }


    private void onSignedInInitialise(String userName){
        mUsername = userName;

        //attachDatabaseReadListener();



    }




    private void onSignedOutCleanUp() {



        mUsername = ANONYMOUS;
        //mMessageAdapter.clear();
        //detachDatabaseReadListener();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(myFirebaseAuthListener != null) {
            myFirebaseAuth.removeAuthStateListener(myFirebaseAuthListener);
        }
        //mMessageAdapter.clear();
        //detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myFirebaseAuth.addAuthStateListener(myFirebaseAuthListener);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(getApplicationContext(),"show details",Toast.LENGTH_SHORT).show();
        }else if( id == R.id.nav_newComp){
            Toast.makeText(getApplicationContext(),"Opening new Complaint",Toast.LENGTH_SHORT).show();
            complaintEnabled= true;

           // topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
            //textScreen.setAnimation(topToBottom);
            //textScreen.setEnabled(false);
            setEnable(true);


        }else if(id == R.id.nav_compStatus){

            Toast.makeText(getApplicationContext(),"Status of old complaint",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, ComplaintStatus.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void newComplaint(View view) {

        startActivity(new Intent(this,NewComplaintActivity.class));

    }

    public void submit(View view) {
        //mUsername

        String cat = eTextCat.getText().toString().trim();
        String date = eTextDate.getText().toString().trim();
        String vicName = eTextVicName.getText().toString().trim();
        String desc = eTextDesc.getText().toString().trim();

        //myRef.push().setValue(friendlyMessage);

        String randKey = new RandomKey().randomAlphaNumeric(3);
        key = randKey;

        Complaint complaint = new Complaint(randKey,"pending",mUsername,cat,date,vicName,desc,null);
        myDatabaseReference.push().setValue(complaint).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Data Uploaded Successfully",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
            }
        });


        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // Toast.makeText(getApplicationContext(),"key "+key,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 //key = dataSnapshot.getKey();
                //Toast.makeText(getApplicationContext(),"key "+key,Toast.LENGTH_SHORT).show();




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

        Toast.makeText(getApplicationContext(),"key2 "+key,Toast.LENGTH_SHORT).show();

        content = "Dear "+ mUsername +" thanks for utilizing our E- services for registering your complaint online\n" +
                "Your reference number is "+key+" save this for future tracking of your complaint status";

        new MailSender(sendersAddress, sendersAccPassword).sendMailAsync(receiversAddr, "My Complaint App", content);
        Toast.makeText(getApplicationContext(),"An Email has been sent to your registered mail id",Toast.LENGTH_SHORT).show();


        startActivity(new Intent(this,NewComplaintActivity.class));





    }

    private void init(){

        textCat = (TextView)findViewById(R.id.textViewCat);
        textDate = (TextView)findViewById(R.id.textViewDate);
        textVicName = (TextView)findViewById(R.id.textViewVicName);
        textDesc = (TextView)findViewById(R.id.textViewDesc);
        textScreen = (ImageView)findViewById(R.id.textView5);

        eTextCat= (EditText)findViewById(R.id.editTextCat);
        eTextDate= (EditText)findViewById(R.id.editTextDate);
        eTextVicName= (EditText)findViewById(R.id.editTextVicName);
        eTextDesc= (EditText)findViewById(R.id.editTextDesc);

       // spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button)findViewById(R.id.buttonSubmit);


    }

    private void setEnable(boolean b){

        textCat.setEnabled(b);
        textDate.setEnabled(b);
        textVicName.setEnabled(b);
        textDesc.setEnabled(b);

        eTextCat.setEnabled(b);
        eTextDate.setEnabled(b);
        eTextVicName.setEnabled(b);
        eTextDesc.setEnabled(b);

        //spinner.setEnabled(b);
        button.setEnabled(b);






    }


}
