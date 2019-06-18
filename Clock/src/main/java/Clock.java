import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Clock extends Application {

    @Override
    public void start(Stage stage) {

        Image image = new Image("file:face.jpg", 600, 600, false, false);
        ImageView imageView = new ImageView(image);

        Line hourHand = new Line(0, 0, 0, -150);
        hourHand.setTranslateX(300);
        hourHand.setTranslateY(300);
        hourHand.setStroke(Paint.valueOf("#000000"));
        hourHand.setStrokeWidth(8);

        Line minuteHand = new Line(0, 0, 0, -200);
        minuteHand.setTranslateX(300);
        minuteHand.setTranslateY(300);
        minuteHand.setStroke(Paint.valueOf("#000000"));
        minuteHand.setStrokeWidth(5);

        Line secondHand = new Line(0, 50, 0, -250);
        secondHand.setTranslateX(300);
        secondHand.setTranslateY(300);
        secondHand.setStroke(Paint.valueOf("#000000"));
        secondHand.setStrokeWidth(2);

        Circle center = new Circle(300, 300, 10);
        center.setFill(Paint.valueOf("#000000"));

        Group root = new Group(imageView, hourHand, minuteHand, secondHand, center);

        Calendar calendar = GregorianCalendar.getInstance();
        double secondDegrees = calendar.get(Calendar.SECOND) * (360 / 60);
        double minuteDegrees = (calendar.get(Calendar.MINUTE) + secondDegrees / 360) * (360 / 60);
        double hourDegrees = (calendar.get(Calendar.HOUR) + minuteDegrees / 360) * (360 / 12);
        Rotate hourRotate = new Rotate(hourDegrees);
        Rotate minuteRotate = new Rotate(minuteDegrees);
        Rotate secondRotate = new Rotate(secondDegrees);

        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        Timeline hourTime = new Timeline(
                new KeyFrame(Duration.hours(12),
                        new KeyValue(hourRotate.angleProperty(),
                                360 + hourDegrees, Interpolator.LINEAR))
        );

        Timeline minuteTime = new Timeline(
                new KeyFrame(Duration.minutes(60),
                        new KeyValue(minuteRotate.angleProperty(),
                                360 + minuteDegrees, Interpolator.LINEAR))
        );

        Timeline secondTime = new Timeline(
                new KeyFrame(Duration.seconds(60),
                        new KeyValue(secondRotate.angleProperty(),
                                360 + secondDegrees, Interpolator.LINEAR))
        );

        hourTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        secondTime.setCycleCount(Animation.INDEFINITE);

        hourTime.play();
        minuteTime.play();
        secondTime.play();

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Clock");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}