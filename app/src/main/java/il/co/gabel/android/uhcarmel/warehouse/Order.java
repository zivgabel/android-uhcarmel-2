package il.co.gabel.android.uhcarmel.warehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Order {

    private Map<String,Integer> items=new HashMap<>();
    private int mirs;
    private Date order_date;

    public Order(){}


    public Order(Map<String,Integer> items,Integer mirs,Date order_date){
        this.items=items;
        this.mirs=mirs;
        this.order_date=order_date;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public int getMirs() {
        return mirs;
    }

    public void setMirs(int mirs) {
        this.mirs = mirs;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String k : items.keySet()) {
            int v = items.get(k);
            builder.append(k).append(": ").append(v).append("\r\n");
        }
        return builder.toString();
    }
}
