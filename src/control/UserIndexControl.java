package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbcs.Jdbc;
import model.Goods;
import model.Orders;
import model.Users;
import utils.ViewUtil;
import view.LoginView;

public class UserIndexControl implements Initializable {
	@FXML
	private Button findBt;

	@FXML
	private Button buyBt;

	@FXML
	private Label labelText;
	@FXML
	private TableView<Goods> goodTable;

	@FXML
	private TableView<Orders> OrderTable;
	@FXML
	private TableColumn<Goods, Long> idCol;
	@FXML
	private TableColumn<Goods, String> nameCol;
	@FXML
	private TableColumn<Goods, Double> priceCol;
	@FXML
	private TableColumn<Goods, Long> weightCol;
	@FXML
	private TableColumn<Goods, String> componentCol;
	@FXML
	private TableColumn<Goods, String> sourceCol;
	@FXML
	private TableColumn<Goods, String> applyCol;
	@FXML
	private TableColumn<Goods, String> typeCol;
	@FXML
	private TableColumn<Goods, String> praiseCol;
	@FXML
	private TableColumn<Goods, String> commonlyCol;
	@FXML
	private TableColumn<Goods, String> negativecommentCol;
	@FXML
	private TableColumn<Goods, Long> numCol;

	public ObservableList<Goods> List = FXCollections.observableArrayList();

	public ObservableList<Orders> orders = FXCollections.observableArrayList();
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// System.out.println(user.getName());
		// labelText.setText(user.getName() + "，欢迎您");

		initOrderTable();
		initGoodTable();

	}

	public void initGoodTable() {
		ObservableList<TableColumn<Goods, ?>> observableList = goodTable
				.getColumns();
		// idCol
		observableList.get(0).setCellValueFactory(
				new PropertyValueFactory("id"));
		// nameCol
		observableList.get(1).setCellValueFactory(
				new PropertyValueFactory("name"));
		// priceCol
		observableList.get(2).setCellValueFactory(
				new PropertyValueFactory("price"));
		// weightCol
		observableList.get(3).setCellValueFactory(
				new PropertyValueFactory("weight"));
		// componentCol
		observableList.get(4).setCellValueFactory(
				new PropertyValueFactory("component"));
		// observableList.get(4).setCellFactory(ProgressBarTableCell.forTableColumn());
		// sourceCol
		observableList.get(5).setCellValueFactory(
				new PropertyValueFactory("sources"));
		// applyCol
		observableList.get(6).setCellValueFactory(
				new PropertyValueFactory("apply"));
		// typeCol
		observableList.get(7).setCellValueFactory(
				new PropertyValueFactory("type"));
		// praiseCol
		observableList.get(8).setCellValueFactory(
				new PropertyValueFactory("praiseString"));
		// commonlyCol
		observableList.get(9).setCellValueFactory(
				new PropertyValueFactory("commonlyString"));
		// negativecommentCol
		observableList.get(10).setCellValueFactory(
				new PropertyValueFactory("negativecommentString"));
		// numCol
		observableList.get(11).setCellValueFactory(
				new PropertyValueFactory("num"));

		goodTable.setItems(List);
	}

	public void initOrderTable() {
		ObservableList<TableColumn<Orders, ?>> observableList = OrderTable
				.getColumns();
		observableList.get(0).setCellValueFactory(
				new PropertyValueFactory("id"));
		observableList.get(1).setCellValueFactory(
				new PropertyValueFactory("g_id"));
		observableList.get(2).setCellValueFactory(
				new PropertyValueFactory("g_name"));
		observableList.get(3).setCellValueFactory(
				new PropertyValueFactory("num"));
		observableList.get(4).setCellValueFactory(
				new PropertyValueFactory("total"));
		observableList.get(5).setCellValueFactory(
				new PropertyValueFactory("statusString"));
		OrderTable.setItems(orders);
	}

	public void find() {
		String input = ViewUtil.showInputDialog("请输入用户商品id或者商品名称:");
		if (!(input == null || ViewUtil.isEmpty(input))) {
			int id = 0;
			try {
				id = Integer.parseInt(input);
			} catch (Exception e) {

			}
			List.clear();
			List.addAll(new Jdbc().findGoodById(id, input));
			// goodTable.setItems(List);
		}
	}

	public void findOrder() {
		String input = ViewUtil.showInputDialog("请输入订单ID:");
		if (!(input == null || ViewUtil.isEmpty(input))) {
			int id = 0;
			try {
				id = Integer.parseInt(input);
			} catch (Exception e) {
				ViewUtil.showErrorDialog("订单ID格式不对");
				return;
			}
			orders.clear();
			orders.addAll(new Jdbc().findOrdersByOId(id));
			// goodTable.setItems(List);
		}
	}

	public void evaluate() {

		System.out.println("评价");
		Orders orders = OrderTable.getSelectionModel().getSelectedItem();
		long o_id = 0;
		long num = 0;
		long g_id = 0;
		if (orders != null) {
			int status = ViewUtil.showShoiceDialog();
			if (status >= 0) {
				o_id = orders.getId();
				num = orders.getNum();
				g_id = orders.getG_id();
				if (new Jdbc().isEvaluate((int) o_id)) {
					ViewUtil.showDialog("已评价，不用重复评价");
					return;
				} else {
					if (new Jdbc().addEvaluateNum(o_id, g_id, num, status)) {
						ViewUtil.showDialog("评价成功！");
						flushOrderTable();
					} else {
						ViewUtil.showDialog("评价失败");
					}
				}
			}

		} else
			ViewUtil.showDialog("请选择要评价的订单");
	}

	public void flushGoodsTable() {
		List.clear();
		List.addAll(new Jdbc().findAllGood());
	}

	public void flushOrderTable() {
		orders.clear();
		System.out.println(123123);
		ArrayList<Orders> f = new Jdbc().findOrdersByUId(user.getId()
				.intValue());
		for (Orders o : f) {
			System.out.println(o);
		}
		orders.addAll(f);
	}

	public void logout() {
		Stage stage = (Stage) findBt.getScene().getWindow();
		stage.close();
		new LoginView().show();
	}

	public void buy() {
		// ViewUtil.showInputDialog("请输入用户商品id或者商品名称:");
		System.out.println("购买");
		Goods good = goodTable.getSelectionModel().getSelectedItem();
		int buyNum = 0;
		if (good != null) {
			String input = ViewUtil.showInputDialog("请输入购买的数量");
			int g_id = good.getId().intValue();
			try {
				buyNum = Integer.parseInt(input);
				if (buyNum > 0) {
					if (new Jdbc().checkNum(g_id, buyNum)) {
						double total = buyNum
								* new Jdbc().findGoodById(g_id, "").get(0)
										.getPrice();
						Orders order = new Orders((long) 0, (long) g_id,
								user.getId(), (long) buyNum, total, (long) 0);
						if (new Jdbc().addNum(g_id, (0 - buyNum))
								&& new Jdbc().addOrder(order)) {
							ViewUtil.showDialog("购买成功");
							flushGoodsTable();
						} else
							ViewUtil.showErrorDialog("购买失败");
					} else
						ViewUtil.showErrorDialog("库存不足");
				} else {
					ViewUtil.showErrorDialog("请输入正整数");
				}
			} catch (Exception e) {
				ViewUtil.showErrorDialog("请输入正整数");
			}
		} else
			ViewUtil.showDialog("请选择购买的商品");
	}

	public void initLabel() {
		labelText.setText(user.getName() + "，欢迎您");
	}
}
