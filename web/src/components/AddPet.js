import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-dropdown';

import '../assets/scss/profile.scss'

class AddPet extends React.Component {
    constructor(props) {
      super(props);

      this.category = [
        'Dog', 'Cat', 'Hamster', 'Fish', 'Other'
      ];

      this.state = {
        name: '',
        description: '',
        location: '',
        age: '',
        category: this.category[0],
        message: '',
        adopted: false,
        selectedFile: null,
        isFilePicked: false,
        errors: {}
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleCategoryChange = this.handleCategoryChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleCategoryChange(event) {
        this.setState({
            ...this.state,
            question: event.value
        });
    }
  
    handleSubmit(event) {
      alert('A name was submitted: ' + this.state.name + this.state.description);
      event.preventDefault();
    }
  
    render() {
      return (
        <div>
            <form className={"form"} onSubmit={this.handleSubmit}>
                <div className={"form-elements"}>
                    <input type="text" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Name"/>
                    <br/>
                    <input type="text" name="description" value={this.state.description} onChange={this.handleChange} placeholder="Description"/>
                    <br/>
                    <input type="text" name="location" value={this.state.location} onChange={this.handleChange} placeholder="Location"/>
                    <br/>
                    <input type="text" name="age" value={this.state.age} onChange={this.handleChange} placeholder="Age"/>
                    <br/>
                    <input type="file" name="file"  accept="image/*" id="upload-button" style={{display: 'none'}} onChange={this.handleChange} />
                    <label htmlFor="upload-button" style={{zIndex: 1000}}>
                        <div aria-label="upload picture" className="btn px-6 py-2 bg-red-500 text-white text-center margin-auto mt-8 imageInput">
                          Import image
                        </div>
                    </label>
                    <Dropdown className={"dropdown"} options={this.category} name="question" onChange={this.handleCategoryChange} value={this.state.category} placeholder="Select an option" />
                    <br/>
                    <textarea type="text" name="message" value={this.state.message} onChange={this.handleChange} placeholder="Message"/>
                    <br/>
                    <input type="submit" value="Add" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8"/>
                </div>
            </form>
        </div>
      );
    }
  }
  
  export default AddPet 