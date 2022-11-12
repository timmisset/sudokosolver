package com.misset.container;

import com.misset.Field;
import com.misset.FieldCollection;
import com.misset.FieldCollectionTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest extends FieldCollectionTest {

    @Test
    void getFieldsReturnsUnmodifiableCopyOfFields() {
        ContainerImpl container = new ContainerImpl(fields);
        Collection<Field> containerFields = container.getFields();
        assertNotEquals(containerFields, fields);
        assertTrue(fields.stream().allMatch(containerFields::contains));
    }

    @Test
    void contains() {
        ContainerImpl container = new ContainerImpl(fields.getRow(1));
        assertTrue(fields.getRow(1).stream().allMatch(container::contains));
        assertTrue(fields.getRow(2).stream().noneMatch(container::contains));
    }

    @Test
    void hasSolved() {
        ContainerImpl container = new ContainerImpl(fields);
        fields.get(0).setValue(1);
        assertTrue(container.hasSolved(1));
        assertFalse(container.hasSolved(2));
    }

    @Test
    void validateThrowsWhenMoreThanOneFieldHoldsSameValue() {
        ContainerImpl container = new ContainerImpl(fields.getGrid(1, 1, 1, 2));
        fields.get(0).setValue(1);
        fields.get(1).setValue(1);
        assertThrows(RuntimeException.class, container::validate);
    }

    @Test
    void validateThrowsWhenFieldsAreNotSolved() {
        ContainerImpl container = new ContainerImpl(fields.getGrid(1, 1, 1, 2));
        fields.get(0).setValue(1);
        assertThrows(RuntimeException.class, container::validate);
    }

    @Test
    void validatesWhenValuesOccurOnce() {
        ContainerImpl container = new ContainerImpl(fields.getGrid(1, 1, 1, 9));
        for(int i = 0; i < 9; i++) {
            fields.get(i).setValue(i + 1);
        }
        assertDoesNotThrow(container::validate);
    }


    class ContainerImpl extends Container {
        protected ContainerImpl(FieldCollection fields) {
            super(fields);
        }
    }
}
