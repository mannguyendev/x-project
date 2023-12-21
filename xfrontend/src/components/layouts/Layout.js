import { Fragment } from "react";
import classes from "./Layout.module.css";
// import MainNavigation from "./header/MainNavigation";
// import Footer from "./footer/Footer";

const Layout = (props) => {
    return (
        <Fragment>
            {/* <MainNavigation /> */}
            {/* <SubNavigation /> */}

            <main className={classes.main}>{props.children}</main>
            {/* <Footer /> */}
        </Fragment>
    );
};

export default Layout;
