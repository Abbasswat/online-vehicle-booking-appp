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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Loader_details;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.User;
import com.example.bookrides.R;
import com.example.bookrides.Simple_booking_details;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimpleAdapter extends FirebaseRecyclerAdapter<User, SimpleAdapter.SimpleViewHolder> {
private Context context;
private List<User> users;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SimpleAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SimpleViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull User model) {


        holder.type.setText(model.getType());
              holder.userName_D.setText(model.getName());
              holder.bloodGroup.setText(model.getBloodgroup());
              holder.userLocation.setText(model.getLocation());

        Glide.with(context).load(model.getProfilepictureurl()).into(holder.userProfileImage);

//        Glide.with(holder.l_image.getContext())
//                .load(model.getLoaderImage())
//                .placeholder(R.drawable.abc)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .into(holder.l_image);



//        holder.userName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Simple_booking_details.class);
//                intent.putExtra("Image",model.getProfilepictureurl());
//                intent.putExtra("name",model.getName());
//                intent.putExtra("email",model.getEmail());
//                intent.putExtra("phone",model.getPhonenumber());
//                intent.putExtra("locations",model.getLocation());
//                intent.putExtra("loaderType",model.getBloodgroup());
//                intent.putExtra("loadernumber",model.getIdnumber());
////                intent.putExtra("Description",tourModelClassList.get(holder.getAdapterPosition()).getTripDesc());
//
//                context.startActivity(intent);
//            }
//        });


        holder.edit_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.userProfileImage.getContext())
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

                name.setText(model.getName());
                phone.setText(model.getPhonenumber());
                email.setText(model.getEmail());
                vehicletype.setText(model.getBloodgroup());
                vehiclenumber.setText(model.getIdnumber());
                location.setText(model.getLocation());
                purl.setText(model.getProfilepictureurl());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("phonenumber",phone.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("bloodgroup",vehicletype.getText().toString());
                        map.put("idnumber",vehiclenumber.getText().toString());
                        map.put("location",location.getText().toString());
                        map.put("profilepictureurl",purl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.userProfileImage.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.userProfileImage.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.delete_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.userName_D.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).removeValue();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.userName_D.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
        holder.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getPhonenumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
        holder.userLocationImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = model.getLocation() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                view.getContext().startActivity(intent);

            }
        });

        holder.emailNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = model.getPhonenumber1();
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
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_displayed_layout_c,parent,false );
        return new SimpleViewHolder(view);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView userProfileImage;
        public TextView type, userName_D, userEmail, phoneNumber, bloodGroup, userLocation;
        public ImageView emailNow, callNow, userLocationImgbtn;

        ImageView edit_S, delete_S;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage  = itemView.findViewById(R.id.userProfileImage_C);
            type = itemView.findViewById(R.id.type_C);
            userName_D= itemView.findViewById(R.id.userName_C);

            userLocation = itemView.findViewById(R.id.userLocation_C);
            bloodGroup = itemView.findViewById(R.id.vehicle_type_C);
            edit_S = itemView.findViewById(R.id.update_S);
            delete_S = itemView.findViewById(R.id.delete_S);
            emailNow = itemView.findViewById(R.id.WhatsappNow_C);
            callNow = itemView.findViewById(R.id.callNow_C);
            userLocationImgbtn = itemView.findViewById(R.id.locationNow_C);
        }
    }
}
