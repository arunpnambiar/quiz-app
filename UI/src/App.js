import React from "react";
import "./App.css";
import Quiz from "./components/dashboard/quiz";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Login from "./components/login/login";

function App() {
  return (
        <Router>
          <Switch>
            <Route exact path="/" component={Login}></Route>
            <Route exact path="/login" component={Login}></Route>
            <Route exact path="/dashbord" component={Quiz}></Route>
          </Switch>
        </Router>
  );
}

export default App;
