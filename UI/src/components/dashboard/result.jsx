import React from "react";
import axios from "axios";
import { Link } from "react-router-dom";
const ExamResult = (props) => {
    console.log(props)
  function next() {
    axios.get(`http://localhost:7272/api/private/v1/findallquestions/${props.uid}/${props.type}`)
    .then(res=>{
            props.pressRetry(res.data.questionsList)
    })
    .catch(error=>{
        console.log(error)
    })
  }
  return (
    <div className="result">
      <div>{props.message}</div>
      <div>
        <button onClick={next}>Retry</button>
        <Link to="/"><button>Log out</button></Link>
      </div>
    </div>
  );
};
export default ExamResult;