import React from 'react';
import {Admin, Resource} from 'react-admin';
import {dataProvider} from './provider/DataProvider';
import polyglotI18nProvider from 'ra-i18n-polyglot';
import russianMessages from 'ra-language-russian';
import UserIcon from '@material-ui/icons/Group';
import LibraryBooksIcon from '@material-ui/icons/LibraryBooks';
import MenuBook from '@material-ui/icons/MenuBook';
import {authorEdit} from "./component/author/AuthorEditForm";
import {authorCreate} from "./component/author/AuthorCreateForm";
import {AuthorList} from "./component/author/AuthorList";
import {GenreList} from "./component/genre/GenreList";
import {genreEdit} from "./component/genre/GenreEditForm";
import {genreCreate} from "./component/genre/GenreCreateForm";
import {BookList} from "./component/book/BookList";
import {bookCreate} from "./component/book/BookCreateForm";
import {bookEdit} from "./component/book/BookEditForm";
import {createTheme} from '@material-ui/core/styles';

const i18nProvider = polyglotI18nProvider(() => russianMessages, 'ru');

const theme = createTheme({
    palette: {
        type: 'dark',
        secondary: {
            light: '#7986cb',
            main: '#3f51b5',
            dark: '#303f9f',
            contrastText: '#fff'
        }
    },
});

function App() {
    return (
        <div className="App">
            <Admin theme={theme} title="Library" dataProvider={dataProvider} i18nProvider={i18nProvider}>
                <Resource name={"books"} list={BookList} edit={bookEdit} create={bookCreate} icon={MenuBook}
                          options={{label: 'Книги'}}/>
                <Resource name={"authors"} list={AuthorList} edit={authorEdit} create={authorCreate} icon={UserIcon}
                          options={{label: 'Авторы'}}/>
                <Resource name={"genres"} list={GenreList} edit={genreEdit} create={genreCreate} icon={LibraryBooksIcon}
                          options={{label: 'Жанры'}}/>
            </Admin>;
        </div>
    )
}

export default App;
