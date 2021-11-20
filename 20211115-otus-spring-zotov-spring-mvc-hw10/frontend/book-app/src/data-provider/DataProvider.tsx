import {fetchUtils} from 'react-admin';
import {stringify} from 'query-string';
import axios from "axios";
import HttpError from "ra-core/src/dataProvider/HttpError";

const apiUrl = 'http://localhost:8080/api';
const httpClient = fetchUtils.fetchJson;

export default {
    getList: async (resource: String, params: any) => {
        const {page, perPage} = params.pagination;
        const {field, order} = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
            filter: JSON.stringify(params.filter),
        };
        // const url = `${apiUrl}/${resource}?${stringify(query)}`;
        const url = `${apiUrl}/${resource}`
        const instanceAxios = axios.create(
            // {
            // baseURL: url,
            // headers:{'Access-Control-Allow-Origin': '*'},}
        );

        return instanceAxios.get(url)
            .then(({headers, data}) => ({
            data: data,
            total:data.length,
        }));

         fetch(url, {mode: 'no-cors'})
             .then(response => response.json());
         return{};
    },

    getOne: (resource: String, params: any) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`).then(({json}) => ({
            data: json,
        })),

    getMany: (resource: String, params: any) => {
        const query = {
            filter: JSON.stringify({id: params.ids}),
        };
        // const url = `${apiUrl}/${resource}?${stringify(query)}`;
        const url = `${apiUrl}/${resource}`;
        return httpClient(url).then(({json}) => ({data: json}));
    },

    getManyReference: (resource: String, params: any) => {
        const {page, perPage} = params.pagination;
        const {field, order} = params.sort;
        // const query = {
        //     sort: JSON.stringify([field, order]),
        //     range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
        //     filter: JSON.stringify({
        //         ...params.filter,
        //         [params.target]: params.id,
        //     }),
        // };
        // const url = `${apiUrl}/${resource}?${stringify(query)}`;
        const url = `${apiUrl}/${resource}`;

        return httpClient(url).then(({headers, json}) => ({
            data: json,
            // total: parseInt(headers.get('content-range').split('/').pop(), 10),
            total: 0,
        }));
    },

    update: (resource: String, params: any) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({json}) => ({data: json})),

    updateMany: (resource: String, params: any) => {
        const query = {
            filter: JSON.stringify({id: params.ids}),
        };
        return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({json}) => ({data: json}));
    },

    create: (resource: String, params: any) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
        }).then(({json}) => ({
            data: {...params.data, id: json.id},
        })),

    delete: (resource: String, params: any) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'DELETE',
        }).then(({json}) => ({data: json})),

    deleteMany: (resource: String, params: any) => {
        const query = {
            filter: JSON.stringify({id: params.ids}),
        };
        return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
            method: 'DELETE',
        }).then(({json}) => ({data: json}));
    }
};
