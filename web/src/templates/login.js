import React from 'react'
import { Link } from 'react-router-dom'
import axios from "axios";
import { saveUserToken, saveUserData } from "../utilities/Common";

import '../assets/scss/login.scss'

class Login extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        username: '',
        password: '',
        errors: {}
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleValidation = this.handleValidation.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleValidation(){
      let errors = {};
      let formIsValid = true;

      //username
      if(!this.state.username){
         formIsValid = false;
         errors["username"] = "This field cannot be empty!";
      }

      else if(this.state.username.length < 4 ){
        formIsValid = false;
        errors["username"] = "Usernames min length is 4!";
     }

      else if(this.state.username.length > 40 ){
        formIsValid = false;
        errors["username"] = "Usernames max length is 40!";
      }


      //password
      if(!this.state.password){
        formIsValid = false;
        errors["password"] = "This field cannot be empty!";
     }

     else if(this.state.password.length < 6 ){
      formIsValid = false;
      errors["password"] = "Passwords min length is 6!";
    }
 
     this.setState({errors: errors});
     return formIsValid;
   }
  
    handleSubmit(event) {
       event.preventDefault();
      if (this.handleValidation()) {
        const config = {
          headers: {
            "Content-Type": "application/json"
          },
        };
      
        // Send login request
        axios
          .post(
            "http://localhost:8088/user_service_api/api/auth/login",
            {
              usernameOrEmail: this.state.username,
              password: this.state.password,
            },
            config
          )
          .then((response) => {
            // Save user token to local storage
            saveUserToken(response.data.accessToken, response.data.tokenType);
            // setToken({token: response.data.accessToken})
            return response.data;
          })
          .then(() => {
            this.props.history.push("/profile");
          })
          .catch((error) => {
            //setWarning(true);
            if (error.response == null) {
              console.log("No internet");
              //setError("No internet");
              return;
            }
            if (error.response.status === 401) {
              //setError("Wrong username or password!");
              console.log("Wrong username or password/Auth error", error.response);
            } else console.log(error.response.data.message);
          });
      }
      else return;
    }
  
    render() {
      return (
        <div>
            <h2 className="text-center margin-auto">Login</h2>
            <form className={"form"} onSubmit={this.handleSubmit}>
                <div className={"form-elements"}>
                    <input type="text" name="username" value={this.state.username} onChange={this.handleChange} placeholder="Username"/>
                    <span className={"error"}>{this.state.errors["username"]}</span>
                    <br/>
                    <input type="password" name="password" value={this.state.password} onChange={this.handleChange} placeholder="Password" />
                    <span className={"error"}>{this.state.errors["password"]}</span>
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