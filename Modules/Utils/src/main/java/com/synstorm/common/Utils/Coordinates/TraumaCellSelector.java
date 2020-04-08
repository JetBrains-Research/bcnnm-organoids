package com.synstorm.common.Utils.Coordinates;

import gnu.trove.set.TIntSet;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Random;


public class TraumaCellSelector extends CellSelector {
    public TraumaCellSelector(Space sp) {
        super(sp);
    }

    public ArrayList<Integer> getCells(double ratio, TIntSet valid, Random rnd) {
        ArrayList<Integer> res = new ArrayList<>();

        int[] borders = sp.getBorderlines();
        int[] target = getPointOnSurface(borders, rnd);

        int maxDist = (int) Math.round(Math.pow(ratio * (borders[1] - borders[0]) * (borders[3] - borders[2]) * (borders[5] - borders[4]), 1. / 3));

        ArrayList<Pair<Integer, Integer>> dists = sp.getNearestObjects(target);

        dists.forEach((p) -> {
            if (valid.contains(p.getKey()) && p.getValue() <= maxDist) {
                res.add(p.getKey());
            }
        });

        return res;
    }

    private int[] getPointOnSurface(int bounds[], Random rnd) {
        final int[] point = new int[3];

        final int side = rnd.nextInt(6);
        final int c = side % 3;
        final int origin1 = bounds[((c + 1) % 3) * 2];
        final int origin2 = bounds[((c + 2) % 3) * 2];
        final int bound1 = bounds[((c + 1) % 3) * 2 + 1] - origin1;
        final int bound2 = bounds[((c + 2) % 3) * 2 + 1] - origin2;

        point[c] = bounds[side];
        point[(c + 1) % 3] = origin1 + rnd.nextInt(bound1); // ThreadLocalRandom.current().nextInt(bounds[((c + 1) % 3) * 2], bounds[((c + 1) % 3) * 2 + 1]);
        point[(c + 2) % 3] = origin2 + rnd.nextInt(bound2); //ThreadLocalRandom.current().nextInt(bounds[((c + 2) % 3) * 2], bounds[((c + 2) % 3) * 2 + 1]);

        return point;
    }
}
