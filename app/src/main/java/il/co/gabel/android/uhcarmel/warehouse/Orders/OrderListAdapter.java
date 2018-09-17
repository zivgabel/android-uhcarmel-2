package il.co.gabel.android.uhcarmel.warehouse.Orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import il.co.gabel.android.uhcarmel.OrderDetailActivity;
import il.co.gabel.android.uhcarmel.OrderDetailFragment;
import il.co.gabel.android.uhcarmel.OrderListActivity;
import il.co.gabel.android.uhcarmel.R;
import il.co.gabel.android.uhcarmel.Utils;
import il.co.gabel.android.uhcarmel.warehouse.Order;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListHolder>{
    private final OrderListActivity mParentActivity;
    private static List<Order> orders = new ArrayList<>();
    private final boolean mTwoPane;
    private static final String TAG=OrderListAdapter.class.getSimpleName();

    public OrderListAdapter(OrderListActivity parent,List<Order> items,boolean twoPane) {
        orders = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    private void setFabAction(Order order, View v){
        Log.e(TAG, "OrderListAdapter setFabAction: removing item with uid "+order.getFb_key() );
        DatabaseReference reference = Utils.getFBDBReference(v.getContext()).child("warehouse").child("orders");
        DatabaseReference completed_reference = Utils.getFBDBReference(v.getContext()).child("warehouse").child("completed_orders");
        reference.child(order.getFb_key()).removeValue();
        completed_reference.push().setValue(order);
        Bundle arguments = new Bundle();
        arguments.putString(OrderDetailFragment.ARG_ITEM_ID, "aaa");
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(arguments);
        mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.order_detail_container, fragment).commit();
        FloatingActionButton fab = mParentActivity.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Order item = (Order) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(OrderDetailFragment.ARG_ITEM_ID, item.getOrder_date().toString());
                OrderDetailFragment fragment = new OrderDetailFragment();
                fragment.setArguments(arguments);
                FloatingActionButton fab = mParentActivity.findViewById(R.id.fab);
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setFabAction(item,v);
                    }
                });
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.order_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra(OrderDetailFragment.ARG_ITEM_ID, item.getOrder_date().toString());
                context.startActivity(intent);
            }
        }
    };


    @NonNull
    @Override
    public OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_content, parent, false);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderListHolder holder, int position) {
        holder.order_list_mirs_text_view.setText(String.valueOf(orders.get(position).getMirs()));
        holder.order_list_date_text_view.setText(orders.get(position).getOrder_date().toString());

        holder.itemView.setTag(orders.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static void addItem(Order order){
        orders.add(order);
    }

    public static Order getOrder(String date_string){
        for (Order o : orders) {
            String odate = o.getOrder_date().toString();
            if (odate.equals(date_string)) {
                return o;
            }
        }
        return null;
    }


    public static void removeItem(Order order,OrderListAdapter adapter) {

        Iterator<Order> i = orders.iterator();
        while (i.hasNext()){
            Order o = i.next();
            if(o.equals(order)){
                i.remove();
                if(adapter!=null) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
