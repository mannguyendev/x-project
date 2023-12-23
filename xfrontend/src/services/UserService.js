import axios from "axios";
import { BACKEND_URL } from "../utilities/Constants";

class UserService {
    login(username, password) {
        return axios.post(`${BACKEND_URL}/users/login`, {
            username,
            password,
        });
    }

    logout(token) {
        return axios.post(
            `${BACKEND_URL}/users/logout`,
            {},
            {
                headers: {
                    Authorization: "Bearer " + token, //the token is a variable which holds the token
                },
            }
        );
    }

    loginByToken(token) {
        return axios.post(`${BACKEND_URL}/users/verifyToken`, { token });
    }

    signup(user) {
        console.log(user);
        return axios.post(`${BACKEND_URL}/users/`, user);
    }

    updateUserById(token, user) {
        return axios.patch(`${BACKEND_URL}/users/${user.id}`, user, {
            headers: {
                Authorization: "Bearer " + token, //the token is a variable which holds the token
            },
        });
    }
}

const userService = new UserService();

export default userService;
