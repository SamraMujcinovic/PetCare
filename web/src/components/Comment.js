import React from 'react'
import { Link } from 'react-router-dom'

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
        {id: 2, user: "scarlett-jo", title:"Title 2", content: "That's a mighty fine comment you've got there my good looking fellow..."},
        {id: 3, user: "rosco", title:"Tile 3", content: "What is the meaning of all of this 'React' mumbo-jumbo?"}
      ],

      reply: [
        {id: 1, user: "landiggity", commentID: 2, content: "Text1"},
        {id: 2, user: "scarlett-jo", commentID: 1, content: "text2"},
        {id: 3, user: "rosco", commentID: 2, content: "text 3"}
    
      ]
    };

    this.toggleComment = this.toggleComment.bind(this);
    this.handleCommentChange = this.handleCommentChange.bind(this);
    this.submitComment = this.submitComment.bind(this);
    this.toggleReply = this.toggleReply.bind(this);
    this.handleReplyChange = this.handleReplyChange.bind(this);
    this.submitReply = this.submitReply.bind(this);

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

  submitComment(event) {
    alert('Comment: ' + this.state.commentTitle + '    ' + this.state.commentContent);
    event.preventDefault();
  }

  toggleReply(event, clickedId) {
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
    event.preventDefault();
  }

  handleReplyChange(event) {
    const value = event.target.value;
    this.setState({
        ...this.state,
        [event.target.name]: value
    });
    event.preventDefault();
}

  submitReply(event, clickedId) {
    alert('Reply: ' + clickedId);
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
                        <p className="pt-5 pb-6">  {reply.content} </p>
                      </div>
                    }
                  </div>
                ))}

                <div className="div-btn-reply">
                  <button onClick={() => this.toggleReply(comment.id)} className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons">Add reply </button>
                </div>
               <div className="add-reply">
                  { this.state.addReply && this.state.replyCommentId === comment.id &&
                    <div className="reply-area">
                      <form className={"form"} >
                        <textarea type="text" name="replyContent" value={this.state.replyContent} onChange={this.handleReplyChange} placeholder="Content"/>
                        <br/>
                        <input type="submit" onClick = {() => this.submitReply(comment.id)} value="Submit" className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8 buttons"/>
                      </form>
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
                    <br/>
                    <textarea type="text" name="commentContent"  value={this.state.commentContent} onChange={this.handleCommentChange} placeholder="Content"/>
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
