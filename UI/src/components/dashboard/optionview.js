import React, { Component } from "react";
import "./dashboard.css";

export default class Options extends Component {
  constructor() {
    super();
    this.state = {
      option: [],
      question: "",
    };
  }
  componentDidMount() {
    this.setState({
      option: this.props.optionVal.split(","),
      question: this.props.question,
    });
  }
  onNext = (event) => {
      event.target.value === "" || event.target.value == null
        ? alert("please select answer")
        : this.props.setAnswer({
            data: event.target.value,
            id: this.props.id,
          });
    }

  render() {
    return (
      <div>
        {this.state.option.map((value) => (
          <div className="options">
            <label className="container">
              {value}
              <input
                type="radio"
                name="radio"
                value={value}
                onChange={this.onNext}
              />
              <span className="checkmark"></span>
            </label>
          </div>
        ))}
      </div>
    );
  }
}
