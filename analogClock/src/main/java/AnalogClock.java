import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AnalogClock extends Application {
    @Override
    public void start(Stage stage) {
        initUI(stage);
    }
    private void initUI(Stage stage) {
        float imageHeight=400;//размеры картники
        float imageWidth=400;
        float pivotX = imageWidth/2;//координаты центра
        float pivotY = imageHeight/2;

        Image image = new Image("clock.png", imageWidth, imageHeight, false, false);
        ImageView imageView = new ImageView(image);
        /*создаем стрелки                 -------------------*/
        Circle pivot = new Circle(pivotX, pivotY, 5);
        pivot.setFill(Color.DARKGRAY);
        Line hourHand = new Line(pivotX,pivotY,pivotX,pivotY-100);
        hourHand.setStroke(Color.ROYALBLUE);
        hourHand.setStrokeWidth(5);
        Line minuteHand = new Line(pivotX,pivotY,pivotX,pivotY-130);
        minuteHand.setStroke(Color.ROYALBLUE);
        minuteHand.setStrokeWidth(5);
        Line secondHand = new Line(pivotX,pivotY,pivotX,pivotY-150);
        secondHand.setStroke(Color.STEELBLUE);
        secondHand.setStrokeWidth(3);
        /*получаем время и задаем начальные углы стрелок-------*/
        GregorianCalendar calendar = new GregorianCalendar();
        System.out.println(calendar.getTime());
        System.out.println(calendar.get(Calendar.HOUR));

        double angleSecond=6*calendar.get(Calendar.SECOND);
        double angleMinute=6*calendar.get(Calendar.MINUTE)+angleSecond/60;
        double angleHour=30*calendar.get(Calendar.HOUR)+angleMinute/12;

        Rotate StartAngleSecond = new Rotate();
        StartAngleSecond.pivotXProperty().setValue(pivotX);
        StartAngleSecond.pivotYProperty().setValue(pivotY);
        StartAngleSecond.setAngle(angleSecond);
        secondHand.getTransforms().addAll(StartAngleSecond);

        Rotate StartAngleMinute = new Rotate();
        StartAngleMinute.pivotXProperty().setValue(pivotX);
        StartAngleMinute.pivotYProperty().setValue(pivotY);
        StartAngleMinute.setAngle(angleMinute);
        minuteHand.getTransforms().add(StartAngleMinute);

        Rotate StartAngleHour = new Rotate();
        StartAngleHour.pivotXProperty().setValue(pivotX);
        StartAngleHour.pivotYProperty().setValue(pivotY);
        StartAngleHour.setAngle(angleHour);
        hourHand.getTransforms().add(StartAngleHour);

        /* задаем анимацию--------------------------------*/
        Timeline tlHour = new Timeline();
        Timeline tlMinute = new Timeline();
        Timeline tlSecond = new Timeline();
        tlHour.setCycleCount(Timeline.INDEFINITE);
        tlMinute.setCycleCount(Timeline.INDEFINITE);
        tlSecond.setCycleCount(Timeline.INDEFINITE);

        KeyValue kH = new KeyValue(StartAngleHour.angleProperty(), angleHour+360);
        tlHour.getKeyFrames().addAll(new KeyFrame(Duration.hours(12), kH));
        KeyValue kM = new KeyValue(StartAngleMinute.angleProperty(), angleMinute+360);
        tlMinute.getKeyFrames().addAll(new KeyFrame(Duration.minutes(60), kM));
        KeyValue kS = new KeyValue(StartAngleSecond.angleProperty(), angleSecond+360);
        tlSecond.getKeyFrames().addAll(new KeyFrame(Duration.seconds(60), kS));


        tlHour.play();
        tlMinute.play();
        tlSecond.play();

        /* stage, scene---------------------------------------*/
        Pane root = new Pane();
        root.getChildren().addAll(imageView,hourHand,minuteHand,secondHand,pivot);

        Scene scene = new Scene(root, imageWidth, imageHeight);
        scene.setFill(null);//прозрачный задний фон

        stage.setTitle("AnalogClock");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT); //прозрачная рамка
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
