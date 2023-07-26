package com.xk.conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xk.entity.Page;
import org.apache.commons.beanutils.BeanUtils;

public class BaseConn {

	private ConnectionManager connMap = ConnectionManager.getInstance(); // C3P0数据连接
	
	public BaseConn() {
	};

	protected Connection getConnection() {
		return  connMap.getConnection();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] close <br>
	 * [描述] 关闭连接 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] void <br>
	 * [时间] 2017-2-26 下午09:28:19 <br>
	 *********************************************************.<br>
	 */
	public static void close(Connection conn,PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null || !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] batchSql <br>
	 * [描述] 批量执行语句 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] boolean <br>
	 * [时间] 2017-2-26 下午09:28:30 <br>
	 *********************************************************.<br>
	 */
	public boolean batchSql(List<String> sqlList) throws Exception {
		boolean flag = false;
		Connection conn = null;
		Statement  pstm = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = connMap.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++) {
				pstm.addBatch(sqlList.get(i));
			}
			int[] numArr = pstm.executeBatch();
			if(numArr.length == sqlList.size()) {
				flag = true;
				conn.commit();//提交
			} else {
				conn.rollback(); //回滚
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
		} finally {
			connMap.closeCon(conn, pstmt, rs,pstm);
		}
		return flag;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selectPolicy <br>
	 * [描述] 查询pos风控信息 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] List<Map<String,String>> <br>
	 * [时间] 2017-3-6 下午02:47:15 <br>
	 *********************************************************.<br>
	 */
	public List<Map<String, String>> selectPolicy(String posNo){
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = connMap.getConnection();
			pstmt = conn.prepareStatement("select rm.riskname,rm.rno,rp.posno,rp.msgtype,rp.params,rm.note,decode(rp.params,null,0,1) resultFlag from riskmanager rm left join riskpos rp on rp.rno = rm.rno and rp.posno = ?");
			pstmt.setString(1, posNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String, String> map = new HashMap<String, String>();
				String riskName = rs.getString("riskname");
				String pos_no = rs.getString("posno");
				String msgtype = rs.getString("msgtype");
				String params = rs.getString("params");
				String rno = rs.getString("rno");
				String note = rs.getString("note");
				String resultFlag = rs.getString("resultFlag");
				map.put("riskName", riskName);
				map.put("pos_no", pos_no);
				map.put("msgtype", msgtype);
				map.put("params", params);
				map.put("rno", rno);
				map.put("note", note);
				map.put("resultFlag", resultFlag);
				resultList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connMap.closeCon(conn, pstmt, rs,null);
		}
		return resultList;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selectPolicy <br>
	 * [描述] 查询机构风控信息 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] List<Map<String,String>> <br>
	 * [时间] 2017-3-6 下午02:47:15 <br>
	 *********************************************************.<br>
	 */
	public List<Map<String, String>> selectPolicyByAgentNo(String agentNo){
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = connMap.getConnection();
			pstmt = conn.prepareStatement("select rm.riskname,rm.rno,rp.posno,rp.msgtype,rp.params,rm.note,decode(rp.params,null,0,1) resultFlag from riskmanager rm left join riskorganization rp on rp.rno = rm.rno and rp.posno = ?");
			pstmt.setString(1, agentNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String, String> map = new HashMap<String, String>();
				String riskName = rs.getString("riskname");
				String pos_no = rs.getString("posno");
				String msgtype = rs.getString("msgtype");
				String params = rs.getString("params");
				String rno = rs.getString("rno");
				String note = rs.getString("note");
				String resultFlag = rs.getString("resultFlag");
				map.put("riskName", riskName);
				map.put("pos_no", pos_no);
				map.put("msgtype", msgtype);
				map.put("params", params);
				map.put("rno", rno);
				map.put("note", note);
				map.put("resultFlag", resultFlag);
				resultList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connMap.closeCon(conn, pstmt, rs,null);
		}
		return resultList;
	}	

	
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getResultList <br>
	 * [描述] 获取连接2分页数据 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] List <br>
	 * [时间] 2017-4-27 下午06:17:54 <br>
	 *********************************************************.<br>
	 */
	public static List getResultList(String sql,Page page){
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   //获取连接
	   ConnectionManager connManager = ConnectionManager.getInstance();
	   Connection connection = connManager.getConnection();
	   if(page.getPageNo()==1){//如果是第一次，那么就统计，接下来翻页将不统计。
		   //给当前的page参数对象设置总记录数
    	   BaseConn.setTotalRecord(page,sql,connection);
       }
       if(page.getPageNo()>page.getTotalPage()&&page.getTotalPage()>0){
    	   page.setPageNo(page.getTotalPage());
       }
       StringBuffer sqlBuffer = new StringBuffer(sql);
       //获取分页Sql语句
       String pageSql = BaseConn.getOraclePageSql(page, sqlBuffer); 
       // 构造泛型结果集
	   List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
	   try {
		   pstmt = connection.prepareStatement(pageSql);
		   rs = pstmt.executeQuery();
		   ResultSetMetaData rsmd = pstmt.getMetaData();
		   // 取得结果集列数
		   int columnCount = rsmd.getColumnCount();
		   Map<String, Object> data = null;
		   while (rs.next()) {
		       data = new HashMap<String, Object>();
		       // 每循环一条将列名和列值存入Map
		       for (int i = 1; i < columnCount; i++) {
		           data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd.getColumnLabel(i)));
		       }
		       // 将整条数据的Map存入到List中
		       datas.add(data);
		   }
	   } catch (SQLException e) {
		   throw new RuntimeException();
	   }finally{
		   connManager.closeCon(connection, pstmt, rs, null);
	   }
	   return datas;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getOraclePageSql <br>
	 * [描述] 获取分页语句 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] String <br>
	 * [时间] 2017-4-27 下午06:04:09 <br>
	 *********************************************************.<br>
	 */
    public static String getOraclePageSql(Page page, StringBuffer sqlBuffer) {       
    	String orderField="1";     	   //默认排序的列，第一列
    	String orderDirection="desc";  //默认排序方式，倒序
    	if(page.getOrderField()!=null&&!page.getOrderField().equals("")&&sqlBuffer.indexOf("order by")==-1){//排序
    		orderField=page.getOrderField();
    		orderDirection=page.getOrderDirection()==null?"desc":"asc";
    		if(orderField.indexOf(".")!=-1)orderField=orderField.split("\\.")[1];    		
    	}
       sqlBuffer.insert(0," select * from (").append(") o order by ").append(orderField).append(" ").append(page.getOrderDirection());
       int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
       sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
       sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
       return sqlBuffer.toString();
    }
  
    /**
     * 
     *********************************************************.<br>
     * [方法] setTotalRecord <br>
     * [描述] 获取总条数 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] void <br>
     * [时间] 2017-4-27 下午06:22:15 <br>
     *********************************************************.<br>
     */
    private static void setTotalRecord(Page page,String sql,Connection connection) {
       String countSql = BaseConn.getCountSql(sql);
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = connection.prepareStatement(countSql);
           rs = pstmt.executeQuery();
           if (rs.next()) {
              int totalRecord = rs.getInt(1);
              page.setTotalRecord(totalRecord);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           try {
              if (rs != null)
                  rs.close();
               if (pstmt != null)
                  pstmt.close();
           } catch (SQLException e) {
              e.printStackTrace();
           }
       }
    }
   
    /**
     * 
     *********************************************************.<br>
     * [方法] getCountSql <br>
     * [描述] 获取条数语句 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] String <br>
     * [时间] 2017-4-27 下午06:22:40 <br>
     *********************************************************.<br>
     */
    private static String getCountSql(String sql) {
       return "select count(*) from(" +sql+")";
    }
    
    /**
     * 
     *********************************************************.<br>
     * [方法] getEntity <br>
     * [描述] 获取实体 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] Object <br>
     * [时间] 2017-4-27 下午07:26:26 <br>
     *********************************************************.<br>
     */
    public static Object getEntity(Class<?> c, String sql){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;                //返回结果
		Connection conn = null;
		try {
			conn = ConnectionManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			if(rs.next()) {
				obj = c.newInstance();
				// 循环获取指定行的每一列的信息
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					if (rs.getObject(i) != null && !"".equals(rs.getObject(i))) {
						BeanUtils.setProperty(obj, meta.getColumnName(i).toLowerCase(),rs.getObject(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,pstmt, rs);
		}
		return obj;
	}
    
    /**
     * 
     *********************************************************.<br>
     * [方法] listMap <br>
     * [描述] 返回listmap <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] List<Map<String,Object>> <br>
     * [时间] 2017-4-27 下午08:31:03 <br>
     *********************************************************.<br>
     */
    public List<Map<String,Object>> listMap(String sql) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();   //返回结果集
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = ConnectionManager.getInstance().getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数3
			Map<String,Object> rowData = new HashMap<String,Object>();
			while (rs.next()) {
				rowData = new HashMap<String,Object>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,pstmt, rs);
		}
		return list;
	}
}