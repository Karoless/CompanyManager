package pl.mycompany.database.dao;

import com.j256.ormlite.dao.Dao;
import pl.mycompany.database.models.Sale;
import pl.mycompany.utils.exception.AppException;
import java.sql.SQLException;


public class SaleDao extends CommonDao {

    public SaleDao() {
        super();
    }

    public double getTotalPrice () throws SQLException, AppException {
        Dao<Sale,Object> dao = getDao(Sale.class);
        double totalPrice;
        totalPrice = dao.queryRawValue("SELECT SUM(total_cost_of_item) FROM sale");
        return totalPrice;
    }
}
