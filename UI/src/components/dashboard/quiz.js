import React, { Component } from "react";
import Options from "./optionview";
import axios from "axios";
import ExamResult from "./result";

export default class Quiz extends Component {
  constructor() {
    super();
    this.state = {
      questions: [
        {
          qid: "",
          question: "",
          options: "",
          qtype: "",
        },
      ],
      answers: [
        {
          id: "",
          answer: "",
        },
      ],
      index: 0,
      buttoLabel: "Next>>",
      userid: "",
      message: "",
      submitCompleted: false,
    };
  }
  componentDidMount() {
    console.log(this.props.location);
    this.setState({
      questions: this.props.location.state.question,
      userid: this.props.location.state.question[0].userid,
      submitCompleted:false
    });
  }
  next = () => {
    if (
      !this.state.answers.some(
        (value) => value.id === this.state.questions[this.state.index].qid
      )
    ) {
      alert("please select answer");
      return;
    }
    const index = this.state.index + 1;
    const arrayLength = this.state.questions.length - 1;
    if (index > arrayLength) {
      const data = this.state.answers;
      axios
        .post(
          `http://localhost:7272/api/private/v1/getresult/${this.state.userid}`,
          data
        )
        .then((res) => {
          if (res.data.percentage < 70) {
            this.setState({
              message:
                "Sorry you are not acheived the cut off mark, your percentage mark =" +
                res.data.percentage,
                submitCompleted:true
            });
          } else {
            this.setState({
              message:
                "Congrdulation you cleared the cut off mark !!!!, your percentage mark =" +
                res.data.percentage,
              submitCompleted:true
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      if (arrayLength === index) {
        this.setState({ buttoLabel: "submit" });
      }
      this.setState({
        index: index,
        questions: this.state.questions,
      });
    }
  };
  setAnswerList = (datafromoption) => {
    if (!this.state.answers.some((data) => data.id === datafromoption.id)) {
      this.state.answers.push({
        id: datafromoption.id,
        answer: datafromoption.data,
      });
    } else {
      this.state.answers.splice(
        this.state.answers.findIndex((data) => data.id === datafromoption.id),
        1,
        {
          id: datafromoption.id,
          answer: datafromoption.data,
        }
      );
    }
  };
  retry = (value)=>{
    console.log(value)
    this.setState({
      questions: value,
      submitCompleted:false,
      index:0,
      buttoLabel: "Next>>",
    });
  };
  render() {
    if (this.state.submitCompleted) {
      return (
        <ExamResult message={this.state.message} uid={this.state.userid} type="GK" pressRetry={this.retry}/>
      );
    }
    return (
      <div className="dashboard-body">
        <div className="options">
          {this.state.index +
            1 +
            ". " +
            this.state.questions[this.state.index].question}
        </div>
        <div className="option-label">
          <Options
            key={this.state.questions[this.state.index].qid}
            id={this.state.questions[this.state.index].qid}
            optionVal={this.state.questions[this.state.index].options}
            type={this.state.questions[this.state.index].qtype}
            setAnswer={this.setAnswerList}
          />
        </div>
        <div className="button">
          <button className="btn btn-primary" onClick={this.next}>
            {this.state.buttoLabel}
          </button>
        </div>
      </div>
    );
  }
}
