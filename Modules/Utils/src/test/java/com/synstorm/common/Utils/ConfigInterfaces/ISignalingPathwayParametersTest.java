package com.synstorm.common.Utils.ConfigInterfaces;

import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression;
import com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.LogicalExpressionFactory;
import com.synstorm.common.Utils.PlatformLoaders.PlatformLoader;
import com.synstorm.common.Utils.SignalCoordinateProbability.SignalsIntensity;
import org.junit.Test;

import java.util.Random;
import java.util.function.Predicate;


/**
 * Test to compare predicates and precompiled expressions
 */
public class ISignalingPathwayParametersTest {
    private PlatformLoader platformLoader;
    private ISignalingPathway simplePathway;
    private ISignalingPathway complexPathway;
    private Random random;

//    @Before
//    public void setUp() {
//        random = new Random();
//        platformLoader = new PlatformLoader("src/test/resources/testdata/");
//        platformLoader.load();
//        simplePathway = platformLoader.getPlatformConfiguration().getMechanismsConfiguration().getSignalingPathway("PSCMove");
//        complexPathway = platformLoader.getPlatformConfiguration().getMechanismsConfiguration().getSignalingPathway("ComplexSP");
//    }


    /**
     * test of simple predicate:
     * PSCMoveFactor > 0.2 && (MSCMoveFactor > 0.15 && MSCMoveFactor <= 0.85)
     */
    //@Test
//    public void testSimplePathway() {
//        Predicate<SignalsIntensity> predicate = simplePathway.getCondition().getPredicate();
//
//        //should false: PSCMoveFactor 0.19, MSCMoveFactor 0.33
//        SignalsIntensity si1 = new SignalsIntensity();//condition.getSignalsIntensity();
//        si1.addIntensity(0, 0.19);
//        si1.addIntensity(1, 0.33);
//        Assert.assertFalse(predicate.test(si1));
//
//        //should true: PSCMoveFactor 0.9, MSCMoveFactor 0.4
//        SignalsIntensity si2 = new SignalsIntensity();
//        si2.addIntensity(0, 0.9);
//        si2.addIntensity(1, 0.4);
//        Assert.assertTrue(predicate.test(si2));
//    }


    /**
     * (PSCMoveFactor > 0.5 || (MSCMoveFactor < 0.7 && MSCMoveFactor >= 0.2))
     *              true    ||   (         true       &&          true) =>>>TRUE
     * &&
     * (
     *      (
     *          (Ligand3 > 0.2 && Ligand4 < 0.5)
     *                  true    &&      false   =>>> false
     *          ||
     *          (Ligand5 > 0.7 || Ligand6 < 0.2)
     *                  false   ||      false   =>>> false
     *      )
     *      && Ligand7 >= 0.3
     *          true
     * ) =>>> FALSE
     * =>>>>>>FALSE
     *
     *        <Ligand num="0" type="PSCMoveFactor" radius="15"/>
     *         <Ligand num="1" type="MSCMoveFactor" radius="15"/>
     *         <Ligand num="2" type="Ligand2" radius="15"/>
     *         <Ligand num="3" type="Ligand3" radius="15"/>
     *         <Ligand num="4" type="Ligand4" radius="15"/>
     *         <Ligand num="5" type="Ligand5" radius="15"/>
     *         <Ligand num="6" type="Ligand6" radius="15"/>
     *         <Ligand num="7" type="Ligand7" radius="15"/>
     */
    //@Test
//    public void testComplexPathway() {
//        Predicate<SignalsIntensity> predicate = complexPathway.getCondition().getPredicate();
//
//        /**
//         * (PSCMoveFactor > 0.5 || (MSCMoveFactor < 0.7 && MSCMoveFactor >= 0.2))
//         *              true    ||   (         true       &&          true) =>>>TRUE
//         * &&
//         * (
//         *      (
//         *          (Ligand3 > 0.2 && Ligand4 < 0.5)
//         *                  true    &&      false   =>>> false
//         *          ||
//         *          (Ligand5 > 0.7 || Ligand6 < 0.2)
//         *                  false   ||      false   =>>> false
//         *      )
//         *      && Ligand7 >= 0.3
//         *          true
//         * ) =>>> FALSE
//         * =>>>>>>FALSE
//         *
//         *        <Ligand num="0" type="PSCMoveFactor" radius="15"/>
//         *         <Ligand num="1" type="MSCMoveFactor" radius="15"/>
//         *         <Ligand num="2" type="Ligand2" radius="15"/>
//         *         <Ligand num="3" type="Ligand3" radius="15"/>
//         *         <Ligand num="4" type="Ligand4" radius="15"/>
//         *         <Ligand num="5" type="Ligand5" radius="15"/>
//         *         <Ligand num="6" type="Ligand6" radius="15"/>
//         *         <Ligand num="7" type="Ligand7" radius="15"/>
//         */
//
//        //assert false
//        SignalsIntensity si2 = new SignalsIntensity();
//        si2.addIntensity(0, 0.6);
//        si2.addIntensity(1, 0.5);
//        si2.addIntensity(2, 0.0);
//        si2.addIntensity(3, 0.5);
//        si2.addIntensity(4, 0.5);
//        si2.addIntensity(5, 0.5);
//        si2.addIntensity(6, 0.5);
//        si2.addIntensity(7, 0.5);
//        Assert.assertFalse(predicate.test(si2));
//
//
//        /**
//         * (PSCMoveFactor > 0.5 || (MSCMoveFactor < 0.7 && MSCMoveFactor >= 0.2))
//         *              true    ||   (         true       &&          true) =>>>TRUE
//         * &&
//         * (
//         *      (
//         *          (Ligand3 > 0.2 && Ligand4 < 0.5)
//         *                  true    &&      true   =>>> true
//         *          ||
//         *          (Ligand5 > 0.7 || Ligand6 < 0.2)
//         *                  false   ||      false   =>>> false
//         *      )
//         *      && Ligand7 >= 0.3
//         *          true
//         * ) =>>> TRUE
//         * =>>>>>>TRUE
//         *
//         *        <Ligand num="0" type="PSCMoveFactor" radius="15"/>
//         *         <Ligand num="1" type="MSCMoveFactor" radius="15"/>
//         *         <Ligand num="2" type="Ligand2" radius="15"/>
//         *         <Ligand num="3" type="Ligand3" radius="15"/>
//         *         <Ligand num="4" type="Ligand4" radius="15"/>
//         *         <Ligand num="5" type="Ligand5" radius="15"/>
//         *         <Ligand num="6" type="Ligand6" radius="15"/>
//         *         <Ligand num="7" type="Ligand7" radius="15"/>
//         */
//        //assert true
//        SignalsIntensity si3 = new SignalsIntensity();
//        si3.addIntensity(0, 0.6);
//        si3.addIntensity(1, 0.5);
//        si3.addIntensity(2, 0.0);
//        si3.addIntensity(3, 0.5);
//        si3.addIntensity(4, 0.4);
//        si3.addIntensity(5, 0.5);
//        si3.addIntensity(6, 0.5);
//        si3.addIntensity(7, 0.5);
//
//        Assert.assertTrue(predicate.test(si3));
//
//    }

    //@Test
//    public void simpleIterationsTest() {
//        int iter = 10;
//        double[] array = new double[iter];
//        for (int i = 0; i < iter; i++) {
//            array[i] = simpleIterations(simplePathway.getCondition().getPredicate(), 100_000_000);
//        }
//
//        double sum = 0.0;
//        for (double d : array) sum += d;
//        System.out.printf("Average for %s iterations of simple pathway is %sms", iter, sum / array.length);
//    }

    //@Test
//    public void complexIterationsTest() {
//        int iter = 10;
//        double[] array = new double[iter];
//        for (int i = 0; i < iter; i++) {
//            array[i] = complexIterations(complexPathway.getCondition().getPredicate(), 100_000_000);
//        }
//
//        double sum = 0.0;
//        for (double d : array) sum += d;
//        System.out.printf("Average for %s iterations of complex pathway is %sms", iter, sum / array.length);
//    }

    private double simpleIterations(Predicate<SignalsIntensity> predicate, int iterations) {
        long tStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SignalsIntensity si = new SignalsIntensity();
            si.addIntensity(0, random.nextDouble());
            si.addIntensity(1, random.nextDouble());
            predicate.test(si);
        }
        long tEnd = System.nanoTime();
        double result = (tEnd - tStart) / 1_000_000;
        System.out.printf("Time of test on %s simpleIterations : %sms\n", iterations, result);
        return result;
    }

    private double complexIterations(Predicate<SignalsIntensity> predicate, int iterations) {
        long tStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            SignalsIntensity si = new SignalsIntensity();
            si.addIntensity(0, random.nextDouble());
            si.addIntensity(1, random.nextDouble());
            si.addIntensity(2, random.nextDouble());
            si.addIntensity(3, random.nextDouble());
            si.addIntensity(4, random.nextDouble());
            si.addIntensity(5, random.nextDouble());
            si.addIntensity(6, random.nextDouble());
            si.addIntensity(7, random.nextDouble());
            predicate.test(si);
        }
        long tEnd = System.nanoTime();
        double result = (tEnd - tStart) / 1_000_000;
        System.out.printf("Time of test on %s complexIterations : %sms\n", iterations, result);
        return result;
    }

    @Test
    public void testCompiledSimplePathway() throws Exception {
        ILogicalExpression le = LogicalExpressionFactory.INSTANCE.generateFunction("simpleSP",
                                                "(PSCMoveFactor _gt 0.2) AND ((MSCMoveFactor _gt 0.15) OR (MSCMoveFactor _~lt 0.85))",
                                                "PSCMoveFactor,MSCMoveFactor");
        int iterations = 100_000_000;
        double[] values = new double[]{0.3,0.5};
        long[] results = new long[iterations];

        for (int i = 0; i < iterations; i++) {
            results[i] = testCompiledExpression(le, values);
        }

        long sum = 0L;
        for (long d : results) sum += d;

        System.out.printf("Average SIMPLE per 100m computes: %sns\n", sum / iterations);
        System.out.printf("Complete SIMPLE computations 100m: %sms\n", sum / 1_000_000);
    }

    @Test
    public void testCompiledComplexPathway() throws Exception {
        ILogicalExpression le = LogicalExpressionFactory.INSTANCE.generateFunction("complexSP",
                "(PSCMoveFactor _gt 0.5 | (MSCMoveFactor _lt 0.7 & MSCMoveFactor _~gt 0.2)) & ( ((Ligand3 _gt 0.2 & Ligand4 _lt 0.5) | (Ligand5 _gt 0.7 | Ligand6 _lt 0.2)) & Ligand7 _~gt 0.3 )",
                "PSCMoveFactor,MSCMoveFactor,Ligand3,Ligand4,Ligand5,Ligand6,Ligand7");
        int iterations = 100_000_000;
        double[] values = new double[]{0.3,0.5,0.3,0.5,0.3,0.5,0.3};
        long[] results = new long[iterations];

        for (int i = 0; i < iterations; i++) {
            results[i] = testCompiledExpression(le, values);
        }

        long sum = 0L;
        for (long d : results) sum += d;

        System.out.printf("Average COMPLEX per 100m computes: %sns\n", sum / iterations);
        System.out.printf("Complete COMPLEX computations 100m: %sms\n", sum / 1_000_000);
    }

    private long testCompiledExpression(ILogicalExpression le, double[] values) {
        long tStart = System.nanoTime();
        le.compute(values);
        return System.nanoTime() - tStart;
    }
}