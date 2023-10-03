package com.example.bookrides;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Model.LoaderModelClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.practiceViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options , Context context) {
        super(options);
        this.context= context;

    }

    @Override
    protected void onBindViewHolder(@NonNull practiceViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MainModel model) {



        holder.P_name.setText(model.getSch_name());
        holder.P_location.setText(model.getSch_location());
        holder.P_sch_name.setText(model.getSch_collegename());
        holder.P_studenttype.setText(model.getSch_studentType());
        holder.P_Vehicltype.setText(model.getSch_vehicleType());
        Glide.with(context).load(model.getSch_image()).into(holder.P_image);

//        Glide.with(holder.P_image.getContext())
//                .load(model.getSch_image())
//                .placeholder(R.drawable.abc)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .into(holder.P_image);





        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.P_image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1200)
                        .create();


                View myview=dialogPlus.getHolderView();

                final EditText name=myview.findViewById(R.id.uname);
                final EditText phone=myview.findViewById(R.id.uphone);
                final EditText email=myview.findViewById(R.id.uemail);
                final EditText vehicletype=myview.findViewById(R.id.uvehicletype);
                final EditText vehiclenumber=myview.findViewById(R.id.uvehiclenumber);
                final EditText location=myview.findViewById(R.id.ulocation);
                final EditText purl=myview.findViewById(R.id.uimgurl);
                Button submit=myview.findViewById(R.id.usubmit);

                name.setText(model.getSch_name());
                phone.setText(model.getSch_phone());
                email.setText(model.getSch_email());
                vehicletype.setText(model.getSch_vehicleType());
                vehiclenumber.setText(model.getSch_vehiclenumber());
                location.setText(model.getSch_location());
                purl.setText(model.getSch_image());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("sch_name",name.getText().toString());
                        map.put("sch_phone",phone.getText().toString());
                        map.put("sch_email",email.getText().toString());
                        map.put("sch_vehicleType",vehicletype.getText().toString());
                        map.put("sch_vehiclenumber",vehiclenumber.getText().toString());
                        map.put("sch_location",location.getText().toString());
                        map.put("sch_image",purl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("School Vehicles Booking")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.P_name.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                   dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.P_name.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.P_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("School Vehicles Booking")
                                .child(getRef(position).getKey()).removeValue();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.P_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        holder.P_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getSch_phone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
        holder.P_locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = model.getSch_location() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                view.getContext().startActivity(intent);

            }
        });

        holder.P_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = model.getSch_phone();
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
        if (context != null && intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            // WhatsApp is not installed, display a Toast or handle the case accordingly
            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show();
        }

    }
    @NonNull
    @Override
    public practiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice,parent,false );
        return new practiceViewHolder(view);
    }

    class practiceViewHolder extends RecyclerView.ViewHolder{
        TextView P_name,P_location,P_Vehicltype,P_studenttype,P_sch_name;
        ImageView P_image;
        CardView P_cardView;
        ImageView P_call,P_locationButton ,P_whatsapp;

        ImageView btnupdate,btndelete;
        public practiceViewHolder(@NonNull View itemView) {
            super(itemView);

            P_name = itemView.findViewById(R.id.userNameP);
            P_image = itemView.findViewById(R.id.ProfileImageP);
            P_location = itemView.findViewById(R.id.LocationP);
            P_studenttype= itemView.findViewById(R.id.studentstypeP);
            P_Vehicltype = itemView.findViewById(R.id.School_Vehicle_rvP);
            P_sch_name = itemView.findViewById(R.id.Sch_Clg_nameP);
            P_call = itemView.findViewById(R.id.call_sch_P);
            P_locationButton = itemView.findViewById(R.id.location_button_P);
            P_whatsapp = itemView.findViewById(R.id.Whatsapp_P);
            P_cardView = itemView.findViewById(R.id.cardview_schP);
            btnupdate = itemView.findViewById(R.id.updateP);
            btndelete = itemView.findViewById(R.id.deleteP);
        }
    }
}
