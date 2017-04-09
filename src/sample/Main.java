package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    private TextField user = new TextField();
    private PasswordField pw = new PasswordField();
    private Label lblGabimi = new Label();
    private Button btnKyqu = new Button("KYQU");
    private Label lblRaportiPath = new Label();
    private Hyperlink hlRep = new Hyperlink("Zgjedh folderin");

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        BorderPane bpane = new BorderPane();
        GridPane grid = new GridPane();
        HBox hb = new HBox();
        HBox msg = new HBox();
        msg.setAlignment(Pos.CENTER);
        btnKyqu.setMinWidth(150);
        lblGabimi.setStyle("-fx-text-fill: #f53d3d");
        Label welcome = new Label("MIRESEVINI");
        welcome.setStyle("-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.5), 2, 0, 1, 1)");
        msg.getChildren().add(welcome);
        msg.setPadding(new Insets(0, 0, 30, 0));
        welcome.setFont(Font.font(22));
        welcome.setTextFill(Color.WHITE);
        btnKyqu.getStyleClass().add("login");
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(btnKyqu);
        user.setPrefWidth(250);
        user.setStyle("-fx-border-radius: 0;" +
                "-fx-background-radius: 0;" +
                "-fx-background-color: rgba(0,0,0,0.2);" +
                "-fx-border-width: 1;" +
                "-fx-text-fill: #fff;" +
                "-fx-border-color: rgba(255,255,255, 0.5);" +
                "-fx-padding: 9");
        pw.setStyle("-fx-border-radius: 0;" +
                "-fx-background-radius: 0;" +
                "-fx-background-color: rgba(0,0,0,0.2);" +
                "-fx-border-width: 1;" +
                "-fx-text-fill: #fff;" +
                "-fx-border-color: rgba(255,255,255, 0.5);" +
                "-fx-padding: 9");

        user.setPromptText("Emri");
        pw.setPromptText("Fjalekalimi");

        StackPane stack = new StackPane();

        Rectangle rect = new Rectangle();
        rect.setHeight(550);
        rect.setWidth(450);
        rect.setStyle("-fx-fill: rgba(34, 36, 47, 0.8);");

        hlRep.setOnAction(e -> {
            new ZgjedhFolderinRaporti();
        });

        HBox hl = new HBox(5);
        hl.getChildren().addAll(lblRaportiPath, hlRep);
        hl.setAlignment(Pos.CENTER_RIGHT);
        hl.setPadding(new Insets(0, 0, 0, 5));

        grid.add(msg, 0, 0);
        grid.add(user, 0, 1);
        grid.add(pw, 0, 2);
        grid.add(lblGabimi, 0, 3);
        grid.add(hb, 0, 4);

        grid.setAlignment(Pos.CENTER_LEFT);

        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 100));

        stack.getChildren().addAll(grid);
        stack.setAlignment(Pos.CENTER_LEFT);

        btnKyqu.setOnAction(e -> {
            if (!user.getText().isEmpty() && !pw.getText().isEmpty()){
                login(primaryStage);
            }
            else lblGabimi.setText("Ju lutem mbushini fushat e kerkuara");
        });
        user.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)){
                if (!user.getText().isEmpty() && !pw.getText().isEmpty())
                    login(primaryStage);
                else lblGabimi.setText("Ju lutem mbushini fushat e kerkuara");
            }
        });
        pw.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)){
                if (!user.getText().isEmpty() && !pw.getText().isEmpty())
                    login(primaryStage);
                else lblGabimi.setText("Ju lutem mbushini fushat e kerkuara");
            }
        });

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        primaryStage.setTitle("Kyqu");
        bpane.setStyle("-fx-background-image: url(\"/sample/foto/car1.jpg\"); -fx-background-size: cover;");
        bpane.setCenter(stack);
        bpane.setBottom(hl);
        Scene scene = new Scene(bpane, 900, 550);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
            if (br.readLine() == null)
                new ZgjedhFolderinRaporti();
            br.close();
        }catch (Exception ex){ex.printStackTrace();}
    }

    private void login(Stage stage){
        try {
            int row = 0;
            String sql = "select * from \"ADMIN\" where lower(emri) = lower('" + user.getText() + "')";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                row++;
                if (pw.getText().equals(rs.getString("fjalekalimi"))){
                    new DritarjaKryesore(rs.getInt("id"), rs.getInt("lloji"), rs.getString("emri"), rs.getString("fjalekalimi"));
                    stage.close();
                }else {
                    lblGabimi.setText("Emri ose fjalekalimi jane gabim");
                }
            }
            if (row == 0) lblGabimi.setText("Emri ose fjalekalimi jane gabim");
            conn.close();
        }catch (Exception ex){ex.printStackTrace();}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
