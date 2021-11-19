import {SimpleForm, TextInput} from "react-admin";
import React from "react";

export const AuthorEdit = (props)=>(
    <SimpleForm >
        <TextInput disabled source="id" label="Ид"/>
        <TextInput source="fio" label="ФИО"/>
    </SimpleForm>
)
