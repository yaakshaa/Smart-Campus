import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
});

export const getAllBooks = () => 
   api
    .get("/books/bookLists")
    .then((resp) => Array.isArray(resp.data.data) ? resp.data.data : []);

export const getBooksByKeyword = (keyword) =>
  api
    .get("/books", { params: { keyword } })
    .then((resp) => Array.isArray(resp.data.data) ? resp.data.data : []);

export const getBooksByCategory = category =>
  api.get(`/books/category?category=${encodeURIComponent(category)}`)
     .then(res => res.data.data || res.data);    

export const getBookById = (bookId) =>
  api.get(`/books/${bookId}`);

export function borrowBook(bookId) {
  return api.post('/borrows', { bookId });
}

export function returnBook(borrowId) {
  return api.put(`/borrows/return/${borrowId}`);
}

