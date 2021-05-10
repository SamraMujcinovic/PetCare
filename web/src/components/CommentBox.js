import React from 'react'
import CommentForm from './CommentForm'
import Comment from './Comment'

import '../assets/scss/comment.scss'

class CommentBox extends React.Component {
    constructor() {
      super();
      
      this.state = {
        showComments: false,
        commentToggle: false,
        questionToggle: false,
        text: 'eeeeee',
        comments: [
          {id: 1, author: "landiggity", body: "This is my first comment on this forum"},
          {id: 2, author: "scarlett-jo", body: "That's a mighty fine comment you've got there my good looking fellow..."},
          {id: 3, author: "rosco", body: "What is the meaning of all of this 'React' mumbo-jumbo?"}
        ]
      };

      this.toggleComment = this.toggleComment.bind(this);
      this.toggleQuestion = this.toggleQuestion.bind(this);
    }

    toggleQuestion(event) {
      console.log("kliknutoooooo", this.state.questionToggle)

      this.state.questionToggle = !this.state.questionToggle;
    }

    toggleComment(id, title) {
      if (this.issueId === id) {
        this.commentToggle = false;
        this.issueId = -1;
      } else {
        this.commentToggle = true;
        this.issueId = id;
        this.questionTitle = title;
      }
    }
    
    render () {
      return(
        <div className="comment-box">
          <div className="comment-container"
          >

            <div class="w-100 d-flex">
              <div class="search-bar md-form col-8">
                <button
                  class="btn btn-outline-primary ml-3 mt-5"
                  onClick={this.toggleQuestion}
                >
                  Ask question
                </button>

                <h1>{this.state.text}</h1>

                { this.state.questionToggle === true && 
                
                <div class="question-area" >

                  <h2>What's wrong?</h2>
                  <input
                    class="question-title"
                    id="question-title"
                    type="text"
                  />
                  <br />
                  <textarea
                    class="text question-text"
                    id="question-text"
                  ></textarea>
                  <br />
                  <button class="btn ml-2" >Add question</button>
                  </div>
                }

              </div>
            </div>
                
            <div class="row ml-2" >
              <div
                class="col-10 issue ml-5 mt-2 mt-2"
               
              >
                <div class="row pt-4 pb-4 pl-0">
                  <div class="col-1 d-flex justify-content-end">
                    <img  class="img" />
                  </div>
                  <div class="col-11">
                    <h4 class="m-0 font-weight-bold"></h4>
                    <small class="font-weight-bold">Issue by: </small>
                    <pre  class="issue-content">
                    </pre>
                    <pre class="issue-content">
                      
                    </pre>
                    <pre  class="issue-content">
                    </pre>
                    <ul class="list-of-comments">
                      <li >
                        <pre class="comment-content">
                        </pre>
                        <pre class="comment-content">
                        </pre>
                        <pre class="comment-content">
                        </pre>
                      </li>
                    </ul>
                    <button
                      class="btn btn-outline-danger mb-3"
                    >
                      COMMENT
                    </button>
                    <div class="comment-area" >
                      <strong> Answering for: </strong>
                      <div class="comment-contetn-area"></div>
                      <textarea
                        class="text comment-text"
                        id="comment-text"
                      
                      ></textarea>
                      <br />
                      <button class="btn">Add comment</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>  
      );
    } // end render
    
    _addComment(author, body) {
      const comment = {
        id: this.state.comments.length + 1,
        author,
        body
      };
      this.setState({ comments: this.state.comments.concat([comment]) }); // *new array references help React stay fast, so concat works better than push here.
    }
    
    _handleClick() {
      this.setState({
        showComments: !this.state.showComments
      });
    }
    
    _getComments() {    
      return this.state.comments.map((comment) => { 
        return (
          <Comment 
            author={comment.author} 
            body={comment.body} 
            key={comment.id} />
        ); 
      });
    }
    
    _getCommentsTitle(commentCount) {
      if (commentCount === 0) {
        return 'No comments yet';
      } else if (commentCount === 1) {
        return "1 comment";
      } else {
        return `${commentCount} comments`;
      }
    }
  } // end CommentBox component

  export default CommentBox