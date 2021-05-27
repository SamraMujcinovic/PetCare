import React from 'react';
import axios from "axios";
import { getToken, getUser, logoutUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import 'react-notifications/lib/notifications.css';
import '../assets/scss/login.scss'

class AddRequests extends React.Component {
    constructor(props) {
      super(props);

      this.state = {
        addRequests: [],
        userId: (JSON.parse(getUser()))?.userId,
      };
    }

    componentDidMount() {
      axios.get(
        `http://localhost:8088/adopt_service_api/add-pet-request/user/${this.state.userId}`,
        {
          headers: {
             Authorization: "Bearer " + getToken(),
          },
        }
      )
    .then((response) => {
      this.setState({
        ...this.state,
        addRequests: response.data
    });
      //console.log(response)
    })
  }
      
  
    render() {
      return (
        <div>
            <TableContainer component={Paper}>
              <Table  aria-label="simple table">
                  <TableHead>
                  <TableRow>
                      <TableCell>Message</TableCell>
                      <TableCell align="left">Status</TableCell>
                     </TableRow>
                  </TableHead>
                  <TableBody>
                  {this.state.addRequests.map((row) => (
                      <TableRow key={row.name}>
                      <TableCell component="th" scope="row">
                          {row.message}
                      </TableCell>
                      <TableCell align="left">{row.approved ? "Approved" : "Not approved"}</TableCell>
                      </TableRow>
                  ))}
                  </TableBody>
              </Table>
              </TableContainer>

              <NotificationContainer/>  
        </div>
      );
    }
  }
  
  export default AddRequests 