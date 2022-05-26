package sg.edu.np.mad.mad_exercise2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class User_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Init variables
    Context context;
    ArrayList<User> userList;

    // Constructor
    public User_RecyclerViewAdapter(Context context, ArrayList<User> userList){
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getItemViewType(int position) {
        String name = userList.get(position).getNameOfUser();
        // If name has last digit 7, return 1. If not, return 0
        if (name.charAt(name.length()-1) == '7'){
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        // Inflate Normal layout (if viewType is 0)
        if (viewType == 0){
            view = inflater.inflate(R.layout.recycler_view_row, parent, false);
            return new MyViewHolder1(view);
        }
        else{
            // Inflate layout with last digit 7 in name (if viewType is 1)
            view = inflater.inflate(R.layout.recycler_view_row2, parent, false);
            return new MyViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Profile");
        builder.setMessage(userList.get(holder.getAdapterPosition()).getNameOfUser());
        builder.setCancelable(false);
        builder.setPositiveButton("View", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Intent i = new Intent(context, MainActivity.class);
                // Passing user object to MainActivity
                i.putExtra("user",userList.get(holder.getAdapterPosition()));
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                // Just close alert and do nothing
            }
        });

        // Bind myViewHolder
        String name = userList.get(position).getNameOfUser();

        switch (holder.getItemViewType()){
            case 0:
                MyViewHolder1 viewHolderOne = (MyViewHolder1) holder;
                viewHolderOne.tvName.setText(userList.get(position).getNameOfUser());
                viewHolderOne.tvDesc.setText(userList.get(position).getDescription());

                // Onclick Listener for image
                viewHolderOne.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Display Alert
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                break;

            case 1:
                MyViewHolder2 viewHolderTwo = (MyViewHolder2) holder;
                viewHolderTwo.tvName2.setText(userList.get(position).getNameOfUser());
                viewHolderTwo.tvDesc2.setText(userList.get(position).getDescription());

                // Onclick Listener for image
                viewHolderTwo.icon2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Display Alert
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder{
        // Init Variables
        TextView tvName;
        TextView tvDesc;
        ImageView icon;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            // Get view from xml
            tvName = itemView.findViewById(R.id.nameOfUser);
            tvDesc = itemView.findViewById(R.id.descOfuser);
            icon = itemView.findViewById(R.id.iconOfUser);
        }
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{
        // Init Variables
        TextView tvName2;
        TextView tvDesc2;
        ImageView icon2;

        public MyViewHolder2 ( @NonNull View itemView) {
            super(itemView);
            // Get view from xml
            tvName2 = itemView.findViewById(R.id.nameOfUser2);
            tvDesc2 = itemView.findViewById(R.id.descOfuser2);
            icon2 = itemView.findViewById(R.id.iconOfUser2);
        }
    }

}

