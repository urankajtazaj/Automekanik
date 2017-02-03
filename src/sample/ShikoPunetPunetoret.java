package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by urankajtazaj on 13/10/16.
 */
public class ShikoPunetPunetoret {
    Stage stage = new Stage();
    TableView<TabelaTeDhenatPunetore> table = new TableView<>();
    TextField txtPuna = new TextField();
    TextField txtKons = new TextField();
    Label lblEmri = new Label();
    Button btnRaporti = new Button("Krijo raport");
    Button btnStatusi = new Button("Statusi");

    int id = -1;
    boolean kryer;

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    String raportiPunetor = "/RaportiPunetor.jrxml";
    String folderi;

    public ShikoPunetPunetoret(String emri, String mbiemri){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
                    raportiPunetor = br.readLine() + raportiPunetor;
                    folderi = br.readLine();
                    br.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        t.start();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Te dhenat");
        GridPane root = new GridPane();
        HBox pyet = new HBox(5);
        txtPuna.setPromptText("Puna e kryer");
        txtKons.setPromptText("Konsumatori");
        Button btnKerko = new Button("Kerko");
        pyet.getChildren().addAll(txtPuna, txtKons, btnKerko);
        pyet.setAlignment(Pos.CENTER_RIGHT);
        lblEmri.setText(emri.toLowerCase() + mbiemri.toLowerCase());

        krijoRaport(emri.toLowerCase() + mbiemri.toLowerCase());

        TableColumn colId = new TableColumn("ID");
        TableColumn colPuna = new TableColumn("Puna e kryer");
        TableColumn colKons = new TableColumn("Konsumatori");
        TableColumn colQmimi = new TableColumn("Pagesa");
        TableColumn colData = new TableColumn("Data");
        TableColumn colStatusi = new TableColumn("Statusi");
        TableColumn colMakina = new TableColumn("Makina");

        colId.setMaxWidth(50);
        colId.setMinWidth(50);
        colPuna.setMinWidth(220);
        colPuna.setMaxWidth(240);
        colQmimi.setMaxWidth(60);
        colQmimi.setMinWidth(60);

        colMakina.setCellValueFactory(new PropertyValueFactory("makina"));
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colPuna.setCellValueFactory(new PropertyValueFactory("puna"));
        colKons.setCellValueFactory(new PropertyValueFactory("konsumatori"));
        colQmimi.setCellValueFactory(new PropertyValueFactory("qmimi"));
        colData.setCellValueFactory(new PropertyValueFactory("data"));
        colStatusi.setCellValueFactory(new PropertyValueFactory("kryer"));
        colStatusi.setMaxWidth(65);
        colStatusi.setMinWidth(60);
        colStatusi.setStyle("-fx-alignment: center center");
        colStatusi.setCellFactory(e -> {
            return new TableCell<punetTbl, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty){
                    }else {
                        if (item.equals("po")){
                            setGraphic(new ImageView(new Image("/sample/foto/green.png")));
                        }else setGraphic(new ImageView(new Image("/sample/foto/red.png")));
                    }
                }
            };
        });

        HBox hb = new HBox(5);
        hb.getChildren().addAll(btnRaporti, btnStatusi);

        btnRaporti.setGraphic(new ImageView(new Image("/sample/foto/report.png")));

        table.getColumns().addAll(colId, colPuna, colKons, colMakina, colQmimi, colData, colStatusi);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Nuk ka te dhena"));
        mbush(emri.toLowerCase() + mbiemri.toLowerCase());

        table.setMinWidth(770);

        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null){
                id = nv.getId();
                if (nv.getKryer().equals("po")) kryer = true;
                else kryer = false;
            }else id = -1;
        });

        btnStatusi.setOnAction(e -> {
            if (id > -1)
                new RregulloPunen(id, this, kryer);
        });

        root.add(pyet, 0, 0);
        root.add(table, 0, 1);
        root.add(hb, 0, 2);

        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        btnKerko.setOnAction(e -> filtroTabelen(emri.toLowerCase() + mbiemri.toLowerCase()));
        txtKons.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER))filtroTabelen(emri.toLowerCase() + mbiemri.toLowerCase());});
        txtPuna.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER))filtroTabelen(emri.toLowerCase() + mbiemri.toLowerCase());});

        Scene scene = new Scene(root, 780, 475);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    public void mbush(String emri){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sql = "select * from Punet where punetori = '" + emri + "' order by id asc";
                    Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    ObservableList<TabelaTeDhenatPunetore> data = FXCollections.observableArrayList();
                    Format format = new SimpleDateFormat("dd/MM/yyyy");
                    String s = "";
                    while (rs.next()) {
                        s = format.format(rs.getDate("data"));
                        data.add(new TabelaTeDhenatPunetore(rs.getInt("id"), rs.getString("lloji"), s,
                                rs.getFloat("qmimi"), rs.getString("konsumatori"), rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina")));
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                    table.setItems(data);
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        t.start();
    }

    public void mbushNgaStatusi(String emri){
        try {
            String sql = "select * from Punet where punetori = '" + emri + "' order by id asc";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<TabelaTeDhenatPunetore> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            String s = "";
            while (rs.next()) {
                s = format.format(rs.getDate("data"));
                data.add(new TabelaTeDhenatPunetore(rs.getInt("id"), rs.getString("lloji"), s,
                        rs.getFloat("qmimi"), rs.getString("konsumatori"), rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina")));
            }

            rs.close();
            stmt.close();
            conn.close();
            table.setItems(data);
        }catch (Exception ex){ex.printStackTrace();}
    }

    private void krijoRaport(String pnt){
        btnRaporti.setOnAction(e -> {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        HashMap hm = new HashMap();
                        hm.put("Folderi", folderi);
                        hm.put("punetori", pnt);
                        JasperReport jreport = JasperCompileManager.compileReport(raportiPunetor);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    }catch (Exception ex){ex.printStackTrace();}
                }
            });
            t.start();
        });
    }

    private void filtroTabelen(String pnt){
        try {
            StringBuilder sql = new StringBuilder("select * from Punet where");
            StringBuilder newSql = new StringBuilder("select * from Punet");

            if (!txtPuna.getText().equals(""))
                sql.append(" lower(lloji) like lower('%" + txtPuna.getText() + "%') and");
            if (!txtKons.getText().equals(""))
                sql.append(" lower(konsumatori) like ('%" + txtKons.getText() + "%') and");

            if (sql.substring(sql.length()-3, sql.length()).equals("and")){
                StringBuilder ns = new StringBuilder(sql.substring(0, sql.length()-3) + "and punetori = '" + pnt + "' order by id asc");
                newSql = ns;
            }else if (sql.substring(sql.length()-5, sql.length()).equals("where")){
                StringBuilder ns = new StringBuilder(sql.substring(0, sql.length()-5) + "and punetori = '" + pnt + "' order by id asc");
                newSql = ns;
                if(sql.substring(14, sql.length()-6).equals("Punet")){
                    StringBuilder nss = new StringBuilder(sql.substring(0, sql.length()-6) + " where punetori = '" + pnt + "' order by id asc");
                    newSql = nss;
                }
            }

            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(newSql.toString());
            ObservableList<TabelaTeDhenatPunetore> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            String s = "";
            while (rs.next()) {
                s = format.format(rs.getDate("data"));
                data.add(new TabelaTeDhenatPunetore(rs.getInt("id"), rs.getString("lloji"), s,
                        rs.getFloat("qmimi"), rs.getString("konsumatori"), rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina")));
            }

            rs.close();
            stmt.close();
            conn.close();
            table.setItems(data);
        }catch (Exception ex){ex.printStackTrace();}
    }
}
