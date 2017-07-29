package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * Created by stzhang on 2017/3/8.
 */
public class TagsConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if(strings != null){
            return StringUtils.join(strings, ",");
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if(s != null){
            Lists.newArrayList(StringUtils.split(s, ","));
        }
        return null;
    }
}
