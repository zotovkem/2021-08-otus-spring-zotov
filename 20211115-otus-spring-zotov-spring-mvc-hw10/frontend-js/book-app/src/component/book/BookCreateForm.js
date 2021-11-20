import React from 'react';
import {Create, SimpleForm, TextInput} from 'react-admin';

/**
 * Форма создания книги
 */
export const bookCreate = props => (
    <Create {...props} title={<span>Создание книги</span>}>
        <SimpleForm >
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="name" label="Наименование"/>
        </SimpleForm>
    </Create>
);
