import React from 'react'
import { Link } from 'react-router-dom'

import '../assets/scss/comment.scss'

import avatar from "../assets/icon/user.png";

class Reply extends React.Component {
  constructor() {
    super();
    
    this.state = {
      addComment: false,
      addReply: false,
      commentTitle: '',
      commentContent: '',
      replyContent: '',
      replyCommentId: -1,
      comments: [
        {id: 1, user: "landiggity", title: "Title 1", content: "This is my first comment on this forum"},
        {id: 2, user: "scarlett-jo", title:"Title 2", content: "That's a mighty fine comment you've got there my good looking fellow..."},
        {id: 3, user: "rosco", title:"Tile 3", content: "What is the meaning of all of this 'React' mumbo-jumbo?"}
      ],

      reply: [
        {id: 1, user: "landiggity", commentID: 2, content: "Text1"},
        {id: 2, user: "scarlett-jo", commentID: 1, content: "text2"},
        {id: 3, user: "rosco", commentID: 2, content: "text 3"}
      ],

      errors: {}
    };

    this.toggleReply = this.toggleReply.bind(this);
    this.handleValidationReply = this.handleValidationReply.bind(this);
    this.handleReplyChange = this.handleReplyChange.bind(this);
    this.submitReply = this.submitReply.bind(this);

  }

 

  toggleReply(clickedId) {
    if(this.state.replyCommentId === -1) {
      this.setState({
        ...this.state,
        addReply: !this.state.addReply,
        replyCommentId: clickedId
      });
    }
    else if (this.state.replyCommentId === clickedId) {
      this.setState({
        ...this.state,
        addReply: !this.state.addReply,
        replyCommentId: -1
      });
    }
    else {
      this.setState({
        ...this.state,
        replyCommentId: clickedId
      });
    }
  }

  handleReplyChange(event) {
    const value = event.target.value;
    this.setState({
        ...this.state,
        [event.target.name]: value
    });
    event.preventDefault();
  } 

  handleValidationReply(){
    let errors = {};
    let formIsValid = true;

    //replyContent
    if(!this.state.replyContent){
      formIsValid = false;
      errors["replyContent"] = "Content can't be blank!";
   }

    else if(this.state.replyContent.length < 2 || this.state.replyContent.length > 1000 ){
      formIsValid = false;
      errors["replyContent"] = "Content must be between 2 and 1000 characters!";
    }

    this.setState({errors: errors});
    return formIsValid;
  }

  submitReply(event) {
    event.preventDefault();
    if (this.handleValidationReply()) {
      alert('Reply: ' + this.props.commentId + '  content:' + this.state.replyContent);
    }
    else return;
  }

    render () {
      return(
        <div className="reply-area">
            <form className={"form"} onSubmit={this.submitReply}>
            <textarea type="text" name="replyContent" value={this.state.replyContent} onChange={this.handleReplyChange} placeholder="Content"/>
            <span className={"error"}>{this.state.errors["replyContent"]}</span>
            <br/>
            <input type="submit" value="Submit" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons"/>
            </form>
        </div>        
      );
    }
}

export default Reply
