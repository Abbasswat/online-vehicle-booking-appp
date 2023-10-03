package com.example.bookrides.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.bookrides.Loader_details;
import com.example.bookrides.MainAdapter;

import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.TourModelClass;
import com.example.bookrides.R;
import com.example.bookrides.RegisterVehicle.Loaders;
import com.example.bookrides.ToursDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LoaderAdapter extends FirebaseRecyclerAdapter<LoaderModelClass,LoaderAdapter.LoaderViewHolder> {
private Context context;
private List<LoaderModelClass> loaderModelClassList;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LoaderAdapter(@NonNull FirebaseRecyclerOptions<LoaderModelClass> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LoaderViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull LoaderModelClass model) {

              holder.l_name.setText(model.getLoaderUsername());
              holder.l_Vehicltype.setText(model.getLoadertVehicle());
              holder.l_location.setText(model.getLoaderlocation());

        Glide.with(context).load(model.getLoaderImage()).into(holder.l_image);

//        Glide.with(holder.l_image.getContext())
//                .load(model.getLoaderImage())
//                .placeholder(R.drawable.abc)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .into(holder.l_image);




        holder.l_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Loader_details.class);
                intent.putExtra("Image",model.getLoaderImage());
                intent.putExtra("name",model.getLoaderUsername());
                intent.putExtra("email",model.getLoaderEmail());
                intent.putExtra("phone",model.getLoaderphone());
                intent.putExtra("locations",model.getLoaderlocation());
                intent.putExtra("loaderType",model.getLoadertVehicle());
                intent.putExtra("loadernumber",model.getVehiclenumber());
//                intent.putExtra("Description",tourModelClassList.get(holder.getAdapterPosition()).getTripDesc());

                context.startActivity(intent);
            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.l_image.getContext())
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

                name.setText(model.getLoaderUsername());
                phone.setText(model.getLoaderphone());
                email.setText(model.getLoaderEmail());
                vehicletype.setText(model.getLoadertVehicle());
                vehiclenumber.setText(model.getVehiclenumber());
                location.setText(model.getLoaderlocation());
                purl.setText(model.getLoaderImage());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("loaderUsername",name.getText().toString());
                        map.put("loaderphone",phone.getText().toString());
                        map.put("loaderEmail",email.getText().toString());
                        map.put("loadertVehicles",vehicletype.getText().toString());
                        map.put("vehiclenumber",vehiclenumber.getText().toString());
                        map.put("loaderlocation",location.getText().toString());
                        map.put("loaderImage",purl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Loader Booking")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.l_image.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.l_image.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(holder.l_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Loader Booking")
                                .child(getRef(position).getKey()).removeValue();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.l_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
        holder.l_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getLoaderphone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
        holder.l_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = model.getLoaderlocation() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                view.getContext().startActivity(intent);

            }
        });

        holder.l_whatsapploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = model.getLoaderphone();
                String message = "Hello!"; // Optional message to pre-fill in WhatsApp

                // Open WhatsApp with the selected user
                openWhatsApp(phoneNumber, message);
            }
        });
    }


//     Check and request SMS permissions
//    private void checkAndRequestPermissions(String PhoneNumber) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Loaders) context, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
//        } else {
//            // Permissions are already granted, you can send SMS
//            sendOTPviaSMS(PhoneNumber, generatedOTP);
//        }
//
//    }

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
    public LoaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_displayed_layout,parent,false );
        return new LoaderViewHolder(view);
    }

    class LoaderViewHolder extends RecyclerView.ViewHolder{
        TextView l_name,l_location,l_Vehicltype,l_type;
        ImageView l_image;
        ImageView edit,delete;
        ImageView l_call,l_locationButton ,l_whatsapploader;


        public LoaderViewHolder(@NonNull View itemView) {
            super(itemView);
            l_name = itemView.findViewById(R.id.userNameLoader);
            l_image = itemView.findViewById(R.id.loader_Image);
            l_location = itemView.findViewById(R.id.userLocationLoader);
            l_Vehicltype = itemView.findViewById(R.id.loaderVehicle);
            l_call = itemView.findViewById(R.id.call_Loader);
            l_locationButton = itemView.findViewById(R.id.location_Loader);
            l_whatsapploader = itemView.findViewById(R.id.Whatsapp_Loader);
            edit = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
        }
    }



}
