import React from "react";
import {SimpleForm, TextInput} from "react-admin";


export const AuthorSimpleForm = () => {
    return <SimpleForm>
        <TextInput disabled source="id" label="Ид"/>
        <TextInput source="fio" label="ФИО"/>
    </SimpleForm>;
};
