package pl.mycompany.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.mycompany.database.models.Item;
import pl.mycompany.utils.exception.AppException;

import java.sql.SQLException;

public class CategoryDao extends CommonDao {

    public CategoryDao() {
        super();
    }

    public void deleteByColumnName (String columnName, int id) throws SQLException, AppException {
        Dao<Item,Object> dao = getDao(Item.class);
        DeleteBuilder<Item,Object> deleteBuilder =dao.deleteBuilder();
        deleteBuilder.where().eq(columnName,id);
        dao.delete(deleteBuilder.prepare());
    }
}