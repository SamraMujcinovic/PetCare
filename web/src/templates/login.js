import React from 'react'
import { Link } from 'react-router-dom'

import '../assets/scss/login.scss'

class Login extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        username: '',
        password: '',
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }
  
    handleSubmit(event) {
      //alert('A name was submitted: ' + this.state.username + this.state.password);
      this.props.history.push('/profile')
      event.preventDefault();
    }
  
    render() {
      return (
        <div>
            <h2 className="text-center margin-auto">Login</h2>
            <form className={"form"} onSubmit={this.handleSubmit}>
                <div className={"form-elements"}>
                <input type="text" name="username" value={this.state.username} onChange={this.handleChange} placeholder="Username"/>
                    <br/>
                    <input type="password" name="password" value={this.state.password} onChange={this.handleChange} placeholder="Password" />
                    <br/>
                    <input type="submit" value="Login" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
            <div className="text-center margin-auto pt-4">
                <h6>You do not have an account yet?  
                <Link to={"/register"}>
                    <button className="text-red-500 btn-link">    
                    Register
                    </button>
                </Link>
                </h6>
                <p className="pt-3">In case you forgot your password,
                  <Link to={"/password-recovery"}>
                      <button className="text-red-500 btn-link">    
                        click here!
                      </button>
                  </Link> 
                </p>
            </div>
        </div>
      );
    }
  }
  
  export default Login 