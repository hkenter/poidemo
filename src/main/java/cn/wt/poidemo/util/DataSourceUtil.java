package cn.wt.poidemo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DataSourceUtil {
	private DruidDataSource dds = null;

	public void initDruidDataSourceFactory() {
		try {
			dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(ConfigUtil.getProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DruidDataSource getDruidDataSource() {
		return dds;
	}

	// test
	public static void main(String[] args) {

		DruidPooledConnection dpc = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			DataSourceUtil dbd = new DataSourceUtil();
			dbd.initDruidDataSourceFactory();
			dpc = dbd.getDruidDataSource().getConnection();
			stmt = dpc.createStatement();
			System.out.println("Executing statement.");
			rset = stmt.executeQuery("select * from para_info_t");
			System.out.println("Results:");
			int numcols = rset.getMetaData().getColumnCount();
			while (rset.next()) {
				for (int i = 1; i <= numcols; i++) {
					System.out.print("\t" + rset.getString(i));
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null)
					rset.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (dpc != null)
					dpc.close();
			} catch (Exception e) {
			}
		}
	}
}
