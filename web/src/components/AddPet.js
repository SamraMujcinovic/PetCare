import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';
import axios from "axios";
import { getToken, getUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';


import '../assets/scss/profile.scss'

class AddPet extends React.Component {
    constructor(props) {
      super(props);

      this.state = {
        name: '',
        description: '',
        location: '',
        age: '',
        categories: [],
        options: [],
        categoryObject: {},
        category: '',
        optionRases: [],
        rases: [],
        raseObject: {},
        rase: '',
        message: '',
        image: 'image',
        adopted: false,
        selectedFile: null,
        isFilePicked: false,
        errors: {}
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleCategoryChange = this.handleCategoryChange.bind(this);
      this.handleRaseChange = this.handleRaseChange.bind(this);
      this.handleValidation = this.handleValidation.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
      axios.get(
          "http://localhost:8088/pet_category_service_api/categories"
      )
      .then((response) => {
        let categoryName = [];
          for(let i = 0; i < response.data.length; i++) {
            categoryName.push(response.data[i].name)
          }
        this.setState({
          ...this.state,
          categories: response.data,
          options: categoryName,
        });
      })
    }
  
    handleChange(event) {
      const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleCategoryChange(event) {

      let choosenCategory = {}

      for(let i = 0; i < this.state.categories.length; i++) {
          if (this.state.categories[i].name === event.value)
            choosenCategory = this.state.categories[i];
      }

        this.setState({
            ...this.state,
            category: event.value,
            categoryObject: choosenCategory,
            raseObject: {},
            rase: '',
        });

        console.log(this.state)

        axios.get(
            `http://localhost:8088/pet_category_service_api/rases/inCategory?id=${choosenCategory.id}`
        )
        .then((response) => {
          let raseName = [];
            for(let i = 0; i < response.data.length; i++) {
              raseName.push(response.data[i].name)
            }
          this.setState({
            ...this.state,
            rases: response.data,
            optionRases: raseName,
          });
        })

    }

    handleRaseChange(event) {
      this.setState({
          ...this.state,
          rases: event.value
      });
  }

    handleValidation(){
      let errors = {};
      let formIsValid = true;
      
      //name
      if(!this.state.name){
        formIsValid = false;
        errors["name"] = "This field cannot be empty!";
     }

      else if(this.state.name.length < 2 ){
        formIsValid = false;
        errors["name"] = "Names min length is 2!";
      }

     else if(this.state.name.length > 50 ){
        formIsValid = false;
        errors["name"] = "Names max length is 50!";
      }

      //location
      if(!this.state.location){
        formIsValid = false;
        errors["location"] = "This field cannot be empty!";
      }

     //age
     if(!this.state.age){
        formIsValid = false;
        errors["age"] = "This field cannot be empty!";
      }

      else if(this.state.age > 100 ){
        formIsValid = false;
        errors["age"] = "Pet can't be older than 100 years!";
      }

      this.setState({errors: errors});
      return formIsValid;
    }
  
    handleSubmit(event) {
      event.preventDefault();
      if (this.handleValidation()) {
        axios.post('http://localhost:8088/pet_category_service_api/pet',
          {
            name: this.state.name,
            location: this.state.location,
            description: this.state.description,
            image: this.state.image,
            rase_id: this.state.rase?.id,
            age: this.state.age
          }, 
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },   
        }).then(res => {
            console.log(res.data.message);
            return NotificationManager.success(res.data.message, '  ', 3000);
        }).catch((error) => {
          return NotificationManager.error('Pet is not added', '  ', 3000);
        });
      }
      else return;
    }
  
    render() {
      return (
        <div>
            <form className={"form"} onSubmit={this.handleSubmit}>
                <div className={"form-elements"}>
                    <input type="text" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Name"/>
                    <span className={"error"}>{this.state.errors["name"]}</span>
                    <br/>
                    <input type="text" name="description" value={this.state.description} onChange={this.handleChange} placeholder="Description"/>
                    <br/>
                    <input type="text" name="location" value={this.state.location} onChange={this.handleChange} placeholder="Location"/>
                    <span className={"error"}>{this.state.errors["location"]}</span>
                    <br/>
                    <input type="number" min="0" max="100" name="age" value={this.state.age} onChange={this.handleChange} placeholder="Age"/>
                    <span className={"error"}>{this.state.errors["age"]}</span>
                    <br/>
                    <input type="file" name="file"  accept="image/*" id="upload-button" style={{display: 'none'}} onChange={this.handleChange} />
                    <span className={"error"}>{this.state.errors["image"]}</span>
                    <label htmlFor="upload-button" style={{zIndex: 1000}}>
                        <div aria-label="upload picture" className="btn px-6 py-2 bg-red-500 text-white text-center margin-auto mt-8 imageInput">
                          Import image
                        </div>
                    </label>
                    <Dropdown className={"dropdown"} options={this.state.options} name="options" onChange={this.handleCategoryChange} value={this.state.category} placeholder="Select pet category" />
                    <br/>
                    <Dropdown className={"dropdown"} options={this.state.optionRases} name="rase" onChange={this.handleRaseChange} value={this.state.rase} placeholder="Select pet rase" />
                    <br/>
                    <textarea type="text" name="message" value={this.state.message} onChange={this.handleChange} placeholder="Message"/>
                    <span className={"error"}>{this.state.errors["message"]}</span>
                    <br/>
                    <input type="submit" value="Add" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
        </div>
      );
    }
  }
  
  export default AddPet 