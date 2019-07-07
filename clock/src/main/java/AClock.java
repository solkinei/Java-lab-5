
import javafx.animation.*;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.net.URL;



public class AClock extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Analog clock");
        //setting background
        URL background = getClass().getResource("clock.jpg");
        Image im = new Image(background.toString(), 500, 500, false, false);
        ImageView iv = new ImageView(im);

        //getting current time and setting degrees for rotation
        Calendar myCalendar = new GregorianCalendar();
        double secDegr = myCalendar.get(Calendar.SECOND) * (360 / 60);
        double minDegr = (myCalendar.get(Calendar.MINUTE) + secDegr / 360.0) * (360 / 60);
        double hourDegr = (myCalendar.get(Calendar.HOUR) + minDegr / 360) * (360 / 12);

        //creating hands of the clock
        Line hour, min, sec;
        hour = new Line(0, 0, 0, -160);
        Color hourColor = new Color(0.90,0.09,0.24,1.0);
        hour.setStroke(hourColor);
        hour.setStrokeWidth(10);
        hour.setTranslateX(250);
        hour.setTranslateY(280);

        /*URL hourHand = getClass().getResource("hand.png");
        Image hH = new Image(hourHand.toString(), 20, 160, false, false);
        ImageView hV = new ImageView(hH);
        hV.setTranslateX(290);
        hV.setTranslateY(105);
*/

        min = new Line(0, 0, -0, -190);
        min.setStroke(Color.DARKBLUE);
        min.setStrokeWidth(5);
        min.setTranslateX(250);
        min.setTranslateY(280);

        sec = new Line(0, 0, -0, -220);
        sec.setStroke(Color.WHITE);
        sec.setStrokeWidth(3);
        sec.setTranslateX(250);
        sec.setTranslateY(280);

        Circle circle = new Circle();
        circle.setFill(hourColor);
        circle.setCenterX(250);
        circle.setCenterY(250);
        circle.setRadius(10);

        //setting rotation
        Rotate rotateSec = new Rotate();
        Rotate rotateMin = new Rotate();
        Rotate rotateHour = new Rotate();
        rotateSec.setAngle(secDegr);
        rotateSec.setPivotX(0);
        rotateSec.setPivotY(-30);
        sec.getTransforms().addAll(rotateSec);

        rotateMin.setAngle(minDegr);
        rotateMin.setPivotX(0);
        rotateMin.setPivotY(-30);
        min.getTransforms().addAll(rotateMin);

        rotateHour.setAngle(hourDegr);
        rotateHour.setPivotX(0);
        rotateHour.setPivotY(-30);
        //rotateHour.setPivotY(-5);
        hour.getTransforms().addAll(rotateHour);
        //hV.getTransforms().addAll(rotateHour);

        //creating timelines for each hand
        final Timeline secTimeline = new Timeline(

                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                rotateSec.angleProperty(),
                                360 + secDegr,
                                Interpolator.LINEAR
                        )
                )
        );

        final Timeline minTimeline = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                rotateMin.angleProperty(),
                                360 + minDegr,
                                Interpolator.LINEAR
                        )
                )
        );
        final Timeline hourTimeline = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                rotateHour.angleProperty(),
                                360 + hourDegr,
                                Interpolator.LINEAR
                        )
                )
        );
        secTimeline.setCycleCount(Animation.INDEFINITE);
        minTimeline.setCycleCount(Animation.INDEFINITE);
        hourTimeline.setCycleCount(Animation.INDEFINITE);

        secTimeline.play();
        minTimeline.play();
        hourTimeline.play();


        Group root = new Group(iv, sec, min, hour, circle);
       // Group root = new Group(iv, min, sec, hV);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();


    }
}
