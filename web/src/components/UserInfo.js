import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';

import '../assets/scss/login.scss'

class UserInfo extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
          name: 'Amila',
          surname: 'Hrustic',
          username: 'ahrustic',
          email: 'amila@gmail.com',
          password: '1234',
        };
    
        this.userInfomartion = this.userInfomartion.bind(this);
        this.updateUser = this.updateUser.bind(this);
      }
    
    userInfomartion(event) {
        const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }
    
    updateUser(event) {
      alert('A name was submitted: ' + this.state.name + this.state.username + this.state.surname + this.state.password + this.state.email + this.state.answer + this.state.question);
      event.preventDefault();
    }
    
    render() {
      return (
        <div>
           <form className={"form"} onSubmit={this.updateUser}>
                <div className={"form-elements"}>
                    <input type="text" name="name" value={this.state.name} onChange={this.userInfomartion} placeholder="Name"/>
                    <br/>
                    <input type="text" name="surname" value={this.state.surname} onChange={this.userInfomartion} placeholder="Surname"/>
                    <br/>
                    <input type="text" name="username" value={this.state.username} onChange={this.userInfomartion} placeholder="Username"/>
                    <br/>
                    <input disabled type="email" name="email" value={this.state.email} onChange={this.userInfomartion} placeholder="Email"/>
                    <br/>
                    <input type="submit" value="Update" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
        </div>
      );
    }
  }
  
  export default UserInfo 