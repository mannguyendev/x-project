import { Fragment, useEffect, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Login.module.css";
import UserService from "../../services/UserService";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
// import Cookies from "universal-cookie";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import logo from "../../assets/logo.png";

// const cookies = new Cookies();
const Login = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    // useEffect(() => {
    //     const loginByToken = async () => {
    //         try {
    //             const token = cookies.get("Auth");

    //             const response = UserService.loginByToken(token);

    //             const responseData = await Promise.resolve(response);

    //             const user = responseData.data.user;

    //             if (user) {
    //                 dispatch(userActions.login({ user: user, authToken: responseData.data.token }));
    //                 navigate("/home");
    //             }

    //             console.log(responseData.data.user);
    //         } catch (error) {
    //             console.log(error.response.status);
    //         }
    //     };

    //     if (cookies.get("Auth")) {
    //         loginByToken();
    //     }
    // }, [dispatch, navigate]);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    // const [user, setUser] = useState(null);
    const user = useSelector((state) => state.user.user);

    const onChangeUsernameHandler = (e) => {
        e.preventDefault();
        setUsername(e.target.value);
        setError(null);
    };

    const onChangePasswordHandler = (e) => {
        e.preventDefault();
        setPassword(e.target.value);
        setError(null);
    };

    const loginHandler = async () => {
        console.log({ username, password });
        try {
            const response = UserService.login(username, password);

            const responseData = await Promise.resolve(response);

            console.log(responseData.data.user);

            dispatch(userActions.login({ user: responseData.data.user, authToken: responseData.data.token }));
            navigate("/");
        } catch (error) {
            console.log(error.message);
            setError("Tên đăng nhập hoặc mật khẩu sai vui lòng kiểm tra lại!");
            toast.error("Tên đăng nhập hoặc mật khẩu sai vui lòng kiểm tra lại!");
        }
    };

    return (
        <Fragment>
            <div className={classes.container}>
                <div className={classes.formInput}>
                    <div className={classes.logoContainer}>
                        <img className={classes.logo} src={logo} alt="x-project" />
                        <h2>Project</h2>
                    </div>
                    <input
                        onChange={onChangeUsernameHandler}
                        type="text"
                        id="username"
                        lable="Username"
                        placeholder="Username"
                        style={error ? { color: "var(--red800)" } : {}}
                    />
                    <input
                        onChange={onChangePasswordHandler}
                        type="password"
                        id="password"
                        lable="Password"
                        placeholder="Password"
                        style={error ? { color: "var(--red800)" } : {}}
                    />
                </div>
                <div className={classes.formSubmit}>
                    <Button onClick={loginHandler}>
                        <b>Đăng nhập</b>
                    </Button>
                    <div className={classes.error}>{error}</div>
                    <div>
                        Bạn chưa có tài khoản,{" "}
                        <Link className={classes.signupBtn} to={"/signup"}>
                            Đăng ký ngay!
                        </Link>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default Login;
