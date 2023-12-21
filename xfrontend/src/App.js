// import logo from "./logo.svg";
import "./App.css";
import Layout from "./components/layouts/Layout.js";
import Home from "./pages/home/Home.js";
import { Routes, Route } from "react-router-dom";

function App() {
    return (
        <div className="App">
            <Layout>
                <Routes>
                    <Route path="/" element={<Home />}>
                        {/* <Navigate to="/home" /> */}
                        {/* <Home /> */}
                    </Route>
                    <Route path="/home" element={<Home />}>
                        {/* <Home /> */}
                    </Route>
                    <Route path="/products" element={<Home />}>
                        {/* <Home /> */}
                    </Route>
                </Routes>
            </Layout>
        </div>
    );
}

export default App;
