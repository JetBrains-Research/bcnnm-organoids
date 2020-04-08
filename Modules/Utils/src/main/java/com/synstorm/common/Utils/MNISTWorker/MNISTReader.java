package com.synstorm.common.Utils.MNISTWorker;

import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry.Bozhko on 3/11/2015.
 */
public class MNISTReader {
    //region Fields
    private List<DigitalImage> dImageCollection;
    private DataInputStream labels;
    private DataInputStream images;
    private boolean isLoaded;
    private int numLabels;
    private int numImages;
    private int numRows;
    private int numCols;
    private int numLabelsRead;
    //endregion

    //region Constructors
    public MNISTReader(String imgFileName, String labelFileName, boolean preload) {
        try {
            labels = new DataInputStream(new FileInputStream(labelFileName));
            images = new DataInputStream(new FileInputStream(imgFileName));
            isLoaded = preload;
            int magicNumber = labels.readInt();

            if (magicNumber != 2049) {
                System.err.println("Label file has wrong magic number: " + magicNumber + " (should be 2049)");
                System.exit(0);
            }

            magicNumber = images.readInt();

            if (magicNumber != 2051) {
                System.err.println("Image file has wrong magic number: " + magicNumber + " (should be 2051)");
                System.exit(0);
            }

            numLabels = labels.readInt();
            numImages = images.readInt();
            numRows = images.readInt();
            numCols = images.readInt();

            if (numLabels != numImages) {
                System.err.println("Image file and label file do not contain the same number of entries.");
                System.err.println("  Label file contains: " + numLabels);
                System.err.println("  Image file contains: " + numImages);
                System.exit(0);
            }

            numLabelsRead = 0;
            dImageCollection = new ArrayList<>();

            if (isLoaded) {
                while (hasNext()) {
                    DigitalImage dImage = next();
                    dImageCollection.add(dImage);

                    int percentageDone = (int) (((double) numLabelsRead / numLabels) * 100);
                    Object[] parameters = new Object[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Getters and Setters
    public int getNumImages() {
        return numImages;
    }
    //endregion

    //region Public Methods
    @Nullable
    public DigitalImage getDigitalImage(int index) {
        if (isLoaded || dImageCollection.size() > index)
            return dImageCollection.get(index);
        else {
            DigitalImage dImage = next();
            dImageCollection.add(dImage);
            return dImage;
        }
    }

    public void printDigit(int index) throws Exception {
        DigitalImage digitalImage = dImageCollection.get(index);
        System.out.println("Value: " + digitalImage.getLabel());
        System.out.println("Pixels:");
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                System.out.print(String.format("%02X ", digitalImage.getPixels()[i][j]) + " ");
            }
            System.out.println();
        }

        DecimalFormat pFormatter = new DecimalFormat("0.000");
        System.out.println("Probability:");
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                System.out.print(pFormatter.format(digitalImage.getSigmoidPs()[i][j]) + " ");
            }
            System.out.println();
        }
    }
    //endregion

    //region Private Methods
    @Nullable
    private DigitalImage next() {
        if (!hasNext()) {
            isLoaded = true;
            return null;
        }

        try {
            int label = labels.readByte();
            numLabelsRead++;
            int[][] image = new int[numCols][numRows];
            for (int colIdx = 0; colIdx < numCols; colIdx++)
                for (int rowIdx = 0; rowIdx < numRows; rowIdx++)
                    image[colIdx][rowIdx] = images.readUnsignedByte();

            return new DigitalImage(28, 28, image, label);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasNext() {
        try {
            return labels.available() > 0 && numLabelsRead < numLabels;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //endregion
}
