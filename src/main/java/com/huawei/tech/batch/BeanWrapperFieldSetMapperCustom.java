//package com.huawei.tech.batch;
//
//import com.huawei.tech.domain.Series;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.DataBinder;
//
//import java.beans.PropertyEditorSupport;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class BeanWrapperFieldSetMapperCustom extends BeanWrapperFieldSetMapper<Series>  {
//
//    @Override
//    protected void initBinder(DataBinder binder) {
////        super.initBinder(binder);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Y M");
//        binder.registerCustomEditor(YearMonth.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                text.replace(":", " ");
//                setValue(YearMonth.parse(text, formatter));
//
//            }
//
//            @Override
//            public String getAsText() throws IllegalArgumentException {
//                Object date = getValue();
//                if (date != null) {
//                    return formatter.format((YearMonth) getValue());
//                } else {
//                    return "";
//                }
//            }
//        });
//    }
//}
