package com.synstorm.common.Utils.Coordinates;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class SelectorTest {
    private Space sp = new Space(100, new int[]{1, 1, 1, 1, 1, 10, 1, 1, 1, 1});

    @Before
    public void initSpace() {
        int index = 1;
        for(int x = 30; x <= 60; ++x)
        {
            for(int y = 40; y <= 70; ++y)
            {
                for(int z = 50; z <= 80; ++z)
                {
                    sp.createObject(index, new int[]{x, y, z}, ObjectState.VolumeMovable);
                    ++index;
                }
            }
        }
    }

    @Test
    public void testTraumaSelector() {
        TIntSet valid = new TIntHashSet();

        for(int i = 1; i < 30*30*30; i += 2) {
            valid.add(i);
        }

        TraumaCellSelector selector = new TraumaCellSelector(sp);
        ArrayList<Integer> cells = selector.getCells(0.1, valid, new Random());
        assert(cells.stream().allMatch(valid::contains));
    }

    @Test
    public void testStrokeSelector() {
        TIntSet valid = new TIntHashSet();

        for(int i = 1; i < 30*30*30; i += 2) {
            valid.add(i);
        }

        StrokeCellSelector selector = new StrokeCellSelector(sp);

        ArrayList<Integer> cells = selector.getCells(0.1, valid, new Random());
        assert(cells.stream().allMatch(valid::contains));
    }

    @Test
    public void testAgingSelector() {
        TIntSet valid = new TIntHashSet();

        for(int i = 1; i < 30*30*30; i += 2) {
            valid.add(i);
        }

        AgingCellSelector selector = new AgingCellSelector(sp);

        while(selector.isAlive()) {
            ArrayList<Integer> cells = selector.getCells(5, valid, new Random());

            if(cells.isEmpty()) {
                System.out.println("Wow, all valid cells are dead!");
                break;
            }

            cells.forEach(p -> sp.removeObject(p));
        }
    }

    @Test
    public void testTraumaAging() {
        TIntSet valid = new TIntHashSet();

        for(int i = 1; i < 30*30*30; i += 2) {
            valid.add(i);
        }

        TraumaCellSelector selectorTrauma = new TraumaCellSelector(sp);
        AgingCellSelector selectorAge = new AgingCellSelector(sp);

        ArrayList<Integer> cellsTrauma = selectorTrauma.getCells(0.2, valid, new Random());
        cellsTrauma.forEach(p -> sp.removeObject(p));

        selectorAge.updateQuery();

        for(int i = 0; i < 50; ++i) {
            ArrayList<Integer> cells = selectorAge.getCells(5, valid, new Random());

            if(cells.isEmpty()) {
                System.out.println("Wow, all valid cells are dead!");
                break;
            }

            cells.forEach(p -> sp.removeObject(p));
        }

        cellsTrauma = selectorTrauma.getCells(0.1, valid, new Random());
        cellsTrauma.forEach(p -> sp.removeObject(p));

        selectorAge.updateQuery();

        for(int i = 0; i < 50; ++i) {
            ArrayList<Integer> cells = selectorAge.getCells(5, valid, new Random());

            if(cells.isEmpty()) {
                System.out.println("Wow, all valid cells are dead!");
                break;
            }

            cells.forEach(p -> sp.removeObject(p));
        }
    }
}
