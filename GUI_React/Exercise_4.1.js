import React, { Component } from 'react';
import './App.css';
import AppBar from '@material-ui/core/AppBar'
import ToolBar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Slider from '@material-ui/lab/Slider'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';

class App extends Component {
  constructor(props){
    super(props);
    let images = require.context('./../public/images', false, /\.(png|jpg)$/);
    let img_list = [];
    for(var i in images.keys()){
      img_list.push(images.keys()[i].slice(1))
    }
    this.state = {
      images: img_list,
      img_index: 0
    }
  }

  handleChange = (event, value) =>{
    this.setState({img_index: value})
  }

  render() {
    return (
      <div className="App">
        <AppBar style={{alignItems:"center"}}>
          <ToolBar>
            <Typography variant="h4" color="inherit" style={{flex: 1}}>
              Exercise 4.1
            </Typography>
          </ToolBar>
        </AppBar>

        <ImageField
          images={this.state.images}
          index={this.state.img_index}
        />
      <div style={{float:"center", width:"500px", margin:"auto"}}>
          <StyledSlider
            index={this.state.img_index}
            handler={this.handleChange}
          />
        </div>
      </div>
    );
  }

}

class ImageField extends Component{
  render(){
    var { images, index } = this.props;
    return(
      <img
        src={"/images" + images[index]}
        alt={index}
        style={{marginTop:"100px", marginBottom:"75px"}}
        height="350"
        width="650"
      />
    )
  }
}

class StepSlider extends Component {
  state = {
    value: this.props.index,
  };

  handleChange = (event, value) => {
    this.setState({ value });
    this.props.handler(event, value);
  };

  render() {
    const { classes } = this.props;
    const { value } = this.state;

    return (
      <div className={classes.root}>
        <Slider
          classes={{ container: classes.slider }}
          value={value}
          min={0}
          max={4}
          step={1}
          onChange={this.handleChange}
        />
      </div>
    );
  }
}
const styles = {
  root: {
    width: 500,
  },
  slider: {
    padding: '22px 0px',
  },
};

const StyledSlider = withStyles(styles)(StepSlider);


StepSlider.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default App;
