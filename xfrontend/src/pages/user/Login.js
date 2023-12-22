import { Fragment, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Login.module.css";
import UserService from "../../services/UserService";
import { useDispatch } from "react-redux";
import { userActions } from "../../store/user-slice";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import XLogo from "../../components/ui/logo/XLogo";
import Input from "../../components/ui/input/Input";

const Login = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

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
            toast.success("Đăng nhập thành công!");
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
                    <XLogo />
                    <Input
                        onChange={onChangeUsernameHandler}
                        type="text"
                        id="username"
                        lable="Username"
                        placeholder="Tên đăng nhập"
                        error={error}
                    />
                    <Input
                        onChange={onChangePasswordHandler}
                        type="password"
                        id="password"
                        lable="Password"
                        placeholder="Mật khẩu"
                        error={error}
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
