
import { getCampus as get, postCampus as post, put, del } from "./campusApi"

    ;

export async function fetchSchedules() {
    return get("/api/schedules");
}

export async function fetchScheduleById(id) {
    return get(`/api/schedules/${id}`);
}

export async function createSchedule(schedule) {
    return post("/api/schedules", schedule);
}

export async function updateSchedule(id, schedule) {
    return put(`/api/schedules/${id}`, schedule);
}

export async function deleteSchedule(id) {
    return del(`/api/schedules/${id}`);
}

