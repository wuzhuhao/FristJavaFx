package view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Admin;
import control.AdminIndexControl;

public class AdminIndexView extends Application {

	Stage stage = new Stage();
	Admin admin;

	public static void main(String[] args) {
		Application.launch(AdminIndexView.class, args);
	}

	public AdminIndexView(Admin admin) {
		super();
		this.admin = admin;
	}

	public AdminIndexView() {
		super();
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL location = getClass().getResource("/fxml/AdminIndexView.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlLoader.load();
		// Parent root = FXMLLoader.load(getClass().getResource(
		// "/fxml/UserIndexView.fxml"));
		AdminIndexControl controller = fxmlLoader.getController();
		controller.setAdmin(admin);
		controller.initLabel();
		controller.flushGoodsTable();
		Scene scene = new Scene(root);
		stage.initStyle(StageStyle.DECORATED);
		stage.setScene(scene);
		stage.setTitle("ึ๗าณ");

		stage.show();

	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void show() {
		try {
			start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
