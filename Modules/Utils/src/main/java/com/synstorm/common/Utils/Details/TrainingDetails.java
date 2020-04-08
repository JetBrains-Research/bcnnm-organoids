package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.EnumTypes.TeacherMode;

/**
 * Created by dvbozhko on 22/12/2016.
 */

@Model_v0
@NonProductionLegacy

public class TrainingDetails implements IDetails {
    //region Fields
    private final TeacherMode teacherMode;
    private final String datasetFileName;
    private final int setSize;
    //endregion

    //region Constructors
    public TrainingDetails(TeacherMode teacherMode, String datasetFileName, String setSize) {
        this.teacherMode = teacherMode;
        this.datasetFileName = datasetFileName;
        this.setSize = Integer.parseInt(setSize);
    }
    //endregion

    //region Getters and Setters
    public TeacherMode getTeacherMode() {
        return teacherMode;
    }

    public String getDatasetFileName() {
        return datasetFileName;
    }

    public int getSetSize() {
        return setSize;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
