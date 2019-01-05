package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import jdbcs.Jdbc;
import model.Admin;
import model.Users;
import utils.ViewUtil;
import view.AdminIndexView;
import view.RegisterView;
import view.UserIndexView;

public class LoginControl implements Initializable {

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

	@FXML
	private TextField idText;

	@FXML
	private PasswordField pwText;

	@FXML
	private RadioButton userBt;

	@FXML
	private RadioButton adminBt;

	final ToggleGroup group = new ToggleGroup();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).
		userBt.setToggleGroup(group);
		adminBt.setToggleGroup(group);
	}

	// When user click on myButton
	// this method will be called.
	public void showDateTime(ActionEvent event) {
		System.out.println("Button Clicked!");

		// Date now= new Date();
		//
		// DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		// String dateTimeString = df.format(now);
		// // Show in VIEW
		// idText.setText(dateTimeString);

	}

	public void login(ActionEvent event) {
		System.out.println("登陆");
		String id = idText.getText();
		String pw = pwText.getText();
		if (ViewUtil.isEmpty(id)) {
			ViewUtil.showErrorDialog("账号不能为空");
		} else if (ViewUtil.isEmpty(pw)) {
			ViewUtil.showErrorDialog("密码不能为空");
		} else {
			if (userBt.isSelected()) {
				try {
					Users user = new Users();
					user.setId(Long.parseLong(id));
					user.setPassword(pw);
					Users index = (Users) new Jdbc().login(user, 0);
					if (index != null) {
						ViewUtil.showDialog("登陆成功");
						Stage stage = (Stage) registerButton.getScene()
								.getWindow();
						stage.close();
						new UserIndexView(index).show();
					} else {
						ViewUtil.showErrorDialog("账号或者密码错误");
					}
				} catch (Exception e) {
					e.printStackTrace();
					ViewUtil.showDialog("请检查账号格式");
				}
			} else if (adminBt.isSelected()) {
				try {
					Admin user = new Admin();
					user.setId(Long.parseLong(id));
					user.setPassword(pw);
					Admin am = (Admin) new Jdbc().login(user, 1);
					if (am != null) {
						ViewUtil.showDialog("登陆成功");
						Stage stage = (Stage) registerButton.getScene()
								.getWindow();
						stage.close();
						new AdminIndexView(am).show();
					} else {
						ViewUtil.showErrorDialog("账号或者密码错误");
					}
				} catch (Exception e) {
					e.printStackTrace();
					ViewUtil.showErrorDialog("请检查账号格式");
				}
			} else {
				ViewUtil.showDialog("请选择身份");
			}
		}
	}

	public void showRegisterView(ActionEvent event) {
		System.out.println("注册");
		Stage stage = (Stage) registerButton.getScene().getWindow();
		stage.close();
		new RegisterView().show();
	}

	public void SingleElection(ActionEvent event) {
		System.out.println(userBt.isSelected());
		System.out.println(adminBt.isSelected());
	}

}
