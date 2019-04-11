import React, { Component } from 'react';
import './App.css';
import Paper from '@material-ui/core/Paper';

class App extends Component {
  constructor() {
    super();
    this.state = {
      width: window.innerWidth,
      height: window.innerHeight,
      leftCenter: window.innerWidth/2-40,
      rightCenter: window.innerWidth/2+40,
      radius: 25,
      leftStart: [window.innerWidth/2-40, 100],
      rightStart: [window.innerWidth/2+40, 100]
    }
    this.myCanvas = null;
  }

  //EventListener in componentDidMount calls this when window is resized
  handleResize(){
    this.setState({
      width: window.innerWidth,
      height: window.innerHeight,
    });
  }

  handleMouseMove(event){
    //Center of left eye
    let leftX = this.state.width/2-40;
    let leftY = 100;
    //Center of right eye
    let rightX = this.state.width/2+40;
    let rightY = 100;
    //Cursor Coordinates
    let mouseX = event.clientX;
    let mouseY = event.clientY;

    //Euclidean distance between cursor and center of eyes
    let left_ed = Math.sqrt(Math.pow(mouseX-leftX, 2) + Math.pow(mouseY-leftY, 2))
    let right_ed = Math.sqrt(Math.pow(mouseX-rightX, 2) + Math.pow(mouseY-rightY, 2))
    //Ratio of distances
    let t_left = 0.5*this.state.radius/left_ed;
    let t_right = 0.5*this.state.radius/right_ed;

    //Coordinates of left moving eye
    let x1 = (1-t_left)*leftX + t_left*mouseX;
    let y1 = (1-t_left)*leftY + t_left*mouseY;

    //Coordinates of right moving eye
    let x2 = (1-t_right)*rightX + t_right*mouseX;
    let y2 = (1-t_right)*rightY + t_right*mouseY;

    //Sets the new eye coordinates and componentDidUpdate redraws the canvas
    this.setState({
      leftStart: [x1, y1],
      rightStart: [x2, y2]
    });
  }

  componentDidMount(){
    //EventListener listens to when window is resized
    window.addEventListener('resize', () => {this.handleResize()});
    window.addEventListener('mousemove', (event) => {this.handleMouseMove(event)});
    this.drawCanvas();
  }

  componentDidUpdate(){
    //Redraw if component is updated
    this.drawCanvas();
  }

  render() {
    return (
      <div className="App">
        <Paper style={{width: this.state.width, height: this.state.height}}>
          <div id="canvasDiv">
            <canvas
              width={this.state.width}
              height={this.state.height}
              ref={(el)=>{this.myCanvas = el;}}
            />
          </div>
        </Paper>
      </div>
    );
  }

  drawCanvas(){
    //Get drawing context on the canvas
    let ctx = this.myCanvas.getContext('2d');
    ctx.lineWidth = 2;
    let x = this.state.width;
    let y = this.state.height;
    let leftStart = this.state.leftStart;
    let rightStart = this.state.rightStart;

    //Clear canvas
    ctx.clearRect(0, 0, this.myCanvas.width, this.myCanvas.height);
    //================Drawing outer face=================
    ctx.beginPath();
    ctx.arc(x/2, 125, 100, 0, 2 * Math.PI);
    ctx.stroke();
    //================Outer face ends====================

    //=================Drawing smile=====================
    ctx.beginPath();
    ctx.arc(x/2, 150, 60, 0, Math.PI);
    ctx.stroke();
    //==================Smile ends=======================

    //=================Drawing eyes======================
    //Left eye
    ctx.beginPath();
    ctx.arc(x/2-40, 100, 25, 0, 2 * Math.PI);
    ctx.stroke();
    //Left eyes moving part, uses euclidean distance
    ctx.beginPath();
    ctx.arc(leftStart[0], leftStart[1], 5, 0, 2 * Math.PI);
    ctx.fill();
    ctx.stroke();

    //Right eye
    ctx.beginPath();
    ctx.arc(x/2+40, 100, 25, 0, 2 * Math.PI);
    ctx.stroke();
    //Right eyes moving part, uses euclidean distance
    ctx.beginPath();
    ctx.arc(rightStart[0], rightStart[1], 5, 0, 2 * Math.PI);
    ctx.fill();
    ctx.stroke();
    //===================Eyes end========================

  }
}

export default App;
