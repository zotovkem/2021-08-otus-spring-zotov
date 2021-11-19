import React from "react";
import {Datagrid, List, TextField} from "react-admin";

/**
 * Компонент списка жанров
 */
export const GenreList = props => (
    <List {...props} pagination={null} exporter={false}>
        <Datagrid rowClick="edit" title={<span>Авторы</span>}>
            <TextField source="id" label="Ид"/>
            <TextField source="name" label="Наименование"/>
        </Datagrid>
    </List>
);
