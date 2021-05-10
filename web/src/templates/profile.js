import React from 'react'
import Tabs from '../components/Tabs'
import UserInfo from '../components/UserInfo'
import ChangePassword from '../components/ChangePassword'
import AddPet from '../components/AddPet'

import '../assets/scss/profile.scss'

class Profile extends React.Component {

  constructor(props) {
    super(props);
    
    this.state = {
      name: 'Amila',
      surname: 'Hrustic',
      username: 'ahrustic',
      email: 'amila@gmail.com',
      password: '1234',
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
    };

    this.userInfomartion = this.userInfomartion.bind(this);
    this.updateUser = this.updateUser.bind(this);
    this.changePassword = this.changePassword.bind(this);
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

changePassword(event) {
  alert('A name was submitted: ' + this.state.oldPassword + this.state.newPassword + this.state.confirmPassword );
  event.preventDefault();
}

  render() {
    return (
      <div className="profile-elements">
        <Tabs>
          Profile
          <span>
            <Tabs>
              Information
              <div className="user-information">
                <UserInfo></UserInfo>
              </div>
              
              Change password
              <div className="password-change">
                <ChangePassword></ChangePassword>
              </div>
            </Tabs>
          </span>
          Requests
          <span>Requests</span>
          Add pet
          <div className="add-pet">
            <AddPet></AddPet>
          </div>
        </Tabs>
      </div>
    );
  }
} 

export default Profile 