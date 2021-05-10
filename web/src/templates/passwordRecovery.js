import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';

import '../assets/scss/login.scss'

class PasswordRecovery extends React.Component {
    constructor(props) {
        super(props);

        
        this.options = [
            'Your favourite color?', 'Your favourite movie?'
        ];
        this.defaultOption = this.options[0];
        
        this.state = {
          newPassword: '',
          confirmPassword: '',
          question: this.options[0],
          answer: '',
          userAnswer: 'red',
          userOption: this.options[0],
          sameAnswer: false
        };
    
        this.userInfomartion = this.userInfomartion.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.checkAnswer = this.checkAnswer.bind(this);
        this.handleDropdownChange = this.handleDropdownChange.bind(this);
      }
    
    userInfomartion(event) {
        event.preventDefault();
        const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleDropdownChange(event) {
        event.preventDefault();
        this.setState({
            ...this.state,
            question: event.value
        });
    }

    checkAnswer(event) {
        event.preventDefault();
        if(this.state.answer === this.state.userAnswer) {
            this.setState({
                ...this.state,
                sameAnswer: true
            });
        } 
        else {
            this.setState({
                ...this.state,
                sameAnswer: false
            });
        }
    }
    
    changePassword(event) {
      alert('A name was submitted: ' + this.state.newPassword + this.state.confirmPassword );
      event.preventDefault();
    }
    
    render() {
      return (
        <div>
            <h2 className="text-center margin-auto">Password recovery</h2>
            <div className={"form-elements"}>
                {!this.state.sameAnswer && 
                    <form className={"form"} onSubmit={this.checkAnswer}>
                        <Dropdown className={"dropdown"} options={this.options} name="question" onChange={this.handleDropdownChange} value={this.state.question} placeholder="Select an option" />
                        <br/>
                        <textarea type="text" name="answer" value={this.state.answer} onChange={this.userInfomartion} placeholder="Answer"/>
                        <br/>
                        <input type="submit" value="Check answer" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                    </form>
                }
                
                {this.state.sameAnswer && 
                    <div>
                        <form className={"form"} onSubmit={this.changePassword}>
                            <input type="password" name="newPassword" value={this.state.newPassword} onChange={this.userInfomartion} placeholder="New password" />
                            <br/>
                            <input type="password" name="confirmPassword" value={this.state.confirmPassword} onChange={this.userInfomartion} placeholder="Confirm password" />
                            <br/>
                            <input type="submit" value="Submit" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 mb-5"/>
                        </form>
                    </div>
                }
            </div> 
        </div>
      );
    }
  }
  
  export default PasswordRecovery 