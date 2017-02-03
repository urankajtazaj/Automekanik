package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

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
 * Created by urankajtazaj on 11/10/16.
 */

public class ShikoPunetoret {
    Stage stage = new Stage();
    TextField emri = new TextField();
    TextField mbiemri = new TextField();
    Button btnKerko = new Button("Kerko");
    Button btnShiko = new Button();
    Button btnFshi = new Button();
    Button btnRaport = new Button();
    Button btnRregullo = new Button();
    Label lbl = new Label();
    TableView<TabelaPunetoret> table = new TableView<>();

    //THREAD
    Thread mbushPntThread;
    Thread rregulloThread;
    Thread filtroThread;

    String strEmri="", strMbiemri="";
    String km="", pz="";
    float pg = 0.0f;
    int id=0;

    String raporti = "/ListaPunetoret.jrxml";

    private static final String CON_STR = "jdbc:h2:file:~/db/auto";

    public ShikoPunetoret(DritarjaKryesore dk){
        if (dk.lbl_Id.getText().equals("0")) {
            btnFshi.setDisable(true);
            btnRregullo.setDisable(true);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
            raporti = br.readLine() + raporti;
            br.close();
        }catch (Exception ex){ex.printStackTrace();}

//        System.out.println(dk.lbl_Id.getText());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        stage.setTitle("Punetoret");
        stage.setResizable(false);
        btnShiko.setGraphic(new ImageView(new Image("/sample/foto/view.png")));
        btnFshi.setGraphic(new ImageView(new Image("/sample/foto/unchecked.png")));
        btnRregullo.setGraphic(new ImageView(new Image("/sample/foto/settings.png")));
        btnRregullo.setTooltip(new Tooltip("Rregullo detajet personale"));
        btnFshi.setTooltip(new Tooltip("Fshi nga lista e punetoreve"));
        btnShiko.setTooltip(new Tooltip("Shiko te dhenat"));
        stage.initModality(Modality.APPLICATION_MODAL);
        emri.setPromptText("Emri");
        mbiemri.setPromptText("Mbiemri");
        btnKerko.setGraphic(new ImageView(new Image("/sample/foto/search.png")));
        GridPane grid = new GridPane();
        HBox pyet = new HBox(10);
        HBox bottom = new HBox(5);
        if (dk.lbl_Id.getText().equals("1"))
            bottom.getChildren().addAll(lbl, btnRaport, btnShiko, btnRregullo, btnFshi);
        else bottom.getChildren().addAll(lbl, btnShiko);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        pyet.setAlignment(Pos.CENTER_RIGHT);
        pyet.getChildren().addAll(emri, mbiemri, btnKerko);

        btnRaport.setGraphic(new ImageView(new Image("/sample/foto/raport24.png")));
        btnRaport.setTooltip(new Tooltip("Krijo raport"));

        TableColumn colId = new TableColumn("ID");
        TableColumn colEmri = new TableColumn("Emri");
        TableColumn colMbiemri = new TableColumn("Mbiemri");
        TableColumn colPaga = new TableColumn("Paga");
        TableColumn colPozita = new TableColumn("Pozita");
        TableColumn colData = new TableColumn("Regjistrimi");
        TableColumn colKomuna = new TableColumn("Komuna");

        colId.setMinWidth(40);
        colId.setMaxWidth(40);

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colMbiemri.setCellValueFactory(new PropertyValueFactory("mbiemri"));
        colEmri.setCellValueFactory(new PropertyValueFactory("emri"));
        colPozita.setCellValueFactory(new PropertyValueFactory("pozita"));
        colData.setCellValueFactory(new PropertyValueFactory("data"));
        colPaga.setCellValueFactory(new PropertyValueFactory("paga"));
        colKomuna.setCellValueFactory(new PropertyValueFactory("komuna"));

        table.getColumns().addAll(colId, colEmri, colMbiemri, colPozita, colKomuna, colData, colPaga);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMinWidth(860);

        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            try {
                if (nv != null){
                        id = nv.getId();
                        strEmri = nv.getEmri();
                        strMbiemri = nv.getMbiemri();
                        km = nv.getKomuna();
                        pg = nv.getPaga();
                        pz = nv.getPozita();
                }else id = -1;
            }catch (NullPointerException ex){}
        });

        //THREAD-at
        mbushPntThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mbushPunetoret();
            }
        });
        mbushPntThread.start();

        filtroThread = new Thread(new Runnable() {
            @Override
            public void run() {
                filtroTabelen();
            }
        });

        //*************************

        btnFshi.setOnAction(e -> {
            if (id > 0){
                new Mesazhi("Informacion", strEmri + " " + strMbiemri, "A jeni te sigurte qe deshironi ta fshini?", this, dk);
            }
            else lbl.setText("Asnje i zgjedhur");
        });

        btnShiko.setOnAction(e -> {
            if (!strEmri.equals("")){
                new ShikoPunetPunetoret(strEmri, strMbiemri);
                lbl.setText("");
            }
            else lbl.setText("Se pari duhet te zgjedhni njerin punetore");
        });

        btnRregullo.setOnAction(e -> {
            if (!strEmri.isEmpty() && id > -1){
                new ShtoPunetor(id, strEmri, strMbiemri, km, pz, pg, ShikoPunetoret.this);
                filtroThread.start();
            }
        });

        btnRaport.setOnAction(e -> raporti());

        btnKerko.setOnAction(e -> filtroThread.start());

        emri.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)) filtroTabelen();});
        mbiemri.setOnKeyPressed(e -> {if (e.getCode().equals(KeyCode.ENTER)) filtroTabelen();});

        grid.add(pyet, 0, 0);
        grid.add(table, 0, 1);
        grid.add(bottom, 0, 2);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid, 870, 500);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    public void mbushPunetoret(){
        try {
            String sql = "select * from Punetoret";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<TabelaPunetoret> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()){
                String s = format.format(rs.getDate("regjistrimi"));
                data.add(new TabelaPunetoret(rs.getInt("id"), rs.getString("emri"),
                        rs.getString("mbiemri"), rs.getString("komuna"), rs.getString("pozita"),
                        s, rs.getFloat("paga")));
            }

            table.setItems(data);
            conn.close();
        }catch (Exception ex){ex.printStackTrace();}
    }

    public void filtroTabelen(){
        try {

            StringBuilder sql = new StringBuilder("select * from Punetoret where");
            StringBuilder newSql = new StringBuilder("select * from Punetoret");
            if (!emri.getText().equals(""))
                sql.append(" lower(emri) like lower('%" + emri.getText() + "%') and");
            if (!mbiemri.getText().equals(""))
                sql.append(" lower(mbiemri) like lower('%" + mbiemri.getText() + "%') and");

            if (sql.substring(sql.length()-3, sql.length()).equals("and")){
                StringBuilder ns = new StringBuilder(sql.substring(0, sql.length()-3));
                newSql = ns;
            }else if (sql.substring(sql.length()-5, sql.length()).equals("where")){
                StringBuilder ns = new StringBuilder(sql.substring(0, sql.length()-5));
                newSql = ns;
            }

            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(newSql.toString());
            ObservableList<TabelaPunetoret> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()){
                String s = format.format(rs.getDate("regjistrimi"));
                data.add(new TabelaPunetoret(rs.getInt("id"), rs.getString("emri"),
                        rs.getString("mbiemri"), rs.getString("komuna"), rs.getString("pozita"),
                        s, rs.getFloat("paga")));
            }
            table.getItems().clear();
            table.setItems(data);
            stmt.close();
            rs.close();
            conn.close();
        }catch (NullPointerException npe){}
        catch (Exception ex){}
    }

    private void raporti(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                    JasperReport jreport = JasperCompileManager.compileReport(raporti);
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, new HashMap(), conn);
                    JasperViewer.viewReport(jprint, false);
                    conn.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        }).start();
    }

    public void fshi(String emri){
        try {
            if (id > 0){
                String sql = "delete from Punetoret where id = " + id;
                String sql2 = "delete from Admin where emri = '" + emri + "'";
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                stmt.execute(sql2);
                lbl.setText(strEmri + " " + strMbiemri + " u fshi me sukses");
                conn.close();
                filtroTabelen();
            }else lbl.setText("Ju lutem zgjedhni njerin");
        }catch (Exception ex){ex.printStackTrace();}
    }
}
