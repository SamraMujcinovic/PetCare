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
          email: '',
          question: this.options[0],
          answer: '',
          userAnswer: 'red',
          userOption: this.options[0],
          sameAnswer: false,
          errors: {}
        };
    
        this.userInfomartion = this.userInfomartion.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.checkAnswer = this.checkAnswer.bind(this);
        this.handleValidationAnswer = this.handleValidationAnswer.bind(this);
        this.handleValidationPassword = this.handleValidationPassword.bind(this);
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

    handleValidationAnswer(){
        let errors = {};
        let formIsValid = true;

         //email
        if(!this.state.email){
            formIsValid = false;
            errors["email"] = "This field cannot be empty!";
        }

        else if(this.state.email.length > 100 ){
            formIsValid = false;
            errors["email"] = "Emails max length is 100!";
        }

        else {               
            let lastAtPos = this.state.email.lastIndexOf('@');
            let lastDotPos = this.state.email.lastIndexOf('.');

            if (!(lastAtPos < lastDotPos && lastAtPos > 0 && this.state.email.indexOf('@@') === -1 && lastDotPos > 2 && (this.state.email.length - lastDotPos) > 2)) {
            formIsValid = false;
            errors["email"] = "Email should be valid";
            }
        }

        //answer
        if(!this.state.answer){
            formIsValid = false;
            errors["answer"] = "This field cannot be empty!";
        }
    
        this.setState({errors: errors});
        return formIsValid;
      }

      handleValidationPassword(){
        let errors = {};
        let formIsValid = true;

        //password
        if(!this.state.newPassword){
          formIsValid = false;
          errors["newPassword"] = "This field cannot be empty!";
       }
  
        else if(this.state.newPassword.length < 6 ){
          formIsValid = false;
          errors["newPassword"] = "New passwords min length is 6!";
        }
    
        this.setState({errors: errors});
        return formIsValid;
      }

    checkAnswer(event) {
        event.preventDefault();
        if(this.handleValidationAnswer()) {
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
        else return;
    }
    
    changePassword(event) {
        event.preventDefault();
        if (this.handleValidationPassword()) {
        }
        else return;
    }
    
    render() {
      return (
        <div>
            <h2 className="text-center margin-auto">Password recovery</h2>
            <div className={"form-elements"}>
                {!this.state.sameAnswer && 
                    <form className={"form"} onSubmit={this.checkAnswer}>
                        <input type="email" name="email" value={this.state.email} onChange={this.userInfomartion} placeholder="Email"/>
                        <span className={"error"}>{this.state.errors["email"]}</span>
                        <br/>
                        <Dropdown className={"dropdown"} options={this.options} name="question" onChange={this.handleDropdownChange} value={this.state.question} placeholder="Select an option" />
                        <br/>
                        <textarea type="text" name="answer" value={this.state.answer} onChange={this.userInfomartion} placeholder="Answer"/>
                        <span className={"error"}>{this.state.errors["answer"]}</span>
                        <br/>
                        <input type="submit" value="Check answer" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                    </form>
                }
                
                {this.state.sameAnswer && 
                    <div>
                        <form className={"form"} onSubmit={this.changePassword}>
                            <input type="password" name="newPassword" value={this.state.newPassword} onChange={this.userInfomartion} placeholder="New password" />
                            <span className={"error"}>{this.state.errors["newPassword"]}</span>
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