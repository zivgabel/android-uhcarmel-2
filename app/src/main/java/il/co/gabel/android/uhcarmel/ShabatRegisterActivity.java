package il.co.gabel.android.uhcarmel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.co.gabel.android.uhcarmel.security.User;

public class ShabatRegisterActivity extends AppCompatActivity {

    private Spinner mShabatSpiner;
    private Switch mShabatSwitch;
    private EditText mShabatAddress;
    private EditText mShabatComment;
    private ArrayAdapter<CharSequence> mSpinerAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shabat_register);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        User user = Utils.currentUser(getApplicationContext());

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference().child("shabat").child(Utils.getUserUID(getApplicationContext()));




        mShabatSpiner = findViewById(R.id.shabat_cities_spinner);
        mShabatSwitch = findViewById(R.id.shabat_switch);
        mShabatAddress = findViewById(R.id.shabat_address);
        mShabatComment = findViewById(R.id.shabat_comments);

        mSpinerAdapter = ArrayAdapter.createFromResource(this,R.array.shabat_cities_array, android.R.layout.simple_spinner_item);
        mSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mShabatSpiner.setAdapter(mSpinerAdapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };



        setViews(mShabatSwitch.isChecked());



    }


    private void setViews(Boolean state){
        mShabatSpiner.setEnabled(state);
        mShabatAddress.setEnabled(state);
        mShabatComment.setEnabled(state);


    }

}
