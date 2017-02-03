package sample;

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
import javafx.scene.paint.Color;
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
import java.util.HashMap;

/**
 * Created by urankajtazaj on 08/10/16.
 */


public class ShikoKonsumatoret {
    Stage stage = new Stage();
    TextField txtEmri = new TextField();
    TextField txtMbiemri = new TextField();
    TextField txtMakina = new TextField();
    TextField txtKomuna = new TextField();
    Button btnKerko = new Button("Kerko");
    Button btnPastro = new Button();
    Button btnDhenat = new Button();
    Button btnRaport = new Button();
    Button btnRregullo = new Button();
    Label lblGabimi = new Label();
    TableView table = new TableView();
    CheckBox cb = new CheckBox("Shfaq te gjitha punet");
    int id;
    String komuna, makina, per;

    String emri = "";
    String mbiemri = "";

    private static final String CON_STR = "jdbc:h2:file:~/db/auto;AUTO_SERVER=TRUE";
    String raporti = "/raportiKonsumatoret.jrxml";

    public ShikoKonsumatoret(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
            raporti = br.readLine() + raporti;
            br.close();
        }catch (Exception ex){ex.printStackTrace();}

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        txtEmri.setPromptText("Emri");
        txtMbiemri.setPromptText("Mbiemri");
        txtKomuna.setPromptText("Komuna");
        txtMakina.setPromptText("Makina");
        stage.setTitle("Lista e konsumatoreve");
        btnRregullo.setGraphic(new ImageView(new Image("/sample/foto/settings.png")));
        btnPastro.setGraphic(new ImageView(new Image("/sample/foto/unchecked.png")));
        btnDhenat.setGraphic(new ImageView(new Image("/sample/foto/view.png")));
        btnRregullo.setTooltip(new Tooltip("Rregullo detajet personale"));
        btnDhenat.setTooltip(new Tooltip("Shiko te dhenat"));
        btnPastro.setTooltip(new Tooltip("Fshi nga lista e konsumatoreve"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
//        btnKerko.setGraphic(new ImageView(new Image("/sample/foto/search.png")));
        GridPane root = new GridPane();
        HBox pyet = new HBox(5);
        pyet.getChildren().addAll(txtEmri, txtMbiemri, txtMakina, txtKomuna, btnKerko);
        pyet.setAlignment(Pos.CENTER_RIGHT);

        TableColumn colId = new TableColumn("ID");
        TableColumn colEmri = new TableColumn("Emri");
        TableColumn colMbiemri = new TableColumn("Mbiemri");
        TableColumn colMakina = new TableColumn("Makina");
        TableColumn colKomuna = new TableColumn("Komuna");
        TableColumn colPershkrimi = new TableColumn("Pershkrimi");

        colId.setMaxWidth(40);
        colId.setMinWidth(40);
        colPershkrimi.setMinWidth(120);

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colEmri.setCellValueFactory(new PropertyValueFactory("emri"));
        colMbiemri.setCellValueFactory(new PropertyValueFactory("mbiemri"));
        colMakina.setCellValueFactory(new PropertyValueFactory("makina"));
        colKomuna.setCellValueFactory(new PropertyValueFactory("komuna"));
        colPershkrimi.setCellValueFactory(new PropertyValueFactory("pershkrimi"));

        table.getColumns().addAll(colId, colEmri, colMbiemri, colMakina, colKomuna, colPershkrimi);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        mbushTabelen();

        table.setPlaceholder(new Label("Nuk ka te dhena"));
        table.setMinWidth(810);

        table.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
            try {
                id = ((TabelaKonsumatori) newVal).getId();
                emri = ((TabelaKonsumatori) newVal).getEmri();
                mbiemri = ((TabelaKonsumatori) newVal).getMbiemri();
                makina = ((TabelaKonsumatori) newVal).getMakina();
                komuna = ((TabelaKonsumatori) newVal).getKomuna();
                per = ((TabelaKonsumatori) newVal).getPershkrimi();
            }catch (NullPointerException n){}
        });

        btnRaport.setGraphic(new ImageView(new Image("/sample/foto/raport24.png")));
        btnRaport.setTooltip(new Tooltip("Krijo raport"));

        HBox posht = new HBox(5);
        posht.setAlignment(Pos.CENTER_RIGHT);
        posht.getChildren().addAll(lblGabimi, btnRaport, btnDhenat, btnRregullo, btnPastro);

        raporti();

        root.add(pyet, 0, 0);
        root.add(table, 0, 1);
        root.add(posht, 0, 2);
        root.setVgap(5);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        btnRregullo.setOnAction(e -> {
            if (id > 0 && !emri.isEmpty()){
                new ShtoKonsumator(id, emri, mbiemri, makina, komuna, per, this);
            }else {
                lblGabimi.setTextFill(Color.FIREBRICK);
                lblGabimi.setText("Se pari zgjedhni konsumatorin");
            }
        });

        btnKerko.setOnAction(e -> filtro());
        txtEmri.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) filtro();});
        txtMbiemri.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) filtro();});
        txtMakina.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) filtro();});
        txtKomuna.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) filtro();});

        btnPastro.setOnAction(e -> {
            if(id > 0)
                new Mesazhi("Informacion", emri + " " + mbiemri, "A jeni te sigurte qe deshironi ta fshini kete person?", this, id);
            else new Mesazhi("Gabim", "Gabim", "Se pari zgjedhni nga lista me larte.");
        });
        btnDhenat.setOnAction(e -> {
            if (!emri.equals("") && !mbiemri.equals("")){
                new TeDhenat(emri, mbiemri);
                lblGabimi.setText("");
            }else {
                lblGabimi.setTextFill(Color.FIREBRICK);
                lblGabimi.setText("Se pari zgjedhni konsumatorin");
            }
        });

        Scene scene = new Scene(root, 820, 500);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

    private void raporti(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                    JasperReport jreport = JasperCompileManager.compileReport(raporti);
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, new HashedMap(), conn);
                    JasperViewer.viewReport(jprint, false);
                    conn.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        btnRaport.setOnAction(e -> t.start());
    }

    public void mbushTabelen(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from Konsumatori");
                    ObservableList<TabelaKonsumatori> data = FXCollections.observableArrayList();
                    while (rs.next()){
                        data.add(new TabelaKonsumatori(rs.getInt("id"), rs.getString("emri"), rs.getString("mbiemri"),
                                rs.getString("makina"), rs.getString("komuna"), rs.getString("pershkrimi")));
                    }
                    table.setItems(data);
                    conn.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        t.start();
    }

    private void filtro(){
        try {

            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            StringBuilder oldSql = new StringBuilder("select * from Konsumatori where");
            StringBuilder newSql = new StringBuilder("select * from Konsumatori");

            if(!txtEmri.getText().isEmpty() || !txtMbiemri.getText().isEmpty() || !txtMakina.getText().isEmpty() ||
                    !txtKomuna.getText().isEmpty()) {

                if (!txtEmri.getText().isEmpty())
                    oldSql.append(" lower(emri) like lower('%" + txtEmri.getText() + "%') and");
                if (!txtMbiemri.getText().isEmpty())
                    oldSql.append(" lower(mbiemri) like lower('%" + txtMbiemri.getText() + "%') and");
                if (!txtKomuna.getText().isEmpty())
                    oldSql.append(" lower(komuna) like lower('%" + txtKomuna.getText() + "%') and");
                if (!txtMakina.getText().isEmpty())
                    oldSql.append(" lower(makina) like lower('%" + txtMakina.getText() + "%') and");
            }

            if (oldSql.substring(oldSql.length()-3).equals("and")) {
                StringBuilder ns = new StringBuilder(oldSql.substring(0, oldSql.length()-3));
                newSql = ns;
            }
            else if (oldSql.substring(oldSql.length()-5, oldSql.length()).equals("where")){
                StringBuilder ns = new StringBuilder(oldSql.substring(0, oldSql.length()-5));
                newSql = ns;
            }

            ResultSet rs = stmt.executeQuery(newSql.toString());
            ObservableList<TabelaKonsumatori> data1 = FXCollections.observableArrayList();
            while (rs.next())
                data1.add(new TabelaKonsumatori(rs.getInt("id"), rs.getString("emri"), rs.getString("mbiemri"),
                        rs.getString("makina"), rs.getString("komuna"), rs.getString("pershkrimi")));

            table.getItems().clear();
            table.setItems(data1);
            conn.close();

        }catch (Exception ex){ex.printStackTrace();}
    }

    public void fshi(int i){
        try {
            if (id > 0){
                String sql = "delete from Konsumatori where id = " + i;
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                stmt.close();
                conn.close();
                table.getItems().remove(table.getSelectionModel().getSelectedIndex());
                lblGabimi.setTextFill(Color.DARKGREEN);
                lblGabimi.setText("Konsumatori u fshi me sukses");
                id = 0;
            }else new Mesazhi("Gabim", "Gabim", "Se pari zgjedhni nga lista me larte.");
//            table.getSelectionModel().clearSelection();
        }catch (Exception ex){ex.printStackTrace();}
    }
}
