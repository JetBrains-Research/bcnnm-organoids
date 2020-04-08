package com.synstorm.common.Utils.Coordinates;

import gnu.trove.set.TIntSet;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StrokeCellSelector extends CellSelector {
    private int strokeSize;

    StrokeCellSelector(Space sp) {
        super(sp);
        strokeSize = 3;
    }

    StrokeCellSelector(Space sp, int strokeSize_) {
        super(sp);
        strokeSize = strokeSize_;
    }

    public ArrayList<Integer> getCells(double ratio, TIntSet valid, Random rnd) {
        HashSet<Integer> res = new HashSet<>(); // we need to check whether cell is dead already. Set in quicker
        final int totalCount = (int)Math.round(ratio*sp.getCellCoint());

        int[] borders = sp.getBorderlines();
        while(res.size() < totalCount) {
            int[] point = getRandomPoint(borders);

            ArrayList<Pair<Integer, Integer>> dists = sp.getNearestObjects(point);

            for(Pair<Integer, Integer> curr : dists)
            {
                if(curr.getValue() > strokeSize) { // we know that array is sorted, just break if the value is larger
                    break;
                }

                if(valid.contains(curr.getKey()) && !res.contains(curr.getKey()) && res.size() < totalCount) {
                    res.add(curr.getKey());
                }
            }
        }

        return new ArrayList<>(res);
    }

    private int[] getRandomPoint(int[] borders) {
        int[] point = new int[3];

        point[0] = ThreadLocalRandom.current().nextInt(borders[0], borders[1] + 1);
        point[1] = ThreadLocalRandom.current().nextInt(borders[2], borders[3] + 1);
        point[2] = ThreadLocalRandom.current().nextInt(borders[4], borders[5] + 1);

        return point;
    }
}
