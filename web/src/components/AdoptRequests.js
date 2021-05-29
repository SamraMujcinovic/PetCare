import React from 'react';
import axios from "axios";
import { getToken, getUser, logoutUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import { Link } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import 'react-notifications/lib/notifications.css';
import '../assets/scss/login.scss'

class AdoptRequests extends React.Component {
    constructor(props) {
        super(props);
  
        this.state = {
          adoptRequests: [],
          userId: (JSON.parse(getUser()))?.userId,
          userRole: (JSON.parse(getUser()))?.role,
        };

        this.deleteRequest = this.deleteRequest.bind(this);
      }
  
      componentDidMount() {
        axios.get(
          `http://localhost:8088/adopt_service_api/adoption-request/user/${this.state.userId}`,
          {
            headers: {
               Authorization: "Bearer " + getToken(),
            },
          }
        )
      .then((response) => {
        this.setState({
          ...this.state,
          adoptRequests: response.data
      });
      })
    }

    deleteRequest(request) {
      axios.delete(`http://localhost:8088/adopt_service_api/adoption-request/${request.id}`, 
        {
          headers: {
            Authorization: "Bearer " + getToken(),
          },  
        }).then(res => {
          axios.get(
            `http://localhost:8088/adopt_service_api/adoption-request/user/${this.state.userId}`,
            {
              headers: {
                 Authorization: "Bearer " + getToken(),
              },
            }
          )
          .then((response) => {
            this.setState({
              ...this.state,
              adoptRequests: response.data
          });
          return NotificationManager.success('Request is deleted!', '  ', 3000);
        })
        }).catch((error) => {
          return NotificationManager.error('Request is not deleted!', '  ', 3000);
        });
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
                        {this.state.userRole === 'admin' && <TableCell align="left">Delete request</TableCell>}
                        <TableCell align="left">View pet</TableCell>
                     </TableRow>
                    </TableHead>
                    <TableBody>
                    {this.state.adoptRequests.map((row) => (
                        <TableRow key={row.name}>
                        <TableCell component="th" scope="row">
                            {row.message}
                        </TableCell>
                        <TableCell align="left">{row.approved ? "Approved" : "Not approved"}</TableCell>
                        {this.state.userRole === 'admin' && <TableCell><button onClick={(e) => {this.deleteRequest(row)}} className="btn bg-red-500 text-white w-max text-xs">Delete</button></TableCell> }
                        <TableCell>
                        <Link to={`/pet/${row.petID}`} className="btn bg-red-500 text-white w-max text-xs">
                          Details
                        </Link>
                      </TableCell>
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
  
  export default AdoptRequests 