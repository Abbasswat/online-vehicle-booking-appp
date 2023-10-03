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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookrides.DetailActivity;
import com.example.bookrides.Model.DataClass;
import com.example.bookrides.R;
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

public class MyAdapter_C extends FirebaseRecyclerAdapter<DataClass, MyAdapter_C.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter_C(@NonNull FirebaseRecyclerOptions<DataClass> options, Context context) {
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
protected void onBindViewHolder(@NonNull MyAdapter_C.MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DataClass model) {


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buses_rv,parent,false );
        return new MyAdapter_C.MyViewHolder(view);
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

    Button editB, deleteB;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage_LC);
        recDest = itemView.findViewById(R.id.recDest_LC);
        recCard = itemView.findViewById(R.id.recCard_LC);
        recTime = itemView.findViewById(R.id.recTime_LC);
        recTitle = itemView.findViewById(R.id.recTitle_LC);
        recAddress = itemView.findViewById(R.id.recAddress_LC);
        recDate = itemView.findViewById(R.id.recDate_LC);
        callBus = itemView.findViewById(R.id.callBus_LC);
        locationBus = itemView.findViewById(R.id.locationBus_LC);
        Whatsapp = itemView.findViewById(R.id.Whatsapp_LC);


    }

}
}
