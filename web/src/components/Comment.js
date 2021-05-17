import React from 'react'
import { Link } from 'react-router-dom'
import Reply from './Reply'

import '../assets/scss/comment.scss'

import avatar from "../assets/icon/user.png";

class Comment extends React.Component {
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
        {id: 13, user: "scarlett-jo", title:"Title 2", content: "That's a mighty fine comment you've got there my good looking fellow..."},
        {id: 222, user: "rosco", title:"Tile 3", content: "What is the meaning of all of this 'React' mumbo-jumbo?"}
      ],

      reply: [
        {id: 1, user: "landiggity", commentID: 222, content: "Text1"},
        {id: 2, user: "scarlett-jo", commentID: 1, content: "text2"},
        {id: 3, user: "rosco", commentID: 222, content: "text 3"}
      ],

      errors: {}
    };

    this.toggleComment = this.toggleComment.bind(this);
    this.handleCommentChange = this.handleCommentChange.bind(this);
    this.handleValidationComment = this.handleValidationComment.bind(this);
    this.submitComment = this.submitComment.bind(this);
    this.toggleReply = this.toggleReply.bind(this);
  }

  toggleComment(event) {
    this.setState({
        ...this.state,
        addComment: !this.state.addComment
    });
  }
  
  handleCommentChange(event) {
    const value = event.target.value;
    this.setState({
        ...this.state,
        [event.target.name]: value
    });
  }

  handleValidationComment(){
    let errors = {};
    let formIsValid = true;

    //commentTitle
    if(!this.state.commentTitle){
      formIsValid = false;
      errors["commentTitle"] = "Title can't be blank!";
   }

    else if(this.state.commentTitle.length < 2 || this.state.commentTitle.length > 100 ){
      formIsValid = false;
      errors["commentTitle"] = "Title must be between 2 and 100 characters!";
    }

    //commentContent
    if(!this.state.commentTitle){
      formIsValid = false;
      errors["commentContent"] = "Content can't be blank!";
   }

    else if(this.state.commentTitle.length < 2 || this.state.commentTitle.length > 1000 ){
      formIsValid = false;
      errors["commentContent"] = "Content must be between 2 and 1000 characters!";
    }

    this.setState({errors: errors});
    return formIsValid;
  }

  submitComment(event) {
    event.preventDefault();
    if (this.handleValidationComment()) {
      }
    else return;
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

    render () {
      return(
        <div className="comment-div">
          <div className="comments">
            {this.state.comments && this.state.comments.map((comment, index) => (
              <div className="comment">
                <div className="comment-info">
                    <img class="img-fouild rounded comment-avatar"
                      src={avatar}
                      alt="avatar"/>
                  <div className="info">
                    <h5> {comment.user} </h5>
                    <h4> {comment.title} </h4>
                  </div>
                </div>
                
                <p className="pt-5 pb-6">  {comment.content} </p>

                {this.state.reply && this.state.reply.map((reply, i) => (
                  <div>
                    {
                      comment.id === reply.commentID && 
                      <div className="reply">
                          <div className="reply-info">
                            <img class="img-fouild rounded comment-avatar"
                              src={avatar}
                              alt="avatar"/>
                          <div className="info">
                            <h5> {reply.user} </h5>
                          </div>
                        </div>
                        <p className="pt-5 pb-6"> {reply.content} </p>
                      </div>
                    }
                  </div>
                ))}

                <div className="div-btn-reply">
                  <button onClick={() => this.toggleReply(comment.id)} className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons">Add reply </button>
                </div>
               <div className="add-reply">
                  { this.state.addReply && this.state.replyCommentId === comment.id &&
                    <div>
                      <Reply commentId={comment.id}></Reply>
                    </div>
                  }
                </div>
              </div>
            ))}

            <div className="div-btn-comment mb-10">
              <button onClick={this.toggleComment} className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons">Add comment </button>
            </div>
            <div className="add-comment mb-10">
              { this.state.addComment &&
                <div className="comment-area">
                  <form className={"form"} onSubmit={this.submitComment}>
                    <input type="text" name="commentTitle" value={this.state.commentTitle} onChange={this.handleCommentChange} placeholder="Title"/>
                    <span className={"error"}>{this.state.errors["commentTitle"]}</span>
                    <br/>
                    <textarea type="text" name="commentContent"  value={this.state.commentContent} onChange={this.handleCommentChange} placeholder="Content"/>
                    <span className={"error"}>{this.state.errors["commentContent"]}</span>
                    <br/>
                    <input type="submit" value="Submit" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons"/>
                  </form>
                </div>
              }
            </div>
          </div>
        </div>
      );
    }
}

export default Comment
