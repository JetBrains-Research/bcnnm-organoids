package com.synstorm.common.Utils.SpaceUtils;

import org.junit.Before;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.List;
import java.util.Random;

/**
 * Tests for correct Coordinate utils implementation
 * Created by dvbozhko on 10/22/15.
 */
public class PerformanceByteCoordinate {

    @Before
    public void setUp() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(351);
    }

    @Test
    public void testCreationOld() throws Exception {
        System.out.println("Filling coordinate cache old");
        long startTime = System.nanoTime();
        fillCache();
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Filling coordinate cache old - Done!");
        System.out.printf("We spent %d ns\n", estimatedTime);
        System.out.printf("We have %d cached count\n", CoordinateUtils.INSTANCE.getCachedCount());
    }

    @Test
    public void testCreationNew() throws Exception {
        System.out.println("Creating coordinates new way");

        long startTime = System.nanoTime();
        fillByteCache();
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Creating coordinates new way - Done!");
        System.out.printf("We spent %d ns\n", estimatedTime);
    }

    @Test
    public void testCreationNewNeighbors() throws Exception {
        System.out.println("Creating coordinates new way");

        long startTime = System.nanoTime();
        fillByteCacheNeighbors();
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Creating coordinates new way - Done!");
        System.out.printf("We spent %d ns\n", estimatedTime);
    }

    @Test
    public void testGetFromCacheOld() throws Exception {
        System.out.println("Getting from old cache");

        long startTime = System.nanoTime();
        getFromCache();
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Getting from old cache - Done!");
        System.out.printf("We spent %d ns\n", estimatedTime);
    }

    @Test
    public void testGetFromCacheNeighborsOld() throws Exception {
        System.out.println("Filling coordinate cache old");

        long startTime = System.nanoTime();
        fillCache();
        long estimatedTime = System.nanoTime() - startTime;

        System.out.printf("We have %d cached count\n", CoordinateUtils.INSTANCE.getCachedCount());
        System.out.printf("We spent %d ns\n", estimatedTime);
        System.out.println("Getting from old cache");

        startTime = System.nanoTime();
        getFromCacheNeighbors();
        estimatedTime = System.nanoTime() - startTime;

        System.out.println("Getting from old cache - Done!");
        System.out.printf("We have %d cached count\n", CoordinateUtils.INSTANCE.getCachedCount());
        System.out.printf("We have %d gets from cache count\n", CoordinateUtils.INSTANCE.getGetFromCacheCount());
        System.out.printf("We spent %d ns\n", estimatedTime);

        System.out.println("Getting from old cache round 2");

        startTime = System.nanoTime();
        getFromCacheNeighbors();
        estimatedTime = System.nanoTime() - startTime;

        System.out.println("Getting from old cache - Done!");
        System.out.printf("We have %d cached count\n", CoordinateUtils.INSTANCE.getCachedCount());
        System.out.printf("We have %d gets from cache count\n", CoordinateUtils.INSTANCE.getGetFromCacheCount());
        System.out.printf("We spent %d ns\n", estimatedTime);

        System.out.println("Getting from old cache round 3");

        startTime = System.nanoTime();
        getFromCacheNeighbors();
        estimatedTime = System.nanoTime() - startTime;

        System.out.println("Getting from old cache - Done!");
        System.out.printf("We have %d cached count\n", CoordinateUtils.INSTANCE.getCachedCount());
        System.out.printf("We have %d gets from cache count\n", CoordinateUtils.INSTANCE.getGetFromCacheCount());
        System.out.printf("We spent %d ns\n", estimatedTime);
    }

    private void fillCache() {
        final Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        for (int i = 0; i < 10000000; i++) {
            final int x = rnd.nextInt(351);
            final int y = rnd.nextInt(351);
            final int z = rnd.nextInt(351);
            CoordinateUtils.INSTANCE.createCoordinate(x, y, z);
        }
    }

    private ICoordinate[] getFromCache() {
        final Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        final ICoordinate[] result = new ICoordinate[10000000];
        for (int i = 0; i < 10000000; i++) {
            final int x = rnd.nextInt(351);
            final int y = rnd.nextInt(351);
            final int z = rnd.nextInt(351);
            result[i] = CoordinateUtils.INSTANCE.getFromCache(x, y, z);
        }

        return result;
    }

    private List[] getFromCacheNeighbors() {
        final Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        final List[] result = new List[1000000];
        for (int i = 0; i < 1000000; i++) {
            final int x = rnd.nextInt(351);
            final int y = rnd.nextInt(351);
            final int z = rnd.nextInt(351);
            final ICoordinate coordinate = CoordinateUtils.INSTANCE.getFromCache(x, y, z);
            result[i] = CoordinateUtils.INSTANCE.makeNeighborCoordinates(coordinate);
        }

        return result;
    }

    private ByteArrCoordinate[] fillByteCache() {
        final Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        final ByteArrCoordinate[] result = new ByteArrCoordinate[10000000];
        for (int i = 0; i < 10000000; i++) {
            final int x = rnd.nextInt(351);
            final int y = rnd.nextInt(351);
            final int z = rnd.nextInt(351);
            result[i] = new ByteArrCoordinate(x, y, z);
        }

        return result;
    }

    private ByteArrCoordinate[][] fillByteCacheNeighbors() {
        final Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        final ByteArrCoordinate[][] result = new ByteArrCoordinate[1000000][];
        for (int i = 0; i < 1000000; i++) {
            final int x = rnd.nextInt(351);
            final int y = rnd.nextInt(351);
            final int z = rnd.nextInt(351);
            final ByteArrCoordinate coordinate = new ByteArrCoordinate(x, y, z);
            final ByteArrCoordinate[] neighbors = coordinate.getNeighbors();
            result[i] = neighbors;
        }

        return result;
    }
}