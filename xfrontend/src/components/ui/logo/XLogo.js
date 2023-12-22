import { Fragment } from "react";
import classes from "./XLogo.module.css";
import logo from "../../../assets/logo.png";

const XLogo = () => {
    return (
        <Fragment>
            <div className={classes.logoContainer}>
                <img className={classes.logo} src={logo} alt="x-project" />
                <h2>Project</h2>
            </div>
        </Fragment>
    );
};

export default XLogo;
