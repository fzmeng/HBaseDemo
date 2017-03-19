package hbase.base;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * @author mengfanzhu
 * @Package hbase.base
 * @Description:
 * @date 17/3/16 10:59
 */
public class BaseConfig {

    /**
     * 创建hbase连接
     * @return
     */
    public static Connection getConnection() throws Exception{
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "10.211.55.5");
        conf.set("hbase.master", "10.211.55.5:9000");
        Connection conn = ConnectionFactory.createConnection(conf);
        return conn;
    }
}
