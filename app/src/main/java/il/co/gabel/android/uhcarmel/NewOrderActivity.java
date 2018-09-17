package il.co.gabel.android.uhcarmel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.co.gabel.android.uhcarmel.warehouse.Item;
import il.co.gabel.android.uhcarmel.warehouse.ItemAdapter;
import il.co.gabel.android.uhcarmel.warehouse.Order;

public class NewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static final String TAG = NewOrderActivity.class.getSimpleName();
    private final ItemAdapter adapter = new ItemAdapter(new ArrayList<Item>());
    private DatabaseReference databaseReference;
    private ChildEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        recyclerView = findViewById(R.id.new_order_items_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        databaseReference = Utils.getFBDBReference(getApplicationContext()).child("warehouse").child("items");
        attachListeners();
        FloatingActionButton fab = findViewById(R.id.new_order_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getOrdered_items().isEmpty()){
                    Toast.makeText(v.getContext(),getString(R.string.new_order_no_items),Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences sp = Utils.getSharedPreferences(getApplicationContext());
                int user_mirs=sp.getInt(getString(R.string.user_mirs),0);
                Date date = new Date();
                Order order = new Order(adapter.getOrdered_items(),user_mirs,date);
                DatabaseReference reference = Utils.getFBDBReference(getApplicationContext()).child("warehouse").child("orders");
                reference.push().setValue(order);
                Toast.makeText(v.getContext(),getString(R.string.order_success),Toast.LENGTH_LONG).show();
                adapter.getOrdered_items().clear();
                onBackPressed();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        detachListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachListeners();
    }

    private void attachListeners(){
        if(listener==null) {
            listener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Item item = dataSnapshot.getValue(Item.class);
                    adapter.addItem(item);
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
        }
        databaseReference.addChildEventListener(listener);
    }

    private void detachListeners(){
        if(listener!=null){
            databaseReference.removeEventListener(listener);
        }
    }

}
