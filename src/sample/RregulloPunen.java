package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by urankajtazaj on 03/11/16.
 */
public class RregulloPunen {
    Stage stage = new Stage();
    CheckBox cbKryer = new CheckBox("Puna ka perfunduar");
    Button btnOk = new Button("Ne rregull");
    Button btnAnulo = new Button("Anulo");

    private static final String CON_STR = "jdbc:h2:file:~/db/auto;AUTO_SERVER=TRUE";

    public RregulloPunen(int id, boolean kryer, TeDhenat td){
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Rregullo");
        HBox btn = new HBox(5);
        btn.getChildren().addAll(btnOk, btnAnulo);
        btn.setAlignment(Pos.CENTER_RIGHT);
        GridPane grid = new GridPane();
        grid.add(cbKryer, 0, 0);
        grid.add(btn, 0, 1);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        cbKryer.setSelected(kryer);

        btnOk.setOnAction(e -> {
            azhurno(id);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    td.mbush();
                }
            }).start();
        });

        btnAnulo.setOnAction(e -> stage.close());

        Scene scene = new Scene(grid, 230, 100);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public RregulloPunen(int id, boolean kryer, DritarjaKryesore dk){
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Rregullo");
        HBox btn = new HBox(5);
        btn.getChildren().addAll(btnOk, btnAnulo);
        btn.setAlignment(Pos.CENTER_RIGHT);
        GridPane grid = new GridPane();
        grid.add(cbKryer, 0, 0);
        grid.add(btn, 0, 1);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        cbKryer.setSelected(kryer);

        btnOk.setOnAction(e -> {
            azhurno(id);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dk.filtroFinancat(new TextField(""));
                    dk.loadThread(dk.lblQmimi4);
                    dk.loadThreadMes(dk.lblQmimi3);
                }
            }).start();
        });

        btnAnulo.setOnAction(e -> stage.close());

        Scene scene = new Scene(grid, 230, 100);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public RregulloPunen(int id, ShikoPunetPunetoret spp, boolean kryer){
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Rregullo");
        HBox btn = new HBox(5);
        btn.getChildren().addAll(btnOk, btnAnulo);
        btn.setAlignment(Pos.CENTER_RIGHT);
        GridPane grid = new GridPane();
        grid.add(cbKryer, 0, 0);
        grid.add(btn, 0, 1);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        cbKryer.setSelected(kryer);

        btnOk.setOnAction(e -> {
            azhurno(id);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    spp.mbushNgaStatusi(spp.lblEmri.getText());
                }
            }).start();
        });
        btnAnulo.setOnAction(e -> stage.close());

        Scene scene = new Scene(grid, 230, 100);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void azhurno(int id){
        try{
            String sql = "";
            if (cbKryer.isSelected())
                sql = "update Punet set kryer = 'po' where id = " + id;
            else
                sql = "update Punet set kryer = 'jo' where id = " + id;

            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stage.close();
        }catch (Exception ex){ex.printStackTrace();}
    }

}
