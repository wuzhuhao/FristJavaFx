package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jdbcs.Jdbc;
import model.Admin;
import model.Goods;
import model.Orders;
import utils.ViewUtil;
import view.LoginView;

public class AdminIndexControl implements Initializable {
	@FXML
	private Button findBt;

	@FXML
	private Button addGBt;
	@FXML
	private Button addBt;
	@FXML
	private Button delBt;
	@FXML
	private Button updateBt;
	@FXML
	private Button flushBt;
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

	Admin admin;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// System.out.println(user.getName());
		// labelText.setText(user.getName() + "，欢迎您");

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
						// flushOrderTable();
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

	public void logout() {
		Stage stage = (Stage) findBt.getScene().getWindow();
		stage.close();
		new LoginView().show();
	}

	// public void buy() {
	// // ViewUtil.showInputDialog("请输入用户商品id或者商品名称:");
	// System.out.println("购买");
	// Goods good = goodTable.getSelectionModel().getSelectedItem();
	// int buyNum = 0;
	// if (good != null) {
	// String input = ViewUtil.showInputDialog("请输入购买的数量");
	// int g_id = good.getId().intValue();
	// try {
	// buyNum = Integer.parseInt(input);
	// if (buyNum > 0) {
	// if (new Jdbc().checkNum(g_id, buyNum)) {
	// double total = buyNum
	// * new Jdbc().findGoodById(g_id, "").get(0)
	// .getPrice();
	// Orders order = new Orders((long) 0, (long) g_id,
	// user.getId(), (long) buyNum, total, (long) 0);
	// if (new Jdbc().addNum(g_id, (0 - buyNum))
	// && new Jdbc().addOrder(order)) {
	// ViewUtil.showDialog("购买成功");
	// flushGoodsTable();
	// } else
	// ViewUtil.showErrorDialog("购买失败");
	// } else
	// ViewUtil.showErrorDialog("库存不足");
	// } else {
	// ViewUtil.showErrorDialog("请输入正整数");
	// }
	// } catch (Exception e) {
	// ViewUtil.showErrorDialog("请输入正整数");
	// }
	// } else
	// ViewUtil.showDialog("请选择购买的商品");
	// }

	public void initLabel() {
		labelText.setText(admin.getName() + "，欢迎您");
	}

	public void showAddDialog() {
		Dialog<Map<String, String>> dialog = new Dialog<>();
		dialog.setTitle("添加商品");
		dialog.setHeaderText(null);

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new ImageView("/img/6.jpg"));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("增加", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes()
				.addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField g_name = new TextField();
		g_name.setPromptText("商品名称");
		TextField Price = new TextField();
		Price.setPromptText("商品价格");
		TextField Weight = new TextField();
		Weight.setPromptText("商品重量");
		TextField Component = new TextField();
		Component.setPromptText("商品成分");
		TextField Sources = new TextField();
		Sources.setPromptText("商品来源");
		TextField Apply = new TextField();
		Apply.setPromptText("商品应用");
		TextField Type = new TextField();
		Type.setPromptText("商品类型");
		TextField Num = new TextField();
		Num.setPromptText("商品数量");

		grid.add(new Label("商品名称:"), 0, 0);
		grid.add(g_name, 1, 0);
		grid.add(new Label("商品价格:"), 0, 1);
		grid.add(Price, 1, 1);
		grid.add(new Label("商品重量:"), 0, 2);
		grid.add(Weight, 1, 2);
		grid.add(new Label("商品成分:"), 0, 3);
		grid.add(Component, 1, 3);
		grid.add(new Label("商品来源:"), 0, 4);
		grid.add(Sources, 1, 4);
		grid.add(new Label("商品应用:"), 0, 5);
		grid.add(Apply, 1, 5);
		grid.add(new Label("商品类型:"), 0, 6);
		grid.add(Type, 1, 6);
		grid.add(new Label("商品数量:"), 0, 7);
		grid.add(Num, 1, 7);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(false);

		// g_name.textProperty().addListener((observable, oldValue, newValue) ->
		// {
		// loginButton.setDisable(newValue.trim().isEmpty());
		// });

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		// Platform.runLater(() -> g_name.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("name", g_name.getText());
				hm.put("Price", Price.getText());
				hm.put("Weight", Weight.getText());
				hm.put("Component", Component.getText());
				hm.put("Sources", Sources.getText());
				hm.put("Apply", Apply.getText());
				hm.put("Type", Type.getText());
				hm.put("Num", Num.getText());
				return hm;
			}
			return null;
		});

		Optional<Map<String, String>> result = dialog.showAndWait();

		result.ifPresent(map -> {
			try {
				Goods good = new Goods((long) 0, map.get("name"), Double
						.parseDouble(map.get("Price")), Long.parseLong(map
						.get("Weight")), map.get("Component"), map
						.get("Sources"), map.get("Apply"), map.get("Type"),
						(long) 0, (long) 0, (long) 0, Long.parseLong(map
								.get("Num")));
				if (new Jdbc().addGood(good)) {
					ViewUtil.showDialog("添加成功");
				} else {
					ViewUtil.showErrorDialog("添加失败");
				}
			} catch (Exception e) {
				ViewUtil.showErrorDialog("字段格式不对");
				return;
			}

			// System.out.println("Username=" + usernamePassword.getKey()
			// + ", Password=" + usernamePassword.getValue());
		});
	}

	// 更新商品信息按钮事件
	public void update() {
		Goods good = goodTable.getSelectionModel().getSelectedItem();
		if (good != null) {
			ArrayList<Goods> gs = new Jdbc().findGoodOne(good.getId()
					.intValue());
			if (gs.size() == 0) {
				ViewUtil.showErrorDialog("商品不存在");
			} else {
				good = gs.get(0);
				showUpdateDialog(good);
			}

		} else
			ViewUtil.showDialog("请选择要修改的商品");
	}

	// 商品商品按钮事件
	public void delete() {
		Goods good = goodTable.getSelectionModel().getSelectedItem();
		if (good != null) {
			ArrayList<Goods> gs = new Jdbc().findGoodOne(good.getId()
					.intValue());
			if (gs.size() == 0) {
				ViewUtil.showErrorDialog("商品不存在");
			} else {
				if (new Jdbc().deleteGood(good.getId().intValue())) {
					ViewUtil.showDialog("删除成功");
				} else {
					ViewUtil.showErrorDialog("删除失败");
				}
			}

		} else
			ViewUtil.showDialog("请选择要删除的商品");
	}

	// 增加库存按钮触发事件
	public void addNum() {
		Goods good = goodTable.getSelectionModel().getSelectedItem();
		if (good != null) {
			ArrayList<Goods> gs = new Jdbc().findGoodOne(good.getId()
					.intValue());
			if (gs.size() == 0) {
				ViewUtil.showErrorDialog("商品不存在");
			} else {
				try {
					String input = ViewUtil.showInputDialog("请输入增加库存量:");
					int num = 0;
					num = Integer.parseInt(input);
					if (new Jdbc().addNum(good.getId().intValue(), num)) {
						ViewUtil.showDialog("增加库存成功");
					} else {
						ViewUtil.showErrorDialog("增加库存失败");
					}
				} catch (Exception e) {
					ViewUtil.showErrorDialog("库存量格式不对");
				}
			}

		} else
			ViewUtil.showDialog("请选择要增加库存的商品");
	}

	public void showUpdateDialog(Goods good) {
		Dialog<Map<String, String>> dialog = new Dialog<>();
		dialog.setTitle("修改商品");
		dialog.setHeaderText(null);

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new ImageView("/img/6.jpg"));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("增加", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes()
				.addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField g_id = new TextField();
		g_id.setEditable(false);
		TextField g_name = new TextField();
		TextField Price = new TextField();
		TextField Weight = new TextField();
		TextField Component = new TextField();
		TextField Sources = new TextField();
		TextField Apply = new TextField();
		TextField Type = new TextField();
		TextField Num = new TextField();
		g_id.setText(good.getId() + "");
		g_name.setText(good.getName() + "");
		Price.setText(good.getPrice() + "");
		Weight.setText(good.getWeight() + "");
		Component.setText(good.getComponent() + "");
		Sources.setText(good.getSources() + "");
		Apply.setText(good.getApply() + "");
		Type.setText(good.getType() + "");
		Num.setText(good.getNum() + "");

		grid.add(new Label("商品ID:"), 0, 0);
		grid.add(g_id, 1, 0);
		grid.add(new Label("商品名称:"), 0, 1);
		grid.add(g_name, 1, 1);
		grid.add(new Label("商品价格:"), 0, 2);
		grid.add(Price, 1, 2);
		grid.add(new Label("商品重量:"), 0, 3);
		grid.add(Weight, 1, 3);
		grid.add(new Label("商品成分:"), 0, 4);
		grid.add(Component, 1, 4);
		grid.add(new Label("商品来源:"), 0, 5);
		grid.add(Sources, 1, 5);
		grid.add(new Label("商品应用:"), 0, 6);
		grid.add(Apply, 1, 6);
		grid.add(new Label("商品类型:"), 0, 7);
		grid.add(Type, 1, 7);
		grid.add(new Label("商品数量:"), 0, 8);
		grid.add(Num, 1, 8);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(false);

		// g_name.textProperty().addListener((observable, oldValue, newValue) ->
		// {
		// loginButton.setDisable(newValue.trim().isEmpty());
		// });

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		// Platform.runLater(() -> g_name.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("id", g_id.getText());
				hm.put("name", g_name.getText());
				hm.put("Price", Price.getText());
				hm.put("Weight", Weight.getText());
				hm.put("Component", Component.getText());
				hm.put("Sources", Sources.getText());
				hm.put("Apply", Apply.getText());
				hm.put("Type", Type.getText());
				hm.put("Num", Num.getText());
				return hm;
			}
			return null;
		});

		Optional<Map<String, String>> result = dialog.showAndWait();

		result.ifPresent(map -> {
			try {
				Goods goods = new Goods(Long.parseLong(map.get("id")), map
						.get("name"), Double.parseDouble(map.get("Price")),
						Long.parseLong(map.get("Weight")),
						map.get("Component"), map.get("Sources"), map
								.get("Apply"), map.get("Type"), (long) 0,
						(long) 0, (long) 0, Long.parseLong(map.get("Num")));
				if (new Jdbc().updateGood(goods)) {
					ViewUtil.showDialog("修改成功");
				} else {
					ViewUtil.showErrorDialog("修改失败");
				}
			} catch (Exception e) {
				ViewUtil.showErrorDialog("字段格式不对");
				return;
			}

			// System.out.println("Username=" + usernamePassword.getKey()
			// + ", Password=" + usernamePassword.getValue());
		});
	}
}
