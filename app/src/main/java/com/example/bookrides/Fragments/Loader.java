package com.example.bookrides.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookrides.Adapter.Loader_UserAdapter;

import com.example.bookrides.Model.UserLoader;
import com.example.bookrides.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Loader extends Fragment {
    private DatabaseReference userRef;

    private RecyclerView recyclerViewL;
    private ProgressBar progressbar;

    private List<UserLoader> userListL;
    private Loader_UserAdapter loader_UserAdapter;


    ImageButton searchButtonL;
    EditText searchFriendL;
    int Status= 0;

    public Loader() {
        // Required empty public constructor
    }





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loader, container, false);

//        progressbar = view.findViewById(R.id.progressbar);
        recyclerViewL = view.findViewById(R.id.recyclerViewL);
        searchButtonL = view.findViewById(R.id.searchImageButtonIdL);
        searchFriendL = view.findViewById(R.id.searchEtIdL);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewL.setLayoutManager(layoutManager);
        userListL = new ArrayList<>();
        loader_UserAdapter = new Loader_UserAdapter(getContext(),userListL);
        recyclerViewL.setAdapter(loader_UserAdapter);












        searchButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = searchFriendL.getText().toString();
                if(location.isEmpty())
                {
                    Toast.makeText(getContext(),"Enter location ",Toast.LENGTH_SHORT).show();
                }else {
                    searchUser(location);
                }


            }
        });



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("LoaderUser").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("Loader")){
                    //readRecipients();
                    readDonorsL();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void searchUser(String location) {
        DatabaseReference searchreference = FirebaseDatabase.getInstance().getReference()
                .child("LoaderUser");
        Query query = searchreference.orderByChild("location").equalTo(location);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userListL.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserLoader userLoader = dataSnapshot.getValue(UserLoader.class);
                    userListL.add(userLoader);
                }
                loader_UserAdapter.notifyDataSetChanged();
//                progressbar.setVisibility(View.GONE);

                if (userListL.isEmpty()){
                    Toast.makeText(getContext(), "No Users found", Toast.LENGTH_SHORT).show();
//                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readDonorsL() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("LoaderUser");
        Query query = reference.orderByChild("type").equalTo("Loader");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userListL.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserLoader userLoader = dataSnapshot.getValue(UserLoader.class);
                    userListL.add(userLoader);
                }
                loader_UserAdapter.notifyDataSetChanged();
//                progressbar.setVisibility(View.GONE);

                if (userListL.isEmpty()){
                    Toast.makeText(getContext(), "No Users found", Toast.LENGTH_SHORT).show();
//                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}