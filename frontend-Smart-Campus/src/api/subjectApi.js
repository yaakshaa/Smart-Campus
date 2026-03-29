
import { getCampus as get, postCampus as post, put, del } from "./campusApi"

    ;

export async function fetchSubjects() {
    return get("/api/subjects");
}

export async function fetchSubjectById(id) {
    return get(`/api/subjects/${id}`);
}

export async function createSubject(subject) {
    return post("/api/subjects", subject);
}

export async function updateSubject(id, subject) {
    return put(`/api/subjects/${id}`, subject);
}

export async function deleteSubject(id) {
    return del(`/api/subjects/${id}`);
}
