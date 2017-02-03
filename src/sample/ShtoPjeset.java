package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;

/**
 * Created by urankajtazaj on 10/10/16.
 */
public class ShtoPjeset {
    Stage stage = new Stage();
    TextField emri = new TextField();
    TextField kategoria = new TextField();
    TextField prodhuesi = new TextField();
    TextField modeli = new TextField();
    TextField qmimi = new TextField();
    TextField sasia = new TextField();
    TextArea info = new TextArea();

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    public ShtoPjeset(DritarjaKryesore dk){
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        stage.setResizable(false);
        stage.setTitle("Shto paisje te reja");
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        HBox btn = new HBox();
        Button ok = new Button("Ne rregull");
        btn.getChildren().add(ok);
        emri.setMinWidth(250);

        root.add(new Label("Paisja"), 0, 0);
        root.add(emri, 1, 0);
        root.add(new Label("Kategoria"), 0, 1);
        root.add(kategoria, 1, 1);
        root.add(new Label("Prodhuesi"), 0, 2);
        root.add(prodhuesi, 1, 2);
        root.add(new Label("Modeli"), 0, 3);
        root.add(modeli, 1, 3);
        root.add(new Label("Qmimi"), 0, 4);
        root.add(qmimi, 1, 4);
        root.add(new Label("Sasia"), 0, 5);
        root.add(sasia, 1, 5);
        root.add(info, 1, 6);
        root.add(btn, 1, 7);

        info.setPrefColumnCount(5);
        info.setPrefRowCount(4);
        info.setPromptText("Informata shtese");

        ok.setOnAction(e -> shtoPjeset(dk));
        sasia.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        qmimi.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        modeli.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        emri.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        prodhuesi.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        kategoria.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) shtoPjeset(dk);
            else if (event.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        btn.setAlignment(Pos.CENTER_RIGHT);
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 390, 390);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void shtoPjeset(DritarjaKryesore dk){
        try {
            if(!emri.getText().isEmpty() && !kategoria.getText().isEmpty() && !qmimi.getText().isEmpty() && !sasia.getText().isEmpty()){
                StringBuilder sb = new StringBuilder("insert into Pjeset (prodhimi, tipi, lloji_pjeses, emri_pjeses, qmimi, sasia, info)" +
                        " values ('" + prodhuesi.getText() + "', '" + modeli.getText() + "', '" + kategoria.getText() + "', '" +
                        emri.getText() + "', " + Float.parseFloat(qmimi.getText()) + ", " +
                        Integer.parseInt(sasia.getText()) + ", '" + info.getText() + "')");
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sb.toString());
                stmt.close();
                conn.close();
                new Mesazhi("Sukses", "Sukses", "Te dhenat u shtuan me sukses");
                emri.clear();
                kategoria.clear();
                prodhuesi.clear();
                modeli.clear();
                qmimi.clear();
                sasia.clear();
                info.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dk.mbushProdhuesin();
                    }
                }).start();
            }else new Mesazhi("Gabim", "Fusha te zbrazta", "Fushat duhet te plotesohen para se te vazhdohet.");
        }catch (SQLNonTransientConnectionException fu){}
        catch (NumberFormatException ne){
            new Mesazhi("Info", "Formati i gabuar", "Ne fushat ku kerkohet vlera numerike, \neshte vendosur nje vlere tjeter jo-numerike.");
        }catch (Exception ex) {ex.printStackTrace();}
    }

}
