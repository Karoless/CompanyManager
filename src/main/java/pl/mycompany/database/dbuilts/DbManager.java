package pl.mycompany.database.dbuilts;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.mycompany.database.models.*;
import java.io.IOException;
import java.sql.SQLException;

public class DbManager {

    private static final String JDBC_URL_ADDRESS = "jdbc:h2:./CompanyDB";
    private static final String USER = "admin";
    private static final String PASS = "admin";
    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);
    private static ConnectionSource connectionSource;

    public static void initDatabase() {
        createConnectionSource();

        try {
            TableUtils.createTableIfNotExists(connectionSource, Sale.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(JDBC_URL_ADDRESS,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource == null){
            createConnectionSource();
        }
        return connectionSource;
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Customer.class);
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
            TableUtils.createTableIfNotExists(connectionSource, Sale.class);
            TableUtils.createTableIfNotExists(connectionSource, SaleList.class);

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connectionSource, Customer.class, true);
            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Sale.class, true);
            TableUtils.dropTable(connectionSource, SaleList.class, true);

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static void closeConnectionSource(){
        if(connectionSource!=null){
            try {
                connectionSource.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }
}