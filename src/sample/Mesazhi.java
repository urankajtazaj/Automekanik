package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

/**
 * Created by urankajtazaj on 10/10/16.
 */
public class Mesazhi {
    Stage stage = new Stage();
    public Mesazhi(String titulli, String titulli_msg, String mesazhi){
        stage.setTitle(titulli);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        HBox root = new HBox(15);
        VBox sub_root = new VBox(10);
        HBox btn = new HBox();
        Text ttl = new Text(titulli_msg);
        ttl.setFont(Font.font(16));
        Button btnOk = new Button("Ne rregull");
        btn.getChildren().add(btnOk);
        btn.setAlignment(Pos.CENTER_RIGHT);

        btnOk.setOnAction(e -> stage.close());
        btnOk.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) stage.close();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        root.setPadding(new Insets(20));
        sub_root.getChildren().addAll(ttl, new Label(mesazhi), btn);
        if (titulli == "Gabim")
            root.getChildren().add(new ImageView(new Image("/sample/foto/error.png")));
        else if (titulli == "Sukses")
            root.getChildren().add(new ImageView(new Image("/sample/foto/success.png")));
        else if (titulli == "Informacion")
            root.getChildren().add(new ImageView(new Image("/sample/foto/question.png")));
        else if (titulli == "Info")
            root.getChildren().add(new ImageView(new Image("/sample/foto/info.png")));
        root.getChildren().add(sub_root);
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, 450, 150);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public Mesazhi(String titulli, String titulli_msg, String mesazhi, ShikoPunetoret sp, DritarjaKryesore dk){
        stage.setTitle(titulli);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        Button btnAnulo = new Button("Anulo");
        HBox root = new HBox(15);
        VBox sub_root = new VBox(10);
        HBox btn = new HBox(5);
        Text ttl = new Text(titulli_msg);
        ttl.setFont(Font.font(16));
        Button btnOk = new Button("Ne rregull");
        btn.getChildren().addAll(btnAnulo, btnOk);
        btn.setAlignment(Pos.CENTER_RIGHT);

        btnOk.setOnAction(e -> {
            sp.fshi(sp.strEmri.toLowerCase() + sp.strMbiemri.toLowerCase());
            stage.close();
        });
        btnOk.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) stage.close();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        btnAnulo.setOnAction(e -> stage.close());

        root.setPadding(new Insets(20));
        sub_root.getChildren().addAll(ttl, new Label(mesazhi), btn);
        if (titulli == "Gabim")
            root.getChildren().add(new ImageView(new Image("/sample/foto/error.png")));
        else if (titulli == "Sukses")
            root.getChildren().add(new ImageView(new Image("/sample/foto/success.png")));
        else if (titulli == "Informacion")
            root.getChildren().add(new ImageView(new Image("/sample/foto/question.png")));
        else if (titulli == "Info")
            root.getChildren().add(new ImageView(new Image("/sample/foto/info.png")));
        root.getChildren().add(sub_root);
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, 450, 150);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        btnOk.isFocused();
        stage.setScene(scene);
        stage.show();
    }

    public Mesazhi(String titulli, String titulli_msg, String mesazhi, ShikoKonsumatoret sk, int skId){
        stage.setTitle(titulli);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        Button btnAnulo = new Button("Anulo");
        HBox root = new HBox(15);
        VBox sub_root = new VBox(10);
        HBox btn = new HBox(5);
        Text ttl = new Text(titulli_msg);
        ttl.setFont(Font.font(16));
        Button btnOk = new Button("Ne rregull");
        btn.getChildren().addAll(btnAnulo, btnOk);
        btn.setAlignment(Pos.CENTER_RIGHT);

        btnOk.setOnAction(e -> {
            sk.fshi(skId);
            stage.close();
        });
        btnOk.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) stage.close();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        btnAnulo.setOnAction(e -> stage.close());

        root.setPadding(new Insets(20));
        sub_root.getChildren().addAll(ttl, new Label(mesazhi), btn);
        if (titulli == "Gabim")
            root.getChildren().add(new ImageView(new Image("/sample/foto/error.png")));
        else if (titulli == "Sukses")
            root.getChildren().add(new ImageView(new Image("/sample/foto/success.png")));
        else if (titulli == "Informacion")
            root.getChildren().add(new ImageView(new Image("/sample/foto/question.png")));
        else if (titulli == "Info")
            root.getChildren().add(new ImageView(new Image("/sample/foto/info.png")));
        root.getChildren().add(sub_root);
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, 450, 150);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        btnOk.isFocused();
        stage.setScene(scene);
        stage.show();
    }

    public Mesazhi(String titulli, String titulli_msg, String mesazhi, DritarjaKryesore sp){
        stage.setTitle(titulli);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        Button btnAnulo = new Button("Anulo");
        HBox root = new HBox(15);
        VBox sub_root = new VBox(10);
        HBox btn = new HBox(5);
        Text ttl = new Text(titulli_msg);
        ttl.setFont(Font.font(16));
        Button btnOk = new Button("Ne rregull");
        btn.getChildren().addAll(btnAnulo, btnOk);
        btn.setAlignment(Pos.CENTER_RIGHT);

        btnOk.setOnAction(e -> {
            sp.fshi();
            stage.close();
        });
        btnOk.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) stage.close();
            else if (e.getCode().equals(KeyCode.ESCAPE)) stage.close();
        });

        btnAnulo.setOnAction(e -> stage.close());

        root.setPadding(new Insets(20));
        sub_root.getChildren().addAll(ttl, new Label(mesazhi), btn);
        if (titulli == "Gabim")
            root.getChildren().add(new ImageView(new Image("/sample/foto/error.png")));
        else if (titulli == "Sukses")
            root.getChildren().add(new ImageView(new Image("/sample/foto/success.png")));
        else if (titulli == "Informacion")
            root.getChildren().add(new ImageView(new Image("/sample/foto/question.png")));
        else if (titulli == "Info")
            root.getChildren().add(new ImageView(new Image("/sample/foto/info.png")));
        root.getChildren().add(sub_root);
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root, 450, 150);
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        btnOk.isFocused();
        stage.setScene(scene);
        stage.show();
    }
}
