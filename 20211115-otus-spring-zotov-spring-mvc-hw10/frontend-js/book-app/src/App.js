import React from 'react';
import {Admin, Resource} from 'react-admin';
import {dataProvider} from './provider/DataProvider';
import polyglotI18nProvider from 'ra-i18n-polyglot';
import russianMessages from 'ra-language-russian';
import UserIcon from '@material-ui/icons/Group';
import LibraryBooksIcon from '@material-ui/icons/LibraryBooks';
import MenuBook from '@material-ui/icons/MenuBook';
import Comment from '@material-ui/icons/Comment';
import {authorEdit} from "./component/author/AuthorEditForm";
import {authorCreate} from "./component/author/AuthorCreateForm";
import {AuthorList} from "./component/author/AuthorList";
import {GenreList} from "./component/genre/GenreList";
import {genreEdit} from "./component/genre/GenreEditForm";
import {genreCreate} from "./component/genre/GenreCreateForm";

const i18nProvider = polyglotI18nProvider(() => russianMessages, 'ru');

function App() {
    return (
        <div className="App">
            <Admin title="Library" dataProvider={dataProvider} i18nProvider={i18nProvider}>
                <Resource name={"books"} list={GenreList} edit={genreEdit}  create={genreCreate} icon={MenuBook} options={{ label: 'Книги' }} />
                <Resource name={"comments"} list={GenreList} edit={genreEdit}  create={genreCreate} icon={Comment} options={{ label: 'Комментарии' }} />
                <Resource name={"authors"} list={AuthorList} edit={authorEdit} create={authorCreate} icon={UserIcon} options={{ label: 'Авторы' }} />
                <Resource name={"genres"} list={GenreList} edit={genreEdit}  create={genreCreate} icon={LibraryBooksIcon} options={{ label: 'Жанры' }} />
            </Admin>;
        </div>
    )
}

export default App;
