package il.co.gabel.android.uhcarmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import il.co.gabel.android.uhcarmel.security.User;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener listener;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG=MainActivity.class.getSimpleName();
    NavigationView navigationView;
    private MenuItem ordersMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent!=null){
            Log.e(TAG, "onCreate: from intent");
            String a = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.e(TAG, "onCreate: intent extra is "+a);
            if(a!=null) {
                if (a.equals(getString(R.string.order_success))) {
                    Log.e(TAG, "onCreate: creating toast");
                    Toast.makeText(this, a, Toast.LENGTH_LONG).show();
                }
            }
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setOnCreateContextMenuListener(this);
        if(authStateListener==null) {
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        onSignedInInitialize(user);
                    } else {
                        onSignedOutCleanup();
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(Arrays.asList(
                                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                                new AuthUI.IdpConfig.GoogleBuilder().build()
                                        )).build(),
                                RC_SIGN_IN);
                    }
                }
            };
            firebaseAuth.addAuthStateListener(authStateListener);
        }
        getUserDetails();
    }
    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
        attachListeners();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), getString(R.string.signed_id), Toast.LENGTH_LONG).show();
                getUserDetails();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_locations) {
            Intent intent = new Intent(this,LocationsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_new_order) {
            Intent intent = new Intent(this,NewOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(this,OrderListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    private void onSignedOutCleanup() {
        
    }

    private void onSignedInInitialize(FirebaseUser user) {
        SharedPreferences.Editor editor = Utils.getSharedPreferencesEditor(getApplicationContext());
        editor.putString(getString(R.string.display_name_key),user.getDisplayName());
        editor.putString(getString(R.string.user_uid_key),user.getUid());
        editor.commit();
        getUserDetails();
    }

    private void getUserDetails(){
        if(firebaseDatabase==null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        if(databaseReference==null) {
            databaseReference = firebaseDatabase.getReference().child("users");
        }
        attachListeners();
    }
    private void attachListeners(){
        if(listener==null) {
            listener = new ChildEventListener() {
                SharedPreferences sp = Utils.getSharedPreferences(getApplicationContext());
                final String uid=sp.getString(getString(R.string.user_uid_key),"");
                private void changeHandler(@NonNull DataSnapshot dataSnapshot) {
                    String duid = dataSnapshot.getKey();
                    if (!duid.equals(uid)) {
                        return;
                    }
                    User user = dataSnapshot.getValue(User.class);
                    Context context = getApplicationContext();
                    SharedPreferences.Editor editor = Utils.getSharedPreferencesEditor(getApplicationContext());
                    editor.putBoolean(getString(R.string.is_admin), user.getAdmin());
                    editor.putBoolean(getString(R.string.is_shabat_admin), user.getShabat_admin());
                    editor.putBoolean(getString(R.string.is_wh_admin), user.getWh_admin());
                    editor.putInt(getString(R.string.user_mirs), user.getMirs());
                    editor.commit();
                    setViewsVisibilty(user.getAdmin(), user.getShabat_admin(), user.getWh_admin());
                }


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.e(TAG, "onChildAdded: UID " + dataSnapshot.getKey());

                    changeHandler(dataSnapshot);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.e(TAG, "onChildChanged: UID " + dataSnapshot.getKey());
                    changeHandler(dataSnapshot);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Log.e(TAG, "onChildRemoved: UID " + dataSnapshot.getKey());
                    setViewsVisibilty(false, false, false);

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.e(TAG, "onChildMoved: UID " + dataSnapshot.getKey());
                    setViewsVisibilty(false, false, false);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            databaseReference.addChildEventListener(listener);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listener);
        listener=null;
    }

    public final void setViewsVisibilty(Boolean admin, Boolean shabat_admin, Boolean wh_admin){
        Log.e(TAG, "setViewsVisibilty: "+admin+" "+shabat_admin+" "+wh_admin );
        ordersMenuItem=navigationView.getMenu().findItem(R.id.nav_orders);
        ordersMenuItem.setVisible(wh_admin);
    }


}
