import { getCampus as get } from './campusApi';


export const fetchRestaurants = () => get('/api/restaurants').catch(err => {
    console.error('fetchRestaurants API failed:', err);
    return [];
});