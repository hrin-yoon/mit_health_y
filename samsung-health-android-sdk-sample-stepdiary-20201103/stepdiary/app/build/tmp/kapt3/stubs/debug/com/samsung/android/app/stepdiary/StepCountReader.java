package com.samsung.android.app.stepdiary;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0011H\u0002J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/samsung/android/app/stepdiary/StepCountReader;", "", "store", "Lcom/samsung/android/sdk/healthdata/HealthDataStore;", "observer", "Lcom/samsung/android/app/stepdiary/StepCountObserver;", "(Lcom/samsung/android/sdk/healthdata/HealthDataStore;Lcom/samsung/android/app/stepdiary/StepCountObserver;)V", "healthDataResolver", "Lcom/samsung/android/sdk/healthdata/HealthDataResolver;", "getBinningData", "", "Lcom/samsung/android/app/stepdiary/StepBinningData;", "zip", "", "readStepCount", "", "startTime", "", "readStepCountBinning", "deviceUuid", "", "readStepDailyTrend", "dayStartTime", "requestDailyStepCount", "Companion", "app_debug"})
public final class StepCountReader {
    private final com.samsung.android.sdk.healthdata.HealthDataResolver healthDataResolver = null;
    private final com.samsung.android.app.stepdiary.StepCountObserver observer = null;
    private static final long TODAY_START_UTC_TIME = 0L;
    private static final long TIME_INTERVAL = 0L;
    private static final java.lang.String ALIAS_TOTAL_COUNT = "count";
    private static final java.lang.String ALIAS_DEVICE_UUID = "deviceuuid";
    private static final java.lang.String ALIAS_BINNING_TIME = "binning_time";
    public static final com.samsung.android.app.stepdiary.StepCountReader.Companion Companion = null;
    
    public final void requestDailyStepCount(long startTime) {
    }
    
    private final void readStepCount(long startTime) {
    }
    
    private final void readStepDailyTrend(long dayStartTime) {
    }
    
    private final java.util.List<com.samsung.android.app.stepdiary.StepBinningData> getBinningData(byte[] zip) {
        return null;
    }
    
    private final void readStepCountBinning(long startTime, java.lang.String deviceUuid) {
    }
    
    public StepCountReader(@org.jetbrains.annotations.NotNull()
    com.samsung.android.sdk.healthdata.HealthDataStore store, @org.jetbrains.annotations.NotNull()
    com.samsung.android.app.stepdiary.StepCountObserver observer) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/samsung/android/app/stepdiary/StepCountReader$Companion;", "", "()V", "ALIAS_BINNING_TIME", "", "ALIAS_DEVICE_UUID", "ALIAS_TOTAL_COUNT", "TIME_INTERVAL", "", "getTIME_INTERVAL", "()J", "TODAY_START_UTC_TIME", "getTODAY_START_UTC_TIME", "todayStartUtcTime", "getTodayStartUtcTime", "app_debug"})
    public static final class Companion {
        
        public final long getTODAY_START_UTC_TIME() {
            return 0L;
        }
        
        public final long getTIME_INTERVAL() {
            return 0L;
        }
        
        private final long getTodayStartUtcTime() {
            return 0L;
        }
        
        private Companion() {
            super();
        }
    }
}