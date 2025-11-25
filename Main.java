public class Main {
    public static void main(String[] args) {
        InMemoryDB db = new Implement();

        // should return null, because A doesn’t exist in the DB yet
        System.out.println("get(A): " + db.get("A"));

        // should throw an error because a transaction is not in progress
        try {
            db.put("A", 5);
        } catch (Exception e) {
            System.out.println("Expected error (put without tx): " + e.getMessage());
        }

        // starts a new transaction
        db.begin_transaction();

        // set value of A to 5, but it's not committed yet
        db.put("A", 5);

        // should return 0, because updates to A are not committed yet
        System.out.println("get(A) before commit: " + db.get("A"));

        // update A’s value to 6 within the transaction
        db.put("A", 6);

        // commits the open transaction
        db.commit();

        // should return 6, that was the last value of A to be committed
        System.out.println("get(A) after commit: " + db.get("A"));

        // throws an error, because there is no open transaction
        try {
            db.commit();
        } catch (Exception e) {
            System.out.println("Expected error (extra commit): " + e.getMessage());
        }

        // throws an error because there is no ongoing transaction
        try {
            db.rollback();
        } catch (Exception e) {
            System.out.println("Expected error (extra rollback): " + e.getMessage());
        }

        // should return null because B does not exist in the database
        System.out.println("get(B): " + db.get("B"));

        // starts a new transaction
        db.begin_transaction();

        // Set key B’s value to 10 within the transaction
        db.put("B", 10);

        // Rollback the transaction - revert any changes made to B
        db.rollback();

        // Should return null because changes to B were rolled back
        System.out.println("get(B) after rollback: " + db.get("B"));
    }
}