package view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Users;
import control.UserIndexControl;

public class UserIndexView extends Application {

	Stage stage = new Stage();
	Users user;

	public static void main(String[] args) {
		Application.launch(UserIndexView.class, args);
	}

	public UserIndexView(Users user) {
		super();
		this.user = user;
	}

	public UserIndexView() {
		super();
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL location = getClass().getResource("/fxml/UserIndexView.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlLoader.load();
		// Parent root = FXMLLoader.load(getClass().getResource(
		// "/fxml/UserIndexView.fxml"));
		UserIndexControl controller = fxmlLoader.getController();
		controller.setUser(user);
		controller.initLabel();
		controller.flushGoodsTable();
		controller.flushOrderTable();
		Scene scene = new Scene(root);
		stage.initStyle(StageStyle.DECORATED);
		stage.setScene(scene);
		stage.setTitle("ึ๗าณ");
		stage.show();

	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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
