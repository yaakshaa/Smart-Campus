import { postCampus as post, getCampus as get, del } from './campusApi';


export const fetchReviews = () => get('/api/reviews').catch(err => {
    console.error('fetchReviews API failed:', err);
    return [];
});

export const deleteReview = (id) => del(`/api/reviews/${id}`).then(res => res).catch(err => {
    console.error(`deleteReview API failed (id=${id}):`, err);
    return [];
});

export const createReview = (review) => post('/api/reviews', review).catch(err => {
    console.error('createReview API failed:', err);
    return null;
});
