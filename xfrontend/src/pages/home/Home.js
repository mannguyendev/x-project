import { Fragment, useEffect, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Home.module.css";
import UserService from "../../services/UserService";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router-dom";

const cookies = new Cookies();
const Home = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    useEffect(() => {
        const loginByToken = async () => {
            try {
                const token = cookies.get("Auth");

                const response = UserService.loginByToken(token);

                const responseData = await Promise.resolve(response);

                const user = responseData.data.user;

                if (user) {
                    dispatch(userActions.login({ user: user, authToken: responseData.data.token }));
                } else {
                    navigate("/login");
                }
            } catch (error) {
                console.log(error.response.status);
                navigate("/login");
            }
        };

        if (cookies.get("Auth")) {
            loginByToken();
        } else {
            navigate("/login");
        }
    }, [dispatch, navigate]);

    // const [username, setUsername] = useState("");
    // const [password, setPassword] = useState("");

    // // const [user, setUser] = useState(null);
    const user = useSelector((state) => state.user.user);
    const token = useSelector((state) => state.user.authToken);

    // const onChangeUsernameHandler = (e) => {
    //     e.preventDefault();
    //     setUsername(e.target.value);
    // };

    // const onChangePasswordHandler = (e) => {
    //     e.preventDefault();
    //     setPassword(e.target.value);
    // };

    // const loginHandler = async () => {
    //     console.log({ username, password });
    //     try {
    //         const response = UserService.login(username, password);

    //         const responseData = await Promise.resolve(response);

    //         console.log(responseData.data.user);

    //         dispatch(userActions.login({ user: responseData.data.user, authToken: responseData.data.token }));
    //     } catch (error) {
    //         console.log("Email hoặc mật khẩu sai vui lòng kiểm tra lại!" + error);
    //     }
    // };

    const logoutHandler = async (e) => {
        e.preventDefault();
        await UserService.logout(token);

        dispatch(userActions.logout());
        console.log(user);

        navigate("/login");
    };

    return (
        <Fragment>
            <div className={classes.Container}>
                <div className={classes.formInput}>
                    <h1>X Project - Home</h1>
                    <div>{JSON.stringify(user)}</div>
                    <div>{token}</div>
                    {/* <input
                        onChange={onChangeUsernameHandler}
                        type="text"
                        id="username"
                        lable="Username"
                        placeholder="Username"
                    />
                    <input
                        onChange={onChangePasswordHandler}
                        type="password"
                        id="password"
                        lable="Password"
                        placeholder="Password"
                    /> */}
                </div>
                <div className={classes.formSubmit}>
                    <Button onClick={logoutHandler}>Đăng xuất</Button>
                </div>
            </div>
        </Fragment>
    );
};

export default Home;
