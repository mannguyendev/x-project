import { createSlice } from "@reduxjs/toolkit";
import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";

const cookies = new Cookies();

const userSlide = createSlice({
    name: "user",
    initialState: {
        isLoggedIn: false,
        authToken: null,
        user: null,
    },
    reducers: {
        login(state, action) {
            state.isLoggedIn = true;
            state.user = action.payload.user;
            state.authToken = action.payload.authToken;
            const decode = jwtDecode(action.payload.authToken);
            console.log(decode);
            cookies.set("Auth", action.payload.authToken, { expires: new Date(decode.exp * 1000) });
            console.log(cookies.get("Auth"));
        },
        logout(state, action) {
            state.isLoggedIn = false;
            state.user = null;
            cookies.remove("Auth");
            console.log(cookies.get("Auth"));
        },
        signup(state, action) {
            state.isLoggedIn = true;
            state.user.user = action.payload.user;
        },
    },
});

export const userActions = userSlide.actions;

export default userSlide;
