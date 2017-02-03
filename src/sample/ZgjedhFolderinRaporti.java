package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by urankajtazaj on 07/11/16.
 */
public class ZgjedhFolderinRaporti {
    Stage stage = new Stage();
    public ZgjedhFolderinRaporti(){
        stage.setTitle("Zgjedh direktoriumin");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        Button btnOk = new Button("Ne rregull");

        File file = new File(System.getProperty("user.home") + "/db/raportet.txt");

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Zgjedh folderin");

        TextField txtPath = new TextField();
        Button btnZgjedh = new Button("Zgjedh");

        txtPath.setMinWidth(250);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            txtPath.setText(br.readLine() + "");
            if (txtPath.getText().equals("null")) txtPath.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnOk.setOnAction(e -> {
            if (!txtPath.getText().isEmpty()){
                try {
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(txtPath.getText());

                    bw.close();
                    stage.close();
                }catch (Exception ex){new Mesazhi("Gabim", "", ex.getMessage());}
            }
        });

        HBox hbPath = new HBox(5);
        hbPath.getChildren().addAll(txtPath, btnZgjedh, btnOk);

        btnZgjedh.setOnAction(e -> {
            txtPath.setText(dc.showDialog(stage).getPath());
        });

        GridPane root = new GridPane();
        root.add(new Label("Zgjedhni folderin me emrin \"raporti\" i cili permban fajllat me \nprapashtesen \"jrxml\""), 0, 0);
        root.add(hbPath, 0, 1);
        root.setAlignment(Pos.CENTER);
        root.setVgap(5);

        Scene scene = new Scene(root, 450, 120);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
