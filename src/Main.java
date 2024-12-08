//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{
    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            Scene root = new Scene(loader.load(getClass().getResource("FroggerGUI.fxml").openStream()));
            primaryStage.setScene(root);
            primaryStage.show();
            root.getRoot().requestFocus();
        }catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}