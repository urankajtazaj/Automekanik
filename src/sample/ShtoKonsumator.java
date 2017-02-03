package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;

/**
 * Created by urankajtazaj on 08/10/16.
 */

public class ShtoKonsumator {
    Stage stage = new Stage();
    TextField txtEmri = new TextField();
    TextField txtMbiemri = new TextField();
    TextField txtVendbanimi = new TextField();
    TextField txtMakina = new TextField();
    TextArea txtDesc = new TextArea();
    Button btnShto = new Button("Shto");
    Button btnPastro = new Button("Anulo");

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    public ShtoKonsumator(){
        stage.setTitle("Shto Konsumatore");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        txtDesc.setPromptText("Informacione shtese");
        txtDesc.setMaxWidth(300);
        txtDesc.setPrefRowCount(4);
        btnShto.setMinWidth(70);
        btnPastro.setMinWidth(60);
        stage.setResizable(false);
        StackPane root = new StackPane();
        VBox v_root = new VBox(0);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnShto, btnPastro);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(8);

        grid.add(new Label("Emri"), 0, 0);
        grid.add(txtEmri, 1, 0);
        grid.add(new Label("Mbiemri"), 0, 1);
        grid.add(txtMbiemri, 1, 1);
        grid.add(new Label("Komuna"), 0, 2);
        grid.add(txtVendbanimi, 1, 2);
        grid.add(new Label("Makina"), 0, 3);
        grid.add(txtMakina, 1, 3);
        grid.add(txtDesc, 1, 4);
        grid.add(btn, 1, 5);
        grid.setAlignment(Pos.CENTER);

        btnShto.setOnAction(e -> {
            shto();
        });
        btnPastro.setOnAction(e -> stage.close());

        txtEmri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMbiemri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMakina.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtVendbanimi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        v_root.getChildren().addAll(grid);
        v_root.setAlignment(Pos.CENTER);
        root.getChildren().add(v_root);
        root.setPadding(new Insets(15));
        Scene scene = new Scene(root, 420, 390);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public ShtoKonsumator(int id, String emri, String mbiemri, String makina, String komuna, String per, ShikoKonsumatoret sk){
        stage.setTitle("Shto Konsumatore");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        txtDesc.setPromptText("Pershkrimi (opcional)");
        txtDesc.setMaxWidth(300);
        txtDesc.setPrefRowCount(4);
        btnShto.setMinWidth(70);
        btnPastro.setMinWidth(60);
        stage.setResizable(false);
        txtEmri.setText(emri);
        txtMbiemri.setText(mbiemri);
        txtDesc.setText(per);
        txtMakina.setText(makina);
        txtVendbanimi.setText(komuna);
        StackPane root = new StackPane();
        VBox v_root = new VBox(0);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnShto, btnPastro);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(8);
        btnShto.setText("Azhurno");

        grid.add(new Label("Emri"), 0, 0);
        grid.add(txtEmri, 1, 0);
        grid.add(new Label("Mbiemri"), 0, 1);
        grid.add(txtMbiemri, 1, 1);
        grid.add(new Label("Komuna"), 0, 2);
        grid.add(txtVendbanimi, 1, 2);
        grid.add(new Label("Makina"), 0, 3);
        grid.add(txtMakina, 1, 3);
        grid.add(txtDesc, 1, 4);
        grid.add(btn, 1, 5);
        grid.setAlignment(Pos.CENTER);

        btnShto.setOnAction(e -> {
            shto(id, sk);
            stage.close();
        });
        txtEmri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto(id, sk);
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMbiemri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto(id, sk);
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMakina.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto(id, sk);
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtVendbanimi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shto(id, sk);
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        btnPastro.setOnAction(e -> stage.close());

        v_root.getChildren().addAll(grid);
        v_root.setAlignment(Pos.CENTER);
        root.getChildren().add(v_root);
        Scene scene = new Scene(root, 420, 390);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public ShtoKonsumator(String emri, String mbiemri, String makina){
        stage.setTitle("Shto Konsumatore");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        txtDesc.setPromptText("Pershkrimi (opcional)");
        txtMbiemri.setText(mbiemri);
        txtEmri.setText(emri);
        txtMakina.setText(makina);
        txtDesc.setMaxWidth(300);
        txtDesc.setPrefRowCount(4);
        btnShto.setMinWidth(70);
        btnPastro.setMinWidth(60);
        stage.setResizable(false);
        StackPane root = new StackPane();
        VBox v_root = new VBox(0);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnShto, btnPastro);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(8);

        grid.getStyleClass().add("grid-add");

        grid.add(new Label("Emri"), 0, 0);
        grid.add(txtEmri, 1, 0);
        grid.add(new Label("Mbiemri"), 0, 1);
        grid.add(txtMbiemri, 1, 1);
        grid.add(new Label("Komuna"), 0, 2);
        grid.add(txtVendbanimi, 1, 2);
        grid.add(new Label("Makina"), 0, 3);
        grid.add(txtMakina, 1, 3);
        grid.add(txtDesc, 1, 4);
        grid.add(btn, 1, 5);
        grid.setAlignment(Pos.CENTER);

        btnShto.setOnAction(e -> {
            shto();
            stage.close();
        });
        btnPastro.setOnAction(e -> stage.close());

        txtEmri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {shto(); stage.close();}
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMbiemri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {shto(); stage.close();}
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtMakina.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {shto(); stage.close();}
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        txtVendbanimi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {shto(); stage.close();}
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        v_root.getChildren().addAll(grid);
        v_root.setAlignment(Pos.CENTER);
        root.getChildren().add(v_root);
        Scene scene = new Scene(root, 420, 390);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void shto(){
        try {
            if (!txtEmri.getText().isEmpty() && !txtVendbanimi.getText().isEmpty() && !txtMakina.getText().isEmpty()
                    && !txtMbiemri.getText().isEmpty()) {
                String em = txtEmri.getText().substring(0, 1).toUpperCase() + txtEmri.getText().substring(1, txtEmri.getText().length()).toLowerCase();
                String mb = txtMbiemri.getText().substring(0, 1).toUpperCase() + txtMbiemri.getText().substring(1, txtMbiemri.getText().length()).toLowerCase();
                String sql = "insert into Konsumatori (emri, mbiemri, makina, komuna, pershkrimi) values ('" + em + "', " +
                        "'" + mb + "', '" + txtMakina.getText() + "', '" + txtVendbanimi.getText() + "', " +
                        "'" + txtDesc.getText() + "')";
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
                new Mesazhi("Sukses", "Sukses", "Konsumatori " + txtEmri.getText().toUpperCase() + " " +
                        txtMbiemri.getText().toUpperCase() + " u shtua me sukese");
            }else new Mesazhi("Info", "", "Fushat duhet te plotesohen per te vazhduar.");
        }catch (SQLNonTransientConnectionException fu){}
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void shto(int id, ShikoKonsumatoret sk){
        try {
            if (!txtEmri.getText().isEmpty() && !txtVendbanimi.getText().isEmpty() && !txtMakina.getText().isEmpty()
                    && !txtMbiemri.getText().isEmpty()){
                String em = txtEmri.getText().substring(0, 1).toUpperCase() + txtEmri.getText().substring(1, txtEmri.getText().length()).toLowerCase();
                String mb = txtMbiemri.getText().substring(0, 1).toUpperCase() + txtMbiemri.getText().substring(1, txtMbiemri.getText().length()).toLowerCase();
                String sql = "update Konsumatori set emri = '" + em + "', mbiemri = '" + mb + "'," +
                        " komuna = '" + txtVendbanimi.getText() + "', makina = '" + txtMakina.getText() + "', pershkrimi = '" +
                        txtDesc.getText() + "' where id = " + id;
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                new Mesazhi("Sukses", "Te dhenat u ruajten", "Te dhenat per konsumatorin " + txtEmri.getText() + " " +
                txtMbiemri.getText() + ",\n u azhurnuan me sukses");
                conn.close();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sk.mbushTabelen();
                    }
                }).start();
                stage.close();
            }else new Mesazhi("Info", "", "Fushat duhet te plotesohen per te vazhduar.");
        }catch (SQLNonTransientConnectionException fu){}
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
