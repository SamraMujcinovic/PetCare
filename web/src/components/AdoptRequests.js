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
import { Badge, Modal } from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';

import 'react-notifications/lib/notifications.css';
import '../assets/scss/login.scss'

class AdoptRequests extends React.Component {
    constructor(props) {
        super(props);
  
        this.state = {
          adoptRequests: [],
          userId: (JSON.parse(getUser()))?.userId,
          userRole: (JSON.parse(getUser()))?.role,
          open: false,
          choosenPet: {},
          openUser: false,
          chosenUser: {},
        };

        this.deleteRequest = this.deleteRequest.bind(this);
        this.handleOpen = this.handleOpen.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleCloseModalUser = this.handleCloseModalUser.bind(this);
        this.handleOpenUser = this.handleOpenUser.bind(this);
        
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

      handleOpen(petID) {
        axios.get(
            `http://localhost:8088/pet_category_service_api/all/pet/${petID}`,
            {
              headers: {
                  Authorization: "Bearer " + getToken(),
              },
            }
          )
        .then((response) => {
            this.setState({
                ...this.state,
                choosenPet: response.data
            });
        })  
      }
  
      handleOpenUser(userID) {
        axios.get(
          `http://localhost:8088/user_service_api/user/username/${userID}`,
          {
            headers: {
                Authorization: "Bearer " + getToken(),
            },
          }
        )
      .then((response) => {
          this.setState({
              ...this.state,
              chosenUser: response.data
          });
      })
      }
  
    
    
    handleClose() {
      this.setState({
          ...this.state,
          open: false
      });
    }

    handleCloseModalUser() {
      this.setState({
        ...this.state,
        openUser: false
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
                        {this.state.userRole === 'admin' && <TableCell align="left">View pet</TableCell>}
                        {this.state.userRole === 'admin' && <TableCell align="left">View user</TableCell>}
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
                        {this.state.userRole === 'admin' &&
                        <TableCell>
                          <button  onClick={(e) => {this.handleOpen(row.newPetID)}} className="btn bg-red-500 text-white w-max text-xs">
                             Pet detail
                          </button>
                        </TableCell>
                        }
                        {this.state.userRole === 'admin' && 
                            <TableCell>
                            <button  onClick={(e) => {this.handleOpenUser(row.userID)}} className="btn bg-red-500 text-white w-max text-xs">
                              User detail
                            </button>
                          </TableCell>
                          }
                      </TableRow>
                    ))}
                    </TableBody>
                </Table>
                </TableContainer>

                <Modal
                  open={this.state.open}
                  onClose={this.handleClose}
                  aria-labelledby="simple-modal-title"
                  aria-describedby="simple-modal-description"
              >
                  <div>  
                      <div className={'paper'}>
                          <button onClick={this.handleClose} style={{right: 20, position: 'absolute',}}>
                              <CloseIcon />
                          </button>
                          <div style={{paddingTop: 20,}}>
                              <h2 id="modal-title">Pet view</h2>
                              <p className="request-detail">Name: {this.state.choosenPet?.name}</p>
                              <p className="request-detail">Description: {this.state.choosenPet?.description}</p>
                              <p className="request-detail">Category: {this.state.choosenPet?.rase?.category?.name}</p>
                              <p className="request-detail">Rase: {this.state.choosenPet?.rase?.name}</p>
                              <p className="request-detail">Location: {this.state.choosenPet?.location}</p>
                              <p className="request-detail">Age: {this.state.choosenPet?.age}</p>
                          </div>
                      </div>
                  </div>
              </Modal>

              
            <Modal
                open={this.state.openUser}
                onClose={this.handleCloseModalUser}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
             >
                <div>  
                    <div className={'paper'}>
                        <button onClick={this.handleCloseModalUser} style={{right: 20, position: 'absolute',}}>
                            <CloseIcon />
                        </button>
                        {
                          this.state.chosenUser?.username === "UNKNOWN" ?
                          <div style={{paddingTop: 20,}}>
                            <h2 id="modal-title">Unknown user</h2>
                          </div>
                          :
                            <div style={{paddingTop: 20,}}>
                              <h2 id="modal-title">User view</h2>
                              <p className="request-detail">Name: {this.state.chosenUser?.name}</p>
                              <p className="request-detail">Surname: {this.state.chosenUser?.surname}</p>
                              <p className="request-detail">Username: {this.state.chosenUser?.username}</p>
                              <p className="request-detail">Email: {this.state.chosenUser?.email}</p>
                          </div>
                        }
                    </div>
                </div>
            </Modal>

  
                <NotificationContainer/>  
          </div>
        );
      }
  }
  
  export default AdoptRequests 