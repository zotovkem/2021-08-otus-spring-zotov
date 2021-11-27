package ru.zotov.hw11.conroller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Created by ZotovES on 22.11.2021
 */
@Component
public class MapperFactoryBean implements FactoryBean<ModelMapper> {

    @Override
    public ModelMapper getObject() throws Exception {
        return new ModelMapper();
    }

    @Override
    public Class<?> getObjectType() {
        return ModelMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
