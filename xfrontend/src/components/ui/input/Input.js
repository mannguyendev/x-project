import { Fragment } from "react";
// import classes from "./Input.module.css";

const Input = ({ id, type, error, lable, placeholder, onChange }) => {
    return (
        <Fragment>
            <input
                onChange={onChange}
                type={type ? type : "text"}
                id={id}
                lable={lable}
                placeholder={placeholder}
                style={error ? { color: "var(--red800)" } : {}}
            />
        </Fragment>
    );
};

export default Input;
