package pl.mycompany.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import pl.mycompany.database.dbuilts.DbManager;
import pl.mycompany.database.models.BaseModel;
import pl.mycompany.utils.FxmlUtils;
import pl.mycompany.utils.exception.AppException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDao.class);
    protected final ConnectionSource connectionSource;

    public CommonDao() {
        this.connectionSource = DbManager.getConnectionSource();
    }

    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws AppException {
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.update"));
        } finally {
            this.closeDbConnection();
        }
    }

    public <T extends BaseModel, I> void deleteById(Class<T> cls, Integer id) throws AppException {
        try {
            Dao<T, I> dao = getDao(cls);
            dao.deleteById((I) id);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.delete"));
        } finally {
            this.closeDbConnection();
        }
    }

    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) throws AppException {
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForId((I) id);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.notfoud"));
        } finally {
            this.closeDbConnection();
        }
    }

    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls) throws AppException {
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForAll();
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.notfoud"));
        } finally {
            this.closeDbConnection();
        }
    }

    public <T extends BaseModel, I> Dao<T, I> getDao(Class<T> cls) throws AppException {
        try {
            return DaoManager.createDao(connectionSource, cls);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.get.dao"));
        } finally {
            this.closeDbConnection();
        }
    }

    private void closeDbConnection() throws AppException {
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            throw new AppException(FxmlUtils.getResourceBundle().getString("error.get.dao"));
        }
    }
}