import { Fragment, useEffect } from "react";
import Button from "../../components/ui/button/Button";

import classes from "./Home.module.css";
import UserService from "../../services/UserService";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router-dom";
import XLogo from "../../components/ui/logo/XLogo";
import toast from "react-hot-toast";

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

        toast.success("Đăng xuất thành công!");
        navigate("/login");
    };

    if (!user) {
        return;
    }

    return (
        <Fragment>
            <XLogo />
            <div className={classes.container}>
                <div className={classes.infoContainer}>
                    <div className={classes.title}>
                        Xin chào, {user.firstName} {user.lastName}!
                    </div>
                    <div className={classes.info}>
                        <label>Tên đăng nhập:</label>
                        <div>{user.username}</div>
                    </div>
                    <div className={classes.info}>
                        <label>Email:</label>
                        <div>{user.email}</div>
                    </div>
                    <div className={classes.info}>
                        <label>Số điện thoại:</label>
                        <div>{user.phoneNo}</div>
                    </div>
                    <div className={classes.info}>
                        <label>Ngày sinh:</label>
                        <div>{user.dateOfBirth}</div>
                    </div>
                    <div className={classes.info}>
                        <label>Ngày tạo TK:</label>
                        <div>{user.createdAt}</div>
                    </div>
                </div>
                <div className={classes.menu}>
                    <div className={classes.title}>Menu</div>
                    <Button>Quản lý tài khoản</Button>
                    <Button>Quản lý khách hàng</Button>
                    <Button secondary={true} onClick={logoutHandler}>
                        Đăng xuất
                    </Button>
                </div>
            </div>
        </Fragment>
    );
};

export default Home;
