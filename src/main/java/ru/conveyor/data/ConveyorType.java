package ru.conveyor.data;

import ru.conveyor.data.conveyor.impl.AbstractConveyor;
import ru.conveyor.data.conveyor.impl.ApacheTreeListConveyor;
import ru.conveyor.data.conveyor.impl.ArrayListConveyor;
import ru.conveyor.data.conveyor.impl.LinkedListConveyor;
import ru.conveyor.data.conveyor.impl.PrimitiveArrayConveyor;
import ru.conveyor.data.conveyor.impl.ThreadSafeConveyor;

import java.util.function.Supplier;

public enum ConveyorType {

    LINKED_LIST(LinkedListConveyor::new),
    ARRAY_LIST(ArrayListConveyor::new),
    THREAD_SAFE(ThreadSafeConveyor::new),
    APACHE_TREE_LIST(ApacheTreeListConveyor::new),
    PRIMITIVE_ARRAY(PrimitiveArrayConveyor::new);

    private final Supplier<AbstractConveyor> constructor;

    ConveyorType(Supplier<AbstractConveyor> constructor) {
        this.constructor = constructor;
    }

    AbstractConveyor newStrategy() {
        return constructor.get();
    }
}
