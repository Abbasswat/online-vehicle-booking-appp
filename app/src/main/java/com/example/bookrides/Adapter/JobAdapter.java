package com.example.bookrides.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Model.JobModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.R;
import com.example.bookrides.ToursDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobAdapter extends FirebaseRecyclerAdapter<JobModelClass,JobAdapter.JobViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public JobAdapter(@NonNull FirebaseRecyclerOptions<JobModelClass> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull JobViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull JobModelClass model) {

//        Glide.with(context).load(jobModelClassList.get(position).getjImage()).into(holder.Jimage2);
        holder.J_name.setText(model.getJusername());
        holder.Jaddress.setText(model.getJlocation());
        holder.JDesc.setText(model.getJjobDesc());
//        holder.Jphone.setText(jobModelClassList.get(position).getjPhonenumber());
        holder.J_date.setText(model.getjDatepicker());
//        holder.t_phonenumber.setText(tourModelClassList.get(position).getTripphone());
//        holder.t_description.setText(tourModelClassList.get(position).getTripDesc());

        Glide.with(context).load(model.getjImage()).into(holder.J_Image);





        holder.deleteJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(holder.J_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Job posts")
                                .child(getRef(position).getKey()).removeValue();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.J_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
        holder.J_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getjPhonenumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });






//
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_rv,parent,false );
        return new JobAdapter.JobViewHolder(view);
    }
//    private List<JobModelClass> jobModelClassList;

//    public JobAdapter(Context context, List<JobModelClass> jobModelClassList) {
//        this.context = context;
//        this.jobModelClassList = jobModelClassList;
//    }
//
//    @NonNull
//    @Override
//    public JobAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_rv,parent,false);
//        return new JobViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull JobAdapter.JobViewHolder holder, int position) {
//        final JobModelClass jobModelClass = jobModelClassList.get(position);
//
//        Glide.with(context).load(jobModelClassList.get(position).getjImage()).into(holder.J_Image);
////        Glide.with(context).load(jobModelClassList.get(position).getjImage()).into(holder.Jimage2);
//        holder.J_name.setText(jobModelClassList.get(position).getJusername());
//        holder.Jaddress.setText(jobModelClassList.get(position).getJlocation());
//        holder.JDesc.setText(jobModelClassList.get(position).getJjobDesc());
////        holder.Jphone.setText(jobModelClassList.get(position).getjPhonenumber());
//        holder.J_date.setText(jobModelClassList.get(position).getjDatepicker());
////        holder.t_phonenumber.setText(tourModelClassList.get(position).getTripphone());
////        holder.t_description.setText(tourModelClassList.get(position).getTripDesc());
//
//
//
//
//
//
//        holder.J_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String phone = jobModelClass.getjPhonenumber();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                context.startActivity(intent);
//            }
//        });
//
//    }



    class JobViewHolder extends RecyclerView.ViewHolder{

        TextView J_name ,Jaddress,Jphone,JDesc,J_date;
        ImageView J_Image;
        ImageButton J_call ;
        ImageView editJ,deleteJ;


        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            J_name = itemView.findViewById(R.id.userNameJ);
            Jaddress = itemView.findViewById(R.id.JAddress);
//            Jphone = itemView.findViewById(R.id.Jphone);
            JDesc = itemView.findViewById(R.id.jobDesc);
            J_date = itemView.findViewById(R.id.posttime);
//            editJ = itemView.findViewById(R.id.updateJ);
            deleteJ = itemView.findViewById(R.id.deleteJ);
            J_Image = itemView.findViewById(R.id.jobImage);
//            Jimage2 = itemView.findViewById(R.id.jobImage);
            J_call = itemView.findViewById(R.id.J_call);






        }
    }
}

