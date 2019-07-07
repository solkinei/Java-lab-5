package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int START_X = 150;
    private static final int START_Y = 150;
    private static final int CIRCLE_RADIUS = 150;
    private static final int SEC_LENGTH = 150;
    private static final int MIN_LENGTH = 130;
    private static final int HOUR_LENGTH = 110;
    private static final int HOUR_STEP = 30;
    private static final int MIN_SEC_STEP = 6;

    private ClockTimer mClockTimer;
    private Line lineSec;
    private Line lineMin;
    private Line lineHour;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mClockTimer = new ClockTimer(5000);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Circle circle = new Circle();
        circle.setFill(Color.AZURE);
        circle.setStrokeWidth(3);
        circle.setStroke(Color.BLACK);
        circle.setRadius(CIRCLE_RADIUS);
        circle.setCenterX(START_X);
        circle.setCenterY(START_Y);
        ((Pane) root).getChildren().add(circle);


        lineSec = new Line();
        lineSec.setStartX(START_X);
        lineSec.setEndX(START_X);
        lineSec.setStartY(START_Y);
        lineSec.setEndY(START_Y - SEC_LENGTH);
        lineSec.setStroke(Color.RED);
        ((Pane) root).getChildren().add(lineSec);

        lineMin = new Line();
        lineMin.setStartX(START_X);
        lineMin.setEndX(START_X);
        lineMin.setStartY(START_Y);
        lineMin.setEndY(START_Y - MIN_LENGTH);
        lineMin.setStroke(Color.BLUE);
        ((Pane) root).getChildren().add(lineMin);

        lineHour = new Line();
        lineHour.setStartX(START_X);
        lineHour.setEndX(START_X);
        lineHour.setStartY(START_Y);
        lineHour.setEndY(START_Y - HOUR_LENGTH);
        lineHour.setStroke(Color.GREEN);
        ((Pane) root).getChildren().add(lineHour);


        primaryStage.setTitle("Clock");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> onSecondChange()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void onSecondChange() {
        System.out.println("");
        System.out.println("Секунда прошла");
        mClockTimer.addSecond();
        System.out.println("Прошло секунд всего = " + mClockTimer.getTime());
        System.out.println("Прошло часов = " + mClockTimer.getHours());
        System.out.println("Прошло минут = " + mClockTimer.getMinutes());
        System.out.println("Прошло секунд = " + mClockTimer.getSeconds());

        lineSec.setEndX(SEC_LENGTH * Math.cos((mClockTimer.getSeconds() * MIN_SEC_STEP - 90) * Math.PI / 180) + 150);
        lineSec.setEndY(SEC_LENGTH * Math.sin((mClockTimer.getSeconds() * MIN_SEC_STEP - 90) * Math.PI / 180) + 150);

        lineMin.setEndX(MIN_LENGTH * Math.cos((mClockTimer.getMinutes() * MIN_SEC_STEP - 90) * Math.PI / 180) + 150);
        lineMin.setEndY(MIN_LENGTH * Math.sin((mClockTimer.getMinutes() * MIN_SEC_STEP - 90) * Math.PI / 180) + 150);

        lineHour.setEndX(HOUR_LENGTH * Math.cos((mClockTimer.getHours() * HOUR_STEP - 90) * Math.PI / 180) + 150);
        lineHour.setEndY(HOUR_LENGTH * Math.sin((mClockTimer.getHours() * HOUR_STEP - 90) * Math.PI / 180) + 150);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
