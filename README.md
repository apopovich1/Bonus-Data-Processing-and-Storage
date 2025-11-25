# Bonus-Data-Processing-and-Storage
Repository on building an in memory database with transaction support

## There are three different files in the project:
* Main.java: Demonstration of the project working as shown in the source document
* InMemoryDB.java: Interface of transaction operations
* Implement.java: Storage class to store keys and values in a database as well as the implementation of the transcation operations

## This project supports basic transaction operations including:
* begin_transaction(): Starts a new uncommited transaction
* put(key,value): Places a new key and value pair in a current transaction
* get(key): Gets the index of the key
* commit(): Commits the transaction to the overall database
* rollback(): Deletes the current transaction

## Prerequsites
Only Java (JDK 8+) is required

## Running the Repository
1. Clone and navigate to the repository
2. Compile all Java files
3. Run Main

## Suggestion for improving the assignment
The first issue I encountered with this assignment is switching the get function in the interface from int to Integer. Either the document should state that you can use 0 to return a null value or switch to the Integer primitive for null values. I think this assignment should also include some implementation of unit testing that we discussed earlier in the semester. Some simple guidelines or examples could be put into the assignment. This would help connect topics found throughout the semester as well as ensure that students are testing for inconsistencies that could happen within the transactions. When submitting the assignment, students should be able to post the link to the repository directly instead of having to create a PDF document for the single link. Lastly, in the instructions it could be helpful to tell students to use hashmaps if it is in the resource links. I did not include it as I wanted to code without using it, but it could be another aspect to grade on as well as make the assignment more challenging as an official assignment. 



