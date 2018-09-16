package il.co.gabel.android.uhcarmel.warehouse.Orders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import il.co.gabel.android.uhcarmel.R;

public class OrderListHolder extends RecyclerView.ViewHolder {
    public TextView order_list_mirs_text_view;
    public TextView order_list_date_text_view;

    public OrderListHolder(View v){
        super(v);
        order_list_mirs_text_view = v.findViewById(R.id.order_list_mirs_text_view);
        order_list_date_text_view = v.findViewById(R.id.order_list_date_text_view);
    }

}

