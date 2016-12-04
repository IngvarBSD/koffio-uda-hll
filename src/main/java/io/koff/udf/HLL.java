package io.koff.udf;

import hyperloglog.HyperLogLog;
import hyperloglog.HyperLogLogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * User defined functions
 */
public final class HLL {
    /**
     * State function. Aggregates values to hll counter
     * @param state byte buffer with serialized HLL counter
     * @param value the value to add to counter
     * @return hll counter with the new value
     */
    public final static ByteBuffer stateFunc(ByteBuffer state, int value) {
        int capacity = state.capacity();
        // if it is the first call then state buffer should be empty
        if(capacity == 0) {
            HyperLogLog.HyperLogLogBuilder builder = new HyperLogLog.HyperLogLogBuilder();
            HyperLogLog hll = builder.build();
            hll.addInt(value);
            return serialize(hll);
        } else {
            // if there is data in state buffer then we need to use it
            HyperLogLog hll = deserialize(state);
            hll.addInt(value);
            return serialize(hll);
        }
    }

    public final static long finalFunc(ByteBuffer state) {
        HyperLogLog hll = deserialize(state);
        return hll.count();
    }

    private final static ByteBuffer serialize(HyperLogLog hll) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            HyperLogLogUtils.serializeHLL(out, hll);
            byte[] bytes = out.toByteArray();
            ByteBuffer result = ByteBuffer.allocate(bytes.length);
            result.put(bytes);
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("problems with hll serialization", e);
        }
    }

    private static HyperLogLog deserialize(ByteBuffer buffer) {
        byte[] tmp  = new byte[buffer.capacity()];
        buffer.limit(buffer.capacity());
        buffer.position(0);
        buffer.get(tmp);
        try {
            return HyperLogLogUtils.deserializeHLL(new ByteArrayInputStream(tmp));
        } catch (IOException e) {
            throw new IllegalStateException("problems with hll deserialization", e);
        }
    }
}
