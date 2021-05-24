import React from 'react'
import Tabs from '../components/Tabs'
import UserInfo from '../components/UserInfo'
import ChangePassword from '../components/ChangePassword'
import AddPet from '../components/AddPet'
import UserList from '../components/UserList'
import Request from '../components/Request'
import axios from "axios";
import { getToken, getUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';

import '../assets/scss/profile.scss'

class Profile extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      token: getToken(),
      userRole: (JSON.parse(getUser()))?.role,
    };
  }

  componentDidMount() {
    if(!getToken()) {
      this.props.history.push("/login");
    }
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
          Add pet
          <div className="add-pet">
            <AddPet></AddPet>
          </div>
          Requests
          <div>
            <Request></Request>
          </div>
          User list
          <div>
            <UserList></UserList>
          </div>
        </Tabs>

        <NotificationContainer/>
      </div>
    );
  }
} 

export default Profile 