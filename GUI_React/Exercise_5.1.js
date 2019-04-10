import React, { Component } from 'react';
import './App.css';
import Paper from '@material-ui/core/Paper';

class App extends Component {
  constructor() {
    super();
    this.state = {
      width: window.innerWidth,
      height: window.innerHeight,
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

  componentDidMount(){
    //EventListener listens to when window is resized
    window.addEventListener('resize', () => {this.handleResize()});
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
    ctx.lineWidth = 5;
    let x = this.state.width;
    let y = this.state.heigth;
    //================Drawing the ground==================
    ctx.moveTo(0, 200);
    ctx.lineTo(this.state.width, 200);

    //================Drawing first house=================
    ctx.strokeRect(x/2-150, 100, 100, 100);
    ctx.fillRect(x/2-115, 160, 30, 40);
    ctx.moveTo(x/2-175, 100);
    ctx.lineTo(x/2-25, 100);
    ctx.lineTo(x/2-100, 50);
    ctx.closePath();
    ctx.stroke();
    //================First house ends====================

    //===============Drawing second house=================
    ctx.strokeRect(x/2+50, 100, 100, 100);
    ctx.fillRect(x/2+85, 160, 30, 40);
    ctx.moveTo(x/2+25, 100);
    ctx.lineTo(x/2+175, 100);
    ctx.lineTo(x/2+100, 50);
    ctx.closePath();
    ctx.stroke();
    //==============Second house ends=====================
  }

}

export default App;
