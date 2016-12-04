-- A simple example of creating functions for a user defined aggregate
CREATE OR REPLACE FUNCTION udf.avg_state ( state tuple<int,bigint>, val int ) CALLED ON NULL INPUT RETURNS tuple<int,bigint> LANGUAGE java AS
  'if (val !=null) { state.setInt(0, state.getInt(0)+1); state.setLong(1, state.getLong(1)+val.intValue()); } return state;';

CREATE OR REPLACE FUNCTION udf.avg_final ( state tuple<int,bigint> ) CALLED ON NULL INPUT RETURNS double LANGUAGE java AS
  'double r = 0; if (state.getInt(0) == 0) return null; r = state.getLong(1); r/= state.getInt(0); return Double.valueOf(r);';

CREATE AGGREGATE IF NOT EXISTS udf.avg ( int )
SFUNC avg_state STYPE tuple<int,bigint> FINALFUNC avg_final INITCOND (0,0);