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
import com.example.bookrides.Model.LoaderModelClass;
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

public class TourAdapter_C extends FirebaseRecyclerAdapter<TourModelClass, TourAdapter_C.TourViewHolder> {

    private Context context;
    private List<LoaderModelClass> loaderModelClassList;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TourAdapter_C(@NonNull FirebaseRecyclerOptions<TourModelClass> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull TourViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull TourModelClass model) {

        holder.t_title_C.setText(model.getTripTiltle());
        holder.t_price_C.setText(model.getTripPrice());
        holder.t_planner_C.setText(model.getTripplanner());
        holder.t_start_place_C.setText(model.getTripstartplace());
        holder.t_duration_C.setText(model.getTripduration());
        Glide.with(context).load(model.gettripImage1()).into(holder.t_image_C);

//        Glide.with(holder.t_image.getContext())
//                .load(model.gettripImage1())
//                .placeholder(R.drawable.abc)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .into(holder.t_image);


        holder.t_detailbutton_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToursDetails.class);
                intent.putExtra("TripImage",model.gettripImage1());
                intent.putExtra("title",model.getTripTiltle());
                intent.putExtra("TripDate",model.getTripDate());
                intent.putExtra("TripTime", model.getTripTime());
                intent.putExtra("startingPlace",model.getTripstartplace());
                intent.putExtra("TripVehicle",model.getTripVehicles_types());
                intent.putExtra("TripVehicleNum",model.getTripVehicleNum());
                intent.putExtra("Duration",model.getTripduration());
                intent.putExtra("Price",model.getTripPrice());
                intent.putExtra("phone",model.getTripphone());
                intent.putExtra("Description",model.getTripDesc());
                intent.putExtra("Planner",model.getTripplanner());

                context.startActivity(intent);
            }
        });







        holder.t_call_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getTripphone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
        holder.t_location_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = model.getTripstartplace() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
                String url = "http://maps.google.com/maps?daddr="+location;
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                view.getContext().startActivity(intent);

            }
        });

        holder.t_whatsapp_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected user's information
                String phoneNumber = model.getTripphone();
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
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourist_customer_rv,parent,false );
        return new TourViewHolder(view);
    }

    class TourViewHolder extends RecyclerView.ViewHolder{
        TextView  t_title_C ,t_price_C,t_planner_C,t_duration_C,t_start_place_C;
        ImageView t_image_C,t_whatsapp_C;
        ImageView t_call_C,t_location_C ;
        Button t_detailbutton_C,Edit_T_C, Delete_T_C;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            t_title_C = itemView.findViewById(R.id.trip_title_C);
            t_start_place_C = itemView.findViewById(R.id.trip_start_place_C);
            t_price_C = itemView.findViewById(R.id.trip_price_C);
            t_duration_C = itemView.findViewById(R.id.trip_duration_C);
            t_image_C = itemView.findViewById(R.id.trip_image_C);
            t_call_C = itemView.findViewById(R.id.trip_call_C);
            t_location_C = itemView.findViewById(R.id.trip_location_C);
            t_whatsapp_C = itemView.findViewById(R.id.trip_whatsapp_C);
            t_detailbutton_C = itemView.findViewById(R.id.trip_detail_C);
            t_planner_C = itemView.findViewById(R.id.trip_planner_C);


        }
    }
//
}





//    private Context context;
//    private List<TourModelClass> tourModelClassList;
//
//    public TourAdapter(Context context, List<TourModelClass> tourModelClassList) {
//        this.context = context;
//        this.tourModelClassList = tourModelClassList;
//    }
//
//    @NonNull
//    @Override
//    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourist_rv,parent,false);
//        return new TourViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
//        final TourModelClass tourModelClass = tourModelClassList.get(position);
//
//        Glide.with(context).load(tourModelClassList.get(position).gettripImage1()).into(holder.t_image);
//        holder.t_title.setText(tourModelClassList.get(position).getTripTiltle());
//        holder.t_price.setText(tourModelClassList.get(position).getTripPrice());
//        holder.t_planner.setText(tourModelClassList.get(position).getTripplanner());
//        holder.t_start_place.setText(tourModelClassList.get(position).getTripstartplace());
//        holder.t_duration.setText(tourModelClassList.get(position).getTripduration());
////        holder.t_phonenumber.setText(tourModelClassList.get(position).getTripphone());
////        holder.t_description.setText(tourModelClassList.get(position).getTripDesc());
//
//
//        holder.t_detailbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ToursDetails.class);
//                intent.putExtra("TripImage",tourModelClassList.get(holder.getAdapterPosition()).gettripImage1());
//                intent.putExtra("title",tourModelClassList.get(holder.getAdapterPosition()).getTripTiltle());
//                intent.putExtra("TripDate",tourModelClassList.get(holder.getAdapterPosition()).getTripDate());
//                intent.putExtra("TripTime",tourModelClassList.get(holder.getAdapterPosition()).getTripTime());
//                intent.putExtra("startingPlace",tourModelClassList.get(holder.getAdapterPosition()).getTripstartplace());
//                intent.putExtra("TripVehicle",tourModelClassList.get(holder.getAdapterPosition()).getTripVehicles_types());
//                intent.putExtra("TripVehicleNum",tourModelClassList.get(holder.getAdapterPosition()).getTripVehicleNum());
//                intent.putExtra("Duration",tourModelClassList.get(holder.getAdapterPosition()).getTripduration());
//                intent.putExtra("Price",tourModelClassList.get(holder.getAdapterPosition()).getTripPrice());
//                intent.putExtra("phone",tourModelClassList.get(holder.getAdapterPosition()).getTripphone());
//                intent.putExtra("Description",tourModelClassList.get(holder.getAdapterPosition()).getTripDesc());
//                intent.putExtra("Planner",tourModelClassList.get(holder.getAdapterPosition()).getTripplanner());
//
//                context.startActivity(intent);
//
//            }
//        });
//
//        holder.t_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String location = tourModelClass.getTripstartplace() ;
////                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
////                context.startActivity(intent);
//                String url = "http://maps.google.com/maps?daddr="+location;
//                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
//                context.startActivity(intent);
//
//            }
//        });
//
//        holder.t_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String phone = tourModelClass.getTripphone();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                context.startActivity(intent);
//            }
//        });
//        holder.t_whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the selected user's information
//                String phoneNumber = tourModelClass.getTripphone();
//                String message = "Hello!"; // Optional message to pre-fill in WhatsApp
//
//                // Open WhatsApp with the selected user
//                openWhatsApp(phoneNumber, message);
//            }
//        });
//
//    }
//    private void openWhatsApp(String phoneNumber, String message) {
//        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setPackage("com.whatsapp");
//
//        // Check if WhatsApp is installed on the device
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        } else {
//            // WhatsApp is not installed, display a Toast or handle the case accordingly
//            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//         return tourModelClassList.size();
//    }
//
//    public void searchDataList(ArrayList<TourModelClass> searchList){
//        tourModelClassList = searchList;
//        notifyDataSetChanged();
//    }
//
//    class TourViewHolder extends RecyclerView.ViewHolder{
//
//        TextView  t_title ,t_price,t_planner,t_duration,t_start_place;
//        ImageView t_image,t_whatsapp;
//        ImageView t_call,t_location ;
//        Button t_detailbutton;
//
//        public TourViewHolder(@NonNull View itemView) {
//            super(itemView);
//            t_title = itemView.findViewById(R.id.trip_title);
//            t_start_place = itemView.findViewById(R.id.trip_start_place);
//            t_price = itemView.findViewById(R.id.trip_price);
//            t_duration = itemView.findViewById(R.id.trip_duration);
//            t_image = itemView.findViewById(R.id.trip_image);
//            t_call = itemView.findViewById(R.id.trip_call);
//            t_location = itemView.findViewById(R.id.trip_location);
//            t_whatsapp = itemView.findViewById(R.id.trip_whatsapp);
//            t_detailbutton = itemView.findViewById(R.id.trip_detail);
//            t_planner = itemView.findViewById(R.id.trip_planner);
//
//
//        }
//    }