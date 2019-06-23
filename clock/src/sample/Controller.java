package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import javafx.event.EventHandler;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Controller {

    @FXML
    Pane myClock;

    @FXML
    ImageView myImge;

    @FXML
    Line headHour;

    @FXML
    Line headMinute;

    @FXML
    Line headSec;

    @FXML
    Line headMounth;

    @FXML
    Label date;

    @FXML
    Label text;

    Calendar myCalendar = new GregorianCalendar();

    Timeline checkTime = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public
        void handle ( ActionEvent event ) {
            rotation();
            //myCalendar.add(Calendar.SECOND, +1);
        }
    }));


    public void rotation(){
        Rotate rotateSecond = new Rotate();
        Rotate rotateMinute = new Rotate();
        Rotate rotateHour = new Rotate();
        rotateSecond.setAngle(6.0);
        rotateSecond.setPivotX(0);
        rotateSecond.setPivotY(0);
        headSec.getTransforms().addAll(rotateSecond);


        rotateMinute.setAngle(6.0 / 60.0);
        rotateMinute.setPivotX(0);
        rotateMinute.setPivotY(0);
        headMinute.getTransforms().addAll(rotateMinute);

        rotateHour.setAngle(-360.0/12.0/60.0/60.0);
        rotateHour.setPivotX(0);
        rotateHour.setPivotY(0);
        headHour.getTransforms().addAll(rotateHour);
    }

    @FXML
    private void initialize(){
        URL url = getClass().getResource("/anticlock.jpg");
        myImge.setImage(new Image(String.valueOf(url)));
        Rotate rotateSecond = new Rotate();
        Rotate rotateMinute = new Rotate();
        Rotate rotateHour = new Rotate();
        Rotate rotateMounth = new Rotate();
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        String[] strDays = new String[] { "Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday", "Saturday" };
        date.setText("Today is " + day + "\n" + strDays[myCalendar.get(Calendar.DAY_OF_WEEK) - 1]);
        if(6 - myCalendar.get(Calendar.DAY_OF_WEEK) < 0){
            text.setText("Just relax and drink your BEER!");
        }
        if(6 - myCalendar.get(Calendar.DAY_OF_WEEK) < 0){
            text.setText("BEERS' TIME!");
        } else {
            text.setText("KEEP CALM!\n" +
                    "Friday will come in " + (6 - myCalendar.get(Calendar.DAY_OF_WEEK))
            + " days!");
        }
        


        int seconds = myCalendar.get(Calendar.SECOND);
        int  minutes = myCalendar.get(Calendar.MINUTE);
        int hour = myCalendar.get(Calendar.HOUR);
        int mounth = myCalendar.get(Calendar.MONTH);
        checkTime.setCycleCount(Timeline.INDEFINITE);
        checkTime.play();

        rotateSecond.setAngle(6.0 * (double)seconds);
        rotateSecond.setPivotX(0);
        rotateSecond.setPivotY(0);
        headSec.getTransforms().addAll(rotateSecond);


        rotateMinute.setAngle(6.0 * ((double)minutes + (double)seconds/60.0));
        rotateMinute.setPivotX(0);
        rotateMinute.setPivotY(0);
        headMinute.getTransforms().addAll(rotateMinute);

        rotateHour.setAngle(-360/12 * ((double)hour + (double)minutes/60.0));
        rotateHour.setPivotX(0);
        rotateHour.setPivotY(0);
        headHour.getTransforms().addAll(rotateHour);

        rotateMounth.setAngle((double)360/12 * (double)mounth);
        rotateMounth.setPivotX(0);
        rotateMounth.setPivotY(0);
        headMounth.getTransforms().setAll(rotateMounth);

    }






}
