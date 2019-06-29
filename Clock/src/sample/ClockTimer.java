package sample;

public class ClockTimer {

    private static final int DAY_IN_SEC = 12 * 60 * 60;
    private static final int HOUR_IN_SEC = 60 * 60;
    private static final int MIN_IN_SEC = 60;

    private int mTime = 0;

    public ClockTimer(int time) {
        if (time < DAY_IN_SEC) mTime = time;
        else mTime = 0;
    }

    public int getTime() {
        return mTime;
    }

     public void addSecond() {
         mTime++;
         if (mTime >= DAY_IN_SEC) mTime = 0;
     }

    int getSeconds() {
        return mTime - getHours() * HOUR_IN_SEC - getMinutes() * MIN_IN_SEC;
    }

    int getMinutes() {
        return (mTime - getHours() * HOUR_IN_SEC) / MIN_IN_SEC;
    }

    int getHours() {
        return mTime / HOUR_IN_SEC;
    }
}
