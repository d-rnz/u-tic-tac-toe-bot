import java.util.LinkedList;
import java.util.Iterator;

public class HashDictionary implements DictionaryADT {

    private LinkedList<Data>[] dict;
    private int size;
    private int num;

    public HashDictionary(int size) {
        this.size = size;
        dict = new LinkedList[size];
        num = 0;
    }

    public int put(Data pair) throws DictionaryException {
        String config = pair.getConfiguration();
        int collision = 1;
        int index = hash(config, size);

        // Check if first time adding to specific index
        if (dict[index] == null) {
            dict[index] = new LinkedList<>();
            collision = 0;
        }

        // Check if config is already in dict
        for (Data record : dict[index]) {
            if (record.getConfiguration().equals(config)) {
                throw new DictionaryException();
            }
        }

        dict[index].add(pair);
        num++;

        return collision;
    }

    public void remove(String config) throws DictionaryException {
        int index = hash(config, size);
        LinkedList<Data> list = dict[index];
        boolean flag = true;

        if (list != null) {
            Iterator<Data> iter = list.iterator();

            // Need iterator because of error when removing record
            // while in the loop
            while (iter.hasNext()) {
                Data record = iter.next();
                if (record.getConfiguration().equals(config)) {
                    flag = false;
                    iter.remove();
                    num--;
                }
            }
        }

        // Check if flag is unchanged, meaning config is not in dict
        if (flag) {
            throw new DictionaryException();
        }
    }

    public int get(String config) {
        int index = hash(config, size);
        LinkedList<Data> list = dict[index];

        // Check if config is in dict and return corresponding score
        if (list != null) {
            for (Data record : list) {
                if (record.getConfiguration().equals(config)) {
                    return record.getScore();
                }
            }
        }

        return -1;
    }

    public int numRecords() {
        return num;
    }

    // Hash function using polynomial hash code and Horner's rule
    private int hash(String config, int size) {
        int x = 39;
        int res = 0;

        for (int i = 0; i < config.length(); i++) {

            res = (res * x + config.charAt(i)) % size;
        }

        return res;
    }

}
