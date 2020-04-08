package com.synstorm.common.Utils.Coordinates;

import gnu.trove.set.TIntSet;

import java.util.ArrayList;
import java.util.Random;

public abstract class CellSelector {
    protected Space sp;

    CellSelector(Space sp_) {
        sp = sp_;
    }

    abstract public ArrayList<Integer> getCells(double ratio, TIntSet valid, Random rnd);
}
