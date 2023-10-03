package com.example.bookrides.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Loader_details;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.SchoolModelClass;
import com.example.bookrides.R;
import com.example.bookrides.School_details;

import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {

private Context context;
private List<SchoolModelClass> schoolModelClassList;

    public SchoolAdapter(Context context, List<SchoolModelClass> schoolModelClassList) {
        this.context = context;
        this.schoolModelClassList = schoolModelClassList;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_displayed_layout,parent,false);
        return new SchoolAdapter.SchoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        final SchoolModelClass schoolModelClass = schoolModelClassList.get(position);

        Glide.with(context).load(schoolModelClassList.get(position).getSch_image()).into(holder.s_image);
        holder.s_name.setText(schoolModelClassList.get(position).getSch_name());
        holder.s_location.setText(schoolModelClassList.get(position).getSch_location());
        holder.s_Vehicltype.setText(schoolModelClassList.get(position).getSch_vehicleType());
        holder.s_studenttype.setText(schoolModelClassList.get(position).getSch_studentType());
        holder.s_sch_name.setText(schoolModelClassList.get(position).getSch_collegename());

        holder.s_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, School_details.class);
                intent.putExtra("Image",schoolModelClassList.get(holder.getAdapterPosition()).getSch_image());
                intent.putExtra("nameS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_name());
                intent.putExtra("emailS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_email());
                intent.putExtra("phoneS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_phone());
                intent.putExtra("locationsS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_location());
                intent.putExtra("schoolVehicleTypeS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_vehicleType());
                intent.putExtra("StudentsTypeS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_studentType());
                intent.putExtra("SchoolVehiclenumS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_vehiclenumber());
                intent.putExtra("timeS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_time());
                intent.putExtra("collegeNameS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_collegename());
                intent.putExtra("DescriptionS",schoolModelClassList.get(holder.getAdapterPosition()).getSch_desc());

                context.startActivity(intent);
            }
        });


        holder.s_locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = schoolModelClass.getSch_location() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                context.startActivity(intent);

            }
        });

        holder.s_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phone = schoolModelClass.getSch_phone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
        holder.s_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = schoolModelClass.getSch_phone();
                String message = "Hello!"; // Optional message to pre-fill in WhatsApp

                // Open WhatsApp with the selected user
                openWhatsApp(phoneNumber, message);
            }
        });


    }
    private void openWhatsApp(String phoneNumber, String message) {
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.whatsapp");

        // Check if WhatsApp is installed on the device
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            // WhatsApp is not installed, display a Toast or handle the case accordingly
            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return schoolModelClassList.size();
    }

    public void searchDataList(ArrayList<SchoolModelClass> searchList){
        schoolModelClassList = searchList;
        notifyDataSetChanged();
    }

    public class SchoolViewHolder extends RecyclerView.ViewHolder{

        TextView s_name,s_location,s_Vehicltype,s_studenttype,s_sch_name;
        ImageView s_image;
        CardView s_cardView;
        ImageView s_call,s_locationButton ,s_whatsapp;

        public SchoolViewHolder(@NonNull View itemView) {
            super(itemView);
            s_name = itemView.findViewById(R.id.userNameS);
            s_image = itemView.findViewById(R.id.ProfileImageS);
            s_location = itemView.findViewById(R.id.LocationS);
            s_studenttype= itemView.findViewById(R.id.studentstype);
            s_Vehicltype = itemView.findViewById(R.id.School_Vehicle_rv);
            s_sch_name = itemView.findViewById(R.id.Sch_Clg_name);
            s_call = itemView.findViewById(R.id.call_sch_S);
            s_locationButton = itemView.findViewById(R.id.location_button_S);
            s_whatsapp = itemView.findViewById(R.id.Whatsapp_S);
            s_cardView = itemView.findViewById(R.id.cardview_sch);


        }
    }
}
