package com.example.sunsai.timer.common;

/**
 * Created by sunsai on 2016/1/29.
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public C _3;
    public ThreeTuple(A _1, B _2, C _3) {
        super(_1, _2);
        this._3 = _3;
    }
}
