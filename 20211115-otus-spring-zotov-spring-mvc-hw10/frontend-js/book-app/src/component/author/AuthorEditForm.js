import React from 'react';
import {Edit, SimpleForm, TextInput} from 'react-admin';

const AuthorTitle = ({record}) => {
    return <span>Редактирование автора {record ? `"${record.fio}"` : ''}</span>;
};

export const authorEdit = props => (
    <Edit {...props}  title={<AuthorTitle/>}>
        <SimpleForm>
            <TextInput disabled source="id" label="Ид"/>
            <TextInput source="fio" label="ФИО"/>
        </SimpleForm>
    </Edit>
);
