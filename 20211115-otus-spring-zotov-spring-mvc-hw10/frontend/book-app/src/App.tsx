import React from 'react';
import {Admin, Datagrid, List, ListGuesser, Resource, TextField} from 'react-admin';
import dataProvider from './data-provider/DataProvider';
import polyglotI18nProvider from 'ra-i18n-polyglot';
// @ts-ignore
import russianMessages from 'ra-language-russian';

const i18nProvider = polyglotI18nProvider(() => russianMessages, 'ru');

function App() {
    // const UserList = props => (
    //     <List {...props}>
    //         <Datagrid rowClick="edit">
    //             <TextField source="id" />
    //         </Datagrid>
    //     </List>
    // );
    return (
        <div className="App">
            <Admin title="Library" dataProvider={dataProvider} i18nProvider={i18nProvider}>
                <Resource name={"authors"} list={ListGuesser}/>
            </Admin>;
        </div>
    );
}

export default App;
