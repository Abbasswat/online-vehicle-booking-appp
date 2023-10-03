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

public class LoaderAdapter_C extends FirebaseRecyclerAdapter<LoaderModelClass, LoaderAdapter_C.LoaderViewHolder> {
private Context context;
private List<LoaderModelClass> loaderModelClassList;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LoaderAdapter_C(@NonNull FirebaseRecyclerOptions<LoaderModelClass> options, Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_displayed_layout_c,parent,false );
        return new LoaderViewHolder(view);
    }

    class LoaderViewHolder extends RecyclerView.ViewHolder{
        TextView l_name,l_location,l_Vehicltype,l_type;
        ImageView l_image;
        Button edit,delete;
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

        }
    }





//DatabaseReference reference;
//private Context context;
//private List<LoaderModelClass> loaderModelClassList;
//
//    public LoaderAdapter(Context context, List<LoaderModelClass> loaderModelClassList) {
//        this.context = context;
//        this.loaderModelClassList = loaderModelClassList;
//    }
//
//    @NonNull
//    @Override
//    public LoaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_displayed_layout,parent,false);
//        return new LoaderAdapter.LoaderViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull LoaderViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        final LoaderModelClass loaderModelClass = loaderModelClassList.get(position);
//
//
//        Glide.with(context).load(loaderModelClassList.get(position).getLoaderImage()).into(holder.l_image);
//        holder.l_name.setText(loaderModelClassList.get(position).getLoaderUsername());
//        holder.l_location.setText(loaderModelClassList.get(position).getLoaderlocation());
//        holder.l_Vehicltype.setText(loaderModelClassList.get(position).getLoadertVehicle());
//        holder.l_name.setText(loaderModelClassList.get(position).getLoaderUsername());
//
//
//
//
//
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.l_image.getContext())
//                        .setContentHolder(new ViewHolder(LayoutInflater.from(context).inflate(R.layout.dialogcontent, null)))
//                        .setExpanded(true,1100)
//                        .create();
//                View myview=dialogPlus.getHolderView();
//                final EditText purl=myview.findViewById(R.id.uimgurl);
//                final EditText name=myview.findViewById(R.id.uname);
//                final EditText phone=myview.findViewById(R.id.uphone);
//                final EditText email=myview.findViewById(R.id.uemail);
//                final EditText vehicletype=myview.findViewById(R.id.uvehicletype);
//                final EditText vehiclenumber=myview.findViewById(R.id.uvehiclenumber);
//                final EditText location=myview.findViewById(R.id.ulocation);
//                Button submit=myview.findViewById(R.id.usubmit);
//
//                purl.setText(loaderModelClass.getLoaderImage());
//                name.setText(loaderModelClass.getLoaderUsername());
//                phone.setText(loaderModelClass.getLoaderphone());
//                email.setText(loaderModelClass.getLoaderEmail());
//                vehicletype.setText(loaderModelClass.getLoadertVehicle());
//                vehiclenumber.setText(loaderModelClass.getVehiclenumber());
//                location.setText(loaderModelClass.getLoaderlocation());
//
//
//                dialogPlus.show();
//
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Map<String,Object> map=new HashMap<>();
//                        map.put("loaderImage",purl.getText().toString());
//                        map.put("loaderUsername",name.getText().toString());
//                        map.put("loaderEmail",email.getText().toString());
//                        map.put("loaderphone",phone.getText().toString());
//                        map.put("loaderVehicle",vehicletype.getText().toString());
//                        map.put("loadernumber",vehiclenumber.getText().toString());
//                        map.put("loaderlocation",location.getText().toString());
//
//                        FirebaseDatabase.getInstance().getReference().child("Loader Booking")
//                                .getRef(position)
//
//                    }
//                });
//
//
//            }
//        });
//
//        holder.l_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Loader_details.class);
//                intent.putExtra("Image",loaderModelClassList.get(holder.getAdapterPosition()).getLoaderImage());
//                intent.putExtra("name",loaderModelClassList.get(holder.getAdapterPosition()).getLoaderUsername());
//                intent.putExtra("email",loaderModelClassList.get(holder.getAdapterPosition()).getLoaderEmail());
//                intent.putExtra("phone",loaderModelClassList.get(holder.getAdapterPosition()).getLoaderphone());
//                intent.putExtra("locations",loaderModelClassList.get(holder.getAdapterPosition()).getLoaderlocation());
//                intent.putExtra("loaderType",loaderModelClassList.get(holder.getAdapterPosition()).getLoadertVehicle());
//                intent.putExtra("loadernumber",loaderModelClassList.get(holder.getAdapterPosition()).getVehiclenumber());
////                intent.putExtra("Description",tourModelClassList.get(holder.getAdapterPosition()).getTripDesc());
//
//                context.startActivity(intent);
//            }
//        });
//
//
//        holder.l_locationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String location = loaderModelClass.getLoaderlocation() ;
////                Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER, Uri.fromParts("Location", location, null));
////                context.startActivity(intent);
//                String url = "http://maps.google.com/maps?daddr="+location;
//                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
//                context.startActivity(intent);
//
//            }
//        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the position of the clicked item
//                int position = holder.getAdapterPosition();
//
//                if (position != RecyclerView.NO_POSITION) {
//                    // Ensure the position is valid
//                    if (position >= 0 && position < loaderModelClassList.size()) {
//                        String keyToRemove = loaderModelClassList.get(position).getKey();
//                        if (keyToRemove != null && !keyToRemove.isEmpty()) {
//                            // Remove the item from Firebase
//                            FirebaseDatabase.getInstance().getReference().child("Loader Booking")
//                                    .child(keyToRemove).removeValue()
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            // Data removed successfully
//                                            Toast.makeText(holder.itemView.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // Handle the failure to remove data
//                                            Toast.makeText(holder.itemView.getContext(), "Failed to delete item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                            // Optionally, remove the item from the list if needed
//                            loaderModelClassList.remove(position);
//                            notifyItemRemoved(position);
//                        } else {
//                            // Handle the case where the key is null or empty
//                            Toast.makeText(holder.itemView.getContext(), "Invalid key for deletion", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        // Handle the case where the position is out of bounds
//                        Toast.makeText(holder.itemView.getContext(), "Invalid position for deletion", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
////        holder.delete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                FirebaseDatabase.getInstance().getReference().child("Loader Booking")
////                        .child(context.getPackageResourcePath().getkey()
////                        .removeValue()
////                        .addOnCompleteListener(new OnCompleteListener<Void>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Void> task) {
////                                Toast.makeText(context, "Delete succesfu", Toast.LENGTH_SHORT).show();
////                            }
////                        }));
////            }
////        });
//
//        holder.l_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String phone = loaderModelClass.getLoaderphone();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                context.startActivity(intent);
//            }
//        });
//        holder.l_whatsapploader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the selected user's information
//                String phoneNumber = loaderModelClass.getLoaderphone();
//                String message = "Hello!"; // Optional message to pre-fill in WhatsApp
//
//                // Open WhatsApp with the selected user
//                openWhatsApp(phoneNumber, message);
//            }
//        });
//
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
//    @Override
//    public int getItemCount() {
//        return loaderModelClassList.size();
//    }

    public void searchDataList(ArrayList<LoaderModelClass> searchList){
        loaderModelClassList = searchList;
        notifyDataSetChanged();
    }

//    public class LoaderViewHolder extends RecyclerView.ViewHolder{
//
//        TextView l_name,l_location,l_Vehicltype,l_type;
//        ImageView l_image;
//        Button edit,delete;
//        ImageView l_call,l_locationButton ,l_whatsapploader;
//
//        public LoaderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            l_name = itemView.findViewById(R.id.userNameLoader);
//            l_image = itemView.findViewById(R.id.loader_Image);
//            l_location = itemView.findViewById(R.id.userLocationLoader);
////            l_type= itemView.findViewById(R.id.typeLoader);
//            l_Vehicltype = itemView.findViewById(R.id.loaderVehicle);
//            l_call = itemView.findViewById(R.id.call_Loader);
//            l_locationButton = itemView.findViewById(R.id.location_Loader);
//            l_whatsapploader = itemView.findViewById(R.id.Whatsapp_Loader);
//            edit = itemView.findViewById(R.id.update);
//            delete = itemView.findViewById(R.id.delete);
//
//        }
//    }
}
