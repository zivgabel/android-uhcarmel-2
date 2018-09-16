package il.co.gabel.android.uhcarmel.warehouse;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import il.co.gabel.android.uhcarmel.R;


import java.util.List;

public class ItemHolder extends RecyclerView.ViewHolder{
    private TextView new_order_item_name_text_view;
    private NumberPicker new_order_amout_number_picker;


    public ItemHolder(View itemView){
        super(itemView);
        new_order_item_name_text_view = itemView.findViewById(R.id.new_order_item_name_text_view);
        new_order_amout_number_picker = itemView.findViewById(R.id.new_order_amout_number_picker);
    }

    public TextView getNew_order_item_name_text_view() {
        return new_order_item_name_text_view;
    }

    public NumberPicker getNew_order_amout_number_picker() {
        return new_order_amout_number_picker;
    }
}
