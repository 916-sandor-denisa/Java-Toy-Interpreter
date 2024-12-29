package GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.state.ProgState;
import repo.IRepo;
import repo.Repo;
import view.command.RunExample;

import java.util.Objects;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(GUI.class.getResource("SelectWindow.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 500, 550);
        SelectController programChooserController = programListLoader.getController();
        primaryStage.setTitle("Select a program");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(GUI.class.getResource("MainWindow.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorRoot, 700, 500);
        MainController programExecutor = programExecutorLoader.getController();
        programChooserController.setProgramExecutor(programExecutor);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
