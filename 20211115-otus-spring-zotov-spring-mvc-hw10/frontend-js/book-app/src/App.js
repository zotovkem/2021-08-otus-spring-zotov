import React from 'react';
import {Admin, Resource} from 'react-admin';
import {dataProvider} from './provider/DataProvider';
import polyglotI18nProvider from 'ra-i18n-polyglot';
import russianMessages from 'ra-language-russian';
import UserIcon from '@material-ui/icons/Group';
import {authorEdit} from "./component/author/AuthorEditForm";
import {authorCreate} from "./component/author/AuthorCreateForm";
import {AuthorList} from "./component/author/AuthorList";

const i18nProvider = polyglotI18nProvider(() => russianMessages, 'ru');

function App() {
    return (
        <div className="App">
            <Admin title="Library" dataProvider={dataProvider} i18nProvider={i18nProvider}>
                <Resource name={"authors"} list={AuthorList} edit={authorEdit} create={authorCreate} icon={UserIcon} options={{ label: 'Авторы' }} />
            </Admin>;
        </div>
    )
}

export default App;
