package e.nasirbashak007.mycomplaint;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> cats = new ArrayList<>();

    private ArrayList<String> myNames = new ArrayList<>();
    private ArrayList<String> myDates = new ArrayList<>();
    private ArrayList<String> myCats = new ArrayList<>();
    private ArrayList<String> myDescs = new ArrayList<>();
    private ArrayList<String> myVicNames = new ArrayList<>();
    private ArrayList<String> myKeys = new ArrayList<>();


    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> dates, ArrayList<String> cats ) {
        this.dates = dates;
        this.cats = cats;
        mContext = context;
    }

    public RecyclerViewAdapter(ArrayList<String> dates, ArrayList<String> values) {



    }

    public RecyclerViewAdapter(Context context,ArrayList<String> myKeys,ArrayList<String> myNames, ArrayList<String> myCats,
                               ArrayList<String> myDates, ArrayList<String> myVicNames,
                               ArrayList<String> myDescs) {

        mContext = context;
        this.myNames = myNames;
        this.myCats = myCats;
        this.myDates = myDates;
        this.myVicNames = myVicNames;
        this.myDescs = myDescs;
        this.myKeys = myKeys;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

       // Glide.with(mContext)
                //.asBitmap()
         //       .load(mImages.get(position));
        //.into(holder.image);

        holder.textDate.setText(myDates.get(position));
        holder.textCat.setText(myCats.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + myDates.get(position));

                Toast.makeText(mContext, myDates.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, GalleryActivity.class);
                intent.putExtra("myNames", myNames.get(position));
                intent.putExtra("myCats", myCats.get(position));

                intent.putExtra("myDates", myDates.get(position));
                intent.putExtra("myVicNames", myVicNames.get(position));
                intent.putExtra("myDescs", myDescs.get(position));
                intent.putExtra("myKeys", myKeys.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDates.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        TextView textDate;
        TextView textCat;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textCat = itemView.findViewById(R.id.textCat);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
