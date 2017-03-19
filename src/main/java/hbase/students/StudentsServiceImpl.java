package hbase.students;

import hbase.base.BaseDao;
import hbase.base.BaseDaoImpl;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengfanzhu
 * @Package hbase.students
 * @Description: students服务
 * @date 17/3/16 11:36
 */
public class StudentsServiceImpl {
    private BaseDao baseDao = new BaseDaoImpl();
    private static final String TABLE_NAME = "t_students";
    private static final String STU_ROW_NAME = "stu_row1";
    private static final byte[] FAMILY_NAME_1 = Bytes.toBytes("name");
    private static final byte[] FAMILY_NAME_2 = Bytes.toBytes("age");
    private static final byte[] FAMILY_NAME_3 = Bytes.toBytes("scores");


    public void createStuTable() throws Exception{
        //创建tablename,列族1,2
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
        HColumnDescriptor columnDescriptor_1 = new HColumnDescriptor(FAMILY_NAME_1);
        HColumnDescriptor columnDescriptor_2 = new HColumnDescriptor(FAMILY_NAME_2);
        HColumnDescriptor columnDescriptor_3 = new HColumnDescriptor(FAMILY_NAME_3);
        tableDescriptor.addFamily(columnDescriptor_1);
        tableDescriptor.addFamily(columnDescriptor_2);
        tableDescriptor.addFamily(columnDescriptor_3);
        baseDao.createTable(tableDescriptor);
    }

    /**
     * 插入数据<列族名称，值>
     * @param bytes
     */
    public void putStuData(Map<byte[],byte[]> bytes) throws Exception{
        Put put =  new Put(Bytes.toBytes(STU_ROW_NAME));;
        int i = 1;
        for(byte[] familyNames : bytes.keySet()){
            put.addColumn(familyNames, bytes.get(familyNames), Bytes.toBytes(0));
            i++;
        }

        baseDao.putData(put, TABLE_NAME);
    }

    public ResultScanner scanData(Map<byte[],byte[]> bytes) throws Exception{
        Scan scan = new Scan();
        for(byte[] familyNames : bytes.keySet()){
            scan.addColumn(familyNames, bytes.get(familyNames));
        }
        scan.setCaching(100);
        ResultScanner results = baseDao.scanData(scan,TABLE_NAME);

        return results;
    }
    public void delStuData(String rowId,byte[] familyName,byte[] qualifierName) throws Exception{
        Delete delete = new Delete(Bytes.toBytes(rowId));
        delete.addColumn(familyName, qualifierName);
        baseDao.delData(delete,TABLE_NAME);
    }

    public static void main(String[] args) throws Exception {
        StudentsServiceImpl ssi = new StudentsServiceImpl();
        //创建table
        ssi.createStuTable();
        //添加数据
        Map<byte[],byte[]> bytes = new HashMap<byte[],byte[]>();
        bytes.put(FAMILY_NAME_1,Bytes.toBytes("Jack"));
        bytes.put(FAMILY_NAME_2,Bytes.toBytes("10"));
        bytes.put(FAMILY_NAME_3,Bytes.toBytes("O:90,T:89,S:100"));
        ssi.putStuData(bytes);

        //查看数据
        Map<byte[],byte[]> byteScans = new HashMap<byte[], byte[]>();
        ResultScanner results = ssi.scanData(byteScans);
        for (Result result : results) {
            while (result.advance()) {
                System.out.println(result.current());
            }
        }
    }
}
