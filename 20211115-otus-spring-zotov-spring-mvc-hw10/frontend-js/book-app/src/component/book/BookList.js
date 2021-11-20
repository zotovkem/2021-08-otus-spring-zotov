import React from "react";
import {Datagrid, List, TextField} from "react-admin";

/**
 * Компонент списка книг
 */
export const BookList = props => (
    <List {...props} pagination={null} exporter={false}>
        <Datagrid rowClick="edit" title={<span>Книги</span>}>
            <TextField source="id" label="Ид"/>
            <TextField source="name" label="Наименование"/>
            <TextField source="releaseYear" label="Год издания"/>
            {<GenreField label= "Жанры" />}
            {<AuthorsField label= "Авторы" />}
        </Datagrid>
    </List>
);

const GenreField = ({ record }) => (
    <ul>
        {record.genres.map(item => (
            <li>  {item.name}</li>
        ))}
    </ul>
)
GenreField.defaultProps = {
    addLabel: true
};
const AuthorsField = ({ record }) => (
    <ul>
        {record.authors.map(item => (
            <li>  {item.fio}</li>
        ))}
    </ul>
)
AuthorsField.defaultProps = {
    addLabel: true
};
