package com.synstorm.common.Utils.PlatformLoaders;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigurationStrings.FilePathsStrings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * Created by human-research on 06/03/2017.
 */
@Model_v1
public enum FileStreamsFactory {
    INSTANCE;

    @Nullable
    public InputStream getXMLFileInputStream(String configPath, @NotNull String configTag) throws FileNotFoundException {
        switch (configTag) {
            case "model":
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_MODEL_CONFIG));
            case "individual":
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_INDIVIDUAL_CONFIG));
            default:
                return null;
        }
    }


    @Nullable
    public InputStream getXSLFileInputStream(String configPath, @NotNull String configTag) throws FileNotFoundException {
        switch (configTag) {
            case "model" :
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_MODEL_XSL));
            case "individual" :
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_INDIVIDUAL_XSL));
            default:
                return null;
        }
    }

    @Nullable
    public InputStream getXSDFileInputStream(String configPath, @NotNull String configTag) throws FileNotFoundException {
        switch (configTag) {
            case "model" :
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_MODEL_SCHEMA));
            case "individual" :
                return new FileInputStream(new File(configPath + FilePathsStrings.FILENAME_INDIVIDUAL_SCHEMA));
            default:
                return null;
        }
    }

    @Nullable
    public FileOutputStream getXMLFileOutputStream(String newPath, @NotNull String configTag, String additional) throws FileNotFoundException {
        switch (configTag) {
            case "model":
                return new FileOutputStream(new File(newPath + FilePathsStrings.FILENAME_MODEL_CONFIG));
            case "individual":
                if (additional.equals(""))
                    return new FileOutputStream(new File(newPath + FilePathsStrings.FILENAME_INDIVIDUAL_CONFIG));
                else
                    return new FileOutputStream(new File(newPath + File.separator + additional));
            default:
                return null;
        }
    }
}
