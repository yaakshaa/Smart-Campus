import { getCampus as get, postCampus as post, put, del } from './campusApi';

export const fetchMenus = () => get('/api/menus').catch(err => {
    console.error('fetchMenus API failed:', err);
    return [];
});

export const createMenu = (menu) => post('/api/menus', menu).catch(err => {
    console.error('createMenu API failed:', err);
    return null;
});

export const updateMenu = (id, menu) => put(`/api/menus/${id}`, menu).catch(err => {
    console.error(`updateMenu API failed (id=${id}):`, err);
    return null;
});

export const deleteMenu = (id) => del(`/api/menus/${id}`).then(res => res).catch(err => {
    console.error(`deleteMenu API failed (id=${id}):`, err);
    return false;
});
