# Drippz-1

## Project Description
 A java based ORM for simplifying connecting to and from an SQL database without the need for SQL or connection management. 

## Technologies Used

* PostgreSQL - version 42.2.12  
* Java - version 8.0  
* HikariCP - version 3.4.5
* JUnit - version 4.12
* log4j - version 1.2.17

## Features

List of features ready and TODOs for future development  
* Simple to use API.  
* No need for SQL, HQL, or any databse specific language.
* Simple Annotation based for ease of use. (Primary Key is required)
* Methods for performing operations with the input of an annotated class or Object of that class depending on the function
* Create: Annotated Class
* Get: Annotated Class, List<String> of field names (e.g. "first_name" to get just the results from the collumn first_name)[A single entry in this list with an * will return all fields], LinkedHashMap<String, String> containing in the first string the field name to search by, and in the 2nd String the value to compare to (e.g. ("id", 20) for WHERE id = 20) [an empty LinkedHashMap<String, String> will return all results in the table] (Returns a ResultsSet containing all results)
* Insert: Object of Annotated Class (Returns DB Generated Primary Key in ResultSet)
* Update: Object of Annotated Class, List<String> of field names (Uses ID of Object to determine which row to edit)(Must Specify the name of the column in the table here) (Returns number of rows changed in Result Set if successful)

To-do list: [`for future iterations`]
* Implement Delete Function   
* Implement of aggregate functions.  
* Test Join Collumn Functionality


## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/*your-repo*_p1.git
  cd *your-repo*_p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>*your-repo*_p1</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>

```

Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  url=path/to/database
  admin-usr=username/of/database
  admin-pw=password/of/database  
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.
   - #### @Table(name = "table_name)  
      - Indicates that this class is associated with table 'table_name'  
   - #### @Column(name = "column_name)  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'  
   - #### @Setter(name = "column_name")  
      - Indicates that the anotated method is a setter for 'column_name'.  
   - #### @Getter(name = "column_name")  
      - Indicates that the anotated method is a getter for 'column_name'.  
   - #### @PrimaryKey(name = "column_name") 
      - Indicates that the annotated field is the primary key for the table.
   - #### @SerialKey(name = "column_name") 
      - Indicates that the annotated field is a serial key.

  ### User API  
  
  - #### `public static Something getInstance()`  
     - returns the singleton instance of the class. It is the starting point to calling any of the below methods.  
  - #### `public HashMap<Class<?>, HashSet<Object>> getCache()`  
     - returns the cache as a HashMap.  
  - #### `public boolean addClass(final Class<?> clazz)`  
     - Adds a class to the ORM. This is the method to use to declare a Class is an object inside of the database.  
  - #### `public boolean UpdateObjectInDB(final Object obj,final String update_columns)`  
     - Updates the given object in the databse. Update columns is a comma seperated lsit fo all columns in the onject which need to be updated  
  - #### `public boolean removeObjectFromDB(final Object obj)`  
     - Removes the given object from the database.  
  - #### `public boolean addObjectToDB(final Object obj)`  
     - Adds the given object to the database.  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions,final String operators)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz)`  
     - Gets a list of all objects in the database which match the included search criteria  
        - columns - comma seperated list of columns to search by.  
        - conditions - coma seperated list the values the columns should match to.  
        - operators - comma seperated list of operators to apply to columns (AND/OR) in order that they should be applied.  
  - #### `public void beginCommit()`  
     - begin databse commit.  
  - #### `public void Rollback()`  
     - Rollback to previous commit.  
  - #### `public void Rollback(final String name)`  
     - Rollback to previous commit with given name.  
  - #### `public void setSavepoint(final String name)`  
     - Set a savepoint with the given name.  
  - #### `public void ReleaseSavepoint(final String name)`  
     - Release the savepoint with the given name.  
  - #### `public void enableAutoCommit()`  
     - Enable auto commits on the database.  
  - #### `public void setTransaction()`  
     - Start a transaction block.  
  - #### `public void addAllFromDBToCache(final Class<?> clazz)`  
     - Adds all objects currently in the databse of the given clas type to the cache.  



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
