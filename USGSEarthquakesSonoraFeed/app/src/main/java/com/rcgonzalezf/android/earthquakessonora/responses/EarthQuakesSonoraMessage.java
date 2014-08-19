package com.rcgonzalezf.android.earthquakessonora.responses;

import java.util.List;

public class EarthQuakesSonoraMessage {
    public List<EarthQuakeInfo> earthquakes;

    public class EarthQuakeInfo{

        public String eqid;
        public Double magnitude;
        public Double lng;
        public String src;
        public String datetime;
        public Double depth;
        public Double lat;
        public String address;
    }
}
