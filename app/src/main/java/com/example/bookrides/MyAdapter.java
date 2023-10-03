package com.example.bookrides;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.Adapter.TourAdapter;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.Model.LoaderModelClass;
import com.example.bookrides.Model.TourModelClass;
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

public class MyAdapter extends FirebaseRecyclerAdapter<DataClass,MyAdapter.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<DataClass> options, Context context) {
        super(options);
        this.context=context;
    }


//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
//return new MyViewHolder(view);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//
//
//        holder.recTitle.setText(dataList.get(position).getDataTitle());
//        holder.recDest.setText(dataList.get(position).getDataDestination());
//        holder.recTime.setText(dataList.get(position).getDataTime());
//        holder.recAddress.setText(dataList.get(position).getDataAddress());
//        holder.recDate.setText(dataList.get(position).getDataDate());
//        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
//
//
//        holder.recCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("Image",dataList.get(holder.getAdapterPosition()).getDataImage());
//                intent.putExtra("Description",dataList.get(holder.getAdapterPosition()).getDataDesc());
//                intent.putExtra("Title",dataList.get(holder.getAdapterPosition()).getDataTitle());
//                intent.putExtra("Time",dataList.get(holder.getAdapterPosition()).getDataTime());
//                intent.putExtra("Phone",dataList.get(holder.getAdapterPosition()).getDataPhone());
//                intent.putExtra("Address",dataList.get(holder.getAdapterPosition()).getDataAddress());
//                intent.putExtra("Date",dataList.get(holder.getAdapterPosition()).getDataDate());
//                intent.putExtra("Destination",dataList.get(holder.getAdapterPosition()).getDataDestination());
//                intent.putExtra("Company",dataList.get(holder.getAdapterPosition()).getDataCompanyName());
//                intent.putExtra("Buses",dataList.get(holder.getAdapterPosition()).getdataBus_types());
////                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
//
//                context.startActivity(intent);
//            }
//        });
//        holder.locationBus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String location = dataClass.getDataAddress() ;
////                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
////                context.startActivity(intent);
//                String url = "http://maps.google.com/maps?daddr="+location;
//                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
//                context.startActivity(intent);
//
//            }
//        });
//
//
//        holder.callBus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String phone = dataClass.getDataPhone();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                context.startActivity(intent);
//            }
//        });
//        holder.Whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the selected user's information
//                String phoneNumber = dataClass.getDataPhone();
//                String message = "Hello!"; // Optional message to pre-fill in WhatsApp
//
//                // Open WhatsApp with the selected user
//                openWhatsApp(phoneNumber, message);
//            }
//        });
//    }
@Override
protected void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DataClass model) {


        holder.recTitle.setText(model.getDataTitle());
        holder.recDest.setText(model.getDataDestination());
        holder.recTime.setText(model.getDataTime());
        holder.recAddress.setText(model.getDataAddress());
        holder.recDate.setText(model.getDataDate());
        Glide.with(context).load(model.getDataImage()).into(holder.recImage);


//    Glide.with(holder.t_image.getContext())
//            .load(model.gettripImage1())
//            .placeholder(R.drawable.abc)
//            .circleCrop()
//            .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//            .into(holder.t_image);


    holder.recCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image",model.getDataImage());
                intent.putExtra("Description",model.getDataDesc());
                intent.putExtra("Title",model.getDataTitle());
                intent.putExtra("Time",model.getDataTime());
                intent.putExtra("Phone",model.getDataPhone());
                intent.putExtra("Address",model.getDataAddress());
                intent.putExtra("Date",model.getDataDate());
                intent.putExtra("Destination",model.getDataDestination());
                intent.putExtra("Company",model.getDataCompanyName());
                intent.putExtra("Buses",model.getdataBus_types());
//                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
        }
    });





    holder.editB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.recImage.getContext())
                    .setContentHolder(new ViewHolder(R.layout.dialogcontent_trip))
                    .setExpanded(true,1200)
                    .create();


            View myview=dialogPlus.getHolderView();

            final EditText visiting_Place=myview.findViewById(R.id.uvisitingPlace_T);
            final EditText phone_T=myview.findViewById(R.id.uphone_T);
            final EditText date=myview.findViewById(R.id.udate_T);
            final EditText vehicletypeT=myview.findViewById(R.id.uvehicletype_T);
//            final EditText vehiclenumberT=myview.findViewById(R.id.uvehiclenumber_T);
            final EditText locationT=myview.findViewById(R.id.ulocation_T);
            final EditText Duration=myview.findViewById(R.id.utripDuration_T);
            final EditText Price=myview.findViewById(R.id.utripPrice_T);
            final EditText Desc=myview.findViewById(R.id.utripDesc_T);

            final EditText purlT=myview.findViewById(R.id.uimgurl_T);
            Button submit=myview.findViewById(R.id.usubmit);

            visiting_Place.setText(model.getDataTitle());
            phone_T.setText(model.getDataPhone());
            date.setText(model.getDataDate());
            Duration.setText(model.getDataAddress());
            vehicletypeT.setText(model.getdataBus_types());
//            vehiclenumberT.setText(model.getTripVehicleNum());
            locationT.setText(model.getDataDestination());
            Price.setText(model.getDataTime());
            Desc.setText(model.getDataDesc());
            purlT.setText(model.getDataImage());

            dialogPlus.show();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("dataTitle",visiting_Place.getText().toString());
                    map.put("dataPhone",phone_T.getText().toString());
                    map.put("dataDate",date.getText().toString());
                    map.put("dataAddress",Duration.getText().toString());
                    map.put("dataBus_types",vehicletypeT.getText().toString());
//                    map.put("tripVehicleNum",vehiclenumberT.getText().toString());
                    map.put("dataDestination",locationT.getText().toString());
                    map.put("dataTime",Price.getText().toString());
                    map.put("dataDesc",Desc.getText().toString());
                    map.put("dataImage",purlT.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.recImage.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(holder.recImage.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            });
                }
            });
        }
    });
    holder.deleteB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(holder.recTitle.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("Deleted data can't be undo.");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FirebaseDatabase.getInstance().getReference().child("Long Travel vehicles")
                            .child(getRef(position).getKey()).removeValue();

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(holder.recTitle.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();
        }
    });
    holder.callBus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String phone = model.getDataPhone();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            view.getContext().startActivity(intent);
        }
    });
    holder.locationBus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String location = model.getDataAddress() ;
//                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
//                context.startActivity(intent);
            String url = "http://maps.google.com/maps?daddr="+location;
            Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
            view.getContext().startActivity(intent);

        }
    });

    holder.Whatsapp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get the selected user's information
            String phoneNumber = model.getDataPhone();
            String message = "Hello!"; // Optional message to pre-fill in WhatsApp

            // Open WhatsApp with the selected user
            openWhatsApp(phoneNumber, message);
        }
    });
}
    public void searchDataListB(ArrayList<DataClass> searchListb){
        dataList = searchListb;
        notifyDataSetChanged();
    }
    public void searchDataListBD(ArrayList<DataClass> searchListb){
        dataList = searchListb;
        notifyDataSetChanged();
    }
public void searchDataList(ArrayList<DataClass> searchListb){
    dataList = searchListb;
    notifyDataSetChanged();
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false );
        return new MyAdapter.MyViewHolder(view);
    }

    //    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//
//
//}
class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView recImage;
    TextView recTitle, recDest, recTime, recAddress, recDate;
    ImageView Whatsapp, callBus, locationBus;
    CardView recCard;

    ImageView editB, deleteB;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recDest = itemView.findViewById(R.id.recDest);
        recCard = itemView.findViewById(R.id.recCard);
        recTime = itemView.findViewById(R.id.recTime);
        recTitle = itemView.findViewById(R.id.recTitle);
        recAddress = itemView.findViewById(R.id.recAddress);
        recDate = itemView.findViewById(R.id.recDate);
        editB = itemView.findViewById(R.id.updateB);
        deleteB = itemView.findViewById(R.id.deleteB);
        callBus = itemView.findViewById(R.id.callBus);
        locationBus = itemView.findViewById(R.id.locationBus);
        Whatsapp = itemView.findViewById(R.id.Whatsapp);


    }

}
}
