import { Fragment, useEffect, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Home.module.css";
import UserService from "../../services/UserService";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import Cookies from "universal-cookie";

const cookies = new Cookies();
const Home = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        const loginByToken = async () => {
            const token = cookies.get("Auth");

            const response = UserService.loginByToken(token);

            const responseData = await Promise.resolve(response);

            console.log(responseData.data.user);

            dispatch(userActions.login({ user: responseData.data.user, authToken: responseData.data.token }));
        };

        if (cookies.get("Auth")) {
            loginByToken();
        }
    }, [dispatch]);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    // const [user, setUser] = useState(null);
    const user = useSelector((state) => state.user.user);
    const token = useSelector((state) => state.user.authToken);

    const onChangeUsernameHandler = (e) => {
        e.preventDefault();
        setUsername(e.target.value);
    };

    const onChangePasswordHandler = (e) => {
        e.preventDefault();
        setPassword(e.target.value);
    };

    const loginHandler = async () => {
        console.log({ username, password });
        try {
            const response = UserService.login(username, password);

            const responseData = await Promise.resolve(response);

            console.log(responseData.data.user);

            dispatch(userActions.login({ user: responseData.data.user, authToken: responseData.data.token }));
        } catch (error) {
            console.log("Email hoặc mật khẩu sai vui lòng kiểm tra lại!" + error);
        }
    };

    const logoutHandler = async (e) => {
        e.preventDefault();
        await UserService.logout(token);

        dispatch(userActions.logout());
        console.log(user);
    };

    return (
        <Fragment>
            <div className={classes.Container}>
                <div className={classes.formInput}>
                    <h1>X Project</h1>
                    <input
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
                    />
                </div>
                <div className={classes.formSubmit}>
                    {!user && <Button onClick={loginHandler}>Đăng nhập</Button>}
                    {user && <Button onClick={logoutHandler}>Đăng xuất</Button>}
                </div>
                {user && (
                    <div className={classes.loginMessage}>
                        <div className={classes.loginMessage}>Đăng nhập thành công</div>
                        <div>Username: {user.username}</div>
                    </div>
                )}
            </div>
        </Fragment>
    );
};

export default Home;
