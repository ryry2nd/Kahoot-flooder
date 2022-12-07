package flooder;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Integer> items;

    public Queue() {
        items = new ArrayList<Integer>();
    }

    public void addJob(int job) {items.add(items.size(), job);}

    public int getJob() {
        int ret = items.get(0);
        items.remove(0);

        return ret;
    }

    public boolean isEmpty() {return items.size() == 0;}
}
