package com.synstorm.common.Utils.MNISTWorker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dvbozhko on 06/07/16.
 */
public class SamplesCsvConverter {
    public static final String MNIST_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator
            + "dbMNIST" + File.separator;

    private static BufferedWriter bufferedWriterTrain;
    private static BufferedWriter bufferedWriterControl;
    private static StringBuilder stringBuilderTrain = new StringBuilder();
    private static StringBuilder stringBuilderControl = new StringBuilder();

    public static void main(String[] args) {
        String trainingDataPath = MNIST_PATH + "train-images.idx3-ubyte";
        String trainingLabelPath =MNIST_PATH + "train-labels.idx1-ubyte";
        String controlDataPath = MNIST_PATH + "t10k-images.idx3-ubyte";
        String controlLabelPath = MNIST_PATH + "t10k-labels.idx1-ubyte";

        MNISTReader mnistReaderTrain = new MNISTReader(trainingDataPath, trainingLabelPath, true);
        System.out.println();
        MNISTReader mnistReaderControl = new MNISTReader(controlDataPath, controlLabelPath, true);

        File fTrain = new File(MNIST_PATH + "linear_first_10_unique_train.csv");
        File fControl = new File(MNIST_PATH + "linear_first_10_unique_control.csv");

        try {
            boolean newFile = true;
            if (!fTrain.exists())
                newFile = fTrain.createNewFile();

            if (!fControl.exists())
                newFile = newFile & fControl.createNewFile();

            if (newFile) {
                FileWriter fwTrain = new FileWriter(fTrain.getAbsoluteFile());
                FileWriter fwControl = new FileWriter(fControl.getAbsoluteFile());
                bufferedWriterTrain = new BufferedWriter(fwTrain);
                bufferedWriterControl = new BufferedWriter(fwControl);

                convertFirstTenToPretrain(mnistReaderTrain, stringBuilderTrain, bufferedWriterTrain);
                convertFirstTenToPretrain(mnistReaderControl, stringBuilderControl, bufferedWriterControl);
            } else
                throw new IllegalStateException("Cannot create file for FileExporter");
        } catch (IOException e) {
            e.printStackTrace();
        }

        close();
    }

    private static void close() {
        try {
            bufferedWriterTrain.close();
            bufferedWriterControl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convert(MNISTReader mnistReaderTrain, StringBuilder sb, BufferedWriter bw) {
        int numImages = mnistReaderTrain.getNumImages();
        for (int i = 0; i < numImages; i++) {
            DigitalImage dImage = mnistReaderTrain.getDigitalImage(i);
            assert dImage != null;
            int label = dImage.getLabel();
            double[][] pixels = dImage.getLinearPs();
            sb.append(label).append(",");
            for (double[] pixel : pixels)
                for (double aPixel : pixel)
                    sb.append(aPixel).append(",");

            deleteLastSeparator(sb, ",");
            try {
                bw.write(sb.toString());
                sb.delete(0, sb.length());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            sb.append("\n");
        }
    }

    private static void convertFirstTenToPretrain(MNISTReader mnistReaderTrain, StringBuilder sb, BufferedWriter bw) {
        Map<Integer, DigitalImage> uniqueSamples = new LinkedHashMap<>();
        int counter = 0;
        while (uniqueSamples.size() < 10) {
            DigitalImage sample = mnistReaderTrain.getDigitalImage(counter);
            assert sample != null;
            final int sampleLabel = sample.getLabel();
            if (!uniqueSamples.containsKey(sampleLabel))
                uniqueSamples.put(sampleLabel, sample);
            counter++;
        }

        uniqueSamples.entrySet().forEach(pair -> {
            double[][] pixels = pair.getValue().getLinearPs();
            sb.append(pair.getKey()).append(",");
            for (double[] pixel : pixels)
                for (double aPixel : pixel)
                    sb.append(aPixel).append(",");

            deleteLastSeparator(sb, ",");
            try {
                bw.write(sb.toString());
                sb.delete(0, sb.length());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            sb.append("\n");
        });
    }

    private static void deleteLastSeparator(StringBuilder sb, String separator) {
        int lastSeparator = sb.lastIndexOf(separator);
        if (lastSeparator >= 0)
            sb.deleteCharAt(lastSeparator);
    }
}
