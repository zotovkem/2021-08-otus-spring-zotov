import React from 'react';
import {Edit, SimpleForm, TextInput} from 'react-admin';

/**
 * Заголовок окна
 */
const BookTitle = ({record}) => {
    return <span>Редактирование книги {record ? `"${record.name}"` : ''}</span>;
};

/**
 * Форма редактирования книги
 */
export const bookEdit = props => (
    <Edit {...props}  title={<BookTitle/>}>
        <SimpleForm>
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="name" label="Наименование"/>
        </SimpleForm>
    </Edit>
);
