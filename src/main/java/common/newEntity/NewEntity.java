package common.newEntity;

import com.xk.util.ExceptionDeal;
import common.newEntity.dispose.WriteEntity;

/**
 * ********************************************************
 * @ClassName: newEntity
 * @Description: 创建各文件
 * @author DoDo
 * @date 2012-12-27 06:29:37
 *******************************************************
 */
@SuppressWarnings("all")
public class NewEntity {

	static String table_name = "task_signup";              //表名

	public static void main(String[] args) throws ExceptionDeal {
		new WriteEntity().write(table_name.trim());
	}

}
