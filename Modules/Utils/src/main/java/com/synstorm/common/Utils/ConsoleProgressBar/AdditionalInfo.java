package com.synstorm.common.Utils.ConsoleProgressBar;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Class which stores all user defined messages for printing in console bar with defined logLevel
 * Created by dvbozhko on 14/07/16.
 */

@Model_v1

public final class AdditionalInfo {
    //region Fields
    private final String info;
    private final int level;
    //endregion

    //region Constructors
    public AdditionalInfo(String caption, Object value, int logLevel) {
        info = caption + ": " + String.valueOf(value);
        level = logLevel;
    }
    //endregion

    //region Getters and Setters
    public String getInfo() {
        return info;
    }

    public int getLevel() {
        return level;
    }
    //endregion
}
