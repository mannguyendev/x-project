import axios from "axios";
import { BACKEND_URL } from "../utilities/constant";

class UserService {
    login(username, password) {
        return axios.post(`${BACKEND_URL}/users/login`, {
            username,
            password,
        });
    }

    // signup(email, password, name = "", phone = "0") {
    //     return axios.post(`${BACKEND_URL}/users`, {
    //         name,
    //         email,
    //         password,
    //         phone,
    //     });
    // }
    // getUserById(id) {
    //     return axios.get(`${BACKEND_URL}/users/${id}`);
    // }
    // updateReview(data) {
    //     return axios.put(`${url}/api/v1/movies/review`, data);
    // }
    // deleteReview(id, userId) {
    //     return axios.delete(`${url}/api/v1/movies/review`, {
    //         data: { review_id: id, user_id: userId },
    //     });
    // }
    // getRatings() {
    //     return axios.get(`${url}/api/v1/movies/ratings`);
    // }
}

const userService = new UserService();

export default userService;
