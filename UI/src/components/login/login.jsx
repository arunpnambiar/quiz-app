import React, { Component } from "react";
import axios from 'axios';
import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css";

export default class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: "",
      password: "",
      type: "",
      qiestions: "",
    };
  }
  login = () => {
    if (
      null == this.state.password ||
      this.state.password.trim().length === 0 ||
      null == this.state.username ||
      this.state.username.trim().length === 0 ||
      null == this.state.type ||
      this.state.type.trim().length === 0
    ) {
      alert("All fields are mandatory. Please check !!! :)");
      return;
    }
    const data = {
       "username": this.state.username,
       "password":this.state.password,
       "type":this.state.type
    }
    axios.post(`http://localhost:7272/api/public/v1/login`,  data )
    .then(res => {
        const { history } = this.props;
        history.push('/dashbord',{
            question: res.data.questionsList
        } )
    }).catch(err=>{
        alert(err.message)
    })
  };
  render() {
    return (
      <div className="auth-wrapper">
        <div className="auth-inner">
          <h3>Sign In</h3>

          <div className="form-group">
            <label>Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter username"
              onChange={(e) => this.setState({ username: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              onChange={(e) => this.setState({ password: e.target.value })}
            />
          </div>
          <div className="form-group">
            <label>Type</label>
            <select
              className="form-control"
              onChange={(e) => this.setState({ type: e.target.value })}
            >
              <option value="">--select--</option>
              <option value="GK">GK</option>
              <option value="English">English</option>
              <option value="Maths">Maths</option>
            </select>
          </div>

          <button
            type="submit"
            className="btn btn-primary btn-block"
            onClick={this.login}
          >
            Submit
          </button>
        </div>
      </div>
    );
  }
}
