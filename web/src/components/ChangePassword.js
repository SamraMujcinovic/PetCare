import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';

import '../assets/scss/login.scss'

class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        };
    
        this.userInfomartion = this.userInfomartion.bind(this);
        this.changePassword = this.changePassword.bind(this);
      }
    
    userInfomartion(event) {
        const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }
    
    changePassword(event) {
      alert('A name was submitted: ' + this.state.oldPassword + this.state.newPassword + this.state.confirmPassword );
      event.preventDefault();
    }
    
    render() {
      return (
        <div>
           <form className={"form"} onSubmit={this.changePassword}>
                <div className={"form-elements"}>
                    <input type="password" name="password" value={this.state.oldPassword} onChange={this.userInfomartion} placeholder="Old password" />
                    <br/>
                    <input type="password" name="newPassword" value={this.state.newPassword} onChange={this.userInfomartion} placeholder="New password" />
                    <br/>
                    <input type="submit" value="Submit" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
        </div>
      );
    }
  }
  
  export default ChangePassword 