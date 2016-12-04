-- Functions for hll counters
CREATE OR REPLACE FUNCTION udf.hll_state ( state blob, val int ) CALLED ON NULL INPUT RETURNS blob LANGUAGE java AS
  'return io.koff.udf.HLL.stateFunc(state, val);';

CREATE OR REPLACE FUNCTION udf.hll_final ( state blob ) CALLED ON NULL INPUT RETURNS bigint LANGUAGE java AS
  'return io.koff.udf.HLL.finalFunc(state);';

-- we need an empty ByteBuffer at the first step so we make it from the empty string
CREATE AGGREGATE IF NOT EXISTS udf.hll ( int ) SFUNC hll_state STYPE blob FINALFUNC hll_final INITCOND textAsBlob('');