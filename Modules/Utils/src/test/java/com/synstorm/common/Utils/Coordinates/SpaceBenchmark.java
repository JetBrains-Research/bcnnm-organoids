package com.synstorm.common.Utils.Coordinates;


import org.junit.Test;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EnumMap;

public class SpaceBenchmark {
    private final boolean writeToFile = true;
    private Space sp = new Space(100, new int[]{1, 1, 1, 1, 1, 10, 1, 1, 1, 1});

    @Test
    public void testMemory() throws Exception {
        long baseMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        ArrayList<Long> memHistory = new ArrayList<>();

        System.out.print("Base memory: ");
        System.out.println(baseMem);

        for(int x = 0; x < 50; ++x)
        {
            for(int y = 0; y < 50; ++y)
            {
                for(int z = 0; z < 50; ++z)
                {
                    int idx = (x*50*50 + y*50+ z);
                    if(idx % 10 == 0)
                    {
                        long currMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - baseMem;
                        memHistory.add(currMem);
                    }

                    sp.createObject(idx, new int[]{x, y, z}, ObjectState.NoVolume);
                }
            }
        }

        if(writeToFile) {
            FileWriter writer = new FileWriter("mem_step_10_only_obj.txt");
            for (Long val : memHistory) {
                writer.write(val.toString() + "\n");
            }
            writer.close();
        }
    }

    @Test
    public void testGetNeighbors() throws Exception {
        ArrayList<Long> allCoords = new ArrayList<>();
        ArrayList<Long> withState = new ArrayList<>();

        int coord[] = new int[]{10, 10, 10};

        for(int i = 0; i < 1000000; ++i)
        {
            long startTime = System.nanoTime();
            for(int j = 0; j < 10; ++j) {
                EnumMap<ObjectState, ArrayList<int[]>> tmp = sp.getNeighborsByState(coord, ObjectState.NoVolume);
            }
            long estimatedTime = System.nanoTime() - startTime;
            withState.add(estimatedTime);

            startTime = System.nanoTime();
            for(int j = 0; j < 10; ++j) {
                ArrayList<int[]> tmp = sp.getNeighborhood(coord);
            }
            estimatedTime = System.nanoTime() - startTime;
            allCoords.add(estimatedTime);
        }

        double allMean = allCoords.stream().mapToDouble(f -> f.doubleValue()).sum() / allCoords.size();
        double stateMean = withState.stream().mapToDouble(f -> f.doubleValue()).sum() / withState.size();

        System.out.printf("All coordinates mean: %f\n", allMean);
        System.out.printf("Coordinates with state mean: %f\n", stateMean);

        if(writeToFile) {
            FileWriter writer = new FileWriter("get_all_neighbors.txt");
            for (Long val : allCoords) {
                writer.write(val.toString() + "\n");
            }
            writer.close();

            writer = new FileWriter("get_neighbors_state.txt");
            for (Long val : withState) {
                writer.write(val.toString() + "\n");
            }
            writer.close();
        }
    }
}
