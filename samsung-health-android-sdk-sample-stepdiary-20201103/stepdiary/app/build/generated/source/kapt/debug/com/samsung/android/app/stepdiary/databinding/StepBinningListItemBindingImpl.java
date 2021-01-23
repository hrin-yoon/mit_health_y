package com.samsung.android.app.stepdiary.databinding;
import com.samsung.android.app.stepdiary.R;
import com.samsung.android.app.stepdiary.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class StepBinningListItemBindingImpl extends StepBinningListItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public StepBinningListItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private StepBinningListItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            );
        this.mainText.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.secondaryText.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.data == variableId) {
            setData((com.samsung.android.app.stepdiary.StepBinningData) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setData(@Nullable com.samsung.android.app.stepdiary.StepBinningData Data) {
        this.mData = Data;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.data);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        int dataCount = 0;
        java.lang.String stringFormatMainTextAndroidStringPercentDStepsDataCount = null;
        com.samsung.android.app.stepdiary.StepBinningData data = mData;
        java.lang.String dataTime = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (data != null) {
                    // read data.count
                    dataCount = data.getCount();
                    // read data.time
                    dataTime = data.getTime();
                }


                // read String.format(@android:string/percent_d_steps, data.count)
                stringFormatMainTextAndroidStringPercentDStepsDataCount = java.lang.String.format(mainText.getResources().getString(R.string.percent_d_steps), dataCount);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mainText, stringFormatMainTextAndroidStringPercentDStepsDataCount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.secondaryText, dataTime);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): data
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}