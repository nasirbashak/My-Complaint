


package e.nasirbashak007.mycomplaint;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    TextView title;

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

}
