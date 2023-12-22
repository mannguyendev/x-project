// import logo from "./logo.svg";
import "./App.css";
import Layout from "./components/layouts/Layout.js";
import Home from "./pages/home/Home.js";
import { Routes, Route } from "react-router-dom";
import Login from "./pages/user/Login.js";
import Signup from "./pages/user/Signup.js";
import { Toaster } from "react-hot-toast";

function App() {
    return (
        <div className="App">
            <Layout>
                <Routes>
                    <Route path="/" element={<Home />}></Route>
                    <Route path="/home" element={<Home />}></Route>
                    <Route path="/login" element={<Login />}></Route>
                    <Route path="/signup" element={<Signup />}></Route>
                </Routes>
            </Layout>
            <Toaster
                position="top right"
                toastOptions={{
                    style: {
                        fontSize: "1.4rem",
                    },
                }}
            ></Toaster>
        </div>
    );
}

export default App;
