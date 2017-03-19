package hbase.base;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;

/**
 * @author mengfanzhu
 * @Package base
 * @Description:
 * @date 17/3/16 10:58
 */
public interface BaseDao {

    /**
     * 创建表
     * @param tableDescriptor
     */
    public void createTable(HTableDescriptor tableDescriptor) throws Exception;

    /**
     *  新增数据
     * @param putData
     *  @param tableName
     */
    public void putData(Put putData,String tableName) throws Exception;

    /**
     * 删除数据
     * @param delData
     *  @param tableName
     */
    public void delData(Delete delData,String tableName) throws Exception;

    /**
     * 查询数据
     * @param scan
     * @param tableName
     * @return
     */
    public ResultScanner scanData(Scan scan,String tableName) throws Exception;

    /**
     * 查询数据
     * @param get
     * @param tableName
     * @return
     */
    public Result getData(Get get,String tableName) throws Exception;
}
