package com.jcpallavicino.sample.myrecyclerviewsample.Utils;

/**
 * Created by jfvazquez on 04/03/2016.
 */
public interface Callback {
    public void starting();
    public void completed(String res, int responseCode);
    public void completedWithErrors(Exception e);
    public void update();
}
