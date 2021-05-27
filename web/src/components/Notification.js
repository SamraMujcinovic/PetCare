import React, {useContext, useState, useEffect} from 'react'
import { Badge, Modal } from '@material-ui/core';
import NotificationsIcon from '@material-ui/icons/Notifications';
import CloseIcon from '@material-ui/icons/Close';
import { useLocation, NavLink, Link, useHistory} from 'react-router-dom'
import {StoreContext} from '../context/index'
import axios from "axios";
import { getToken, getUser, logoutUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';

import '../assets/scss/other.scss'


class Notification extends React.Component {
    constructor(props) {
      super(props);

      this.state = {
          notifications: [],
          unreadNotification: [],
          displayNotification: false,
          interval: null,
          open: false,
          choosenNotification: {},
          choosenRequest: {},
          newPet: {},
      };

      this.toggleNotification = this.toggleNotification.bind(this);
      this.getData = this.getData.bind(this);
      this.handleOpen = this.handleOpen.bind(this);
      this.handleClose = this.handleClose.bind(this);
      this.approveRequest = this.approveRequest.bind(this);
      this.notApproveRequest = this.notApproveRequest.bind(this);
    }

    componentDidMount() {
        this.state.interval = setInterval(this.getData, 5000);
        this.getData();
    }

    componentWillUnmount() {
        clearInterval(this.state.interval);
      }

    getData() {
        axios.get(
            `http://localhost:8088/notification_service_api/notifications/all/unread/${(JSON.parse(getUser()))?.userId}`,
            {
              headers: {
                 Authorization: "Bearer " + getToken(),
              },
            }
          )
        .then((response) => {
            this.setState({
                ...this.state,
                unreadNotification: response.data
            });
        })
        .then((response) => {
            axios.get(
                `http://localhost:8088/notification_service_api/notifications/all/${(JSON.parse(getUser()))?.userId}`,
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                this.setState({
                    ...this.state,
                    notifications: response.data
                });
            })
        })
    }

    toggleNotification() {
        this.setState({
            ...this.state,
            displayNotification: !this.state.displayNotification
        });

        if (this.state.displayNotification !== true) {
            axios.put(
                `http://localhost:8088/notification_service_api/notifications/setRead/${(JSON.parse(getUser()))?.userId}`,
                {"jwt":getToken()},
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                axios.get(
                    `http://localhost:8088/notification_service_api/notifications/all/unread/${(JSON.parse(getUser()))?.userId}`,
                    {
                      headers: {
                         Authorization: "Bearer " + getToken(),
                      },
                    }
                  )
                .then((response) => {
                    this.setState({
                        ...this.state,
                        unreadNotification: response.data
                    });
                })
            })
        } 
    }
    
    handleOpen(notification) {
        if(notification.isAddPetRequest === true) {
            axios.get(
                `http://localhost:8088/adopt_service_api/add-pet-request/${notification.requestId}`,
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                this.setState({
                    ...this.state,
                    choosenNotification: notification,
                    choosenRequest: response.data
                });
                axios.get(
                    `http://localhost:8088/pet_category_service_api/pet/${this.state.choosenRequest.newPetID}`
                  )
                    .then((response) => {
                        this.setState({
                            ...this.state,
                            newPet: response.data,
                            open: true
                        });
                    })
            })
        }
        else {
            axios.get(
                `http://localhost:8088/adopt_service_api/adoption-request/${notification.requestId}`,
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                this.setState({
                    ...this.state,
                    choosenNotification: notification,
                    choosenRequest: response.data
                });
                axios.get(
                    `http://localhost:8088/pet_category_service_api/pet/${this.state.choosenRequest.petID}`
                  )
                    .then((response) => {
                        this.setState({
                            ...this.state,
                            newPet: response.data,
                            open: true
                        });
                    })
            })
        }
    }

    approveRequest() {
        if(this.state.choosenNotification.isAddPetRequest === true) {
            axios.put(
                `http://localhost:8088/adopt_service_api/add-pet-request/approved/${this.state.choosenNotification.requestId}`,
                {"jwt":getToken()},
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                return NotificationManager.success(response.data.message, '  ', 3000);
            })
            .catch((error) => {
                return NotificationManager.error('Pet is not added', '  ', 3000);
            });
        }
        else {
            axios.put(
                `http://localhost:8088/adopt_service_api/adoption-request/approve/${this.state.choosenNotification.requestId}`,
                {"jwt":getToken()},
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                return NotificationManager.success(response.data.message, '  ', 3000);
            })
            .catch((error) => {
                return NotificationManager.error('Pet is not adopted', '  ', 3000);
            });
        }
    }

    notApproveRequest() {
        if(this.state.choosenNotification.isAddPetRequest === true) {
            axios.put(
                `http://localhost:8088/adopt_service_api/add-pet-request/not-approved/${this.state.choosenNotification.requestId}`,
                {"jwt":getToken()},
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                return NotificationManager.success(response.data.message, '  ', 3000);
            })
            .catch((error) => {
                return NotificationManager.error('Invalid request', '  ', 3000);
            });
        }
        else {
            axios.put(
                `http://localhost:8088/adopt_service_api/adopt-request/not-approved/${this.state.choosenNotification.requestId}`,
                {"jwt":getToken()},
                {
                  headers: {
                     Authorization: "Bearer " + getToken(),
                  },
                }
              )
            .then((response) => {
                return NotificationManager.success(response.data.message, '  ', 3000);
            })
            .catch((error) => {
                return NotificationManager.error('Invalid request', '  ', 3000);
            });
        }
    }

    handleClose() {
        this.setState({
            ...this.state,
            open: false
        });
    }
  
    render() {
      return (
        <div className={'notification-containter'} >
           <Badge color="secondary" badgeContent={this.state.unreadNotification.length} 
                onClick={this.toggleNotification}
            >
                <NotificationsIcon />
           </Badge>

            {
                this.state.displayNotification &&
                <div className={'notifications'}>
                    <h5 className={'notification-title'}>Notifications</h5>

                    {this.state.notifications.map((notification, index) => (
                        <div key={index} className={'notification-item'}>
                            <p> {notification.content} </p>
 
                            {
                                notification.requestId !== -1 &&
                                <p className={'see-request'} onClick={(e) => this.handleOpen(notification)}>  See request </p>
                            }
                        </div>
                    ))}

                </div>
            }
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
                            <h2 id="modal-title">Request view</h2>
                            <p className="request-detail">{this.state.choosenRequest?.message}</p>
                            <p className="request-detail">Name: {this.state.newPet?.name}</p>
                            <p className="request-detail">Description: {this.state.newPet?.description}</p>
                            <p className="request-detail">Category: {this.state.newPet?.rase?.category?.name}</p>
                            <p className="request-detail">Rase: {this.state.newPet?.rase?.name}</p>
                            <p className="request-detail">Location: {this.state.newPet?.location}</p>
                            <p className="request-detail">Age: {this.state.newPet?.age}</p>
                            
                            <div>
                                <button 
                                    className="btn bg-red-500 text-white ml-4 mt-5 delete-btn"
                                    onClick={this.notApproveRequest}
                                >
                                    Not approve 
                                </button>
                                <button 
                                    className="btn bg-red-500 text-white mt-5 delete-btn"
                                    onClick={this.approveRequest}
                                >
                                    Approve 
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>
         
            <NotificationContainer/>
        </div>
      );
    }
  }
  
  export default Notification 
