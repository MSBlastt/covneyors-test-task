package ru.conveyor.test;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorType;
import ru.conveyor.data.IntersectionPoint;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConfigPropertiesTest {

    @Test
    public void configPropertiesTest() throws Exception {
        //config test
        FactoryConfig factoryConfig = new FactoryConfig();

        int conveyorAlen = factoryConfig.getConveyorALength();
        int conveyorBlen = factoryConfig.getConveyorBLength();
        ConveyorType conveyorType = factoryConfig.getConveyorType();
        List<IntersectionPoint> intersectionPoints = new ArrayList<>(factoryConfig.getIntersectionPoints());

        //check length
        assertThat(conveyorAlen, CoreMatchers.is(5));
        assertThat(conveyorBlen, CoreMatchers.is(6));

        //check type of conveyor
        assertThat(conveyorType, CoreMatchers.is(ConveyorType.LINKED_LIST));

        // Check intersection points exist
        assertFalse(intersectionPoints.isEmpty());
    }
}
