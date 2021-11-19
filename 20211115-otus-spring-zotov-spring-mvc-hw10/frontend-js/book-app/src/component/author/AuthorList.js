import React from "react";
import {Datagrid, List, TextField} from "react-admin";

export const AuthorList = props => (
    <List {...props} pagination={null} exporter={false}>
        <Datagrid rowClick="edit" title={<span>Авторы</span>}>
            <TextField source="id" label="Ид"/>
            <TextField source="fio" label="ФИО"/>
        </Datagrid>
    </List>
);
