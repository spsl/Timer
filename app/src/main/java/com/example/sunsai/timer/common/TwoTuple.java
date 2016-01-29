package com.example.sunsai.timer.common;

/**
 * Created by sunsai on 2016/1/29.
 */
public class TwoTuple<T, K> extends Tuple<T> {
    public K _2;
    public TwoTuple(T _1, K _2) {
        super(_1);
        this._2 = _2;
    }
}
