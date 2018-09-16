package il.co.gabel.android.uhcarmel.warehouse;

import java.util.Objects;

public class Item {
    private String name;
    private int max;


    public Item(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return max == item.max &&
                name.equals(item.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, max);
    }

}
