package pl.mycompany.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers")
public class Customer implements  BaseModel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "first_name", canBeNull = false)
    private String firstName;
    @DatabaseField(columnName = "last_name", canBeNull = false)
    private String lastName;
    @DatabaseField(columnName = "address")
    private String address;
    @DatabaseField(columnName = "nip", unique = true)
    private String NIP;

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}