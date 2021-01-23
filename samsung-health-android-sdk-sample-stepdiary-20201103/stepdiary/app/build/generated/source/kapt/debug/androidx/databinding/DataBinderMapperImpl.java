package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.samsung.android.app.stepdiary.DataBinderMapperImpl());
  }
}
