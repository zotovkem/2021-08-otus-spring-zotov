import React from 'react';
import {Create, SimpleForm, TextInput} from 'react-admin';

export const authorCreate = props => (
    <Create {...props} title={<span>Создание автора</span>}>
        <SimpleForm >
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="fio" label="ФИО"/>
        </SimpleForm>
    </Create>
);
