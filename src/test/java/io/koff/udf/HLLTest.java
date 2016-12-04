package io.koff.udf;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

/**
 * Test for HLL
 */
public class HLLTest {
    // hll counter should calculate unique values 100% correctly up to 1000
    private static final int uniqueCount = 100;

    @Test
    public void test() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(0);

        for (int i = 0; i < uniqueCount; i++) {
            byteBuffer = HLL.stateFunc(byteBuffer, i);
        }

        long uniqueValues = HLL.finalFunc(byteBuffer);
        assertEquals(uniqueValues, uniqueCount);
    }
}
