import React from 'react';
import {ArrayInput, Edit, NumberInput, ReferenceInput, SelectInput, SimpleForm, SimpleFormIterator, TextInput} from 'react-admin';

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
    <Edit {...props} title={<BookTitle/>}>
        <SimpleForm>
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="name" label="Наименование"/>
            <NumberInput source="releaseYear" label="Год издания"/>
            <ArrayInput source="genres" label="Жанры">
                <SimpleFormIterator>
                    <ReferenceInput label="Жанр" source="id" reference="genres">
                        <SelectInput source="name"/>
                    </ReferenceInput>
                </SimpleFormIterator>
            </ArrayInput>
            <ArrayInput source="authors" label="Авторы">
                <SimpleFormIterator>
                    <ReferenceInput label="Авторы" source="id" reference="authors">
                        <SelectInput optionText="fio" optionValue="id" fullWidth/>
                    </ReferenceInput>
                </SimpleFormIterator>
            </ArrayInput>
            <ArrayInput source="comments" label="Комментарии">
                <SimpleFormIterator>
                        <TextInput label="Текст комментария" source="content"/>
                        <TextInput label="Автор" source="author"/>
                </SimpleFormIterator>
            </ArrayInput>
        </SimpleForm>
    </Edit>
);
