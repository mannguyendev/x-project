import { Fragment, useEffect, useState } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Home.module.css";
import UserService from "../../services/UserService";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router-dom";
import XLogo from "../../components/ui/logo/XLogo";
import toast from "react-hot-toast";
import Input from "../../components/ui/input/Input";

let logMsg;

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
                    setEnderedUsername(user.username);
                    setEnderedDateOfBirth(user.dateOfBirth);
                    setEnderedEmail(user.email);
                    setEnderedGender(user.gender);
                    setEnderedPhoneNo(user.phoneNo);
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
    const [editable, setEditable] = useState(false);
    const [enteredUsername, setEnderedUsername] = useState(user ? user.username : "");
    const [enteredEmail, setEnderedEmail] = useState(user ? user.email : "");
    const [enteredPhoneNo, setEnderedPhoneNo] = useState(user ? user.phoneNo : "");
    const [enteredDateOfBirth, setEnderedDateOfBirth] = useState(user ? user.dateOfBirth : "");
    const [enteredGender, setEnderedGender] = useState(user ? user.gender : "");

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

        toast.success("Đăng xuất thành công!");
        navigate("/login");
    };

    if (!user) {
        return;
    }

    const editHander = () => {
        setEditable(true);
    };

    const saveHander = async () => {
        try {
            const updatedUser = {
                id: user.id,
                username: enteredUsername,
                email: enteredEmail,
                phoneNo: enteredPhoneNo,
                gender: enteredGender,
                dateOfBirth: enteredDateOfBirth,
            };
            console.log(updatedUser);

            const response = UserService.updateUserById(token, updatedUser);

            const responseData = await Promise.resolve(response);

            console.log(responseData.data);

            dispatch(userActions.login({ user: responseData.data, authToken: token }));
            toast.success("Cập nhật tài khoản thành công!");

            setEditable(false);
        } catch (error) {
            console.log(error);
            logMsg = "Có lỗi trong quá trình cập nhật tài khoản: " + error.response.data.Error;
            toast.error(logMsg);
        }
    };

    return (
        <Fragment>
            <XLogo />
            <div className={classes.container}>
                <div className={classes.infoContainer}>
                    <div className={classes.title}>Xin chào, {user.username}!</div>
                    <div className={classes.info}>
                        <label>Tên đăng nhập:</label>
                        <Input
                            onChange={(e) => setEnderedUsername(e.target.value)}
                            value={enteredUsername}
                            editable={false}
                        />
                    </div>
                    <div className={classes.info}>
                        <label>Email:</label>
                        <Input
                            onChange={(e) => setEnderedEmail(e.target.value)}
                            value={enteredEmail}
                            editable={editable}
                        />
                    </div>
                    <div className={classes.info}>
                        <label>Số điện thoại:</label>
                        <Input
                            onChange={(e) => setEnderedPhoneNo(e.target.value)}
                            value={enteredPhoneNo}
                            editable={editable}
                        />
                    </div>
                    <div className={classes.info}>
                        <label>Ngày sinh:</label>
                        <Input
                            onChange={(e) => setEnderedDateOfBirth(e.target.value)}
                            value={enteredDateOfBirth}
                            editable={editable}
                        />
                    </div>
                    <div className={classes.info}>
                        <label>Giới tính:</label>
                        <Input
                            onChange={(e) => setEnderedGender(e.target.value)}
                            value={enteredGender}
                            editable={editable}
                        />
                    </div>
                    <div className={classes.editBtn}>
                        {!editable && (
                            <Button onClick={editHander} className={classes.btn}>
                                Chỉnh sửa
                            </Button>
                        )}
                        {editable && (
                            <Button onClick={saveHander} className={classes.btn}>
                                Lưu
                            </Button>
                        )}
                    </div>
                </div>
                <div className={classes.menu}>
                    <div className={classes.title}>Menu</div>
                    <Button>Quản lý tài khoản</Button>
                    <Button>Đổi mật khẩu</Button>
                    <Button secondary={true} onClick={logoutHandler}>
                        Đăng xuất
                    </Button>
                </div>
            </div>
        </Fragment>
    );
};

export default Home;
