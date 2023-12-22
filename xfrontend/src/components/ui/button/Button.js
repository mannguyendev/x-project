import React from "react";

import classes from "./Button.module.css";

const Button = (props) => {
    let classNames = `${classes.button} ${props.className} `;

    if (props.secondary) {
        classNames += `${classes.secondary}`;
    }

    return (
        <button type={props.type || "button"} className={classNames} onClick={props.onClick} disabled={props.disabled}>
            {props.children}
        </button>
    );
};

export default Button;
