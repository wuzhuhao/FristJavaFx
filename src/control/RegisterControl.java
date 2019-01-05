package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jdbcs.Jdbc;
import model.Users;
import utils.ViewUtil;

public class RegisterControl implements Initializable {
	@FXML
	private Button registerBt;

	@FXML
	private Button resetBt;

	@FXML
	private TextField idText;

	@FXML
	private PasswordField pwText;

	@FXML
	private TextField nameText;
	@FXML
	private TextField sexText;
	@FXML
	private TextField ageText;
	@FXML
	private TextField telText;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void reset(ActionEvent event) {
		idText.setText("");
		pwText.setText("");
		nameText.setText("");
		sexText.setText("");
		ageText.setText("");
		telText.setText("");
	}

	public void register(ActionEvent event) {
		System.out.println("注册");
		String id = idText.getText();
		String pw = pwText.getText();
		String name = nameText.getText();
		String sex = sexText.getText();
		String age = ageText.getText();
		String tel = telText.getText();
		if (!ViewUtil.checkId(id)) {
			ViewUtil.showErrorDialog("账号格式不对，格式为6-13位数字");
		} else if (!ViewUtil.checkPassword(pw)) {
			ViewUtil.showErrorDialog("密码格式不对，格式为6-13位数字或者字母混合");
		} else if (!ViewUtil.checkAge(age)) {
			ViewUtil.showErrorDialog("年龄应为整数");
		} else if (ViewUtil.isEmpty(name)) {
			ViewUtil.showErrorDialog("姓名不能为空");
		} else if (ViewUtil.isEmpty(sex)) {
			ViewUtil.showErrorDialog("性别不能为空");
		} else if (ViewUtil.isEmpty(tel)) {
			ViewUtil.showErrorDialog("电话不能为空");
		} else {
			Users user = new Users(Long.parseLong(id), pw, name,
					Long.parseLong(age), sex, tel);
			if (new Jdbc().register(user)) {
				ViewUtil.showDialog("注册成功");
			} else {
				ViewUtil.showErrorDialog("注册失败");
			}
		}
	}
}
