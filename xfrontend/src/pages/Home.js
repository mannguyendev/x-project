import { Fragment, useState } from "react";
import Button from "../components/ui/button/Button";

import classes from "./Home.module.css";
import UserService from "../services/UserService";

const Home = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [user, setUser] = useState(null);

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

            console.log(responseData.data);
            setUser(responseData.data);
        } catch (error) {
            console.log("Email hoặc mật khẩu sai vui lòng kiểm tra lại!" + error);
        }
    };

    const logoutHandler = (e) => {
        e.preventDefault();
        setUser(null);
    };

    return (
        <Fragment>
            <div className={classes.Container}>
                <div className={classes.formInput}>
                    <p>Home page</p>
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
                    {!user && <Button onClick={loginHandler}>Login</Button>}
                    {user && <Button onClick={logoutHandler}>Logout</Button>}
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
