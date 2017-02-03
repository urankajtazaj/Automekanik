package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.String;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by urankajtazaj on 10/10/16.
 */
public class TeDhenat {
    Stage stage = new Stage();
    TableView table = new TableView();
    Text e = new Text();
    TextField txtPuna = new TextField();
    Button kerko = new Button("Kerko");
    Button btnRaporti  = new Button("Krijo raport");
    Button btnNdryshpo = new Button("Statusi");
    int id = -1;
    boolean kryer = false;

    private static final String CON_STR = "jdbc:h2:file:~/db/auto;AUTO_SERVER=TRUE;MVCC=TRUE;LOCK_TIMEOUT=10000";

    String raporti2 = "/report2.jrxml";
    String folderi = "";

    public TeDhenat(String emri, String mbiemri){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
            raporti2 = br.readLine() + raporti2;
            folderi = br.readLine();
            br.close();
        }catch (Exception ex){ex.printStackTrace();}

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        stage.setTitle("Titulli");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        HBox hb2 = new HBox(5);
        hb2.getChildren().addAll(new Label("Puna e kryer"), txtPuna, kerko);
        hb2.setAlignment(Pos.CENTER_RIGHT);
        e.setText(emri + " " + mbiemri);
        e.setFont(Font.font(16));

        raporti(emri + " " + mbiemri);
        TableColumn colId = new TableColumn("ID");
        TableColumn colPuna = new TableColumn("Puna e kryer");
        TableColumn colQmimi = new TableColumn("Qmimi");
        TableColumn colPer = new TableColumn("Pershkrimi i punes");
        TableColumn colData = new TableColumn("Data");
        TableColumn colKryer = new TableColumn("Statusi");
        TableColumn colMakina = new TableColumn("Makina");

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setMinWidth(40);
        colId.setMaxWidth(40);
        colPuna.setMinWidth(120);
        colMakina.setCellValueFactory(new PropertyValueFactory("makina"));
        colPuna.setCellValueFactory(new PropertyValueFactory("lloji"));
        colQmimi.setCellValueFactory(new PropertyValueFactory("qmimi"));
        colPer.setCellValueFactory(new PropertyValueFactory("pershkrimi"));
        colPer.setMinWidth(120);
        colData.setCellValueFactory(new PropertyValueFactory("data"));
        colKryer.setCellValueFactory(new PropertyValueFactory("ngjyra"));
        colKryer.setMaxWidth(65);
        colKryer.setMinWidth(60);
        colKryer.setStyle("-fx-alignment: center center");
        colKryer.setCellFactory(e -> {
            return new TableCell<punetTbl, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty){
//                        setStyle("-fx-background-color: red;");
                    }else {
                        if (item.equals("po")){
                            setGraphic(new ImageView(new Image("/sample/foto/green.png")));
                        }else setGraphic(new ImageView(new Image("/sample/foto/red.png")));
                    }
                }
            };
        });

        table.getColumns().addAll(colId, colPuna, colMakina, colQmimi, colData, colPer, colKryer);
        table.setPlaceholder(new Label("Nuk ka te dhena"));
        table.setMinWidth(780);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null){
                id = ((punetTbl) nv).getId();
                if (((punetTbl) nv).getNgjyra().equals("po"))
                    kryer = true;
                else kryer = false;
            }else id = -1;
        });

        btnNdryshpo.setOnAction(e -> {
            if (id > -1){
                new RregulloPunen(id, kryer, this);
            }else {
                new Mesazhi("Gabim", "", "Duhet te zgjedhni njeren prej puneve nga tabela");
            }
        });

        kerko.setOnAction(e -> filtro());
        txtPuna.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)){
                filtro();
                id = 0;
            }
        });

        HBox hbtn = new HBox(5);
        hbtn.getChildren().addAll(btnRaporti, btnNdryshpo);
        btnRaporti.setGraphic(new ImageView(new Image("/sample/foto/report.png")));

        mbush();
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.add(e, 0, 0);
        root.add(hb2, 1, 0);
        root.add(table, 0, 1, 2, 1);
        root.add(hbtn, 0, 2);
        Scene scene = new Scene(root, 790, 490);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    public void mbush(){
        try {
            String sql = "select * from Punet where konsumatori = '" + e.getText() + "' order by id asc";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ObservableList<punetTbl> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            String s = "";
            while (rs.next()){
                s = format.format(rs.getDate("data"));
                data.add(new punetTbl(rs.getInt("id"), rs.getString("lloji"),
                        s, rs.getFloat("qmimi"), rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina")));
            }
            table.getItems().clear();
            table.setItems(data);
            conn.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void raporti(String emri){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                    HashMap hm = new HashMap();
                    hm.put("Folderi", folderi);
                    hm.put("konsumatori", emri);
                    JasperReport jreport = JasperCompileManager.compileReport(raporti2);
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                    JasperViewer.viewReport(jprint, false);
                    conn.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        btnRaporti.setOnAction(e -> {
            t.start();
        });
    }

    private void filtro(){
        try {
            String sql;
            if (!txtPuna.getText().isEmpty())
                sql = "select * from Punet where konsumatori = '" + e.getText() + "' and lower(lloji) like lower('%" + txtPuna.getText() + "%')";
            else
                sql = "select * from Punet where konsumatori = '" + e.getText() + "'";

            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ObservableList<punetTbl> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            String s = "";
            while (rs.next()){
                s = format.format(rs.getDate("data"));
                data.add(new punetTbl(rs.getInt("id"), rs.getString("lloji"),
                        s, rs.getFloat("qmimi"), rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina")));
            }
            table.setItems(data);
            conn.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}