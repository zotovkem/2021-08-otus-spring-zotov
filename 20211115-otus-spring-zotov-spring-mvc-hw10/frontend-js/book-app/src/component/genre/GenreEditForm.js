import React from 'react';
import {Edit, SimpleForm, TextInput} from 'react-admin';

/**
 * Заголовок окна
 */
const GenreTitle = ({record}) => {
    return <span>Редактирование жанра {record ? `"${record.name}"` : ''}</span>;
};

/**
 * Форма редактирования жанра
 */
export const genreEdit = props => (
    <Edit {...props}  title={<GenreTitle/>}>
        <SimpleForm>
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="name" label="Наименование"/>
        </SimpleForm>
    </Edit>
);
