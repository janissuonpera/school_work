import React, { Component } from 'react';
import './App.css';
import Paper from '@material-ui/core/Paper';
import Popover from '@material-ui/core/Popover';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import TextField from '@material-ui/core/TextField';

class App extends Component {
  constructor() {
    super();
    let images = require.context('./../public/images', false, /\.(png|jpg)$/);
    let img_list = [];
    for(var i in images.keys()){
      img_list.push(images.keys()[i].slice(1))
    }
    this.state = {
      width: window.innerWidth,
      height: window.innerHeight*0.75,
      images: img_list,
      currentImg: null,
      imageDialogOpen: false,
      textDialogOpen: false,
      upper: false,
      anchorEl: null,
      x: 0,
      y: 0,
      upperText: "",
      lowerText: ""
    }
    this.myCanvas = null;
  }
  //EventListener in componentDidMount calls this when window is resized
  handleResize(){
    this.setState({
      width: window.innerWidth,
      height: window.innerHeight*0.75,
    });
  }

  componentDidMount(){
    //EventListener listens to when window is resized
    window.addEventListener('resize', () => {this.handleResize()});
    //EventListener listens to right click and handles context menu
    document.getElementById("App").addEventListener('contextmenu', (event)=>{
      event.preventDefault();
      this.handleContextMenu(event);
    });
    this.drawCanvas();
  }

  componentDidUpdate(){
    //Redraw if component is updated
    this.drawCanvas();
  }

  //Closes context menu
  handleClose = () => {
    this.setState({ anchorEl: null, x:0, y:0 });
  };

  //Listener function for right clicks. Opens context menu to mouse location
  handleContextMenu = (event)=>{
    this.setState({
      anchorEl: event.currentTarget,
      x: event.clientX,
      y: event.clientY
    });
  };

  //Opens image dialog
  selectImage = () =>{
    this.setState({
      anchorEl: null,
      imageDialogOpen: true
    });
  }
  //Handler for upper captions, opens dialog
  selectUpper = () =>{
    this.setState({
      upper: true,
      textDialogOpen: true
    });
  }
  //Handler for lower captions, opens dialog
  selectLower = () =>{
    this.setState({
      upper: false,
      textDialogOpen: true
    });
  }

  //Handles image dialog, when users clicks one of the images
  //that image is set as the state and then closes dialog
  handleListItemClick = (image) =>{
    this.setState({
      imageDialogOpen: false,
      image: "/images" + image
    });
  }

  //Submits text to upper or lower state and closes dialogs
  submitText = (event) =>{
    if(this.state.upper){
      this.setState({
        upperText: document.getElementById("captionText").value,
        textDialogOpen: false,
        anchorEl: null
      });
    }
    else{
      this.setState({
        lowerText: document.getElementById("captionText").value,
        textDialogOpen: false,
        anchorEl: null
      });
    }

  }

  render() {
    return (
      <div className="App" id="App">
        <Paper style={{width: this.state.width, height: this.state.height}}>
          <div id="canvasDiv">
            <canvas
              width={this.state.width}
              height={this.state.height}
              ref={(el)=>{this.myCanvas = el;}}
            />
          </div>
        </Paper>
        <Popover
          id="simplePopper"
          open={Boolean(this.state.anchorEl)}
          anchorEl={this.state.anchorEl}
          onClose={this.handleClose}
          anchorReference="anchorPosition"
          anchorPosition={{ top: this.state.y+10, left: this.state.x+10 }}
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'center',
          }}
        >
          <Button variant="contained" color="primary" onClick={this.selectImage}>
            Set image...
          </Button>
          <br/>
          <Button variant="contained" color="primary" onClick={this.selectUpper}>
            Set upper...
          </Button>
          <br/>
          <Button variant="contained" color="primary" onClick={this.selectLower}>
            Set lower...
          </Button>
        </Popover>
        <Dialog
          open={this.state.imageDialogOpen}
          onClose={()=>{this.setState({imageDialogOpen:false});}}
          aria-labelledby="simple-dialog-title"
          aria-describedby="simple-dialog-description"
        >
          <DialogTitle id="simple-dialog-title">{"Choose image"}</DialogTitle>
          <DialogContent>
            <List>
              {this.state.images.map(image => (
              <ListItem button onClick={() => this.handleListItemClick(image)} key={image}>
                <ListItemText primary={image} />
              </ListItem>
            ))}
            </List>
          </DialogContent>
        </Dialog>
        <Dialog
          open={this.state.textDialogOpen}
          onClose={()=>{this.setState({textDialogOpen:false});}}
          aria-labelledby="form-dialog-title"
        >
          <DialogTitle id="form-dialog-title">Write caption</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="captionText"
              label="Caption"
              type="text"
              fullWidth
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={()=>{this.setState({textDialogOpen:false});}} color="primary">
              Cancel
            </Button>
            <Button onClick={this.submitText} color="primary">
              Submit
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }

  drawCanvas(){
    let ctx = this.myCanvas.getContext('2d');
    ctx.clearRect(0, 0, this.myCanvas.width, this.myCanvas.height);
    let base_image = new Image();
    base_image.src = this.state.image;
    let tmp = this.state;
    //Draws the image and writes the upper and lower captions if there any
    base_image.onload = function(event, state=tmp){
      ctx.drawImage(base_image, 0, 0, window.innerWidth, window.innerHeight*0.75);
      ctx.font = '48px sans-serif';
      ctx.lineWidth = 2;
      ctx.fillStyle = "red";
      ctx.fillText(state.upperText, state.width/2-100, state.height/5);
      ctx.fillText(state.lowerText, state.width/2-100, state.height*0.75);
    }
  }

}

export default App;
