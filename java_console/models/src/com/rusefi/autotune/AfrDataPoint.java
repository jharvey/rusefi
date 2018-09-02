package com.rusefi.autotune;

import com.rusefi.config.Fields;

/**
 * Air/Fuel ratio data point
 *
 * (c) Andrey Belomutskiy 2013-2017
 * 2/23/2016.
 */
public class AfrDataPoint {
    public final double AFR;
    private final int rpm;
    private final double engineLoad;
    int rpmIndex;
    int engineLoadIndex;

    public AfrDataPoint(double AFR, int rpmIndex, int engineLoadIndex, int rpm, double engineLoad) {
        this.rpm = rpm;
        this.engineLoad = engineLoad;
        if (rpmIndex < 0 || rpmIndex >= Fields.FUEL_RPM_COUNT)
            throw new IllegalStateException("rpmIndex " + rpmIndex);
        if (engineLoadIndex < 0 || engineLoadIndex >= Fields.FUEL_LOAD_COUNT)
            throw new IllegalStateException("engineLoadIndex " + engineLoadIndex);
        this.AFR = AFR;
        this.rpmIndex = rpmIndex;
        this.engineLoadIndex = engineLoadIndex;
    }

    public static AfrDataPoint valueOf(double afr, int rpm, double engineLoad) {
        int rpmIndex = (int) (rpm / 7000.0 * FuelAutoTune.SIZE);
        if (rpmIndex < 0 || rpmIndex >= Fields.FUEL_RPM_COUNT)
            return null;
        int engineLoadIndex = (int) (engineLoad / 120.0 * FuelAutoTune.SIZE);
        return new AfrDataPoint(afr, rpmIndex, engineLoadIndex, rpm, engineLoad);
    }

    int getRpmIndex() {
        return rpmIndex;
    }

    public int getEngineLoadIndex() {
        return (int) engineLoadIndex;
    }

    public int PRESS_RT_32() {
        return getEngineLoadIndex();
    }

    public int RPM_RT_32() {
        return getRpmIndex();
    }

    public int getRpm() {
        return rpm;
    }

    public double getEngineLoad() {
        return engineLoad;
    }
}