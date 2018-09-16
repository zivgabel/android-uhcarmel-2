package il.co.gabel.android.uhcarmel.locations;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import il.co.gabel.android.uhcarmel.R;

public class LocationsAdapter extends RecyclerView.Adapter<LocationHolder>{
    private List<Location> locations;


    public LocationsAdapter(List<Location> locations){
        this.locations = locations;
    }

    public void addLocation(Location location){
        locations.add(location);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        final Location location = locations.get(position);

        holder.getLocation_name_text_view().setText(location.getName());

        holder.getLocation_whatsapp_image_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = location.getName()+"\r\n"+location.getWazeUrl();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                intent.setType("text/plain");
                if(intent.resolveActivity(v.getContext().getPackageManager())!=null){
                    v.getContext().startActivity(intent);
                }
            }
        });

        holder.getLocation_maps_image_button().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(location.getWazeUrl()));
                intent.setPackage("com.waze");
                if(intent.resolveActivity(v.getContext().getPackageManager())!=null){
                    v.getContext().startActivity(intent);
                }else {
                    Toast.makeText(v.getContext(),R.string.no_waze_installed,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}

