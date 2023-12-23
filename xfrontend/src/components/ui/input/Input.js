import classes from "./Input.module.css";

const Input = ({ id, type, error, lable, placeholder, onChange, editable, value }) => {
    if (editable === undefined) {
        editable = true;
    }

    return (
        <div className={`${classes.container} ${!editable ? classes.disable : ""}`}>
            <input
                onChange={onChange}
                type={type ? type : "text"}
                id={id}
                lable={lable}
                placeholder={placeholder}
                style={error ? { color: "var(--red800)" } : {}}
                value={value}
                readOnly={!editable}
            />
        </div>
    );
};

export default Input;
