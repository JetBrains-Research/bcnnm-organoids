package com.synstorm.common.Utils.SignalCoordinateProbability;

import com.synstorm.common.Utils.EnumTypes.SignalSelectionType;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;
import org.junit.Before;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by dvbozhko on 13/04/2017.
 */
public class SignalProbabilityMapTest {
    private ICoordinate startCoordinate;
    private List<ICoordinate> coordinates;

    @Before
    public void setUp() throws Exception {
        CoordinateUtils.INSTANCE.initializeUtils(10);
        startCoordinate = CoordinateUtils.INSTANCE.createCoordinate(4, 4, 4);
        coordinates = CoordinateUtils.INSTANCE.makeNeighborCoordinates(startCoordinate);
        coordinates.add(startCoordinate);
    }

    @Test
    public void getCoordinateCandidate() throws Exception {
        Random rnd = new MersenneTwisterRNG(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        Set<String> signals = new HashSet<String>() {{
            add("S1");
            add("S2");
        }};
        Map<String, SignalSelectionType> signalSelection = new HashMap<String, SignalSelectionType>() {{
            put("S1", SignalSelectionType.Gradient);
            put("S2", SignalSelectionType.Gradient);
        }};

        SignalProbabilityMap signalProbabilityMap = new SignalProbabilityMap(signals);
        signalProbabilityMap.addCoordinates(coordinates);

        coordinates
                .forEach(coordinate -> signals
                        .forEach(signal -> {
                            final double randomIntensity = rnd.nextDouble();
                            System.out.printf("%s : %s -> %f\n", coordinate.toString(), signal, randomIntensity);
                            signalProbabilityMap.addSignalIntensity(coordinate, signal, randomIntensity);
                        }));

        SignalProbability signalProbability = new SignalProbability(signals);
        Predicate<SignalProbability> p1 = signalProbability.isIntensityMore("S1", 0.);
        Predicate<SignalProbability> p2 = signalProbability.isIntensityLess("S2", 0.5);
        CoordinateProbability candidate = signalProbabilityMap.getCoordinateCandidate(0.8, p1.and(p2), signalSelection);

        if (candidate != null)
            System.out.println("Candidate: " + candidate.getCoordinate().toString());
    }
}