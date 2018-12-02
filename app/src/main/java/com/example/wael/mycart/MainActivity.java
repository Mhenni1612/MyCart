package com.example.wael.mycart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.support.v4.app.Fragment;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListView expandableListView;
    ExpandableListViewAdapter adapter;
    Map<String, Categories> data;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        expandableListView = (ExpandableListView)findViewById(R.id.exp_listview);
        final MainActivity main=this;
        setCategoryList(main);

        createProduitFragment();

        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandablelistview, View clickedView, int groupPosition, int childPosition, long childId) {
                String cat=adapter.getChild(groupPosition, childPosition).toString();
                Log.d("click","**************************************\n"+cat);
                FragmentManager fm = getSupportFragmentManager();
                RecyclerFragment f = (RecyclerFragment) fm.findFragmentByTag("produit");
                f.refreshFragment(cat);
                return false;
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setCategoryList(final Context ctx){
        CollectionReference collCat = db.collection("/menu/UXWGaeXrMcSolwMULl9c/categories");
        Log.d("setCatList","******************************Start**************************");
        collCat
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Categories> docData = new HashMap<>();
                            Log.d("setCatList","******************************Success before loop**************************"+(task.getResult()==null));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("setCatList","******************************Success inside loop**************************");
                                Log.d("setCatList", document.getId() + " ******=> " + document.getData());
                                Categories cat = document.toObject(Categories.class);
                                cat.setId(document.getId());
                                docData.put(document.getId(),cat);
                            }
                            Log.d("setCatList","******************************data collected**************************\n"+docData);
                            adapter = new ExpandableListViewAdapter(ctx, docData);
                            expandableListView.setAdapter(adapter);
                            Log.d("setCatList","******************************DONE**************************");
                        } else {
                            Log.d(TAG, "**********************Error getting documents: ", task.getException());

                        }
                    }
                });
    }

    public void createProduitFragment(){
        Log.d("Produit","******************************Start**************************");
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, new RecyclerFragment().newInstance(""),"produit");
        mFragmentTransaction.commit();
        Log.d("Produit","******************************DONE**************************");
    }
}
