package com.example.quantumittask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth mFirebaseAuth;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions gso;
    int check=0;
    private FirebaseAuth.AuthStateListener authStateListener;
    RecyclerView circularRecyclerView,layoutRecycler,layoutRecycler2,layoutRecycler3;
    List<App> circularAppList,recyclerAppList,recyclerAppList2,recyclerAppList3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       int check1 = getIntent().getIntExtra("checkforlogin",0);
        check = check1;
        getSupportActionBar().setTitle("Home");

        SliderView sliderView  = findViewById(R.id.image_slider);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.oneimageslider);
        images.add(R.drawable.twoimageslider);
        images.add(R.drawable.threeimageslider);
        MySliderAdapter mySliderAdapter = new MySliderAdapter(images);
        sliderView.setSliderAdapter(mySliderAdapter);
        sliderView.setAutoCycle(true);


        circularRecyclerView = findViewById(R.id.circularViewList);
        circularAppList = new ArrayList<>();
        circularAppList.add(new App(R.drawable.books,"BOOKS"));
        circularAppList.add(new App(R.drawable.toysone,"TOYS"));
        circularAppList.add(new App(R.drawable.child,"BABY"));
        circularAppList.add(new App(R.drawable.footwear,"FOOT WEAR"));
        circularAppList.add(new App(R.drawable.computer,"COMPUTER"));
        circularAppList.add(new App(R.drawable.artandcrafts,"ART"));
        circularAppList.add(new App(R.drawable.car,"CAR"));

        LinearLayoutManager m1 = new LinearLayoutManager(this);
        m1.setOrientation(LinearLayoutManager.HORIZONTAL);
        circularRecyclerView.setLayoutManager(m1);

        CircularAdapter adapter1 = new CircularAdapter(this,circularAppList);
        circularRecyclerView.setAdapter(adapter1);


        layoutRecycler = findViewById(R.id.layout_recyclerview_list);
        recyclerAppList = new ArrayList<>();
        recyclerAppList.add(new App(R.drawable.lappy,"UP TO 50% OFF"));
        recyclerAppList.add(new App(R.drawable.flowerpot,"UP TO 25% OFF"));
        recyclerAppList.add(new App(R.drawable.phone,"UP TO 20% OFF"));
        recyclerAppList.add(new App(R.drawable.shoes,"UP TO 70% OFF"));
        recyclerAppList.add(new App(R.drawable.watch,"UP TO 10% OFF"));
        recyclerAppList.add(new App(R.drawable.jackets,"UP TO 40% OFF"));
        recyclerAppList.add(new App(R.drawable.dress,"UP TO 30% OFF"));

        LinearLayoutManager m2 = new LinearLayoutManager(this);
        m2.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutRecycler.setLayoutManager(m2);

        CustomAdapter adapter2 = new CustomAdapter(this,recyclerAppList);
        layoutRecycler.setAdapter(adapter2);

        layoutRecycler2 = findViewById(R.id.layout_recyclerview_list2);
        recyclerAppList2 = new ArrayList<>();
        recyclerAppList2.add(new App(R.drawable.clothing2,"UP TO 10% OFF"));
        recyclerAppList2.add(new App(R.drawable.clothing4,"UP TO 25% OFF"));
        recyclerAppList2.add(new App(R.drawable.clothing5,"UP TO 20% OFF"));
        recyclerAppList2.add(new App(R.drawable.dress,"UP TO 40% OFF"));
        recyclerAppList2.add(new App(R.drawable.watch,"UP TO 90% OFF"));
        recyclerAppList2.add(new App(R.drawable.jackets,"UP TO 60% OFF"));
        recyclerAppList2.add(new App(R.drawable.kids,"UP TO 30% OFF"));

        LinearLayoutManager m3 = new LinearLayoutManager(this);
        m3.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutRecycler2.setLayoutManager(m3);

        CustomAdapter adapter3 = new CustomAdapter(this,recyclerAppList2);
        layoutRecycler2.setAdapter(adapter3);


        layoutRecycler3 = findViewById(R.id.layout_recyclerview_list3);
        recyclerAppList3 = new ArrayList<>();
        recyclerAppList3.add(new App(R.drawable.gadget1,"UP TO 30% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget2,"UP TO 25% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget3,"UP TO 50% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget5,"UP TO 10% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget4,"UP TO 60% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget10,"UP TO 40% OFF"));
        recyclerAppList3.add(new App(R.drawable.gadget20,"UP TO 40% OFF"));

        LinearLayoutManager m4 = new LinearLayoutManager(this);
        m4.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutRecycler3.setLayoutManager(m4);

        CustomAdapter adapter4 = new CustomAdapter(this,recyclerAppList3);
        layoutRecycler3.setAdapter(adapter4);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inside_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_home:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
       if(check==1)
       {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
               @Override
               public void onResult(@NonNull Status status) {
                   if(status.isSuccess())
                   {
                       startActivity(new Intent(HomeActivity.this,MainActivity.class));
                       finish();
                   }
                   else
                   {
                       Toast.makeText(HomeActivity.this,"Logout failed",Toast.LENGTH_LONG).show();
                   }
               }
           });
       }
       else{
           FirebaseAuth.getInstance().signOut();
           Intent i = new Intent(this,MainActivity.class);
           startActivity(i);
           finish();
           Toast.makeText(HomeActivity.this,"Logged out successfuly",Toast.LENGTH_LONG).show();

       }
         }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}