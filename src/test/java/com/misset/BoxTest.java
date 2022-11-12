package com.misset;

import com.misset.container.Box;
import org.junit.jupiter.api.Test;

class BoxTest {

    @Test
    void createIntersects() {



    }

    private Box createBox() {
        FieldCollection fields = new FieldCollection();
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                fields.add(new Field(x, y));
            }
        }
        return new Box(fields);
    }
}
