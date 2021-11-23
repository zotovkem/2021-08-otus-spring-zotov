import {fetchUtils} from 'react-admin';

const apiUrl = 'http://localhost:9000/api';
const httpClient = fetchUtils.fetchJson;

export const dataProvider = {
    getList: (resource) => {
        const url = `${apiUrl}/${resource}`

        return httpClient(url).then(({json}) => ({
            data: json,
            total: json.length,
        }));
    },

    getMany: (resource) => {
        const url = `${apiUrl}/${resource}`

        return httpClient(url).then(({json}) => ({
            data: json,
            total: json.length,
        }));
    },

    getOne: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`).then(({json}) => ({
            data: json,
        })),

    update: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({json}) => ({data: json})),

    create: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
        }).then(({json}) => ({
            data: {json, id: json.id},
        })),

    delete: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'DELETE',
            body: JSON.stringify([params.id]),
        }).then(({json}) => ({data: []})),

    deleteMany: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'DELETE',
            body: JSON.stringify(params.ids)
        }).then(({json}) => ({data: []}))
};

