import React, { Component } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';
import Button from '@material-ui/core/Button';

class App extends Component {
  state = {
    firstName: '',
    middleName: '',
    lastName: '',
    checkedMiddle: false
  };

  handleChange = name => event => {
    this.setState({
      [name]: event.target.value
    });
  }
  handleCheck = name => event => {
    this.setState({
      [name]: event.target.checked
    });

  }

  autofill = name => event =>{
    this.setState({
      firstName: "Janis",
      middleName: "Matias",
      lastName: "Suonperä",
    });
  }

  render() {
    return (
      <div className="App">
          {/*AppBar with the exercise as title*/}
          <AppBar style={styles.appbar} position="relative">
              <Typography variant="h6" color="inherit">
                Exercise 1.1
              </Typography>
          </AppBar>
          {/*The actual text fields form*/}
          <div align="center">
            <form>
              <TextField
                id="firstName"
                label="First Name"
                value={this.state.firstName}
                margin="normal"
                onChange={this.handleChange('firstName')}
              />
              <br/>

                <Checkbox
                  style={{float:"center", marginTop:"30px"}}
                  checked={this.state.checkedMiddle}
                  onChange={this.handleCheck('checkedMiddle')}
                  value={"false"}
                />
                <TextField
                  style={{float:"center", marginRight:"50px"}}
                  disabled={!this.state.checkedMiddle}
                  id="middleName"
                  label="Middle Name"
                  value={this.state.middleName}
                  margin="normal"
                  onChange={this.handleChange('middleName')}
                />

              <br/>
              <TextField
                id="lastName"
                label="Last Name"
                value={this.state.lastName}
                margin="normal"
                onChange={this.handleChange('lastName')}
              />
              <br/>
              <Button
                variant="contained"
                color="primary"
                style={{marginTop:"10px"}}
                onClick={this.autofill()}>
                Autofill
              </Button>
            </form>
          </div>
      </div>
    );
  }
}


const styles = {
  appbar:{
    alignItems: 'center',
    color: "white",
    padding: "20px",
  }
};

export default App;
