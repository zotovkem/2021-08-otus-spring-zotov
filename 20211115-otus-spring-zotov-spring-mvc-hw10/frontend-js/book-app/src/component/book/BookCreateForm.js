import React from 'react';
import {
    ArrayInput,
    Create,
    NumberInput,
    ReferenceInput,
    SelectInput,
    SimpleForm,
    SimpleFormIterator,
    TextInput
} from 'react-admin';

/**
 * Форма создания книги
 */
export const bookCreate = props => (
    <Create {...props} title={<span>Создание книги</span>}>
        <SimpleForm >
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
    </Create>
);
