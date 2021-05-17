import React from 'react'
import Tabs from '../components/Tabs'
import UserInfo from '../components/UserInfo'
import ChangePassword from '../components/ChangePassword'
import AddPet from '../components/AddPet'
import axios from "axios";
import { getToken, getUser } from "../utilities/Common";

import '../assets/scss/profile.scss'

class Profile extends React.Component {

  constructor(props) {
    super(props);
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
                <UserInfo user={this.state}></UserInfo>
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