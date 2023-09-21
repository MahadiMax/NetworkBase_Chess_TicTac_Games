package Animators;

import javafx.animation.AnimationTimer;

abstract class Animator extends AnimationTimer {

    private static final double ENDTIME = 550000000;

    long startTime;


    Animator(){
        startTime = System.nanoTime();
    }

    @Override
    public void handle(long l){
        double percent = percentPast(l);
        if(percent >= 1)
            onEnd();
        else
            tick(percent);
    }

    private double percentPast(long l){
         return (l - startTime)/ENDTIME;
    }

    void onEnd(){}

    abstract void tick(double percent);


}
