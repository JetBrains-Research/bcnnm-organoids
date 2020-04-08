package com.synstorm.common.Utils.PlatformLoaders;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by human-research on 2019-02-04.
 */
public class PlatformLoaderTest_Load_Save {
    @Test
    public void test() {
        PlatformLoader platformLoader = new PlatformLoader("data/ModelConfiguration/loaders_reconstruction/BiologicalPlatformConfigs/2018");
        boolean result = platformLoader.load();
        System.out.printf("loaded? %s\n", result);
        //this test discontinued.
//        if (result)
//            platformLoader.saveIndividualConfiguration(platformLoader.getPlatformConfiguration().getIndividualConfiguration(),
//                    "data/ModelConfiguration/loaders_reconstruction/BiologicalPlatformConfigs/2018",
//                    "Individual_Zero.xml");
        assertTrue(result);
    }
}