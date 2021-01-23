package com.samsung.android.app.stepdiary;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/samsung/android/app/stepdiary/StepCountObserver;", "", "onBinningDataChanged", "", "binningCountList", "", "Lcom/samsung/android/app/stepdiary/StepBinningData;", "onChanged", "count", "", "app_debug"})
public abstract interface StepCountObserver {
    
    public abstract void onChanged(int count);
    
    public abstract void onBinningDataChanged(@org.jetbrains.annotations.NotNull()
    java.util.List<com.samsung.android.app.stepdiary.StepBinningData> binningCountList);
}