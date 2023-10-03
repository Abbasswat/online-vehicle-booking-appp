package com.example.bookrides.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Model.User;
import com.example.bookrides.Model.UserLoader;
import com.example.bookrides.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Loader_UserAdapter extends RecyclerView.Adapter<Loader_UserAdapter.ViewHolderL>{

    private Context context;
    private List<UserLoader> userListL;

    public Loader_UserAdapter(Context context, List<UserLoader> userListL) {
        this.context = context;
        this.userListL = userListL;
    }

    @NonNull
    @Override
    public ViewHolderL onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.loaderuser_displayed_layout, parent,false);
        return new ViewHolderL(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderL holder, int position) {
         final  UserLoader userL = userListL.get(position);

         holder.type.setText(userL.getType());

         if (userL.getType().equals("Loader")){
            holder.WhatsappLoader.setVisibility(View.VISIBLE);
         }

//         holder.userEmail.setText(user.getEmail());
//         holder.phoneNumber.setText(user.getPhonenumber());
         holder.userNameL.setText(userL.getNameL());
         holder.userLocationL.setText(userL.getLocationL());
         holder.LoaderVehicle.setText(userL.getLoaderVehicle1L());


        Glide.with(context).load(userL.getProfilepictureurlL()).into(holder.userProfileImage);

        final String nameOfTheReceiver = userL.getNameL();
        final String idOfTheReceiver = userL.getIdL();

        //sending the email



        holder.userLocationImgbtnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = userL.getLocationL() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                context.startActivity(intent);

            }
        });



        holder.callLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phone = userL.getPhonenumberL();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });

        holder.WhatsappLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = userL.getPhonenumber1L();
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
        return userListL.size();
    }

    public class ViewHolderL extends RecyclerView.ViewHolder{

        public CircleImageView userProfileImage;
        public TextView type, userNameL, userEmail, phoneNumber, LoaderVehicle, userLocationL;
        public ImageButton WhatsappLoader, callLoader, userLocationImgbtnL;

        public ViewHolderL(@NonNull View itemView) {
            super(itemView);

            userProfileImage  = itemView.findViewById(R.id.user_loader_ProfileImage);
            type = itemView.findViewById(R.id.typeL);
            userNameL   = itemView.findViewById(R.id.userNameL);
           // userEmail = itemView.findViewById(R.id.userEmail);
//            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            userLocationL = itemView.findViewById(R.id.userLocationL);
            LoaderVehicle = itemView.findViewById(R.id.LoaderVehicle);
            WhatsappLoader = itemView.findViewById(R.id.WhatsappLoader);
            callLoader = itemView.findViewById(R.id.callLoader);
            userLocationImgbtnL = itemView.findViewById(R.id.locationLoader);

        }
    }

    private void addNotifications(String receiverId, String senderId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference().child("notifications").child(receiverId);
        String date = DateFormat.getDateInstance().format(new Date());
        HashMap<String, Object>  hashMap = new HashMap<>();
        hashMap.put("receiverId", receiverId);
        hashMap.put("senderId", senderId);
        hashMap.put("text", "Sent you an email, kindly check it out!");
        hashMap.put("date", date);

        reference.push().setValue(hashMap);
    }
}
