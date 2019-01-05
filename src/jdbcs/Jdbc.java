/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Admin;
import model.Goods;
import model.Orders;
import model.Users;
import utils.JdbcUtil;

/**
 * 
 * @author wuzhuhao
 */
public class Jdbc implements JdbcInterface {

	Connection con = null;
	PreparedStatement stat = null;

	public Connection getConnection() {
		return JdbcUtil.getConnection();
	}

	public void closeJdbc(Connection c, ResultSet rs, Statement statement) {
		JdbcUtil.closeJdbc(c, rs, statement);
	}

	public ResultSet getResultSets(String sql, ArrayList<Object> patem) {
		con = getConnection();
		ResultSet rs = null;
		try {
			stat = con.prepareStatement(sql);
			for (int i = 0; i < patem.size(); i++) {
				stat.setObject(i + 1, patem.get(i));
			}
			rs = stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int Update(String sql, ArrayList<Object> patem) {
		con = getConnection();
		int rs = 0;
		try {
			stat = con.prepareStatement(sql);
			for (int i = 0; i < patem.size(); i++) {
				stat.setObject(i + 1, patem.get(i));
			}
			rs = stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void main(String[] args) {

		System.out.println(new Jdbc().addNum(1, -12));
	}

	@Override
	public Object login(Object data, int type) {
		String sql = "select * from admin where id = ? and password = ?";
		ResultSet rs = null;
		Users user = null;
		Admin admin = null;
		ArrayList<Object> patem = new ArrayList<>();
		if (type == 0) {
			try {
				sql = "select * from users where id = ? and password = ?";
				Users userData = (Users) data;
				patem.add(userData.getId());
				patem.add(userData.getPassword());
				rs = getResultSets(sql, patem);
				if (rs.next()) {
					user = new Users(rs.getLong("id"),
							rs.getString("password"), rs.getString("name"),
							rs.getLong("age"), rs.getString("sex"),
							rs.getString("tel"));
				}
				return user;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeJdbc(con, rs, stat);
			}
		} else if (type == 1) {
			try {
				Admin userData = (Admin) data;
				patem.add(userData.getId());
				patem.add(userData.getPassword());
				rs = getResultSets(sql, patem);
				if (rs.next()) {
					admin = new Admin(rs.getLong("id"),
							rs.getString("password"), rs.getString("name"),
							rs.getLong("age"), rs.getString("sex"));
				}
				return admin;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeJdbc(con, rs, stat);
			}
		}
		return null;
	}

	@Override
	public boolean register(Users user) {
		String sql = "INSERT INTO users (id,password,name,age,sex,tel) VALUES (?,?,?,?,?,?)";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(user.getId());
			patem.add(user.getPassword());
			patem.add(user.getName());
			patem.add(user.getAge());
			patem.add(user.getSex());
			patem.add(user.getTel());
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Ô±¹¤ÒÑ´æÔÚ  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public ArrayList<Goods> findGoodById(int g_id, String g_name) {
		String sql = "select * from goods where id = ? or name like ? ";
		System.out.println(sql);
		ResultSet rs = null;
		ArrayList<Goods> goods = new ArrayList<Goods>();
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			patem.add("%" + g_name + "%");
			rs = getResultSets(sql, patem);
			while (rs.next()) {
				Goods user = new Goods(rs.getLong("id"), rs.getString("name"),
						rs.getDouble("price"), rs.getLong("weight"),
						rs.getString("component"), rs.getString("sources"),
						rs.getString("apply"), rs.getString("type"),
						rs.getLong("praise"), rs.getLong("commonly"),
						rs.getLong("negativecomment"), rs.getLong("num"));

				goods.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return goods;
	}

	public ArrayList<Goods> findGoodOne(int g_id) {
		String sql = "select * from goods where id = ?";
		System.out.println(sql);
		ResultSet rs = null;
		ArrayList<Goods> goods = new ArrayList<Goods>();
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			rs = getResultSets(sql, patem);
			while (rs.next()) {
				Goods user = new Goods(rs.getLong("id"), rs.getString("name"),
						rs.getDouble("price"), rs.getLong("weight"),
						rs.getString("component"), rs.getString("sources"),
						rs.getString("apply"), rs.getString("type"),
						rs.getLong("praise"), rs.getLong("commonly"),
						rs.getLong("negativecomment"), rs.getLong("num"));

				goods.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return goods;
	}

	@Override
	public ArrayList<Goods> findAllGood() {
		String sql = "select * from goods";
		ResultSet rs = null;
		ArrayList<Goods> goods = new ArrayList<Goods>();
		ArrayList<Object> patem = new ArrayList<>();
		try {
			rs = getResultSets(sql, patem);
			while (rs.next()) {
				Goods user = new Goods(rs.getLong("id"), rs.getString("name"),
						rs.getDouble("price"), rs.getLong("weight"),
						rs.getString("component"), rs.getString("sources"),
						rs.getString("apply"), rs.getString("type"),
						rs.getLong("praise"), rs.getLong("commonly"),
						rs.getLong("negativecomment"), rs.getLong("num"));
				goods.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return goods;
	}

	@Override
	public boolean addNum(int g_id, int num) {
		String sql = "update goods set num = num+(" + num + ") where id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("¸üÐÂÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean addGood(Goods good) {
		String sql = "INSERT INTO goods (name,Price,Weight,Component,Sources,Apply,Type,Praise,Commonly,Negativecomment,Num) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(good.getName());
			patem.add(good.getPrice());
			patem.add(good.getWeight());
			patem.add(good.getComponent());
			patem.add(good.getSources());
			patem.add(good.getApply());
			patem.add(good.getType());
			patem.add(good.getPraise());
			patem.add(good.getCommonly());
			patem.add(good.getNegativecomment());
			patem.add(good.getNum());
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("²åÈëÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean deleteGood(int g_id) {
		if (!deleteOrderByGid(g_id)) {
			return false;
		}
		String sql = "delete from goods where id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("É¾³ýÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean updateGood(Goods good) {
		String sql = "update goods set name = ?,Price = ?,Weight = ?,Component = ?,Sources = ?,Apply = ?,type = ? where id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(good.getName());
			patem.add(good.getPrice());
			patem.add(good.getWeight());
			patem.add(good.getComponent());
			patem.add(good.getSources());
			patem.add(good.getApply());
			patem.add(good.getType());
			patem.add(good.getId());
			rs = Update(sql, patem);
			System.out.println(sql + rs);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("¸üÐÂÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean updatePassword(Object data, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEvaluateNum(long o_id, long g_id, long num, int type) {
		String s = "Praise";
		if (type == 0) {
			s = "Negativecomment";
		} else if (type == 1) {
			s = "Commonly";
		} else if (type == 2) {
			s = "Praise";
		} else {
			return false;
		}
		String sql = "update goods set " + s + " = (" + s + "+" + num
				+ ") where id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			rs = Update(sql, patem);
			if (rs != 0) {
				if (updateOrderStatus((int) o_id)) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("¸üÐÂÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public ArrayList<Orders> findOrdersByOId(int o_id) {
		String sql = "select orders.*,goods.name from orders,goods where orders.id = ? and orders.g_id = goods.id";
		ResultSet rs = null;
		ArrayList<Orders> orders = new ArrayList<Orders>();
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(o_id);
			rs = getResultSets(sql, patem);
			while (rs.next()) {
				Orders user = new Orders(rs.getLong("id"), rs.getLong("g_id"),
						rs.getLong("u_id"), rs.getLong("num"),
						rs.getDouble("total"), rs.getLong("status"));
				user.setG_name(rs.getString("name"));
				orders.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return orders;
	}

	@Override
	public ArrayList<Orders> findOrdersByUId(int u_id) {
		String sql = "select orders.*,goods.name from orders,goods where orders.u_id = ? and orders.g_id = goods.id";
		ResultSet rs = null;
		ArrayList<Orders> orders = new ArrayList<Orders>();
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(u_id);
			rs = getResultSets(sql, patem);
			while (rs.next()) {
				Orders user = new Orders(rs.getLong("id"), rs.getLong("g_id"),
						rs.getLong("u_id"), rs.getLong("num"),
						rs.getDouble("total"), rs.getLong("status"));
				user.setG_name(rs.getString("name"));
				orders.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return orders;
	}

	public boolean isEvaluate(int o_id) {
		String sql = "select * from orders where id = ?";
		ResultSet rs = null;
		boolean flag = false;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(o_id);
			rs = getResultSets(sql, patem);
			if (rs.next()) {
				if (rs.getInt("status") == 0) {
					flag = false;
				} else {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return flag;
	}

	@Override
	public ArrayList<Orders> findAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addOrder(Orders order) {
		String sql = "INSERT INTO orders (g_id,u_id,num,total,Status) VALUES (?,?,?,?,?)";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(order.getG_id());
			patem.add(order.getU_id());
			patem.add(order.getNum());
			patem.add(order.getTotal());
			patem.add(order.getStatus());
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("´´½¨¶©µ¥Ê§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean deleteOrderByOid(int o_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOrderByGid(int g_id) {
		String sql = "delete from orders where g_id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(g_id);
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("É¾³ýÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return true;
	}

	@Override
	public boolean updateOrderStatus(int o_id) {

		String sql = "update orders set status = ? where id = ?";
		int rs = 0;
		ArrayList<Object> patem = new ArrayList<>();
		try {
			patem.add(1);
			patem.add(o_id);
			rs = Update(sql, patem);
			if (rs != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("É¾³ýÊ§°Ü  ");
		} finally {
			closeJdbc(con, null, stat);
		}
		return false;
	}

	@Override
	public boolean checkNum(int g_id, int num) {
		String sql = "select * from goods where id = ?";
		ResultSet rs = null;
		ArrayList<Object> patem = new ArrayList<>();
		boolean flag = false;
		try {
			patem.add(g_id);
			rs = getResultSets(sql, patem);
			if (rs.next()) {
				if ((rs.getLong("num") - num) >= 0) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJdbc(con, rs, stat);
		}
		return flag;
	}

}
