package il.co.gabel.android.uhcarmel.locations;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import il.co.gabel.android.uhcarmel.R;

public class LocationHolder extends RecyclerView.ViewHolder{
    private TextView location_name_text_view;
    private ImageButton location_maps_image_button;
    private ImageButton location_whatsapp_image_button;

    public LocationHolder(View itemView) {
        super(itemView);
        location_name_text_view = itemView.findViewById(R.id.location_name_text_view);
        location_maps_image_button = itemView.findViewById(R.id.location_maps_image_button);
        location_whatsapp_image_button = itemView.findViewById(R.id.location_whatsapp_image_button);
    }

    public TextView getLocation_name_text_view() {
        return location_name_text_view;
    }

    public ImageButton getLocation_maps_image_button() {
        return location_maps_image_button;
    }

    public ImageButton getLocation_whatsapp_image_button() {
        return location_whatsapp_image_button;
    }
}