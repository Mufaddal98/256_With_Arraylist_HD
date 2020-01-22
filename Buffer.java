import java.util.ArrayList;

public class Buffer {

    Multiple multiple = new Multiple();
    int maxElements;
    ArrayList<Integer> listB1 = new ArrayList<>(),
    listB2 = new ArrayList<>();


    public void addToBufferOne(int value) {
        listB1.add(value);
    }

    public void addToBufferTwo(int value) {
        listB2.add(value);
    }

    public boolean checkForBufferOneLimit(){
        if (listB1.size() > 5){
            return false;
        }
        else
            return true;
    }

}
