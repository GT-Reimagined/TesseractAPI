package org.gtreimagined.tesseract;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;

public class TesseractConfig {

    public static ConfigEntry.DoubleValue EU_TO_TRE_RATIO;
    public static ConfigEntry.BoolValue ENABLE_TRE_COMPAT, ENABLE_MI_COMPAT;

    public static ConfigHandler CONFIG;

    public static void init(){
    }

    static {
        Config config = new Config(Tesseract.API_ID);
        ConfigSection section = config.add("general");
        EU_TO_TRE_RATIO = section.addDouble("eu_to_tre_ratio", 1.0, "The ratio of the eu to the tre energy converting - Default: (1.0 EU = 1.0 TRE)").setMin(Double.MIN_VALUE);
        ENABLE_TRE_COMPAT = section.addBool("enable_tre_compat", true, "Enables EU cables using tesseract's system being able to input and output Tech Reborn Energy. - Default: true");
        ENABLE_MI_COMPAT = section.addBool("enabled_mi_compat", true, "Enables Tesseract EU having compat with MI energy. - Default: true");
        CONFIG = CarbonConfig.createConfig(Tesseract.API_ID, config);;
        CONFIG.register();
    }
}
