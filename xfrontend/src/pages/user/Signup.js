import { Fragment, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Signup.module.css";
import UserService from "../../services/UserService";
import { useDispatch } from "react-redux";
import { userActions } from "../../store/user-slice";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import XLogo from "../../components/ui/logo/XLogo";
import Input from "../../components/ui/input/Input";

let logMsg;

const Signup = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [rePassword, setRePassword] = useState("");
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

    const onChangeRePasswordHandler = (e) => {
        e.preventDefault();
        setRePassword(e.target.value);
        setError(null);
    };

    const signupHandler = async () => {
        console.log({ username, password, rePassword });

        if (password === "" || rePassword === "" || username === "") {
            logMsg = "Xin vui lòng nhập đủ thông tin!";
            setError(logMsg);
            toast.error(logMsg);
            return;
        }

        if (password !== rePassword) {
            logMsg = "Mật khẩu nhập lại không khớp!";
            setError(logMsg);
            toast.error(logMsg);
            return;
        }

        try {
            const user = {
                username,
                password,
            };

            const response = UserService.signup(user);

            const responseData = await Promise.resolve(response);

            console.log(responseData.data.user);

            dispatch(userActions.login({ user: responseData.data.user, authToken: responseData.data.token }));
            toast.success("Tạo tài khoản thành công!");
            navigate("/");
        } catch (error) {
            console.log(error);
            logMsg = "Có lỗi trong quá trình tạo tài khoản: " + error.response.data.Error;
            setError(logMsg);
            toast.error(logMsg);
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
                    <Input
                        onChange={onChangeRePasswordHandler}
                        type="password"
                        id="re-password"
                        lable="Re-Password"
                        placeholder="Nhập lại mật khẩu"
                        error={error}
                    />
                </div>
                <div className={classes.formSubmit}>
                    <Button onClick={signupHandler}>
                        <b>Đăng ký</b>
                    </Button>
                    <div className={classes.error}>{error}</div>
                    <div>
                        Bạn đã có tài khoản,{" "}
                        <Link className={classes.btn} to={"/login"}>
                            Đăng nhập!
                        </Link>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};

export default Signup;
