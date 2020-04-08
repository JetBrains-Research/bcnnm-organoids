package com.synstorm.common.Utils.EnumTypes;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.ProductionLegacy;

/**
 * Created by dvbozhko on 12/7/15.
 */

@Model_v0
@ProductionLegacy

public enum ConsoleColors {
    ANSI_RESET {
        public String value() {
            return "\u001B[0m";
        }
    },
    ANSI_BLACK {
        public String value() {
            return "\u001B[30m";
        }
    },
    ANSI_RED {
        public String value() {
            return "\u001B[31m";
        }
    },
    ANSI_GREEN {
        public String value() {
            return "\u001B[32m";
        }
    },
    ANSI_YELLOW {
        public String value() {
            return "\u001B[33m";
        }
    },
    ANSI_BLUE {
        public String value() {
            return "\u001B[34m";
        }
    },
    ANSI_PURPLE {
        public String value() {
            return "\u001B[35m";
        }
    },
    ANSI_CYAN {
        public String value() {
            return "\u001B[36m";
        }
    },
    ANSI_WHITE {
        public String value() {
            return "\u001B[37m";
        }
    };

    public abstract String value();
}
