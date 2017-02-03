package sample;

import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;

/**
 * Created by urankajtazaj on 13/11/16.
 */
public class RrethProgramit {
    Stage stage = new Stage();

    public RrethProgramit(){
        stage.setResizable(false);
        stage.setTitle("Rreth programit");
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox(10);

        Hyperlink hl = new Hyperlink("uran.kajtazaj");
        Label lblEmail = new Label("uran.kajtazaj@hotmail.com");
        Hyperlink twitter = new Hyperlink("@ur_uran");

        hl.setGraphic(new ImageView(new Image("/sample/foto/facebook.png")));
        twitter.setGraphic(new ImageView(new Image("/sample/foto/twitter.png")));
        lblEmail.setGraphic(new ImageView(new Image("/sample/foto/email.png")));

        HBox hb = new HBox(5);
        hb.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #DDDDDD; -fx-padding: 5 0 0 0");
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(hl, twitter, lblEmail);

        twitter.setOnAction(e -> {
            if (Desktop.isDesktopSupported()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Desktop.getDesktop().browse(new URI("https://twitter.com/ur_uran"));
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        hl.setOnAction(e -> {
            if (Desktop.isDesktopSupported()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Desktop.getDesktop().browse(new URI("https://www.facebook.com/uran.kajtazaj"));
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        ImageView foto = new ImageView(new Image("/sample/foto/java.png"));
        Label lblTitulli = new Label("Automekanik Software");
//        lblTitulli.contentDisplayProperty().setValue(ContentDisplay.RIGHT);
        lblTitulli.setTextFill(javafx.scene.paint.Paint.valueOf("#297CBC"));
        Label lblContent = new Label("© Copyright Uran Kajtazaj, Kosove, 2016. \nTe gjitha te drejtat e rezervuara.");

        lblTitulli.setStyle("-fx-font-size: 28;");

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(lblTitulli, lblContent, hb);

        Scene scene = new Scene(root, 480, 170);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE) || e.getCode().equals(KeyCode.ENTER)){
                stage.close();
            }
        });
        scene.getStylesheets().add(getClass().getResource("/sample/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
