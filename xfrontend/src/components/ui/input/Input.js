import classes from "./Input.module.css";

const Input = ({ id, type, error, lable, placeholder, onChange }) => {
    return (
        <div className={classes.container}>
            <input
                onChange={onChange}
                type={type ? type : "text"}
                id={id}
                lable={lable}
                placeholder={placeholder}
                style={error ? { color: "var(--red800)" } : {}}
            />
        </div>
    );
};

export default Input;
