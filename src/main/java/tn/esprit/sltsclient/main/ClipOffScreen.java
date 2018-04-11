package tn.esprit.sltsclient.main;

import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.image.Image;

public class ClipOffScreen extends Application {

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // background image
        ImageView imageView = new ImageView( "http://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/800px-Siberischer_tiger_de_edit02.jpg");

        // some text
        Label label = new Label( "This is a Tiger. Tigers are awesome!");
        label.relocate(20, 400);
        label.setTextFill(Color.RED);
        label.setFont(new Font("Tahoma", 48));

        root.getChildren().addAll( imageView, label);

        Scene scene = new Scene( root, 1024, 768);

        primaryStage.setScene( scene);
        primaryStage.show();

        // pane with clipped area
        CirclePane circlePane = new CirclePane();
        makeDraggable( circlePane);

        root.getChildren().addAll( circlePane);

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                circlePane.scroll();                
            }

        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private class CirclePane extends Pane {

        double y = 0;
        ImageView clippedView;
        ImageView imageView;
        Circle circle;
        SnapshotParameters parameters;
        WritableImage wim = null;

        boolean direction = true;

        public CirclePane() {

            Pane offScreenPane = new Pane();

            // background image
           
            String localUrl = null;
    		File file = new File("C:/wamp64/www/ImagesItradeit/41.jpg");
            try {
               localUrl = file.toURI().toURL().toString();
           } catch (MalformedURLException ex) {
               //Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
            Image image = new Image(localUrl);
     
            // background image
            ImageView imageView2 = new ImageView();
            imageView2.setImage(image);
            // some text
            Text text = new Text( "Let the sun shine!");
            text.relocate(180, 220);
            text.setFill(Color.YELLOW);
            text.setFont(new Font("Tahoma", 18));

            offScreenPane.getChildren().addAll( imageView2, text);

            // non-clip area should be transparent
            parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT); 

            WritableImage wim = null;

            // load image
            wim = offScreenPane.snapshot( parameters, wim);

            imageView = new ImageView( wim);

            // create circle
            circle = new Circle( 100);
            circle.relocate(200, 100);

            // clip image by circle
            imageView.setClip(circle);



            // new image from clipped image
            wim = null;
            wim = imageView.snapshot(parameters, wim);

            // new imageview
            clippedView = new ImageView( wim);

            // some shadow
            clippedView.setEffect(new DropShadow(15, Color.BLACK));

            clippedView.relocate( 150, 100);

            getChildren().addAll( clippedView);

        }

        public void scroll() {

            if( direction) {
                y++;
                if( y > 100) {
                    direction = false;
                }
            } else {
                y--;
                if( y < 0) {
                    direction = true;
                }
            }

            circle.relocate(150, 100 + y);
            imageView.setClip(circle);
            wim = imageView.snapshot(parameters, wim);
            clippedView.setImage( wim);
        }

    }

    // make node draggable
    class DragContext { 
        double x;
        double y; 
    } 

    public void makeDraggable( Node node) {

        final DragContext dragDelta = new DragContext();

        node.setOnMousePressed(mouseEvent -> {

            dragDelta.x = node.getBoundsInParent().getMinX() - mouseEvent.getScreenX();
            dragDelta.y = node.getBoundsInParent().getMinY() - mouseEvent.getScreenY();

        });

        node.setOnMouseDragged(mouseEvent -> node.relocate( mouseEvent.getScreenX() + dragDelta.x, mouseEvent.getScreenY() + dragDelta.y));

    }


}