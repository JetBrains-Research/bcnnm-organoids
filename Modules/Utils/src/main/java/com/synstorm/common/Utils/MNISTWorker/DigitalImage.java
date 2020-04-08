package com.synstorm.common.Utils.MNISTWorker;

import org.jetbrains.annotations.Contract;

/**
 * Created by Dmitry.Bozhko on 3/11/2015.
 */
public class DigitalImage {
    //region Fields
    private int width; // 28 standard
    private int height; // 28 standard
    private int[][] pixels; // 0(black) - 255(white)
    private double[][] sigmoidPs; // 0.006(low) - 0.994(high)
    private double[][] linearPs; // 0.006(low) - 0.994(high)
    private int label; // '0' - '9'
    //endregion

    //region Constructors
    public DigitalImage(int width, int height, int[][] image, int label) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width];
        sigmoidPs = new double[height][width];
        linearPs = new double[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = image[i][j];
                sigmoidPs[i][j] = calculateSigmoidProbability(pixels[i][j]);
                linearPs[i][j] = calculateLinearProbability(pixels[i][j]);
            }
        }

        this.label = label;
    }
    //endregion

    //region Getters and Setters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getPixels() {
        return pixels;
    }

    public double[][] getSigmoidPs() {
        return sigmoidPs;
    }

    public double[][] getLinearPs() {
        return linearPs;
    }

    public int getLabel() {
        return label;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    @Contract(pure = true)
    private double calculateLinearProbability(int input) {
        return input / 255.;
    }

    private double calculateSigmoidProbability(int input) {
        return Math.tanh(0.02 * input - 3) / 2 + 0.5; //it gives us scale from 0.006 to 0.994 on intensity from 0 to 255
    }
    //endregion
}
