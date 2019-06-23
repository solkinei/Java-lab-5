import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AnalogClock extends Application {
    @Override
    public void start(Stage stage) {


        Image image = new Image("file:home.jpg", 714, 714, false, false);
        ImageView imageView = new ImageView(image);
        Calendar calendar = GregorianCalendar.getInstance();
        Circle face = new Circle (357,357,10);

        Line secondHand   = new Line(0, 60, 0, -300);
        secondHand.setTranslateX(357);
        secondHand.setTranslateY(357);
        secondHand.setStroke(Color.PERU);
        secondHand.setStrokeWidth(2);
        Line minuteHand   = new Line(0, 0, 0, -250);
        minuteHand.setTranslateX(357);
        minuteHand.setTranslateY(357);
        minuteHand.setStroke(Color.SADDLEBROWN);
        minuteHand.setStrokeWidth(4);
        Line hourHand   = new Line(0, 0, 0, -150);
        hourHand.setTranslateX(357);
        hourHand.setTranslateY(357);
        hourHand.setStroke(Color.SADDLEBROWN);
        hourHand.setStrokeWidth(10);

        double seedSecondDegrees  = calendar.get(Calendar.SECOND) * (360 / 60);
        double seedMinuteDegrees  = (calendar.get(Calendar.MINUTE) + seedSecondDegrees / 360.0) * (360 / 60);
        double seedHourDegrees    = (calendar.get(Calendar.HOUR)   + seedMinuteDegrees / 360.0) * (360 / 12) ;

        Rotate secondRotate    = new Rotate(seedSecondDegrees);
        Rotate minuteRotate    = new Rotate(seedMinuteDegrees);
        Rotate hourRotate      = new Rotate(seedHourDegrees);

        secondHand.getTransforms().add(secondRotate);
        minuteHand.getTransforms().add(minuteRotate);
        hourHand.getTransforms().add(hourRotate);

        final Timeline secondTime = new Timeline(

                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                secondRotate.angleProperty(),
                                360 + seedSecondDegrees,
                                Interpolator.LINEAR
                        )
                )
        );
        final Timeline minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                minuteRotate.angleProperty(),
                                360 + seedMinuteDegrees,
                                Interpolator.LINEAR
                        )
                )
        );
        Timeline hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hourRotate.angleProperty(),
                                360 + seedHourDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        secondTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        hourTime.setCycleCount(Animation.INDEFINITE);

        secondTime.play();
        minuteTime.play();
        hourTime.play();

        Group root = new Group(imageView, hourHand, minuteHand, secondHand, face);
        Scene scene = new Scene(root, 714,714);

        stage.setScene(scene);
        stage.show();

    }
    public static void main(String args[]) {
        launch(args);
    }
}
