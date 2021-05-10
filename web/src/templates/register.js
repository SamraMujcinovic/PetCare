import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';

import '../assets/scss/login.scss'

class Register extends React.Component {
    constructor(props) {
      super(props);
      
      this.options = [
        'Your favourite color?', 'Your favourite movie?'
      ];
      this.defaultOption = this.options[0];
      this.state = {
        name: '',
        surname: '',
        username: '',
        email: '',
        password: '',
        question: this.options[0],
        answer: '',
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleDropdownChange = this.handleDropdownChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
        const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleDropdownChange(event) {
        this.setState({
            ...this.state,
            question: event.value
        });
    }
  
    handleSubmit(event) {
      alert('A name was submitted: ' + this.state.name + this.state.username + this.state.surname + this.state.password + this.state.email + this.state.answer + this.state.question);
      event.preventDefault();
    }
  
    render() {
      return (
        <div>
            <h2 className="text-center margin-auto">Register</h2>
            <form className={"form"} onSubmit={this.handleSubmit}>
                <div className={"form-elements"}>
                    <input type="text" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Name"/>
                    <br/>
                    <input type="text" name="surname" value={this.state.surname} onChange={this.handleChange} placeholder="Surname"/>
                    <br/>
                    <input type="text" name="username" value={this.state.username} onChange={this.handleChange} placeholder="Username"/>
                    <br/>
                    <input type="email" name="email" value={this.state.email} onChange={this.handleChange} placeholder="Email"/>
                    <br/>
                    <input type="password" name="password" value={this.state.password} onChange={this.handleChange} placeholder="Password" />
                    <br/>
                    <Dropdown className={"dropdown"} options={this.options} name="question" onChange={this.handleDropdownChange} value={this.state.question} placeholder="Select an option" />
                    <br/>
                    <textarea type="text" name="answer" value={this.state.answer} onChange={this.handleChange} placeholder="Answer"/>
                    <br/>
                    <input type="submit" value="Register" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
            <div className="text-center margin-auto pt-4 pb-10">
                <h6>You already have an account?  
                <Link to={"/login"}>
                    <button className="text-red-500 btn-link">    
                    Login
                    </button>
                </Link>
                </h6>
            </div>
        </div>
      );
    }
  }
  
  export default Register 