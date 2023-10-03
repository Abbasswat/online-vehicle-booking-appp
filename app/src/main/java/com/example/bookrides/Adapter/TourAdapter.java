package com.example.bookrides.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.lights.LightState;
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
import com.example.bookrides.MainAdapter;
import com.example.bookrides.Model.DataClass;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourAdapter extends FirebaseRecyclerAdapter<TourModelClass,TourAdapter.TourViewHolder> {

    private Context context;
    private List<LoaderModelClass> loaderModelClassList;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TourAdapter(@NonNull FirebaseRecyclerOptions<TourModelClass> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull TourViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull TourModelClass model) {

        holder.t_title.setText(model.getTripTiltle());
        holder.t_price.setText(model.getTripPrice());
        holder.t_planner.setText(model.getTripplanner());
        holder.t_start_place.setText(model.getTripstartplace());
        holder.t_duration.setText(model.getTripduration());
        Glide.with(context).load(model.gettripImage1()).into(holder.t_image);

//        Glide.with(holder.t_image.getContext())
//                .load(model.gettripImage1())
//                .placeholder(R.drawable.abc)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .into(holder.t_image);


        holder.t_detailbutton.setOnClickListener(new View.OnClickListener() {
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





        holder.Edit_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.t_image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent_trip))
                        .setExpanded(true,1200)
                        .create();


                View myview=dialogPlus.getHolderView();

                final EditText visiting_Place=myview.findViewById(R.id.uvisitingPlace_T);
                final EditText phone_T=myview.findViewById(R.id.uphone_T);
                final EditText date=myview.findViewById(R.id.udate_T);
                final EditText vehicletypeT=myview.findViewById(R.id.uvehicletype_T);
                final EditText vehiclenumberT=myview.findViewById(R.id.uvehiclenumber_T);
                final EditText locationT=myview.findViewById(R.id.ulocation_T);
                final EditText Duration=myview.findViewById(R.id.utripDuration_T);
                final EditText Price=myview.findViewById(R.id.utripPrice_T);
                final EditText Desc=myview.findViewById(R.id.utripDesc_T);

                final EditText purlT=myview.findViewById(R.id.uimgurl_T);
                Button submit=myview.findViewById(R.id.usubmit);

                visiting_Place.setText(model.getTripTiltle());
                phone_T.setText(model.getTripphone());
                date.setText(model.getTripDate());
                Duration.setText(model.getTripduration());
                vehicletypeT.setText(model.getTripVehicles_types());
                vehiclenumberT.setText(model.getTripVehicleNum());
                locationT.setText(model.getTripstartplace());
                Price.setText(model.getTripPrice());
                Desc.setText(model.getTripDesc());
                purlT.setText(model.gettripImage1());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("tripTitle  ",visiting_Place.getText().toString());
                        map.put("tripphone",phone_T.getText().toString());
                        map.put("tripDate",date.getText().toString());
                        map.put("tripduration",Duration.getText().toString());
                        map.put("tripVehicles_type",vehicletypeT.getText().toString());
                        map.put("tripVehicleNum",vehiclenumberT.getText().toString());
                        map.put("tripstartplace",locationT.getText().toString());
                        map.put("tripPrice",Price.getText().toString());
                        map.put("tripDesc",Desc.getText().toString());
                        map.put("tripImage1",purlT.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Tour Booking")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.t_image.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.t_image.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.Delete_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(holder.t_title.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Tour Booking")
                                .child(getRef(position).getKey()).removeValue();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.t_title.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
        holder.t_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = model.getTripphone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                view.getContext().startActivity(intent);
            }
        });
        holder.t_location.setOnClickListener(new View.OnClickListener() {
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

        holder.t_whatsapp.setOnClickListener(new View.OnClickListener() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tourist_rv,parent,false );
        return new TourViewHolder(view);
    }

    class TourViewHolder extends RecyclerView.ViewHolder{
        TextView  t_title ,t_price,t_planner,t_duration,t_start_place;
        ImageView t_image,t_whatsapp;
        ImageView t_call,t_location,Edit_T, Delete_T ;
        Button t_detailbutton;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            t_title = itemView.findViewById(R.id.trip_title);
            t_start_place = itemView.findViewById(R.id.trip_start_place);
            t_price = itemView.findViewById(R.id.trip_price);
            t_duration = itemView.findViewById(R.id.trip_duration);
            t_image = itemView.findViewById(R.id.trip_image);
            t_call = itemView.findViewById(R.id.trip_call);
            t_location = itemView.findViewById(R.id.trip_location);
            t_whatsapp = itemView.findViewById(R.id.trip_whatsapp);
            t_detailbutton = itemView.findViewById(R.id.trip_detail);
            t_planner = itemView.findViewById(R.id.trip_planner);
            Edit_T = itemView.findViewById(R.id.updateT);
            Delete_T = itemView.findViewById(R.id.deleteT);

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