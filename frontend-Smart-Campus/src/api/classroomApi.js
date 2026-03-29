import { getCampus as get, postCampus as post, put, del } from "./campusApi";

export async function fetchClassrooms() {
    return get("/api/classrooms");
}

export async function fetchClassroomById(id) {
    return get(`/api/classrooms/${id}`);
}

export async function createClassroom(classroom) {
    return post("/api/classrooms", classroom);
}

export async function updateClassroom(id, classroom) {
    return put(`/api/classrooms/${id}`, classroom);
}

export async function deleteClassroom(id) {
    return del(`/api/classrooms/${id}`);
}
