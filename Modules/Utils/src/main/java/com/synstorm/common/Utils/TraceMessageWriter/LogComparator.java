package com.synstorm.common.Utils.TraceMessageWriter;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dvbozhko on 11/05/16.
 */
@Model_v0
@NonProductionLegacy
public class LogComparator {

    public static void main(String[] args) {
        BufferedReader bufferedReader1;
        BufferedReader bufferedReader2;
        String fileName1 = "6bf8bee8_5ec4_4298_adc2_9d6bde7953f3_11_05_2016_09_41.log";
        String fileName2 = "3ecdf936_7bf6_43b2_92f5_56fd5babbd47_11_05_2016_09_42.log";
        File f1 = new File(fileName1);
        File f2 = new File(fileName2);
        try {
            FileReader fr1 = new FileReader(f1.getAbsoluteFile());
            FileReader fr2 = new FileReader(f2.getAbsoluteFile());
            bufferedReader1 = new BufferedReader(fr1);
            bufferedReader2 = new BufferedReader(fr2);
            compare(bufferedReader1, bufferedReader2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //region Fields
    //endregion

    //region Constructors
    //endregion

    //region Getters and Setters
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    private static void compare(BufferedReader br1, BufferedReader br2) {
        String[] str1 = br1.lines().toArray(String[]::new);
        String[] str2 = br2.lines().toArray(String[]::new);
        String[] source;
        String[] dest;

        if (str1.length < str2.length) {
            source = str1;
            dest = str2;
        } else {
            source = str2;
            dest = str1;
        }

        List<String> incomplete = new ArrayList<>();

        for (int i = 0; i < source.length; i++) {
            if (!source[i].equals(dest[i])) {
                incomplete.add(source[i]);
                incomplete.add(dest[i]);
//                System.out.println(i + " -> ");
//                System.out.println(source[i]);
//                System.out.println(dest[i]);
//                break;
            }
        }

        Collections.sort(incomplete);

        for (int i = 1; i < incomplete.size(); i++) {
            if (incomplete.get(i - 1).equals(incomplete.get(i))) {
                incomplete.remove(i - 1);
                incomplete.remove(i - 1);
                i--;
            }
        }

        List<String> listWithoutDuplicates =
                incomplete.stream().distinct().collect(Collectors.toList());

        System.out.println(listWithoutDuplicates.get(0));
//        listWithoutDuplicates.stream().forEach(System.out::println);
    }
    //endregion
}
