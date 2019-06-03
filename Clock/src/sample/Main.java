package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circle = new Circle(100, 100, 100);
        circle.setCenterX(200.0f);
        circle.setCenterY(150.0f);
        circle.setFill(Paint.valueOf("#808080"));
        circle.setStroke(Paint.valueOf("#000000"));
        Label brand     = new Label("Rolegz");
        brand.layoutXProperty().bind(circle.centerXProperty().subtract(20));
        brand.layoutYProperty().bind(circle.centerYProperty().subtract(-45));
        Line hourHand = new Line(0, 0, 0, -50);
        hourHand.setTranslateX(200);
        hourHand.setTranslateY(150);
        hourHand.setStrokeWidth(2);
        Line minuteHand = new Line(0, 0,0 , -75);
        hourHand.setStroke(Paint.valueOf("#FFFFFF"));
        minuteHand.setTranslateX(200);
        minuteHand.setTranslateY(150);
        minuteHand.setStrokeWidth(2);
        minuteHand.setStroke(Paint.valueOf("#FFFFFF"));
        Line secondHand = new Line(0, 15, 0, -88);
        secondHand.setTranslateX(200);
        secondHand.setTranslateY(150);
        secondHand.setStrokeWidth(2);
        secondHand.setStroke(Paint.valueOf("#FFFFFF"));
        Circle spindle = new Circle(200, 150, 5);
        spindle.setFill(Paint.valueOf("#FFFFFF"));
        Group ticks = new Group();
        for (int i = 0; i < 12; i++) {
            Line tick = new Line(0, -83, 0, -93);
            tick.setTranslateX(200);
            tick.setTranslateY(150);
            tick.getStyleClass().add("tick");
            tick.getTransforms().add(new Rotate(i * (360 / 12)));
            tick.setStrokeWidth(2);
            ticks.getChildren().add(tick);
        }
        Group root = new Group(circle, hourHand, minuteHand, secondHand, spindle, ticks,brand);
        Calendar calendar = GregorianCalendar.getInstance();
        double seedSecondDegrees = calendar.get(Calendar.SECOND) * (360 / 60);
        double seedMinuteDegrees = (calendar.get(Calendar.MINUTE) + seedSecondDegrees / 360.0) * (360 / 60);
        double seedHourDegrees = (calendar.get(Calendar.HOUR) + seedMinuteDegrees / 360.0) * (360 / 12);
        Rotate hourRotate = new Rotate(seedHourDegrees);
        Rotate minuteRotate = new Rotate(seedMinuteDegrees);
        Rotate secondRotate = new Rotate(seedSecondDegrees);
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);
        Timeline hourTime = new Timeline(new KeyFrame(Duration.hours(12), new KeyValue(hourRotate.angleProperty(),
                360 + seedHourDegrees,
                Interpolator.LINEAR)));
        Timeline minuteTime = new Timeline(new KeyFrame(Duration.minutes(60), new KeyValue(minuteRotate.angleProperty(),
                360 + seedMinuteDegrees,
                Interpolator.LINEAR)));
        Timeline secondTime = new Timeline(new KeyFrame(Duration.seconds(60), new KeyValue(secondRotate.angleProperty(),
                360 + seedSecondDegrees,
                Interpolator.LINEAR)));
        hourTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        secondTime.setCycleCount(Animation.INDEFINITE);
        secondTime.play();
        minuteTime.play();
        hourTime.play();
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Clock");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
