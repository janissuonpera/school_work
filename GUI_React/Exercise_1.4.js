import React, { Component } from 'react';
import './App.css';
import AppBar from '@material-ui/core/AppBar';
import Typography from '@material-ui/core/Typography';
import Checkbox from '@material-ui/core/Checkbox';


class App extends Component {
  constructor(props){
    super(props);
    this.outputRef = React.createRef();
  }

  state = {
    clicks: 0,
    wheel: 0,
    c1: false,
    c2: false,
    c3: false,
    output: ""
  };

  handleCheck = name => event =>{
    this.setState({
      [name]: event.target.checked
    });
    //If checked, register listener, else unregister it
    if(event.target.checked){
      this.register(name);
    }else{
      this.unregister(name);
    }
  }

  register = name => {
    switch(name){
      case "c1":
        document.getElementById("MainFrame").addEventListener("mousedown", this.handleDown, false)
        break;
      case "c2":
        document.getElementById("MainFrame").addEventListener("mousemove", this.handleMove, false)
        break;
      case "c3":
        document.getElementById("MainFrame").addEventListener("mousewheel", this.handleWheel, false)
        break;
      default:
        console.log("error in registering event");
    }
  }

  unregister = name => {
    switch(name){
      case "c1":
        document.getElementById("MainFrame").removeEventListener("mousedown", this.handleDown, false)
        break;
      case "c2":
        document.getElementById("MainFrame").removeEventListener("mousemove", this.handleMove, false)
        break;
      case "c3":
        document.getElementById("MainFrame").removeEventListener("mousewheel", this.handleWheel, false)
        break;
      default:
        console.log("error in registering event");
    }
  }

  handleDown = event => {
    this.setState({
      clicks: this.state.clicks + 1
    });
    this.outputRef.current.innerHTML = "Clicks: " + this.state.clicks;
  }

  handleMove = event =>{
    this.outputRef.current.innerHTML = "Mouse Coordinates: x:" +
    event.pageX + " y:" + event.pageY;
  }

  handleWheel = event =>{
    this.setState({
      wheel: this.state.wheel + event.wheelDeltaY
    });
    this.outputRef.current.innerHTML = "Wheel rolled: " + this.state.wheel;
  }


  render() {
    return (
      <div className="App" id="MainFrame">
        {/*AppBar with the exercise as title*/}
        <AppBar
          style={{
            alignItems: 'center',
            color: "white",
            padding: "20px",
          }}
          position="relative">
            <Typography variant="h6" color="inherit">
              Exercise 1.4
            </Typography>
        </AppBar>
        <div className="CheckBoxes">
          <span style={{marginRight:"52px"}}>
            <Checkbox
              checked={this.state.c1}
              onChange={this.handleCheck('c1')}
            /> Mouse Listener
          </span>
          <br/>
          <span>
            <Checkbox
              checked={this.state.c2}
              onChange={this.handleCheck('c2')}
            />
             Mouse Motion Listener
          </span>
          <br/>
          <span style={{marginRight:"5px"}}>
            <Checkbox
              checked={this.state.c3}
              onChange={this.handleCheck('c3')}
            />
             Mouse Wheel Listener
          </span>
        </div>
        <div
          className="Output"
          ref={this.outputRef}
        >
        {this.state.output}
        </div>
      </div>
    );
  }
}



export default App;
