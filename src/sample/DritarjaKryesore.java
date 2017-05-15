/**
 * Created by urankajtazaj on 05/10/16.
 */
package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DritarjaKryesore {

    private static final String CON_STR = "jdbc:h2:file:~/db/auto;AUTO_SERVER=TRUE";

    Label lblQmimi4 = new Label();
    Label lblQmimi3 = new Label();

    ScrollPane sp = new ScrollPane();
    HashMap<String, Double> viti = new HashMap();
    String tani, kaluar;

    Label lbl_Id = new Label();
    Label log_user = new Label();
    Label log_pw = new Label();

    Stage stage = new Stage();
    GridPane grid_fin = new GridPane();
    BorderPane root;
    TextField txtLloji = new TextField();
    TextField txtTipi = new TextField();
    TextField txtPaisja = new TextField();
    TextField txtPjesa = new TextField();
    TextField txtQmimi = new TextField();
    TextField txtSasia = new TextField();
    TextArea txtInfo = new TextArea();
    Button btnKerko = new Button("Search");
    Button btnPastro = new Button("Clean");
    //    Button btnShto = new Button("Shto");
    Button btnFshi = new Button("Delete");
    Button btnPlus = new Button("");
    Button btnMinus = new Button("");
    Button btnRregullo = new Button("Update");
    Button btnRifresko = new Button("Refresh");
    TableView table = new TableView();
    TableView<TabelaPunet> tblPunet = new TableView<>();
    ObservableList<mbushTabelen> data;
    int id = 0;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();

    ScrollPane sc = new ScrollPane();

    int emriKons = -1;

    //    ComboBox<String> cbLlojiTabeles = new ComboBox<>();
    TextField qCbProdhuesi = new TextField();
    ComboBox<String> qCbOp = new ComboBox<>();
    TextField qTxtPaisja = new TextField();
    TextField qTxtPjesa = new TextField();
    TextField qTxtTipi = new TextField();
    TextField qTxtQmimi = new TextField();
    Label err = new Label("");
    Label lblTeHyrat = new Label("Parts data");
    Label lblShpenzimet = new Label("Jobs");
    Label lblId = new Label();
    boolean show = true, show2 = true;
    TextField s_cbPuna = new TextField();
    DatePicker s_data = new DatePicker();
    TextField s_txtQmimi = new TextField();
    TextField s_txtSasia = new TextField();
    TextField s_txtPaisja = new TextField();
    TextField s_cbEmriKons = new TextField();
    TextArea txtPer = new TextArea();
    Button btnBlej = new Button("Save");
    Button s_btnPastro = new Button("Clean");
    TextField txtPunetori = new TextField();
    TableColumn<TabelaPunet, Float> colPagesa = new TableColumn("Wage");
    VBox left_root = new VBox();

    CheckBox punaKryer = new CheckBox("Job has finished");
    ComboBox<String> cbPaisjet = new ComboBox<>();
    ComboBox<String> cbProdhuesi = new ComboBox<>();
    TextField txtMakina = new TextField();

    String raportiSot = "/RaportiSot.jrxml";
    String raportiTotal = "/RaportiTotal.jrxml";
    String raportiFatura = "/Fatura.jrxml";
    String raportiMujor = "/RaportiMuajiAktual.jrxml";
    String strRaportiViti = "/RaportiVitiAktual.jrxml";
    String raportiPaisjet = "/RaportiPaisjet.jrxml";
    String folderi = "";

    boolean kryer = false;

    public DritarjaKryesore(int log_id_r, int log_id, String username, String password) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/db/raportet.txt")));
            String temp = br.readLine();
            raportiSot = temp + raportiSot;
            raportiFatura = temp + raportiFatura;
            raportiMujor = temp + raportiMujor;
            raportiTotal = temp + raportiTotal;
            strRaportiViti = temp + strRaportiViti;
            raportiPaisjet = temp + raportiPaisjet;
            folderi = temp;
            temp = null;
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/foto/icon.png")));
        lbl_Id.setText(log_id + "");
        log_user.setText(username);
        log_pw.setText(password);
        lblId.setText(log_id_r + "");
        btnFshi.getStyleClass().add("del");
        btnBlej.getStyleClass().add("add-btn");
        txtPunetori.setEditable(false);
        btnPlus.setMinWidth(30);
        btnMinus.setMinWidth(30);
        btnKerko.setMinWidth(80);
        btnFshi.setMinWidth(56);
        btnRregullo.setMinWidth(55);
        btnPastro.setMinWidth(55);
        btnMinus.setGraphic(new ImageView(new Image("/sample/foto/minus.png")));
        btnPlus.setGraphic(new ImageView(new Image("/sample/foto/add.png")));
//        btnRifresko.setGraphic(new ImageView(new Image("/sample/foto/refresh.png")));
//        btnKerko.setGraphic(new ImageView(new Image("/sample/foto/search.png")));
        btnKerko.getStyleClass().add("btnV");
        btnRifresko.getStyleClass().add("btnV");

        HBox rectLay = new HBox(10);
        rectLay.setPadding(new Insets(15, 0, 15, 0));
        rectLay.setAlignment(Pos.CENTER);

        StackPane stack1 = new StackPane();
        StackPane stack2 = new StackPane();
        StackPane stack3 = new StackPane();
        StackPane stack4 = new StackPane();

        VBox rect1_v = new VBox(5);
        HBox btn_rect1 = new HBox();
        VBox rect2_v = new VBox(5);
        HBox btn_rect2 = new HBox();
        VBox rect3_v = new VBox(5);
        HBox btn_rect3 = new HBox();
        VBox rect4_v = new VBox(5);
        HBox btn_rect4 = new HBox();

        Button btnA1 = new Button();
        Button btnA2 = new Button();
        Button btnA3 = new Button();
        Button btnV1 = new Button();
        Button btnV2 = new Button();
        Button btnV3 = new Button();
        btnA1.getStyleClass().add("trans");
        btnA2.getStyleClass().add("trans");
        btnV1.getStyleClass().add("trans");
        btnV3.getStyleClass().add("trans");
        btnV2.getStyleClass().add("trans");
        btnA3.getStyleClass().add("trans");

        btnA1.setCursor(Cursor.HAND);
        btnA2.setCursor(Cursor.HAND);
        btnA3.setCursor(Cursor.HAND);
        btnV1.setCursor(Cursor.HAND);
        btnV2.setCursor(Cursor.HAND);
        btnV3.setCursor(Cursor.HAND);

        btnA1.setGraphic(new ImageView(new Image("/sample/foto/add_24.png")));
        btnA2.setGraphic(new ImageView(new Image("/sample/foto/add_24.png")));
        btnA3.setGraphic(new ImageView(new Image("/sample/foto/add_24.png")));
        btnV1.setGraphic(new ImageView(new Image("/sample/foto/view.png")));
        btnV2.setGraphic(new ImageView(new Image("/sample/foto/view.png")));
        btnV3.setGraphic(new ImageView(new Image("/sample/foto/view.png")));

        btnA1.setTooltip(new Tooltip("Add consumer"));
        btnV1.setTooltip(new Tooltip("View consumers"));
        btnA2.setTooltip(new Tooltip("Add part"));
        btnV2.setTooltip(new Tooltip("View finances"));
        btnA3.setTooltip(new Tooltip("Add employee"));
        btnV3.setTooltip(new Tooltip("View employees"));

        Text lblCons = new Text("Costumer");
        Text lblFin = new Text("Finance");
        Text lblPaisjet = new Text("Parts");
        Text lblPun = new Text("Employees");
        lblCons.setTextAlignment(TextAlignment.LEFT);

        btn_rect1.getChildren().addAll(btnA1, btnV1);
        btn_rect3.getChildren().add(btnA2);
        btn_rect2.getChildren().add(btnV2);
        if (lbl_Id.getText().equals("1"))
            btn_rect4.getChildren().addAll(btnA3, btnV3);
        else btn_rect4.getChildren().add(btnV3);
        btn_rect1.setPadding(new Insets(10, 0, 0, 0));
        btn_rect2.setPadding(new Insets(10, 0, 0, 0));
        btn_rect3.setPadding(new Insets(10, 0, 0, 0));
        btn_rect4.setPadding(new Insets(10, 0, 0, 0));

        btnA2.setOnAction(e -> new ShtoPjeset(this));

        rect1_v.getChildren().addAll(lblCons, new ImageView(new Image("/sample/foto/user.png")), btn_rect1);
        rect1_v.setAlignment(Pos.CENTER);
        btn_rect1.setAlignment(Pos.CENTER);
        rect1_v.setPadding(new Insets(5, 0, 0, 0));
        rect2_v.setPadding(new Insets(5, 0, 0, 0));

        rect3_v.getChildren().addAll(lblPaisjet, new ImageView(new Image("/sample/foto/car.png")), btn_rect3);
        rect3_v.setAlignment(Pos.CENTER);
        btn_rect3.setAlignment(Pos.CENTER);
        rect2_v.setPadding(new Insets(5, 0, 0, 0));

        rect2_v.getChildren().addAll(lblFin, new ImageView(new Image("/sample/foto/line.png")), btn_rect2);
        btn_rect2.setAlignment(Pos.CENTER);
        rect2_v.setAlignment(Pos.CENTER);

        rect4_v.getChildren().addAll(lblPun, new ImageView(new Image("/sample/foto/wrench.png")), btn_rect4);
        rect4_v.setAlignment(Pos.CENTER);
        btn_rect4.setAlignment(Pos.CENTER);
        rect4_v.setPadding(new Insets(5, 0, 0, 0));

        Rectangle rectKons = new Rectangle();
        Rectangle rectCal = new Rectangle();
        Rectangle rectFin = new Rectangle();
        Rectangle rectPun = new Rectangle();

        rectKons.setWidth(220);
        rectKons.setHeight(220);
        rectPun.setWidth(220);
        rectPun.setHeight(220);
        rectFin.setWidth(220);
        rectFin.setHeight(220);
        rectCal.setWidth(220);
        rectCal.setHeight(220);
        btnA1.setMinWidth(rectKons.getWidth() / 2);
        btnV1.setMinWidth(rectKons.getWidth() / 2);
        btnA2.setMinWidth(rectCal.getWidth());
        if (lbl_Id.getText().equals("1")) {
            btnV3.setMinWidth(rectCal.getWidth() / 2);
            btnA3.setMinWidth(rectPun.getWidth() / 2);
        } else btnV3.setMinWidth(rectCal.getWidth());
        btnV2.setMinWidth(rectCal.getWidth());

        stack1.getChildren().addAll(rectKons, rect1_v);
        stack2.getChildren().addAll(rectFin, rect2_v);
        stack3.getChildren().addAll(rectCal, rect3_v);
        stack4.getChildren().addAll(rectPun, rect4_v);

        Task financatTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        financat();
                    }
                });
                return null;
            }
        };

        Thread finT = new Thread(financatTask);
        finT.setDaemon(true);
        finT.start();

        btnA1.setOnAction(e -> new ShtoKonsumator());
        btnV1.setOnAction(e -> new ShikoKonsumatoret());
        btnA3.setOnAction(e -> new ShtoPunetor());
        btnV3.setOnAction(e -> new ShikoPunetoret(this));
        btnV2.setOnAction(e -> root.setCenter(sp));

        rectCal.getStyleClass().add("rect");
        rectFin.getStyleClass().add("rect");
        rectKons.getStyleClass().add("rect");
        rectPun.getStyleClass().add("rect");

        rectLay.getChildren().addAll(stack1, stack4, stack2, stack3);

        btnPastro.setOnAction(e -> {
            pastro();
        });

        btnRifresko.setMinWidth(85);
        qCbProdhuesi.setPromptText("Maker");
        txtLloji.setMaxWidth(210);
        txtSasia.setMaxWidth(120);
        txtSasia.setMinWidth(120);
        txtQmimi.setMaxWidth(210);
        txtPaisja.setMaxWidth(210);
        txtPjesa.setMaxWidth(210);
        txtTipi.setMaxWidth(210);
        cbPaisjet.setMinWidth(95);
        cbPaisjet.setMaxWidth(95);
        cbProdhuesi.setMinWidth(95);
        cbProdhuesi.setMaxWidth(95);
        root = new BorderPane();
        Scene scene = new Scene(root, 942, 600);
        HBox pyet = new HBox(10);
        VBox center = new VBox();
//        root.setPadding(new Insets(10));
        stage.setTitle("Main Window");
//        connectionToDB();
        leftSide(this);
        HBox ns = new HBox(5);
        Label qm = new Label("Price");
        qm.setMinWidth(45);
        qm.setAlignment(Pos.CENTER_RIGHT);
        qm.setPadding(new Insets(7, 0, 0, 0));
        ns.getChildren().addAll(qm, qCbOp, qTxtQmimi);

        qTxtPjesa.setPrefWidth(120);
        qTxtPaisja.setPrefWidth(120);
        qTxtQmimi.setPrefWidth(90);
        qTxtTipi.setPrefWidth(120);
        qCbProdhuesi.setPrefWidth(120);
        qTxtPaisja.setPromptText("Category");
        qTxtTipi.setPromptText("Car model");
        qTxtPjesa.setPromptText("Part");
        qCbProdhuesi.setPromptText("Maker");
        qTxtQmimi.setPromptText("Price");

        qCbOp.getItems().addAll("=", ">", "<");
        qCbOp.getSelectionModel().select(0);

        pyet.getChildren().addAll(qTxtPjesa, qTxtPaisja, qCbProdhuesi, qTxtTipi, ns, btnKerko, btnRifresko);

        pyet.setPadding(new Insets(5, 0, 5, 0));

        //Krijimi i kolonave per tabel - Select
        TableColumn colId = new TableColumn("Id");
        TableColumn colPjesa = new TableColumn("Part");
        TableColumn colPaisja = new TableColumn("Category");
        TableColumn colProdhuesi = new TableColumn("Model");
        TableColumn colTipi = new TableColumn("Maker");
        TableColumn colQmimi = new TableColumn("Price");
        TableColumn colSasia = new TableColumn("Quantity");
        TableColumn colShtese = new TableColumn("Info");

        //Gjeresia e kolonave
        colId.setMaxWidth(40);
        colId.setMinWidth(40);
        colPjesa.setMinWidth(150);
//        colPjesa.setMaxWidth(180);
        colSasia.setMaxWidth(50);
        colSasia.setMinWidth(50);
        colQmimi.setMaxWidth(60);
        colQmimi.setMinWidth(60);
        colProdhuesi.setMaxWidth(110);
        colProdhuesi.setMinWidth(100);
        colTipi.setMaxWidth(100);
        colTipi.setMinWidth(100);

        //Cilat te dhena me i fut ne secilen fush
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colPaisja.setCellValueFactory(new PropertyValueFactory("paisja"));
        colPjesa.setCellValueFactory(new PropertyValueFactory("pjesa"));
        colProdhuesi.setCellValueFactory(new PropertyValueFactory("lloji"));
        colQmimi.setCellValueFactory(new PropertyValueFactory("qmimi"));
        colSasia.setCellValueFactory(new PropertyValueFactory("sasia"));
        colTipi.setCellValueFactory(new PropertyValueFactory("tipi"));
        colShtese.setCellValueFactory(new PropertyValueFactory("info"));

        //***********************************

        TableColumn colStat_p = new TableColumn("");
        TableColumn colPunaKryer = new TableColumn("Job");
        TableColumn colData_p = new TableColumn("Date");
        TableColumn colPershkrimi = new TableColumn("Description");
        TableColumn colKonsumatori = new TableColumn("Costumer");
        TableColumn colMakina = new TableColumn("Car");

        colStat_p.setMinWidth(50);
        colStat_p.setMaxWidth(50);
        colPagesa.setMaxWidth(70);
        colPagesa.setMinWidth(70);
        colData_p.setMaxWidth(100);
        colData_p.setMinWidth(100);
        colKonsumatori.setMaxWidth(170);
        colKonsumatori.setMinWidth(150);
        colPunaKryer.setMinWidth(175);

        colStat_p.setCellValueFactory(new PropertyValueFactory("kryer"));
        colKonsumatori.setCellValueFactory(new PropertyValueFactory("konsumatori"));
        colPagesa.setCellValueFactory(new PropertyValueFactory("puna"));
        colPershkrimi.setCellValueFactory(new PropertyValueFactory("pershkrimi"));
        colPagesa.setCellValueFactory(new PropertyValueFactory("qmimi"));
        colPunaKryer.setCellValueFactory(new PropertyValueFactory("puna"));
        colData_p.setCellValueFactory(new PropertyValueFactory("data"));
        colMakina.setCellValueFactory(new PropertyValueFactory("makina"));

        colStat_p.setStyle("-fx-alignment: center center;");
        colStat_p.setCellFactory(e -> {
            return new TableCell<TabelaPunet, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null || !empty) {
                        if (item.equals("po")) {
                            setGraphic(new ImageView(new Image("/sample/foto/green.png")));
                        } else if (item.equals("jo")) {
                            setGraphic(new ImageView(new Image("/sample/foto/red.png")));
                        }
                    } else setGraphic(null);
                }
            };
        });

        tblPunet.getColumns().addAll(colStat_p, colPunaKryer, colData_p, colKonsumatori, colMakina, colPagesa, colPershkrimi);

        table.getColumns().addAll(colId, colPjesa, colPaisja, colTipi, colProdhuesi, colQmimi, colSasia, colShtese);
        nxerrTeDhenat();

        table.setMaxHeight(Double.MAX_VALUE);
        center.setVgrow(table, Priority.ALWAYS);
        selektimi();

        ContextMenu cm = new ContextMenu();
        MenuItem miRaportiPaisjet = new MenuItem("Create report for parts");
        cm.getItems().add(miRaportiPaisjet);

        table.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                cm.show(stage, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
            }
        });

        miRaportiPaisjet.setOnAction(e -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        JasperReport jreport = JasperCompileManager.compileReport(raportiPaisjet);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, new HashMap(), conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    } catch (Exception ex) {
                        new Mesazhi("", "", ex.getMessage());
                    }
                }
            }).start();
        });

        btnKerko.setOnAction(e -> {
//            table.getSelectionModel().clearSelection();
            filtroTabelen();
        });
        btnRifresko.setOnAction(e -> {
            try {
                nxerrTeDhenat();
                qTxtQmimi.clear();
                qTxtTipi.clear();
                qTxtPaisja.clear();
                qTxtPjesa.clear();
            } catch (NullPointerException n) {
            }
        });
        btnPlus.setOnAction(e -> {
            if (txtSasia.getText().isEmpty())
                txtSasia.setText("1");
            else
                txtSasia.setText((Integer.parseInt(txtSasia.getText()) + 1) + "");
        });
        btnMinus.setOnAction(e -> {
            if (txtSasia.getText().isEmpty())
                txtSasia.setText("0");
            else {
                if (!txtSasia.getText().equals("0"))
                    if (Integer.parseInt(txtSasia.getText()) > 0)
                        txtSasia.setText((Integer.parseInt(txtSasia.getText()) - 1) + "");
                    else
                        txtSasia.setText("0");
            }
        });

        s_btnPastro.setOnAction(e -> {
            s_cbEmriKons.clear();
            s_txtQmimi.clear();
            s_cbPuna.clear();
            s_txtPaisja.clear();
            txtPer.clear();
            s_data.getEditor().clear();
            s_data.setValue(null);
            cbProdhuesi.getSelectionModel().select("Asnje");
            cbPaisjet.getSelectionModel().select("Asnje");
            punaKryer.setSelected(false);
        });

        qTxtPjesa.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
                filtroTabelen();
        });
        qCbProdhuesi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
                filtroTabelen();
        });
        qTxtPaisja.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
                filtroTabelen();
        });
        qTxtQmimi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
                filtroTabelen();
        });
        qTxtTipi.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
                filtroTabelen();
        });

        if (lbl_Id.getText().equals("0")) btnA3.setDisable(true);
        txtPunetori.setText(log_user.getText());

        MenuBar menu = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mProgrami = new Menu("File");
        MenuItem miMbyll = new MenuItem("Close");
        MenuItem miDil = new MenuItem("Log out");
        MenuItem miAbout = new MenuItem("About");
        mHelp.getItems().addAll(miAbout);
        mProgrami.getItems().addAll(miDil, new SeparatorMenuItem(), miMbyll);
        menu.getMenus().addAll(mProgrami, mHelp);

        miMbyll.setOnAction(e -> stage.close());
        miDil.setOnAction(e -> {
            stage.close();
            restart();
        });

        miAbout.setOnAction(e -> new RrethProgramit());

        root.setTop(menu);

        center.getChildren().addAll(pyet, table, rectLay);
        center.fillWidthProperty().setValue(true);
        sc.setContent(center);
        sc.getStyleClass().add("edge-to-edge");
        sc.setPadding(new Insets(10));
        sc.setFitToWidth(true);
        sc.setFitToHeight(true);
//        root.setTop(menu);
        root.setCenter(sc);
        table.setPlaceholder(new Label("No data"));
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(942);
        stage.setMinHeight(680);
        stage.setMaximized(true);
        stage.show();
    }

    public void leftSide(DritarjaKryesore dk) {
        s_cbPuna.setEditable(true);
        Label lblDeri = new Label(" to ");
        lblDeri.setPadding(new Insets(5, 0, 0, 0));
        lblTeHyrat.setGraphic(new ImageView(new Image("/sample/foto/down.png")));
        lblShpenzimet.setGraphic(new ImageView(new Image("/sample/foto/down.png")));
        lblShpenzimet.setGraphicTextGap(10);
        lblTeHyrat.setGraphicTextGap(15);

        mbushProdhuesin();

        cbProdhuesi.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            mbushPaisjenCB(nv);
        });

        HBox hbCb = new HBox(5);
        hbCb.getChildren().addAll(cbProdhuesi, cbPaisjet);

        cbProdhuesi.getSelectionModel().select("Asnje");
        cbPaisjet.getSelectionModel().select("Asnje");

        HBox laySasia = new HBox(5);
        laySasia.getChildren().addAll(btnMinus, txtSasia, btnPlus);

        HBox butonat1 = new HBox(5);
        butonat1.setAlignment(Pos.CENTER_RIGHT);
        butonat1.getChildren().addAll(btnFshi, btnRregullo, btnPastro);
        txtInfo.setPrefColumnCount(5);
        txtInfo.setPrefRowCount(2);
        txtInfo.setPromptText("Informacione shtese");

        GridPane grid = new GridPane();
        grid.add(new Label("Part"), 0, 0);
        grid.add(txtPjesa, 1, 0);
        grid.add(new Label("Category"), 0, 1);
        grid.add(txtPaisja, 1, 1);
        grid.add(new Label("Maker"), 0, 2);
        grid.add(txtLloji, 1, 2);
        grid.add(new Label("Model"), 0, 3);
        grid.add(txtTipi, 1, 3);
        grid.add(new Label("Price "), 0, 4);
        grid.add(txtQmimi, 1, 4);
        grid.add(new Label("Quantity"), 0, 5);
        grid.add(laySasia, 1, 5);
        grid.add(txtInfo, 1, 6);
        grid.add(butonat1, 1, 7);

        grid.setHgap(30);
        grid.setVgap(5);

        grid.getStyleClass().addAll("grid");

        VBox s_root = new VBox();
        HBox btn = new HBox(5);
        btn.getChildren().addAll(btnBlej, s_btnPastro);
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.setPadding(new Insets(5, 0, 0, 0));
        btnBlej.setMinWidth(100);
        HBox hb_sasia = new HBox(2);
        Button btnP = new Button();
        Button btnM = new Button();
        s_txtSasia.setMaxWidth(120);
        s_txtSasia.setMinWidth(120);
        txtPunetori.setMinWidth(170);
//        txtPunetori.setMaxWidth(200);
//        s_cbPuna.setMaxWidth(200);
//        s_cbEmriKons.setMaxWidth(200);
//        txtMakina.setMaxWidth(200);
//        s_txtQmimi.setMaxWidth(200);
//        s_data.setMaxWidth(200);
        hb_sasia.getChildren().addAll(btnM, s_txtSasia, btnP);
        GridPane s_grid = new GridPane();
        s_data.setPromptText("muaji/dita/viti");

        txtPer.setPrefColumnCount(4);
        txtPer.setPrefRowCount(3);
        txtPer.setPromptText("More info");
        txtPer.setPadding(new Insets(0));

        s_grid.add(new Label("Puna e kryer"), 0, 0);
        s_grid.add(s_cbPuna, 1, 0);
        s_grid.add(new Label("Konsumatori"), 0, 1);
        s_grid.add(s_cbEmriKons, 1, 1);
        s_grid.add(new Label("Makina"), 0, 2);
        s_grid.add(txtMakina, 1, 2);
        s_grid.add(new Label("Qmimi"), 0, 3);
        s_grid.add(s_txtQmimi, 1, 3);
        s_grid.add(new Label("Data"), 0, 4);
        s_grid.add(s_data, 1, 4);
        s_grid.add(new Label("Punetori"), 0, 5);
        s_grid.add(txtPunetori, 1, 5);
        s_grid.add(new Label("Paisja"), 0, 6);
        s_grid.add(hbCb, 1, 6);
        s_grid.add(txtPer, 1, 7);
        s_grid.add(punaKryer, 1, 8);
        s_grid.add(btn, 1, 9);

        txtPunetori.setTooltip(new Tooltip("Emri nuk mund te ndryshohet"));
        s_grid.setVgap(5);
        s_grid.setHgap(15);
        s_root.getChildren().addAll(s_grid);

        s_grid.getStyleClass().add("grid");
        s_root.setPadding(new Insets(15, 0, 5, 0));

        btnBlej.setOnAction(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    shtoPune();
                }
            });
        });

        table.setMinHeight(420);
        table.setMinWidth(930);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        btnRregullo.setOnAction(e -> update());
        btnFshi.setOnAction(e -> {
            if (!txtLloji.getText().isEmpty() && !txtPaisja.getText().isEmpty() && !txtQmimi.getText().isEmpty()
                    && !txtSasia.getText().isEmpty() && !txtPjesa.getText().isEmpty())
                new Mesazhi("Informacion", txtPjesa.getText(), "A jeni te sigurte qe doni ta fshini kete paisje?", this);
            else new Mesazhi("Gabim", "Gabim", "Duhet te zgjedhni njeren prej pjeseve nga tabela.");
        });

        lblShpenzimet.setContentDisplay(ContentDisplay.RIGHT);
        lblTeHyrat.setContentDisplay(ContentDisplay.RIGHT);

        lblTeHyrat.setOnMouseClicked(e -> {
            if (show2) {
                lblTeHyrat.setGraphic(new ImageView(new Image("/sample/foto/right.png")));
                left_root.getChildren().remove(grid);
                show2 = false;
            } else {
                lblTeHyrat.setGraphic(new ImageView(new Image("/sample/foto/down.png")));
                left_root.getChildren().add(1, grid);
                show2 = true;
            }
        });

        lblShpenzimet.setOnMouseClicked(e -> {
            if (show) {
                lblShpenzimet.setGraphic(new ImageView(new Image("/sample/foto/right.png")));
                left_root.getChildren().remove(s_grid);
                show = false;
            } else {
                lblShpenzimet.setGraphic(new ImageView(new Image("/sample/foto/down.png")));
                if (!show2)
                    left_root.getChildren().add(2, s_grid);
                else left_root.getChildren().add(3, s_grid);
                show = true;
            }
        });

        ScrollPane sp = new ScrollPane();
        sp.getStyleClass().add("edge-to-edge");
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setMinWidth(325);
        sp.setContent(left_root);

        left_root.setMinWidth(320);

        lblTeHyrat.setStyle("-fx-font-size: 17");
        lblShpenzimet.setStyle("-fx-font-size: 17");
        lblTeHyrat.setPadding(new Insets(10, 0, 20, 0));
        lblShpenzimet.setPadding(new Insets(20, 0, 10, 0));
        left_root.getStyleClass().add("left");
//        left_root.setStyle("-fx-border-width: 0 2 0 0;" +
//                            "-fx-border-color: #e5e5e5;");
        left_root.setPadding(new Insets(15, 5, 0, 15));
        left_root.getChildren().addAll(lblTeHyrat, grid, lblShpenzimet, s_grid);
        root.setLeft(sp);
    }

    public void restart() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void nxerrTeDhenat() {
//        Thread t = new Thread(new Runnable() {
//            public void run() {
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Pjeset");

            data = FXCollections.observableArrayList();
            while (rs.next()) {
                data.add(new mbushTabelen(rs.getInt("id"), rs.getString("prodhimi").toUpperCase(),
                        rs.getString("tipi").toUpperCase(), rs.getString("lloji_pjeses").toUpperCase(),
                        rs.getString("emri_pjeses").toUpperCase(), rs.getFloat("qmimi"),
                        rs.getInt("sasia"), rs.getString("info")));
            }
            conn.close();
            table.setItems(data);
        } catch (SQLNonTransientConnectionException ntr) {
        } catch (Exception ex) {
            err.setText(ex.getMessage());
        }
//            }
//        });
//        t.start();
    }

    public void selektimi() {
        try {
            table.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
                if (!table.getSelectionModel().isEmpty()) {
                    lblId.setText("" + ((mbushTabelen) newVal).getId());
                    txtLloji.setText(((mbushTabelen) newVal).getTipi());
                    txtPaisja.setText(((mbushTabelen) newVal).getPaisja());
                    txtPjesa.setText(((mbushTabelen) newVal).getPjesa());
                    txtTipi.setText(((mbushTabelen) newVal).getLloji());
                    txtQmimi.setText(((mbushTabelen) newVal).getQmimi() + "");
                    txtSasia.setText(((mbushTabelen) newVal).getSasia() + "");
                    txtInfo.setText(((mbushTabelen) newVal).getInfo());
                }
            });
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
    }

    public void filtroTabelen() {
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            StringBuilder sqlCmd = new StringBuilder("select * from Pjeset where");

            if (!qCbProdhuesi.getText().isEmpty()
                    || !qTxtPaisja.getText().isEmpty() || !qTxtPjesa.getText().isEmpty()
                    || !qTxtQmimi.getText().isEmpty() || !qTxtTipi.getText().isEmpty()) {
                if (!qCbProdhuesi.getText().equals(""))
                    sqlCmd.append(" lower(prodhimi) like lower('%" + qCbProdhuesi.getText() + "%') and");
                if (!qTxtPjesa.getText().isEmpty())
                    sqlCmd.append(" lower(emri_pjeses) like lower('%" + qTxtPjesa.getText() + "%') and");
                if (!qTxtPaisja.getText().isEmpty())
                    sqlCmd.append(" lower(lloji_pjeses) like lower('%" + qTxtPaisja.getText() + "%') and");
                if (!qTxtTipi.getText().isEmpty())
                    sqlCmd.append(" lower(tipi) like ('%" + qTxtTipi.getText() + "%') and");
                if (!qTxtQmimi.getText().isEmpty()) {
                    if (qCbOp.getSelectionModel().getSelectedIndex() == 0)
                        sqlCmd.append(" cast(qmimi as decimal) = cast(" + Float.parseFloat(qTxtQmimi.getText()) + " as decimal) and");
                    else if (qCbOp.getSelectionModel().getSelectedIndex() == 1)
                        sqlCmd.append(" cast(qmimi as decimal) > cast(" + Float.parseFloat(qTxtQmimi.getText()) + " as decimal) and");
                    else if (qCbOp.getSelectionModel().getSelectedIndex() == 2)
                        sqlCmd.append(" cast(qmimi as decimal) < cast(" + Float.parseFloat(qTxtQmimi.getText()) + " as decimal) and");

                }

            } else {
                sqlCmd.equals("select * from Pjeset");
            }

            StringBuilder newSql = new StringBuilder();
            if (sqlCmd.substring(sqlCmd.length() - 3, sqlCmd.length()).equals("and")) {
                StringBuilder ns = new StringBuilder(sqlCmd.substring(0, sqlCmd.length() - 3));
                newSql = ns;
            } else if (sqlCmd.substring(sqlCmd.length() - 5, sqlCmd.length()).equals("where")) {
                StringBuilder ns = new StringBuilder(sqlCmd.substring(0, sqlCmd.length() - 5));
                newSql = ns;
            } else newSql = sqlCmd;

            ResultSet rs = stmt.executeQuery(newSql.toString());
            data.remove(0, data.size());
            while (rs.next()) {
                data.add(new mbushTabelen(rs.getInt("id"), rs.getString("prodhimi").toUpperCase(),
                        rs.getString("tipi").toUpperCase(), rs.getString("lloji_pjeses").toUpperCase(),
                        rs.getString("emri_pjeses").toUpperCase(), rs.getFloat("qmimi"),
                        rs.getInt("sasia"), rs.getString("info")));
            }
            table.setItems(data);
            conn.close();
        } catch (NumberFormatException nf) {
            new Mesazhi("Info", "Format i gabuar", "Fusha qmimi duhet te permbaje vetem numra.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pastro() {
        lblId.setText("");
        txtSasia.clear();
        txtTipi.clear();
        txtPjesa.clear();
        txtPaisja.clear();
        txtLloji.clear();
        txtQmimi.clear();
        txtInfo.clear();
        table.getSelectionModel().clearSelection();
    }

    public void punetTabela() {
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Punet order by data desc");
            ObservableList<TabelaPunet> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                String d = format.format(rs.getDate("data"));
                data.add(new TabelaPunet(rs.getInt("id"), rs.getString("lloji").toUpperCase(), d,
                        rs.getFloat("qmimi"), rs.getString("konsumatori").toUpperCase(),
                        rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina").toUpperCase()));
            }

            tblPunet.setItems(data);
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void shtoPune() {
        try {
            if (!s_cbPuna.getText().equals("") && !s_data.getValue().equals("")
                    && !s_cbEmriKons.getText().equals("") && !txtMakina.getText().equals("")) {
                String[] emri_l = s_cbEmriKons.getText().split(" ");
                emri_l[0] = emri_l[0].substring(0, 1).toUpperCase() + emri_l[0].substring(1, emri_l[0].length()).toLowerCase();
                emri_l[1] = emri_l[1].substring(0, 1).toUpperCase() + emri_l[1].substring(1, emri_l[1].length()).toLowerCase();
                String sql = "";
                if (punaKryer.isSelected()) {
                    sql = "insert into Punet (lloji, data, qmimi, konsumatori, pershkrimi, punetori, kryer, makina) " +
                            "values ('" + s_cbPuna.getText() + "', '" + s_data.getValue() + "', " +
                            Float.parseFloat(s_txtQmimi.getText()) + ", '" + emri_l[0] + " " + emri_l[1] + "', '" +
                            txtPer.getText() + "', '" + txtPunetori.getText() + "', 'po', '" + txtMakina.getText() + "')";
                } else {
                    sql = "insert into Punet (lloji, data, qmimi, konsumatori, pershkrimi, punetori, kryer, makina) " +
                            "values ('" + s_cbPuna.getText() + "', '" + s_data.getValue() + "', " +
                            Float.parseFloat(s_txtQmimi.getText()) + ", '" + emri_l[0] + " " + emri_l[1] + "', '" +
                            txtPer.getText() + "', '" + txtPunetori.getText() + "', 'jo', '" + txtMakina.getText() + "')";
                }
                String comp = "select emri, mbiemri from Konsumatori";
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                Statement st = conn.createStatement();
                stmt.execute(sql);
                Thread.sleep(500);

                String paisja = null;
                if (!cbPaisjet.getSelectionModel().getSelectedItem().equals("Asnje") ||
                        !cbProdhuesi.getSelectionModel().getSelectedItem().equals("Asnje")) {
                    paisja = "update Pjeset set sasia = sasia - 1 where prodhimi = '" + cbProdhuesi.getSelectionModel().getSelectedItem()
                            + "' and emri_pjeses = '" + cbPaisjet.getSelectionModel().getSelectedItem() + "'";
                    Statement PaisjetStmt = conn.createStatement();
                    PaisjetStmt.execute(paisja);
                }

                if (s_cbEmriKons.getText().contains(" ")) {
                    int num = 0;
                    ResultSet rs = st.executeQuery("select emri, mbiemri from Konsumatori where emri = '" + emri_l[0] +
                            "' and mbiemri = '" + emri_l[1] + "'");
                    while (rs.next()) {
                        if (rs.getString("emri").toLowerCase().equals(emri_l[0].toLowerCase()) &&
                                rs.getString("mbiemri").toLowerCase().equals(emri_l[1].toLowerCase())) {
                            num++;
                        }
                    }
                    if (num == 0) new ShtoKonsumator(emri_l[0], emri_l[1], txtMakina.getText());
                    else new Mesazhi("Sukses", "Te dhenat u ruajten me sukses", "Te dhenat per punen e kryer " +
                            "ndaj konsumatorit\n" + emri_l[0] + " " + emri_l[1] + ", u kryen me sukses");
                }
                conn.close();

                if (!cbPaisjet.getSelectionModel().getSelectedItem().equals("Asnje") ||
                        !cbProdhuesi.getSelectionModel().getSelectedItem().equals("Asnje")) {
                    nxerrTeDhenat();
                }

                s_cbEmriKons.clear();
                s_txtQmimi.clear();
                s_cbPuna.clear();
                s_txtPaisja.clear();
                txtPer.clear();
                s_data.getEditor().clear();
                s_data.setValue(null);
                punaKryer.setSelected(false);
                cbProdhuesi.getSelectionModel().select("Asnje");
                cbPaisjet.getSelectionModel().select("Asnje");
                txtMakina.clear();
                punaKryer.setSelected(false);

            } else {
                new Mesazhi("Gabim", "Fusha e zbrazet", "Ju lutem mbushini te gjitha fushat para se te vazhdoni!");
            }
        } catch (SQLNonTransientConnectionException fu) {
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mbushPaisjenCB(String p) {
        try {
            cbPaisjet.getItems().clear();
            String sql = "select distinct emri_pjeses, sasia from Pjeset where prodhimi = '" + p + "'";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cbPaisjet.getItems().clear();
            while (rs.next()) {
                if (rs.getInt("sasia") > 0)
                    cbPaisjet.getItems().add(rs.getString("emri_pjeses"));
            }
            if (cbPaisjet.getItems().size() > 0)
                cbPaisjet.getSelectionModel().select(0);
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mbushProdhuesin() {
        try {
            String sql = "select distinct prodhimi from Pjeset";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cbProdhuesi.getItems().clear();
            while (rs.next()) {
                cbProdhuesi.getItems().add(rs.getString("prodhimi"));
            }
            conn.close();

            if (cbProdhuesi.getItems().size() == 0)
                cbProdhuesi.getSelectionModel().select("Asnje");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        try {
            if (!txtPjesa.getText().isEmpty() && !txtSasia.getText().isEmpty() && !txtQmimi.getText().isEmpty() &&
                    !txtPaisja.getText().isEmpty() && !txtLloji.getText().isEmpty() && !lblId.getText().isEmpty()) {
                cbProdhuesi.getItems().clear();
                String prodh = txtPaisja.getText().substring(0, 1).toUpperCase() + txtPaisja.getText().substring(1, txtPaisja.getText().length()).toLowerCase();
                String emri = txtPjesa.getText().substring(0, 1).toUpperCase() + txtPjesa.getText().substring(1, txtPjesa.getText().length()).toLowerCase();
                StringBuilder sb = new StringBuilder("update Pjeset set prodhimi = '" + txtLloji.getText() + "', tipi = '" + txtTipi.getText() + "', " +
                        "lloji_pjeses = '" + prodh + "', emri_pjeses = '" + emri + "', qmimi = " +
                        txtQmimi.getText() + ", sasia = " +
                        Integer.parseInt(txtSasia.getText()) +
                        ", info = '" + txtInfo.getText() + "' where id = " + lblId.getText());
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sb.toString());
                stmt.close();
                conn.close();
                new Mesazhi("Sukses", "Sukses", "Te dhenat u ruajten me sukses.");
                table.getSelectionModel().clearSelection();
                filtroTabelen();
                mbushProdhuesin();
            } else
                new Mesazhi("Informacion", "Gabimet e mundshme", "1. Disa prej fushave te kerkuara jane te zbrazta.\n" +
                        "2. Duhet te zgjedhet njeri prej paisjeve ne tabele.");

        } catch (NumberFormatException nfe) {
            new Mesazhi("Gabim", "", "Nuk lejohen shkronja tek fusha 'Sasia'.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fshi() {
        try {
            if (!txtPjesa.getText().isEmpty() && !txtSasia.getText().isEmpty() && !txtQmimi.getText().isEmpty() &&
                    !txtPaisja.getText().isEmpty() && !txtLloji.getText().isEmpty() && !lblId.getText().isEmpty()) {
                cbProdhuesi.getItems().clear();
                StringBuilder sb = new StringBuilder("delete from Pjeset where id = " + lblId.getText());
                Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                Statement stmt = conn.createStatement();
                stmt.execute(sb.toString());
                stmt.close();
                conn.close();
                new Mesazhi("Sukses", "Sukses", "Paisja u fshi me sukses nga baza e te dhenave");
                pastro();
                filtroTabelen();
                mbushProdhuesin();
            } else new Mesazhi("Gabim", "Gabim", "Disa prej fushave te kerkuara jane te zbrazta.");
        } catch (SQLNonTransientConnectionException fu) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void financat() {
        Button btnBack = new Button();
        btnBack.setGraphic(new ImageView(new Image("/sample/foto/back.png")));
        btnBack.setOnAction(e -> {
            root.setCenter(null);
            root.setCenter(sc);
        });
        Button btnRifresko = new Button();

        punetTabela();

        AreaChart<String, Number> chart = new AreaChart<String, Number>(x, y);
        chart.setLegendVisible(true);
        chart.setVerticalGridLinesVisible(false);
        chart.setAnimated(false);

        XYChart.Series muaji = new XYChart.Series();
        muaji.setName(Calendar.getInstance().get(Calendar.YEAR) + "");
        double jan = 0, shk = 0, mar = 0, pri = 0, maj = 0, qer = 0, kor = 0, gsht = 0, sht = 0, tet = 0, nen = 0, dhj = 0,
                jank = 0, shkk = 0, mark = 0, prik = 0, majk = 0, qerk = 0, kork = 0, gshtk = 0, shtk = 0, tetk = 0, nenk = 0, dhjk = 0;

        viti.put(Calendar.getInstance().get(Calendar.YEAR) + "", 0.0);
        viti.put((Calendar.getInstance().get(Calendar.YEAR) - 1) + "", 0.0);

        tani = Calendar.getInstance().get(Calendar.YEAR) + "";
        kaluar = Calendar.getInstance().get(Calendar.YEAR) - 1 + "";

        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(6, 10).equals(tani)) {
                if (((String) tblPunet.getColumns().get(0).getCellData(i)).equals("po")) {
                    viti.put(tani, viti.get(tani) + colPagesa.getCellData(i));
                }
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(6, 10).equals(kaluar)) {
                if (((String) tblPunet.getColumns().get(0).getCellData(i)).equals("po")) {
                    viti.put(kaluar, viti.get(kaluar) + colPagesa.getCellData(i));
                }
            }
            if (((String) tblPunet.getColumns().get(0).getCellData(i)).equals("po")){
                if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("01/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    jan += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("02/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    shk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("03/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    mar += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("04/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    pri += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("05/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    maj += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("06/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    qer += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("07/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    kor += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("08/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    gsht += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("09/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    sht += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("10/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    tet += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("11/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    nen += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("12/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    dhj += colPagesa.getCellData(i);
                }

                if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("01/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    jank += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("02/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    shkk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("03/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    mark += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("04/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    prik += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("05/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    majk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("06/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    qerk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("07/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    kork += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("08/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    gshtk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("09/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    shtk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("10/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    tetk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("11/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    nenk += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals("12/" + (Calendar.getInstance().get(Calendar.YEAR)-1))) {
                    dhjk += colPagesa.getCellData(i);
                }

            }
        }

        muaji.getData().add(new XYChart.Data("Janar", jan));
        muaji.getData().add(new XYChart.Data("Shkurt", shk));
        muaji.getData().add(new XYChart.Data("Mars", mar));
        muaji.getData().add(new XYChart.Data("Prill", pri));
        muaji.getData().add(new XYChart.Data("Maj", maj));
        muaji.getData().add(new XYChart.Data("Qershor", qer));
        muaji.getData().add(new XYChart.Data("Korrik", kor));
        muaji.getData().add(new XYChart.Data("Gusht", gsht));
        muaji.getData().add(new XYChart.Data("Shtator", sht));
        muaji.getData().add(new XYChart.Data("Tetor", tet));
        muaji.getData().add(new XYChart.Data("Nentor", nen));
        muaji.getData().add(new XYChart.Data("Dhjetor", dhj));

        //kaluar
        XYChart.Series vitiKaluar = new XYChart.Series();
        vitiKaluar.setName((Calendar.getInstance().get(Calendar.YEAR)-1) + "");
        vitiKaluar.getData().add(new XYChart.Data("Janar", jank));
        vitiKaluar.getData().add(new XYChart.Data("Shkurt", shkk));
        vitiKaluar.getData().add(new XYChart.Data("Mars", mark));
        vitiKaluar.getData().add(new XYChart.Data("Prill", prik));
        vitiKaluar.getData().add(new XYChart.Data("Maj", majk));
        vitiKaluar.getData().add(new XYChart.Data("Qershor", qerk));
        vitiKaluar.getData().add(new XYChart.Data("Korrik", kork));
        vitiKaluar.getData().add(new XYChart.Data("Gusht", gshtk));
        vitiKaluar.getData().add(new XYChart.Data("Shtator", shtk));
        vitiKaluar.getData().add(new XYChart.Data("Tetor", tetk));
        vitiKaluar.getData().add(new XYChart.Data("Nentor", nenk));
        vitiKaluar.getData().add(new XYChart.Data("Dhjetor", dhjk));

        int[] muajt = new int[12];
        muajt[0] = (int) jan;
        muajt[1] = (int) shk;
        muajt[2] = (int) mar;
        muajt[3] = (int) pri;
        muajt[4] = (int) maj;
        muajt[5] = (int) qer;
        muajt[6] = (int) kor;
        muajt[7] = (int) gsht;
        muajt[8] = (int) sht;
        muajt[9] = (int) tet;
        muajt[10] = (int) nen;
        muajt[11] = (int) dhj;

        Map<Integer, String> m_muajt = new HashMap<>();
        m_muajt.put(1, "Janar");
        m_muajt.put(2, "Shkurt");
        m_muajt.put(3, "Mars");
        m_muajt.put(4, "Prill");
        m_muajt.put(5, "Maj");
        m_muajt.put(6, "Qershor");
        m_muajt.put(7, "Korrik");
        m_muajt.put(8, "Gusht");
        m_muajt.put(9, "Shtator");
        m_muajt.put(10, "Tetor");
        m_muajt.put(11, "Nentor");
        m_muajt.put(12, "Dhjetor");

        int min = 1000, max = 0, tot = 0, muajiMax = 0, muajiMin = 0;
        for (int i = 0; i <= Calendar.getInstance().get(Calendar.MONTH); i++) {
            if (min > muajt[i] && muajt[i] > 0) {
                min = muajt[i];
                muajiMin = i + 1;
            }
            if (max < muajt[i]) {
                max = muajt[i];
                muajiMax = i + 1;
            }
            tot += muajt[i];
        }


        chart.getData().addAll(vitiKaluar, muaji);

        HBox comboLay = new HBox(10);
        comboLay.setPadding(new Insets(5, 5, 5, 0));
        comboLay.setAlignment(Pos.CENTER_RIGHT);
        Hyperlink rbAktual = new Hyperlink("Muaji aktual");
        Hyperlink rbPeriudha = new Hyperlink("6 Muajt e fundit");
        Hyperlink rbViti = new Hyperlink("Viti aktual");

//        Button btnRaporti = new Button("Krijo raport");

        ComboBox<String> cbMuaji = new ComboBox<>();
        for (Map.Entry<Integer, String> m : m_muajt.entrySet()) {
            if (m.getKey() > Calendar.getInstance().get(Calendar.MONTH) + 1) break;
            cbMuaji.getItems().add(m.getValue());
        }
        cbMuaji.getSelectionModel().select(0);

        cbMuaji.getSelectionModel().selectedIndexProperty().addListener((ol, ov, newVal) -> {
            if (newVal.intValue() + 1 < 10)
                muajiVitit(chart, "0" + (newVal.intValue() + 1));
            else muajiVitit(chart, "" + (newVal.intValue() + 1));
        });

        comboLay.getChildren().addAll(rbViti, rbAktual, rbPeriudha, cbMuaji);
        rbAktual.setOnAction(e -> {
            aktual(chart, m_muajt);
            rbAktual.setVisited(false);
        });

        rbViti.setOnAction(e -> {
            chart.getData().clear();
            chart.getData().addAll(vitiKaluar, muaji);
            rbViti.setVisited(false);
        });

        rbPeriudha.setOnAction(e -> {
            gjashtMujori(chart, Calendar.getInstance().get(Calendar.MONTH) + 1);
            rbPeriudha.setVisited(false);
        });

        for (int i = 0; i < 12; i++) {
            XYChart.Data item = (XYChart.Data) muaji.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue() + " €"));
            XYChart.Data item2 = (XYChart.Data) vitiKaluar.getData().get(i);
            Tooltip.install(item2.getNode(), new Tooltip(item2.getYValue() + " €"));
        }

        btnRifresko.setGraphic(new ImageView(new Image("/sample/foto/refresh-24.png")));
        btnRifresko.setOnAction(e -> financat());

        Button btnKerko2 = new Button("Kerko");
        TextField txtKerkoPunet = new TextField();
        HBox hbk = new HBox(5);
        hbk.setAlignment(Pos.CENTER_RIGHT);
        hbk.setPadding(new Insets(0, 5, 0, 0));
        hbk.getChildren().addAll(txtKerkoPunet, btnKerko2);

        btnKerko2.setOnAction(e -> filtroFinancat(txtKerkoPunet));
        txtKerkoPunet.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                filtroFinancat(txtKerkoPunet);
                emriKons = -1;
            }
        });

        tblPunet.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null) {
                emriKons = nv.getId();
                if (nv.getKryer().equals("po")) kryer = true;
                else kryer = false;
            } else emriKons = -1;
        });

        ContextMenu cm = new ContextMenu();
        MenuItem cmStatusi = new MenuItem("Ndrysho statusin");
        MenuItem cmRaport = new MenuItem("Krijo raport per punen e zgjedhur");
        MenuItem cmRaportSot = new MenuItem("Krijo raport per sot");
        MenuItem cmRaportAktual = new MenuItem("Krijo raport per muajin aktual");
        MenuItem cmRaportTeGjitha = new MenuItem("Raport per te gjitha punet");
        MenuItem cmRaportiViti = new MenuItem("Krijo raport per kete vit");

        cmStatusi.setOnAction(e -> {
            if (emriKons > -1) {
                new RregulloPunen(emriKons, kryer, DritarjaKryesore.this);
            }
        });

        cmRaport.setOnAction(e -> {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (emriKons > -1) {
                            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                            HashMap hm = new HashMap();
                            hm.put("kompania", "Emri i kompanise");
                            hm.put("Folderi", folderi);
                            hm.put("id", emriKons);
                            JasperReport jreport = JasperCompileManager.compileReport(raportiFatura);
                            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                            JasperViewer.viewReport(jprint, false);
                            conn.close();
                        } else
                            new Mesazhi("Gabim", "Zgjedhni njerin", "Ju duhet te zgjedhni njeren nga punet ne tabele");
                    } catch (Exception ex) {
                        new Mesazhi("", "", ex.getMessage());
                    }
                }
            });
            t.start();
        });

        cmRaportSot.setOnAction(e -> {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String strData = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
                            Calendar.getInstance().get(Calendar.YEAR);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date data = format.parse(strData);
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        HashMap hm = new HashMap();
                        hm.put("data", data);
                        JasperReport jreport = JasperCompileManager.compileReport(raportiSot);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    } catch (JRException fnfe) {
                        new Mesazhi("Gabim", "Folderi nuk u gjet", "Te dhenat qe nevojiten per te krijuar raportin\n" +
                                "nuk u gjeten.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t.start();
        });

        cmRaportTeGjitha.setOnAction(e -> {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        HashMap hm = new HashMap();
                        JasperReport jreport = JasperCompileManager.compileReport(raportiTotal);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    } catch (JRException fnfe) {
                        new Mesazhi("Gabim", "Folderi nuk u gjet", "Te dhenat qe nevojiten per te krijuar raportin\n" +
                                "nuk u gjeten.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t.start();
        });

        cmRaportAktual.setOnAction(e -> {
            Thread raporti = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap hm = new HashMap();
                        String muajiAktual = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "";
                        String Viti = Calendar.getInstance().get(Calendar.YEAR) + "";
                        hm.put("MuajiAktual", muajiAktual);
                        hm.put("Viti", Viti);
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        JasperReport jreport = JasperCompileManager.compileReport(raportiMujor);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    } catch (JRException fnfe) {
                        new Mesazhi("Gabim", "Folderi nuk u gjet", "Te dhenat qe nevojiten per te krijuar raportin\n" +
                                "nuk u gjeten.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            raporti.start();
        });

        cmRaportiViti.setOnAction(e -> {
            Thread raportiViti = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap hm = new HashMap();
                        String Viti = Calendar.getInstance().get(Calendar.YEAR) + "";
                        hm.put("Viti", Viti);
                        Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
                        JasperReport jreport = JasperCompileManager.compileReport(strRaportiViti);
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, conn);
                        JasperViewer.viewReport(jprint, false);
                        conn.close();
                    } catch (JRException fnfe) {
                        new Mesazhi("Gabim", "Folderi nuk u gjet", "Te dhenat qe nevojiten per te krijuar raportin\n" +
                                "nuk u gjeten.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            raportiViti.start();
        });

        cm.getItems().addAll(cmStatusi, new SeparatorMenuItem(), cmRaport, new SeparatorMenuItem(), cmRaportSot, cmRaportAktual, new SeparatorMenuItem(), cmRaportTeGjitha, cmRaportiViti);

        tblPunet.setRowFactory(tv -> {
            TableRow<TabelaPunet> row = new TableRow<TabelaPunet>();
            row.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(stage, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                }
            });
            return row;
        });

        tblPunet.setMaxHeight(300);
        chart.setMaxHeight(300);

        //Informate perfundi Grafikut
        DecimalFormat df = new DecimalFormat("#,###.00€");
        DecimalFormat dfPerc = new DecimalFormat("#.0");
        Rectangle rect1 = new Rectangle();
        rect1.setWidth(210);
        rect1.setHeight(200);
        rect1.getStyleClass().add("rect");
        StackPane stack1 = new StackPane();
        BorderPane bp1 = new BorderPane();
        VBox vb1 = new VBox();
        Label lblTitulli1 = new Label("Muaji aktual");
        Label lblQmimi1 = new Label(muajt[Calendar.getInstance().get(Calendar.MONTH)] + "");
        Label lblKaluar1 = new Label(muajt[Calendar.getInstance().get(Calendar.MONTH) - 1] + "");
        lblQmimi1.setTooltip(new Tooltip("Muaji aktual"));
        lblKaluar1.setTooltip(new Tooltip("Muaji i kaluar"));
        Label lblDif1 = new Label(Double.parseDouble(lblQmimi1.getText()) - Double.parseDouble(lblKaluar1.getText()) + "");
        lblTitulli1.getStyleClass().add("titulli");
        lblKaluar1.getStyleClass().add("kaluar");
        lblQmimi1.getStyleClass().add("aktual");
        lblDif1.getStyleClass().add("dif");
        if (Double.parseDouble(lblKaluar1.getText()) != 0.0) {
            lblDif1.setText((Double.parseDouble(lblDif1.getText()) / Double.parseDouble(lblKaluar1.getText()) * 100) + "");
            if (Double.parseDouble(lblDif1.getText()) > 0) {
                lblDif1.setText(lblDif1.getText() + "");
                lblDif1.setGraphic(new ImageView(new Image("/sample/foto/up.png")));
            } else if (Double.parseDouble(lblDif1.getText()) < 0) {
                lblDif1.setText(Math.abs(Double.parseDouble(lblDif1.getText())) + "");
                lblDif1.setGraphic(new ImageView(new Image("/sample/foto/down_2.png")));
            } else {
                lblDif1.setText(Math.abs(Double.parseDouble(lblDif1.getText())) + "");
            }
        }else {
            if (Double.parseDouble(lblKaluar1.getText()) == 0 &&
                    Double.parseDouble(lblQmimi1.getText()) > 0){
                lblDif1.setText(lblQmimi1.getText() + "");
                lblDif1.setGraphic(new ImageView(new Image("/sample/foto/up.png")));
            }else if (Double.parseDouble(lblKaluar1.getText()) > 0 &&
                    Double.parseDouble(lblQmimi1.getText()) == 0){
                lblDif1.setText(lblKaluar1.getText() + "");
                lblDif1.setGraphic(new ImageView(new Image("/sample/foto/down_2.png")));
            }
        }
        vb1.getChildren().addAll(lblQmimi1, lblKaluar1);
        bp1.setTop(lblTitulli1);
        bp1.setCenter(vb1);
        bp1.setBottom(lblDif1);
        stack1.getChildren().addAll(rect1, bp1);
        vb1.setAlignment(Pos.CENTER);
        bp1.setAlignment(lblTitulli1, Pos.CENTER);
        bp1.setAlignment(lblDif1, Pos.CENTER);

        //**************************************
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(210);
        rect2.setHeight(200);
        rect2.getStyleClass().add("rect");
        StackPane stack2 = new StackPane();
        BorderPane bp2 = new BorderPane();
        VBox vb2 = new VBox();
        Label lblTitulli2 = new Label("Viti aktual");
        Label lblQmimi2 = new Label(viti.get(tani) + "");
        Label lblKaluar2 = new Label(viti.get(kaluar) + "");
        lblQmimi2.setTooltip(new Tooltip("Viti " + Calendar.getInstance().get(Calendar.YEAR)));
        lblKaluar2.setTooltip(new Tooltip("Viti " + (Calendar.getInstance().get(Calendar.YEAR) - 1)));
        Label lblDif2 = new Label((Double.parseDouble(lblQmimi2.getText()) - Double.parseDouble(lblKaluar2.getText())) + "");
        lblTitulli2.getStyleClass().add("titulli");
        lblKaluar2.getStyleClass().add("kaluar");
        lblQmimi2.getStyleClass().add("aktual");
        lblDif2.getStyleClass().add("dif");
        if (Double.parseDouble(lblKaluar2.getText()) != 0.0 && Double.parseDouble(lblQmimi2.getText()) > 0.0) {
            lblDif2.setText((Double.parseDouble(lblDif2.getText()) / Double.parseDouble(lblKaluar2.getText()) * 100) + "");
            if (Double.parseDouble(lblDif2.getText()) > 0) {
                lblDif2.setText(lblDif2.getText() + "");
                lblDif2.setGraphic(new ImageView(new Image("/sample/foto/up.png")));
            } else if (Double.parseDouble(lblDif2.getText()) < 0) {
                lblDif2.setText(Math.abs(Double.parseDouble(lblDif2.getText())) + "");
                lblDif2.setGraphic(new ImageView(new Image("/sample/foto/down_2.png")));
            } else {
                lblDif2.setText(Math.abs(Double.parseDouble(lblDif2.getText())) + "");
            }
        }else {
            if (Double.parseDouble(lblKaluar2.getText()) == 0 &&
                    Double.parseDouble(lblQmimi2.getText()) > 0){
                lblDif2.setText(lblQmimi2.getText() + "");
                lblDif2.setGraphic(new ImageView(new Image("/sample/foto/up.png")));
            }else if (Double.parseDouble(lblKaluar2.getText()) > 0 &&
                    Double.parseDouble(lblQmimi2.getText()) == 0){
                lblDif2.setText(lblKaluar2.getText() + "");
                lblDif2.setGraphic(new ImageView(new Image("/sample/foto/down_2.png")));
            }
        }
        vb2.getChildren().addAll(lblQmimi2, lblKaluar2);
        bp2.setTop(lblTitulli2);
        bp2.setCenter(vb2);
        bp2.setBottom(lblDif2);
        stack2.getChildren().addAll(rect2, bp2);
        vb2.setAlignment(Pos.CENTER);
        bp2.setAlignment(lblTitulli2, Pos.CENTER);
        bp2.setAlignment(lblDif2, Pos.CENTER);

        if (Double.parseDouble(lblKaluar1.getText()) > 0 &&
                Double.parseDouble(lblQmimi1.getText()) > 0)
            lblDif1.setText(dfPerc.format(Double.parseDouble(lblDif1.getText())) + "%");
        else lblDif1.setText(df.format(Double.parseDouble(lblDif1.getText())) + "");
        if (Double.parseDouble(lblKaluar2.getText()) > 0 &&
                Double.parseDouble(lblQmimi2.getText()) > 0)
            lblDif2.setText(dfPerc.format(Double.parseDouble(lblDif2.getText())) + "%");
        else {
            lblDif2.setText(df.format(Double.parseDouble(lblDif2.getText())) + "");
        }
        lblQmimi1.setText(df.format(Double.parseDouble(lblQmimi1.getText())));
        lblQmimi2.setText(df.format(Double.parseDouble(lblQmimi2.getText())));
        lblKaluar1.setText(df.format(Double.parseDouble(lblKaluar1.getText())));
        lblKaluar2.setText(df.format(Double.parseDouble(lblKaluar2.getText())));
        //***************************************
        Rectangle rect3 = new Rectangle();
        rect3.setWidth(210);
        rect3.setHeight(200);
        rect3.getStyleClass().add("rect");
        StackPane stack3 = new StackPane();
        BorderPane bp3 = new BorderPane();
        VBox vb3 = new VBox();
        loadThreadMes(lblQmimi3);
        Label lblTitulli3 = new Label("Pagesa mesatare");
        lblTitulli3.getStyleClass().add("titulli");
        lblQmimi3.getStyleClass().add("aktual");
        vb3.getChildren().addAll(lblQmimi3);
        bp3.setTop(lblTitulli3);
        bp3.setCenter(vb3);
        stack3.getChildren().addAll(rect3, bp3);
        vb3.setAlignment(Pos.CENTER);
        bp3.setAlignment(lblTitulli3, Pos.CENTER);
        //******************************************
        Rectangle rect4 = new Rectangle();
        rect4.setWidth(210);
        rect4.setHeight(200);
        rect4.getStyleClass().add("rect");
        StackPane stack4 = new StackPane();
        BorderPane bp4 = new BorderPane();
        VBox vb4 = new VBox();
        Label lblTitulli4 = new Label("Punet e kryera");
        loadThread(lblQmimi4);
        lblTitulli4.getStyleClass().add("titulli");
        lblQmimi4.getStyleClass().add("aktual");
        lblQmimi4.setText(lblQmimi4.getText() + "");
        vb4.getChildren().addAll(lblQmimi4);
        bp4.setTop(lblTitulli4);
        bp4.setCenter(vb4);
        stack4.getChildren().addAll(rect4, bp4);
        vb4.setAlignment(Pos.CENTER);
        bp4.setAlignment(lblTitulli4, Pos.CENTER);

        HBox hbtn = new HBox(13);
        hbtn.getChildren().addAll(stack1, stack2, stack3, stack4);

        HBox hbtn0 = new HBox(5);
        hbtn0.getChildren().addAll(btnBack, btnRifresko);

        grid_fin.setHgrow(tblPunet, Priority.ALWAYS);
        tblPunet.setPlaceholder(new Label("Nuk ka te dhena"));
        tblPunet.setPrefWidth(940);
        tblPunet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        grid_fin.getChildren().clear();
        grid_fin.add(hbtn0, 0, 0);
//        grid_fin.add(info, 2, 0);
//        grid_fin.add(hbMaxMin, 3, 0);
        grid_fin.add(hbk, 1, 0);
        grid_fin.add(tblPunet, 0, 1, 2, 1);
        grid_fin.add(hbtn, 0, 2, 2, 1);
        grid_fin.add(comboLay, 0, 3, 2, 1);
        grid_fin.add(chart, 0, 4, 2, 1);
        grid_fin.setVgap(10);
        grid_fin.setHgap(5);
        grid_fin.setPadding(new Insets(30, 0, 0, 15));
        sp.setContent(grid_fin);
        sp.getStyleClass().add("edge-to-edge");
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void aktual(AreaChart chart, Map<Integer, String> hm) {
        try {

            Map<String, Float> map = new HashMap<>();
            XYChart.Series aktual = new XYChart.Series();
            aktual.setName(hm.get(Calendar.getInstance().get(Calendar.MONTH)+1));

            for (int i = 0; i < tblPunet.getItems().size(); i++) {
                if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals((Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    if (!map.containsKey(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2)))
                        map.put(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2), colPagesa.getCellData(i));
                    else
                        map.put(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2), map.get(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2)) + colPagesa.getCellData(i));
                }
            }

            Map<String, Float> mp = new TreeMap<>(map);

            for (Map.Entry<String, Float> m : mp.entrySet()) {
                aktual.getData().add(new XYChart.Data(m.getKey(), m.getValue()));
            }

            chart.getData().clear();
            chart.getData().add(aktual);

            for (int i = 0; i < aktual.getData().size(); i++) {
                XYChart.Data item = (XYChart.Data) aktual.getData().get(i);
                Tooltip.install(item.getNode(), new Tooltip(item.getYValue() + " €"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void muajiVitit(AreaChart chart, String muaji_nr) {
        Map<String, Float> map = new HashMap<>();
        XYChart.Series aktual = new XYChart.Series();


        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(muaji_nr + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                if (!map.containsKey(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2)))
                    map.put(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2), colPagesa.getCellData(i));
                else
                    map.put(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2), map.get(((String) tblPunet.getColumns().get(2).getCellData(i)).substring(0, 2)) + colPagesa.getCellData(i));
            }
        }

        Map<String, Float> mp = new TreeMap<>(map);

        for (Map.Entry<String, Float> m : mp.entrySet()) {
            aktual.getData().add(new XYChart.Data(m.getKey(), m.getValue()));
        }

        chart.getData().clear();
        chart.getData().add(aktual);

        for (int i = 0; i < aktual.getData().size(); i++) {
            XYChart.Data item = (XYChart.Data) aktual.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue() + " €"));
        }
    }

    private void gjashtMujori(AreaChart<String, Number> chart, int aktual) {
        XYChart.Series muajt = new XYChart.Series();
        double m6 = 0, m5 = 0, m4 = 0, m3 = 0, m2 = 0, m1 = 0;
        if ((aktual - 6) > 0) {
            for (int i = 0; i < tblPunet.getItems().size(); i++) {
                if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m6 += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m5 += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m4 += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m3 += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH) - 3) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH) - 3) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m2 += colPagesa.getCellData(i);
                } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                        (Calendar.getInstance().get(Calendar.MONTH) - 4) + "/" + Calendar.getInstance().get(Calendar.YEAR)) ||
                        ((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                                "0" + (Calendar.getInstance().get(Calendar.MONTH) - 4) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                    m1 += colPagesa.getCellData(i);
                }
            }

        } else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 5) {
            gjmPesMuaj(m6, m5, m4, m3, m2, m1);
        } else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 4) {
            gjmKaterMuaj(m6, m5, m4, m3, m2, m1);
        } else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 3) {
            gjmTreMuaj(m6, m5, m4, m3, m2, m1);
        } else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 2) {
            gjmDyMuaj(m6, m5, m4, m3, m2, m1);
        } else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 1) {
            gjmNjeMuaj(m6, m5, m4, m3, m2, m1);
        }

        chart.getData().clear();
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) - 4 + " " +
                Calendar.getInstance().get(Calendar.YEAR), m1));
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) - 3 + " " +
                Calendar.getInstance().get(Calendar.YEAR), m2));
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) - 2 + " " +
                Calendar.getInstance().get(Calendar.YEAR), m3));
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) - 1 + " " +
                Calendar.getInstance().get(Calendar.YEAR), m4));
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) + " " +
                Calendar.getInstance().get(Calendar.YEAR), m5));
        muajt.getData().add(new XYChart.Data(Calendar.getInstance().get(Calendar.MONTH) + 1 + " " +
                Calendar.getInstance().get(Calendar.YEAR), m6));
        chart.getData().add(muajt);
        for (int i = 0; i < 6; i++) {
            XYChart.Data item = (XYChart.Data) muajt.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue() + " €"));
        }

    }

    private void gjmPesMuaj(double m6, double m5, double m4, double m3, double m2, double m1) {
        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m6 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m5 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m4 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m3 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 3) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m2 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "12/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m1 += colPagesa.getCellData(i);
            }
        }
    }

    private void gjmKaterMuaj(double m6, double m5, double m4, double m3, double m2, double m1) {
        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m6 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m5 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m4 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m3 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "11/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m2 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "12/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m1 += colPagesa.getCellData(i);
            }
        }
    }

    private void gjmTreMuaj(double m6, double m5, double m4, double m3, double m2, double m1) {
        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m6 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m5 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m4 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "10/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m3 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "11/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m2 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "12/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m1 += colPagesa.getCellData(i);
            }
        }
    }

    private void gjmDyMuaj(double m6, double m5, double m4, double m3, double m2, double m1) {
        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m6 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH)) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m5 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "09/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m4 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "10/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m3 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "11/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m2 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "12/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m1 += colPagesa.getCellData(i);
            }
        }
    }

    private void gjmNjeMuaj(double m6, double m5, double m4, double m3, double m2, double m1) {
        for (int i = 0; i < tblPunet.getItems().size(); i++) {
            if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR))) {
                m6 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "08/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m5 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "09/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m4 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "10/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m3 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "11/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m2 += colPagesa.getCellData(i);
            } else if (((String) tblPunet.getColumns().get(2).getCellData(i)).substring(3, 10).equals(
                    "12/" + (Calendar.getInstance().get(Calendar.YEAR) - 1))) {
                m1 += colPagesa.getCellData(i);
            }
        }
    }

    private double mesatarja() {
        Double total = 0.0;
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*), sum(qmimi) from punet where kryer = 'po' and year(data) = " +
            Calendar.getInstance().get(Calendar.YEAR));
            while (rs.next()) {
                if (rs.getInt(2) > 0 && rs.getInt(1) > 0)
                    total = rs.getInt(2) / rs.getDouble(1);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }

    private int totalVitiAktual() {
        int total = 0;
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from punet where kryer = 'po'");
            while (rs.next()) {
                total = rs.getInt(1);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }

    private int totalPunet() {
        int total = 0;
        try {
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from punet");
            while (rs.next()) {
                total = rs.getInt(1);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }

    public void filtroFinancat(TextField puna) {
        try {
            String sql;
            if (!puna.getText().isEmpty())
                sql = "select * from Punet where lower(lloji) like lower('%" + puna.getText() + "%') or lower(konsumatori) like lower('%" + puna.getText() + "%'" +
                        ") order by data desc";
            else
                sql = "select * from Punet order by data desc";
            Connection conn = DriverManager.getConnection(CON_STR, "test", "test");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<TabelaPunet> data = FXCollections.observableArrayList();
            Format format = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                String d = format.format(rs.getDate("data"));
                data.add(new TabelaPunet(rs.getInt("id"), rs.getString("lloji").toUpperCase(), d,
                        rs.getFloat("qmimi"), rs.getString("konsumatori").toUpperCase(),
                        rs.getString("pershkrimi"), rs.getString("kryer"), rs.getString("makina").toUpperCase()));
            }
            tblPunet.getItems().clear();
            tblPunet.setItems(data);
            stmt.close();
            rs.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadThread(Label lbl) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbl.setText(totalVitiAktual() + " / " + totalPunet());
                    }
                });
                return null;
            }
        };
        Thread newT = new Thread(task);
        newT.setDaemon(true);
        newT.start();
    }

    public void loadThreadMes(Label lbl) {
        Task tMes = new Task<Void>() {
            DecimalFormat df = new DecimalFormat("#.00€");

            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbl.setText(df.format(mesatarja()) + "");
                    }
                });
                return null;
            }
        };
        Thread mesT = new Thread(tMes);
        mesT.setDaemon(true);
        mesT.start();
    }
}
