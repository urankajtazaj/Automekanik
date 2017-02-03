package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Created by urankajtazaj on 11/10/16.
 */

public class ShtoPunetor {
    Stage stage = new Stage();
    TextField emri = new TextField();
    TextField mbiemri = new TextField();
    TextField komuna = new TextField();
    TextField paga = new TextField();
    PasswordField pw = new PasswordField();
    Hyperlink shfaq = new Hyperlink();
    Label lblPw = new Label();
    TextField user = new TextField();
    DatePicker data = new DatePicker();
    TextField pjesa = new TextField();
    Button btnOk = new Button("Ne rregull");
    Button btnAnulo = new Button("Anulo");

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    public ShtoPunetor(){
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        stage.setTitle("Shtimi i punetoreve");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnOk, btnAnulo);
        data.setValue(LocalDate.now());

        GridPane root = new GridPane();
        root.add(new Label("Emri"), 0, 0);
        root.add(emri, 1, 0);
        root.add(new Label("Mbiemri"), 0, 1);
        root.add(mbiemri, 1, 1);
        root.add(new Label("Fjalekalimi"), 0, 2);
        root.add(pw, 1, 2);
        root.add(new Label("Komuna"), 0, 3);
        root.add(komuna, 1, 3);
        root.add(new Label("Pozita"), 0, 4);
        root.add(pjesa, 1, 4);
        root.add(new Label("Paga mujore"), 0, 5);
        root.add(paga, 1, 5);
        root.add(new Label("Data e regjistrimit"), 0, 6);
        root.add(data, 1, 6);
        root.add(btn, 1, 7);

        btnOk.setOnAction(e -> {
            shtoPunetoreMethod();
        });
        btnAnulo.setOnAction(e -> stage.close());

        data.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shtoPunetoreMethod();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        emri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shtoPunetoreMethod();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        mbiemri.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shtoPunetoreMethod();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        komuna.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod(); }});
        pjesa.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shtoPunetoreMethod();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        paga.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) shtoPunetoreMethod();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        root.setVgap(7);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 360, 370);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public ShtoPunetor(DritarjaKryesore dk, String dump){
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        stage.setTitle("Ndrysho fjalekalimin");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnOk, btnAnulo);
        data.setValue(LocalDate.now());

        GridPane root = new GridPane();
        root.add(new Label("Emri"), 0, 0);
        root.add(user, 1, 0);
        root.add(new Label("Fjalekalimi"), 0, 1);
        root.add(pw, 1, 1);
        root.add(shfaq, 2, 1);
        root.add(lblPw, 1, 2);
        root.add(btn, 1, 4);

        shfaq.setGraphic(new ImageView(new Image("/sample/foto/eye.png")));
        shfaq.setOnMousePressed(e -> lblPw.setText(pw.getText()));
        shfaq.setOnMouseReleased(e -> lblPw.setText(""));

        user.setText(dk.log_user.getText());
        pw.setText(dk.log_pw.getText());
        user.setEditable(false);
        user.setTooltip(new Tooltip("Emri nuk mund te ndryshohet"));

        btnOk.setOnAction(e -> {
            if (pw.getText().length() > 5){
                rregullo(Integer.parseInt(dk.lblId.getText()));
            }else new Mesazhi("Info", "Fjalekalimi i shkurte", "Fjalekalimi duhet te jete mbi 5 karaktere i gjate.");
        });
        btnAnulo.setOnAction(e -> stage.close());

        pw.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){
            if (pw.getText().length() > 5)
                rregullo(Integer.parseInt(dk.lblId.getText()));
            else new Mesazhi("Info", "Fjalekalimi i shkurte", "Fjalekalimi duhet te jete mbi 5 karaktere i gjate.");
        }});
        emri.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){
            if (pw.getText().length() > 5)
                rregullo(Integer.parseInt(dk.lblId.getText()));
            else new Mesazhi("Info", "Fjalekalimi i shkurte", "Fjalekalimi duhet te jete mbi 5 karaktere i gjate.");
        }});

        root.setVgap(7);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 320, 210);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public ShtoPunetor(int id, String stremri, String strmbiemri, String strkomuna, String strpozita, float strpaga, ShikoPunetoret sp){
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        System.out.println(id);
        stage.setTitle("Shtimi i punetoreve");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        HBox btn = new HBox(5);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.getChildren().addAll(btnOk, btnAnulo);
        data.setValue(LocalDate.now());

        emri.setText(stremri);
        mbiemri.setText(strmbiemri);
        komuna.setText(strkomuna);
        pjesa.setText(strpozita);
        paga.setText(strpaga + "");

        GridPane root = new GridPane();
        root.add(new Label("Emri"), 0, 0);
        root.add(emri, 1, 0);
        root.add(new Label("Mbiemri"), 0, 1);
        root.add(mbiemri, 1, 1);
        root.add(new Label("Fjalekalimi"), 0, 2);
        root.add(pw, 1, 2);
        root.add(new Label("Komuna"), 0, 3);
        root.add(komuna, 1, 3);
        root.add(new Label("Pozita"), 0, 4);
        root.add(pjesa, 1, 4);
        root.add(new Label("Paga mujore"), 0, 5);
        root.add(paga, 1, 5);
        root.add(btn, 1, 6);

        pw.setPromptText("Mund te lihet i zbrazet");
        btnOk.setOnAction(e -> {
            shtoPunetoreMethod2(id, stremri.toLowerCase() + strmbiemri.toLowerCase());
            sp.mbushPunetoret();
        });
        btnAnulo.setOnAction(e -> stage.close());

        data.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});
        emri.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});
        mbiemri.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});
        komuna.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});
        pjesa.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});
        paga.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)){shtoPunetoreMethod2(id,stremri.toLowerCase() + strmbiemri.toLowerCase()); sp.mbushPunetoret();}});

        root.setVgap(7);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 360, 350);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void shtoPunetoreMethod(){
        try {
            if (!emri.getText().isEmpty() && !mbiemri.getText().isEmpty() && !komuna.getText().isEmpty() && !pjesa.getText().isEmpty() &&
                    !paga.getText().isEmpty() && !data.getEditor().getText().isEmpty() && !pw.getText().isEmpty()) {
                String em = emri.getText().substring(0, 1).toUpperCase() + emri.getText().substring(1, emri.getText().length()).toLowerCase();
                String mb = mbiemri.getText().substring(0, 1).toUpperCase() + mbiemri.getText().substring(1, mbiemri.getText().length()).toLowerCase();
                String sql = "insert into Punetoret (emri, mbiemri, komuna, pozita, paga, regjistrimi) values ('" +
                        em + "', '" + mb + "', '" + komuna.getText() + "', '" + pjesa.getText() + "', " +
                        Float.parseFloat(paga.getText()) + ", DATE '" + data.getValue() + "')";
                String adminSql = "insert into Admin (emri, fjalekalimi, lloji) values " +
                        "('" + emri.getText().toLowerCase() + mbiemri.getText().toLowerCase() + "', '" + pw.getText() + "', 0)";
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                Statement AdminStmt = conn.createStatement();
                stmt.execute(sql);
                AdminStmt.execute(adminSql);
                conn.close();
                new Mesazhi("Sukses", em + " " + mb, "Punetori u shtua me sukses me daten " + data.getEditor().getText());
                stage.close();
            }else new Mesazhi("Info", "", "Fushat duhet te plotesohen para se te vazhdoni");
        }catch (SQLNonTransientConnectionException fu){}
        catch (NumberFormatException nfw){new Mesazhi("Info", "Gabim", "Fusha qmimi duhet te permbaj vetem numra.");}
        catch (Exception ex){ex.printStackTrace();}
    }

    private void shtoPunetoreMethod2(int id, String name){
        try {
            if (!emri.getText().isEmpty() && !mbiemri.getText().isEmpty() && !komuna.getText().isEmpty() && !pjesa.getText().isEmpty() &&
                    !paga.getText().isEmpty()) {
                String em = emri.getText().substring(0, 1).toUpperCase() + emri.getText().substring(1, emri.getText().length()).toLowerCase();
                String mb = mbiemri.getText().substring(0, 1).toUpperCase() + mbiemri.getText().substring(1, mbiemri.getText().length()).toLowerCase();
                String sql = "update Punetoret set emri = '" + em + "', mbiemri = '" + mb +
                        "', komuna = '" + komuna.getText() + "', pozita = '" + pjesa.getText() + "', paga = " +
                        Float.parseFloat(paga.getText()) + " where id = " + id;

                String adminSql = "";
                String punetSql = "update Punet set punetori = '" + emri.getText().toLowerCase() + mbiemri.getText().toLowerCase()
                        + "' where punetori = '" + name + "'";
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                if (pw.getText().isEmpty()){
                    adminSql = "update Admin set emri = '" + emri.getText().toLowerCase() + mbiemri.getText().toLowerCase() + "'" +
                            " where emri = '" + name + "'";
                }else {
                    adminSql = "update Admin set emri = '" + emri.getText().toLowerCase() + mbiemri.getText().toLowerCase() + "'" +
                            ", fjalekalimi = '" + pw.getText() + "' where emri = '" + name + "'";
                }
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                Statement adminStmt = conn.createStatement();
                adminStmt.execute(adminSql);
                Statement punetStmt = conn.createStatement();
                punetStmt.execute(punetSql);
                conn.close();
                new Mesazhi("Sukses", em + " " + mb, "Te dhenat u ruajten me sukses.");
                stage.close();
            }
            else new Mesazhi("Info", "", "Fushat duhet te plotesohen para se te vazhdoni");
        }catch (SQLNonTransientConnectionException fu){}
        catch (NullPointerException npe){new Mesazhi("Info", "Gabim", "Fusha qmimi duhet te permbaj vetem numra.");}
        catch (Exception ex){ex.printStackTrace();}
    }

    private void rregullo(int id){
        try {
            if (!user.getText().isEmpty() && !pw.getText().isEmpty()) {
                String sql = "update Admin set emri = '" + user.getText() + "', fjalekalimi = '" + pw.getText() +
                        "' where id = " + id;
                System.out.println("ID: " + id);
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
                new Mesazhi("Sukses", "", "Te dhenat u ruajten me sukses. Ju lutem restartoni programin.");
                stage.close();
            }
            else new Mesazhi("Gabim", "", "Fushat duhet te plotesohen para se te vazhdoni");
        }catch (SQLNonTransientConnectionException fu){}
        catch (NullPointerException npe){new Mesazhi("Info", "Gabim", "Fusha qmimi duhet te permbaj vetem numra.");}
        catch (Exception ex){ex.printStackTrace();}
    }
}
