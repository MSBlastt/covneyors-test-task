package ru.conveyor.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorType;
import ru.conveyor.data.IntersectionPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesReader {

    private static final String CONFIG_FILE = "config.properties";

    private static final String CONVEYORS_A_LENGTH = "conveyors.a.length";
    private static final String CONVEYORS_B_LENGTH = "conveyors.b.length";

    private static final String CONVEYORS_TYPE = "conveyors.type";
    private static final String INTERSECTIONS = "intersections";

    private static final String SEMICOLON_DELIMITER = ";";

    public static FactoryConfig getConfigFromProperties() throws Exception {
        Properties properties = PropertiesLoaderUtils.loadAllProperties(CONFIG_FILE);

        int conveyorALength = Integer.valueOf(properties.getProperty(CONVEYORS_A_LENGTH));
        int conveyorBLength = Integer.valueOf(properties.getProperty(CONVEYORS_B_LENGTH));
        ConveyorType conveyorType = ConveyorType.valueOf(properties.getProperty(CONVEYORS_TYPE));

        List<IntersectionPoint> intersectionPoints = new ArrayList<>();

        for (String str : properties.getProperty(INTERSECTIONS).split(SEMICOLON_DELIMITER)) {
            int indexA = Integer.parseInt(String.valueOf(str.charAt(0)));
            int indexB = Integer.parseInt(String.valueOf(str.charAt(2)));

            intersectionPoints.add(new IntersectionPoint(indexA, indexB));
        }

        return new FactoryConfig(intersectionPoints, conveyorALength, conveyorBLength, conveyorType, true);
    }
}
