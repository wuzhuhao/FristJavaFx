package jdbcs;

import java.util.ArrayList;

import model.Goods;
import model.Orders;
import model.Users;

public interface JdbcInterface {
	/**
	 * @param data
	 *            表单数据
	 * @param type
	 *            0是用户登陆，1是管理员登陆
	 * @return users或者admin实体类
	 */
	public Object login(Object data, int type);

	/**
	 * @param user
	 *            注册表单数据
	 * @return 注册结果
	 */
	public boolean register(Users user);

	/**
	 * @param g_id
	 *            表单商品id
	 * @param g_name
	 *            表单商品名称 模糊查找
	 * @return 商品list
	 */
	public ArrayList<Goods> findGoodById(int g_id, String g_name);

	/**
	 * @return 全部商品的list
	 */
	public ArrayList<Goods> findAllGood();

	/**
	 * @param g_id
	 *            商品id
	 * @param num
	 *            增加库存数量,购买的时候数量是负数
	 * @return 增加结果
	 */
	public boolean addNum(int g_id, int num);

	/**
	 * @param good
	 *            商品表单数据
	 * @return 增加商品结果
	 */
	public boolean addGood(Goods good);

	/**
	 * @param g_id
	 *            商品id
	 * @return 删除结果
	 */
	public boolean deleteGood(int g_id);

	/**
	 * @param good
	 *            商品表单数据
	 * @return 结果
	 */
	public boolean updateGood(Goods good);

	/**
	 * @param data
	 *            表单数据
	 * @param type
	 *            0是用户修改密码，1是管理员修改密码
	 * @return 结果
	 */
	public boolean updatePassword(Object data, int type);

	/**
	 * @param g_id
	 *            商品id
	 * @param num
	 *            增加数量
	 * @param type
	 *            0是差评，1是中评，2是好评
	 * @return 结果
	 */
	public boolean addEvaluateNum(long o_id, long g_id, long num, int type);

	/**
	 * @param o_id
	 *            订单id
	 * @return 订单list
	 */
	public ArrayList<Orders> findOrdersByOId(int o_id);

	/**
	 * @param u_id
	 *            用户id
	 * @return
	 */
	public ArrayList<Orders> findOrdersByUId(int u_id);

	/**
	 * @return 全部订单list
	 */
	public ArrayList<Orders> findAllOrders();

	/**
	 * @param order
	 *            订单表单
	 * @return 结果
	 */
	public boolean addOrder(Orders order);

	/**
	 * @param o_id
	 *            订单id
	 * @return
	 */
	public boolean deleteOrderByOid(int o_id);

	/**
	 * @param g_id
	 *            商品id
	 * @return
	 */
	public boolean deleteOrderByGid(int g_id);

	/**
	 * @param o_id
	 *            订单id
	 * @return
	 */
	public boolean updateOrderStatus(int o_id);

	/**
	 * 检查库存
	 * 
	 * @param g_id
	 * @param num
	 * @return
	 */
	public boolean checkNum(int g_id, int num);
}
