public class Implement implements InMemoryDB { //implementation of the InMemoryDB interface with storage class
    private static class Storage { //storage class to help keep track of keys and values
        private String[] keys;
        private Integer[] vals;
        private int size; 

        public Storage() {
            keys = new String[8]; //constructor
            vals = new Integer[8];
            size = 0;
        }

        private int locateIdx(String key){ //searches for the key and returns index position or -1
            for(int i = 0; i < size; i++){
                if(keys[i].equals(key)){
                    return i;
                }
            }
        return -1; 
        }

        public Integer get(String key){ //find the key in reverse from locateIdx
            int idx = locateIdx(key);
            if (idx == -1){
                return null;
            }
            return vals[idx];
        }
        public void put(String key, int val){
            int idx = locateIdx(key);
            if(idx != -1){
                vals[idx] = val; //if key exists update value
            }
            else{
                hasCap(); //ensure both arrays are big enough
                keys[size] = key; //store the key at the end
                vals[size] = val; //store value at the end
                size++;
            }
        }
        public int size(){
            return size; //helper to return size
        }
        public String getKey(int idx){
            return keys[idx]; //helper to get the key at the index
        }
        public Integer getVal(int idx){
            return vals[idx]; //helper to get the value at the index
        }
        
        private void hasCap(){
            if (size >= keys.length){
                int newCap = keys.length*2; //add length to the array to store more values and keys
                String[] newKeys = new String[newCap];
                Integer[] newVals = new Integer[newCap];
                for(int i = 0; i < size; i++){
                    newKeys[i] = keys[i]; //place in all existing keys
                    newVals[i] = vals[i]; //place in all existing values
                }
                keys = newKeys; //swap array names
                vals = newVals;
            }
        }
    }
    private Storage actual_DB = new Storage(); //call storage constructor
    private Storage curr_transactions = null; //storage of all uncommited transactions
    private boolean inTrans = false; //is there a current transaction

    public Integer get(String key){
        return actual_DB.get(key);  //call on actual storage instance and get the key
    }

    public void put(String key, int val){
        if(!inTrans){ //if there is no current transaction throw error
            throw new RuntimeException("No active transaction!");
        }
        curr_transactions.put(key, val); //otherwise put in the key and value
    }

    public void begin_transaction(){
        if (inTrans){ //if there is a current transaction throw error
            throw new RuntimeException("There is an unfinished transaction. Resetting to a new transaction");
        }
        inTrans = true; //reset in transaction
        curr_transactions = new Storage(); //create new storage instance for current transaction
    }

    public void commit(){
        if (!inTrans){ //need to have current transaction occuring
            throw new RuntimeException("Must have an active transaction");
        }
        for(int i = 0; i < curr_transactions.size(); i++){ //for the entire transaction size
            String key = curr_transactions.getKey(i); //get the key at the positon
            Integer val = curr_transactions.getVal(i); //get the value at the position
            actual_DB.put(key, val); //commit the key and value combination found at each position
        }
        curr_transactions = null; //reset to no current transactions
        inTrans = false; //reset to no current transactions
    }

    public void rollback(){
        if (!inTrans){ //must have an uncommitted transactino
            throw new RuntimeException("No active transaction");
        }
        curr_transactions = null; //delete current transaction
        inTrans = false; //reset to have no current transaction
    }
}