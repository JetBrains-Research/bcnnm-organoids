package com.synstorm.common.Utils.ProbabilitiesEvaluation;

import org.apache.commons.math3.util.Pair;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Vlad Myrov
 * Date: 04/10/2018
 */

public final class DistributionsTest {
    private static final String testData = "Modules/Utils/src/test/resources/testdata/ProbabilitiesTestConfig.txt";

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        test_distributions();
        simulateDivision();
        simulateSpiking();
    }

    private static IDistribution read_config(String filename) {
        File file = new File(filename);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();

            Element e = (Element)doc.getElementsByTagName("event").item(0);

            String distroType = e.getAttribute("family");
            double[] params = Arrays.stream(e.getAttribute("parameters").split(",")).mapToDouble(Double::parseDouble).toArray();

            return DistributionFactory.INSTANCE.generateDistribution(distroType, params);
        } catch (Exception e) {
            return null;
        }
    }

    private static void simulateSpiking() {
        IDistribution d = DistributionFactory.INSTANCE.generateDistribution("Pascal", new double[]{10, 0.5});

        int ticksTotal = 1000;

        int spikeCounter = 0;
        double[] args = new double[1];
        double[] spikeData = new double[ticksTotal];
        double[] ticksData = new double[ticksTotal];

        for (int i = 0; i < ticksTotal; ++i) {
            args[0] = spikeCounter;
            double spikeProb = d.getCumulativeProbability(args);
            if(spikeProb >= Math.random()) {
                spikeData[i] = 1;
                spikeCounter = 0;
            } else {
                spikeData[i] = 0;
                spikeCounter += 1;
            }
            args[0] = spikeCounter;
            ticksData[i] = i;
        }

        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Prob", "CDF", ticksData, spikeData);
        new SwingWrapper(chart).displayChart("Spiking plot");
    }

    private static void simulateDivision() {
        IDistribution distribution = read_config(testData);

        int ticksTotal = 1000;

        double currConcentration = 0.4;
        double[] args = new double[1];
        double[] divisionData = new double[ticksTotal];
        double[] cellCounter = new double[ticksTotal];
        double[] concentrations = new double[ticksTotal];
        double[] ticksData = new double[ticksTotal];
        double[] cellDivProbs = new double[ticksTotal];

        int totalDivisions = 0;

        for(int i = 0; i < ticksTotal; ++i) {
            args[0] = currConcentration;
            assert distribution != null;
            double spikeProb = distribution.getEventProbability(args);

            int doDivision = spikeProb >= Math.random() ? 1 : 0;

            totalDivisions += doDivision;
            divisionData[i] = doDivision;
            cellCounter[i] = totalDivisions;
            concentrations[i] = currConcentration;
            cellDivProbs[i] = spikeProb;
            ticksData[i] = i;

            currConcentration += (Math.random() - 0.5)/10;
            currConcentration = clamp(currConcentration, 0, 1);
        }


        double[] xVals = new double[100];
        double[] probVals = new double[100];

        for(int i = 0; i < 100; ++i) {
            args[0] = 1.0*i/100;
            xVals[i] = 1.0*i/100;
            probVals[i] = distribution.getEventProbability(args);
        }

        List<XYChart> chartList = new ArrayList<>();
        chartList.add(QuickChart.getChart("Concentration chart", "X", "Concentration", "CDF", ticksData, concentrations));
        chartList.add(QuickChart.getChart("Division probability chart", "X", "Division probability", "CDF", ticksData, cellDivProbs));
        chartList.add(QuickChart.getChart("Division event chart", "X", "Division event", "CDF", ticksData, divisionData));
        chartList.add(QuickChart.getChart("Cell count chart", "X", "Cell count", "PDF", ticksData, cellCounter));
        chartList.add(QuickChart.getChart("Division probability", "X", "Probability", "CDF", xVals, probVals));

        // Show it
        new SwingWrapper(chartList).displayChartMatrix();
    }


    private static void test_distributions() {
        double[] args = new double[1];

        List<Pair<String, IDistribution>> distroList = new ArrayList<>();
        distroList.add(new Pair<>("Gaussian", DistributionFactory.INSTANCE.generateDistribution("Gaussian", new double[]{0.6, 0.1})));
        distroList.add(new Pair<>("Exponential", DistributionFactory.INSTANCE.generateDistribution("Exponential", new double[]{1.5})));
        distroList.add(new Pair<>("Poisson", DistributionFactory.INSTANCE.generateDistribution("Poisson", new double[]{0.5})));
        distroList.add(new Pair<>("Binomial", DistributionFactory.INSTANCE.generateDistribution("Binomial", new double[]{3., 0.25})));


        // data is saved as reference
        // so I have to instanitiate array for each of the distributions
        double[][] xData = new double[distroList.size()][];
        double[][] pdfData = new double[distroList.size()][];
        double[][] cdfData = new double[distroList.size()][];

        for (int i = 0; i < distroList.size(); ++i) {
            xData[i] = new double[120];
            pdfData[i] = new double[120];
            cdfData[i] = new double[120];
        }


        for (int i = 0; i < distroList.size(); ++i) {
            String name = distroList.get(i).getKey();
            IDistribution d = distroList.get(i).getValue();

            args[0] = 0;

            for (int j = 0; j < 120; ++j) {
                xData[i][j] = args[0];
                pdfData[i][j] = d.getProbabilityDensity(args);
                cdfData[i][j] = d.getCumulativeProbability(args);

                args[0] += 0.05;
            }

            List<XYChart> chartList = new ArrayList<>();
            chartList.add(QuickChart.getChart("Sample Chart", "X", "Prob", "PDF", xData[i], pdfData[i]));
            chartList.add(QuickChart.getChart("Sample Chart", "X", "Prob", "CDF", xData[i], cdfData[i]));

            // Show it
            new SwingWrapper(chartList).displayChartMatrix(name);
        }
    }

    private static double clamp(double x, double l, double r) {
        return Math.max(l, Math.min(x, r));
    }
}
