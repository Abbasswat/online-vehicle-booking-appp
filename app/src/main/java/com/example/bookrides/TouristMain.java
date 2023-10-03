package com.example.bookrides;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.example.bookrides.Adapter.UserAdapter;
import com.example.bookrides.Fragments.HomeFragment;
import com.example.bookrides.Fragments.categorySelectedFragment;
import com.example.bookrides.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TouristMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference userRef;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar1;
    private NavigationView nav_view;

    private CircleImageView nav_profile_image;
    private TextView nav_fullname, nav_email, nav_bloodgroup, nav_type;


    private List<User> userList;
    private UserAdapter userAdapter;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserId;
    Bundle bundle;
    boolean homeFragmentStatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_main);

        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setTitle("online vehicle Booking app");

        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        fragmentLoader(new HomeFragment());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();


        bundle = new Bundle();
        boolean homeFragmentStatus=false;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(TouristMain.this, drawerLayout,
                toolbar1, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();


        nav_view.setNavigationItemSelectedListener(this);

//        progressbar = findViewById(R.id.progressbar);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(layoutManager);
//
//        userList = new ArrayList<>();
//        userAdapter = new UserAdapter(MainActivity.this, userList);
//
//        recyclerView.setAdapter(userAdapter);
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
//                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//              String type = snapshot.child("type").getValue().toString();
//              if (type.equals("donor")){
//                  readRecipients();
//              }else {
//                  readDonors();
//              }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        nav_profile_image = nav_view.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_bloodgroup = nav_view.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);
        nav_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);

//
//        try {
//
//
//            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(
//                    FirebaseAuth.getInstance().getCurrentUser().getUid()
//            );
//
//
//            userRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//
//                        String name = snapshot.child("name").getValue().toString();
//                        nav_fullname.setText(name);
//
//                        String email = snapshot.child("email").getValue().toString();
//                        nav_email.setText(email);
//
//                        String bloodgroup = snapshot.child("bloodgroup").getValue().toString();
//                        nav_bloodgroup.setText(bloodgroup);
//
//                        String type = snapshot.child("type").getValue().toString();
//                        nav_type.setText(type);
//
//
//                        if (snapshot.hasChild("image")){
//                            String imageUrl = snapshot.child("profilepictureurl").getValue().toString();
//                            Glide.with(getApplicationContext()).load(imageUrl).into(nav_profile_image);
//                        }else {
//                            nav_profile_image.setImageResource(R.drawable.profile);
//                        }
//
//                        Menu nav_menu = nav_view.getMenu();
//
//
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Error : "+ e, Toast.LENGTH_SHORT).show();
//        }
//
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
//            SendUserToLoginActivity();
        }
        else{

//            VerifyUserExistance();
            updateUserStatus("online");


//            String data = getIntent().getExtras().getString("openChatSection","defaultKey");
//            if (data.isEmpty()){
//
//            }else{
//
//
//
//                        bottomNavigation.performClick();
//
//                        fragmentLoader(new ChatMainFragment());
//
//
////                View view = bottomNavigation.findViewById(R.id.bottomNagivation);
////                view.performClick();
////                fragmentLoader(new ChatMainFragment());
//
//            }





        }
    }

//
//    private void readDonors() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
//                .child("users");
//        Query query = reference.orderByChild("type").equalTo("donor");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    User user = dataSnapshot.getValue(User.class);
//                    userList.add(user);
//                }
//                userAdapter.notifyDataSetChanged();
//                progressbar.setVisibility(View.GONE);
//
//                if (userList.isEmpty()){
//                    Toast.makeText(MainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
//                    progressbar.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void readRecipients() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
//                .child("users");
//        Query query = reference.orderByChild("type").equalTo("recipient");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//              userList.clear();
//              for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                  User user = dataSnapshot.getValue(User.class);
//                  userList.add(user);
//              }
//              userAdapter.notifyDataSetChanged();
//              progressbar.setVisibility(View.GONE);
//
//              if (userList.isEmpty()){
//                  Toast.makeText(MainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
//                  progressbar.setVisibility(View.GONE);
//              }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){





            case R.id.Motorcycle:


                bundle.putString("group", "Motorcycle");
// set Fragmentclass Arguments
                categorySelectedFragment fragobj = new categorySelectedFragment();
                fragobj.setArguments(bundle);
                fragmentLoader(fragobj);
                SettingDatatoToolbar("Motorcycle");
                break;

            case R.id.Suzuki:
                bundle.putString("group", "Suzuki");
                categorySelectedFragment fragobj2 = new categorySelectedFragment();
                fragobj2.setArguments(bundle);
                fragmentLoader(fragobj2);
                SettingDatatoToolbar("Suzuki");
                break;
            case R.id.Dyna:
                bundle.putString("group", "Dyna");
                categorySelectedFragment fragobj3 = new categorySelectedFragment();
                fragobj3.setArguments(bundle);
                fragmentLoader(fragobj3);
                SettingDatatoToolbar("Dyna");


                break;

            case R.id.Rilshaw:
                bundle.putString("group", "Rikshaw");
                categorySelectedFragment fragobj4 = new categorySelectedFragment();
                fragobj4.setArguments(bundle);
                fragmentLoader(fragobj4);
                SettingDatatoToolbar("Rikshaw");

                break;
            case R.id.PlaneCoach:
                bundle.putString("group", "PlaneCoach");
                categorySelectedFragment fragobj5 = new categorySelectedFragment();
                fragobj5.setArguments(bundle);
                fragmentLoader(fragobj5);
                SettingDatatoToolbar("PlaneCoach");

                break;
            case R.id.Car:
                bundle.putString("group", "Car");
                categorySelectedFragment fragobj6 = new categorySelectedFragment();
                fragobj6.setArguments(bundle);
                fragmentLoader(fragobj6);
                SettingDatatoToolbar("Car");

                break;
            case R.id.CarryDabba:
                bundle.putString("group", "CarryDabba");
                categorySelectedFragment fragobj7 = new categorySelectedFragment();
                fragobj7.setArguments(bundle);
                fragmentLoader(fragobj7);
                SettingDatatoToolbar("CarryDabba");
                break;
            case R.id.Van:
                bundle.putString("group", "Van");
                categorySelectedFragment fragobj8 = new categorySelectedFragment();
                fragobj8.setArguments(bundle);
                fragmentLoader(fragobj8);
                SettingDatatoToolbar("Van");
                break;




        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;




    }

    private void updateUserStatus(String state){
        String saveCurrentUserTime, saveCurrentUserDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate  = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentUserDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime  = new SimpleDateFormat("hh:mm ss");
        saveCurrentUserTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();

        onlineStateMap.put("time", saveCurrentUserTime);
        onlineStateMap.put("date", saveCurrentUserDate);
        onlineStateMap.put("state", state);

        currentUserId = currentUser.getUid();
        rootRef.child("users").child(currentUserId).child("userState")
                .updateChildren(onlineStateMap);

    }
    private void fragmentLoader(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void SettingDatatoToolbar( String ToolbarData){
        this.getSupportActionBar().setTitle(ToolbarData);

    }

    //    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        if (homeFragmentStatus==true){
//            finish();
//        }
//
//        if (getSupportFragmentManager().getBackStackEntryCount() ==0) {
//            // dLayout.closeDrawers();
//            homeFragmentStatus=false;
//            finish();
//        }
//            else
//        {
//            homeFragmentStatus=true;
//          fragmentLoader(new FragmentHome());
//
//        }
//    }
    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}